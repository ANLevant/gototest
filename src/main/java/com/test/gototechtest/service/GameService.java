package com.test.gototechtest.service;

import com.test.gototechtest.dto.GameDTO;
import com.test.gototechtest.persistance.dao.CardDAO;
import com.test.gototechtest.persistance.dao.GameDAO;
import com.test.gototechtest.persistance.dao.ShoeDAO;
import com.test.gototechtest.persistance.entities.Card;
import com.test.gototechtest.persistance.entities.Game;
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
    private DeckFactory deckFactory;

    public GameDTO createGame()
    {
        Game game = new Game();
        Shoe shoe = new Shoe();

        shoe = shoeDAO.save(shoe);

        List<Card> initialDeck = createInitialDeck(shoe);

        shoe.addDeck(initialDeck);

        game.setShoe(shoe);

        return new GameDTO(gameDAO.save(game));
    }

    public void deleteGame(GameDTO gameDTO)
    {
        Game game = new Game(gameDTO);
        gameDAO.delete(game);
    }

    public GameDTO getGame(GameDTO gameDTO)
    {
        Optional<Game> game = gameDAO.findById(gameDTO.getId());
        return new GameDTO(game.get());
    }

    private List<Card> createInitialDeck(Shoe shoe)
    {
        List<Card> cards = deckFactory.getDeck();
        List<Card> cardsWithIds = new ArrayList<>();

        for(Card card : cards)
        {
            card.setShoe(shoe);
            cardsWithIds.add(cardDAO.save(card));
        }

        return cardsWithIds;
    }
}
