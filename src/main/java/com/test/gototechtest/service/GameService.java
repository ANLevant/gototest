package com.test.gototechtest.service;

import com.test.gototechtest.dto.GameDTO;
import com.test.gototechtest.dto.PlayerDTO;
import com.test.gototechtest.persistance.dao.CardDAO;
import com.test.gototechtest.persistance.dao.GameDAO;
import com.test.gototechtest.persistance.dao.PlayerDAO;
import com.test.gototechtest.persistance.dao.ShoeDAO;
import com.test.gototechtest.persistance.entities.Card;
import com.test.gototechtest.persistance.entities.Game;
import com.test.gototechtest.persistance.entities.Player;
import com.test.gototechtest.persistance.entities.Shoe;
import com.test.gototechtest.util.DeckFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private ShoeDAO shoeDAO;

    @Autowired
    private CardDAO cardDAO;

    @Autowired
    private PlayerDAO playerDAO;

    @Autowired
    private DeckFactory deckFactory;

    public GameDTO createGame() {
        Game game = new Game();
        Shoe shoe = new Shoe();

        shoe = shoeDAO.save(shoe);

        addDeckToShoe(shoe);

        game.setShoe(shoe);

        return new GameDTO(gameDAO.save(game));
    }

    public void deleteGame(GameDTO gameDTO) {
        Game game = new Game(gameDTO);
        gameDAO.delete(game);
    }

    public GameDTO getGame(GameDTO gameDTO) {
        Optional<Game> game = gameDAO.findById(gameDTO.getId());
        return new GameDTO(game.get());
    }

    public GameDTO addDeckToShoe(GameDTO gameDTO) {
        Optional<Game> game = gameDAO.findById(gameDTO.getId());
        Shoe shoe = game.get().getShoe();

        addDeckToShoe(shoe);

        return new GameDTO();
    }

    public PlayerDTO dealCardsToPlayer(Long id, Long playerId, Long amoutOfCardsToDeal) {
        Optional<Game> game = gameDAO.findById(id);
        Shoe shoe = game.get().getShoe();
        shoe.shuffle();

        Optional<Player> player = playerDAO.findById(playerId);

        for (int i = 0; i < amoutOfCardsToDeal; i++) {
            Card card = shoe.getCards().pop();
            card.setPlayer(player.get());

            card.setShoe(null);

            cardDAO.save(card);
        }

        player = playerDAO.findById(playerId);

        return new PlayerDTO(player.get());
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
