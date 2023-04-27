package com.test.gototechtest.service;

import com.test.gototechtest.dto.CardDTO;
import com.test.gototechtest.dto.GameDTO;
import com.test.gototechtest.dto.ShoeDTO;
import com.test.gototechtest.error.EntityDoesntExistException;
import com.test.gototechtest.error.NotEnoughCardsInShoeExistException;
import com.test.gototechtest.persistance.dao.CardDAO;
import com.test.gototechtest.persistance.dao.GameDAO;
import com.test.gototechtest.persistance.dao.ShoeDAO;
import com.test.gototechtest.persistance.entities.Card;
import com.test.gototechtest.persistance.entities.Game;
import com.test.gototechtest.persistance.entities.Shoe;
import com.test.gototechtest.util.DeckFactory;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Semaphore;

/**
 * I gave this a lot of thought.
 * I will have to make this stateful, sadly violating the Stateless principle of REST
 * https://restfulapi.net/statelessness/
 * This problem is probably best suited for websockets
 */
@Service
public class ShoeConcurrentWrapperService {

    private HashMap<Long, Pair<Shoe, Semaphore>> gameContainerMap;

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private ShoeDAO shoeDAO;
    @Autowired
    private CardDAO cardDAO;

    @Autowired
    private DeckFactory deckFactory;

    public ShoeConcurrentWrapperService() {
        gameContainerMap = new HashMap<Long, Pair<Shoe, Semaphore>>();
    }

    public ShoeDTO getShoeDTO(Long shoeId) throws EntityDoesntExistException {
        Optional<Shoe> shoe = shoeDAO.findById(shoeId);
        if (shoe.isPresent()) {
            return new ShoeDTO(shoe.get());
        }

        throw new EntityDoesntExistException("Shoe doesn't exist!");
    }

    public void createShoeGameWrapper(ShoeDTO shoeDTO, GameDTO gameDTO) throws EntityDoesntExistException {
        Optional<Shoe> shoe = shoeDAO.findById(shoeDTO.getId());
        if (shoe.isPresent()) {
            gameContainerMap.put(gameDTO.getId(), new Pair<Shoe, Semaphore>(shoe.get(), new Semaphore(1)));
        } else {
            throw new EntityDoesntExistException("Shoe doesn't exist!");
        }
    }

    public void removeShoeGameWrapper(ShoeDTO shoeDTO, GameDTO gameDTO) throws EntityDoesntExistException {
        Optional<Shoe> shoe = shoeDAO.findById(shoeDTO.getId());
        if (shoe.isPresent() && gameContainerMap.containsKey(gameDTO.getId())) {
            gameContainerMap.remove(gameDTO.getId());
            shoeDAO.delete(shoe.get());
        } else {
            throw new EntityDoesntExistException("Shoe doesn't exist!");
        }
    }

    public ShoeDTO createDatabaseShoe() {
        Shoe shoe = new Shoe();

        shoe = shoeDAO.save(shoe);
        addDeckToShoe(shoe);

        return new ShoeDTO(shoe);
    }

    public GameDTO addDeckToShoe(GameDTO gameDTO) throws EntityDoesntExistException, InterruptedException {
        Optional<Game> game = gameDAO.findById(gameDTO.getId());
        if (game.isPresent() && gameContainerMap.containsKey(gameDTO.getId())) {
            Pair<Shoe, Semaphore> syncedShoes = gameContainerMap.get(gameDTO.getId());
            syncedShoes.getValue1().acquire();

            addDeckToShoe(syncedShoes.getValue0());

            gameContainerMap.remove(gameDTO.getId());
            gameContainerMap.put(gameDTO.getId(), syncedShoes);

            syncedShoes.getValue1().release();

            return new GameDTO(game.get());
        }

        throw new EntityDoesntExistException("Game doesn't exist!");
    }

    public List<CardDTO> drawCards(int cardsToDraw, GameDTO gameDTO) throws InterruptedException, EntityDoesntExistException, NotEnoughCardsInShoeExistException {
        if (gameContainerMap.containsKey(gameDTO.getId())) {
            Pair<Shoe, Semaphore> syncedShoed = gameContainerMap.get(gameDTO.getId());
            if (syncedShoed.getValue0().getCards().size() > cardsToDraw) {
                syncedShoed.getValue1().acquire();
                List<CardDTO> cardsDrawn = new ArrayList<>();

                syncedShoed.getValue0().shuffle();

                for (int i = 0; i < cardsToDraw; i++) {
                    Card card = syncedShoed.getValue0().getCards().pop();
                    card.setShoe(null);
                    cardsDrawn.add(new CardDTO(cardDAO.save(card)));
                }

                syncedShoed.getValue1().release();
                return cardsDrawn;
            } else {
                throw new NotEnoughCardsInShoeExistException("Not enough Cards in Shoe!");
            }
        }
        throw new EntityDoesntExistException("Shoe doesn't exist!");
    }

    private void addDeckToShoe(Shoe shoe) {
        List<Card> cards = deckFactory.getDeck();
        List<Card> cardsWithIds = new ArrayList<>();

        for (Card card : cards) {
            card.setShoe(shoe);
            cardsWithIds.add(cardDAO.save(card));

            cardDAO.save(card);
        }

        shoe.addDeck(cardsWithIds);
    }

}
