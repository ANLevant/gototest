package com.test.gototechtest.service;

import com.test.gototechtest.dto.*;
import com.test.gototechtest.error.EntityDoesntExistException;
import com.test.gototechtest.error.NotEnoughCardsInShoeExistException;
import com.test.gototechtest.multithread.CardDrawerThread;
import com.test.gototechtest.persistance.dao.GameDAO;
import com.test.gototechtest.persistance.entities.Game;
import com.test.gototechtest.persistance.entities.Shoe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameDAO gameDAO;
    @Autowired
    private ShoeConcurrentWrapperService shoeConcurrentWrapperService;

    @Autowired
    private PlayerService playerService;

    public GameDTO createGame() throws EntityDoesntExistException {
        Game game = new Game();

        ShoeDTO createdShoe = shoeConcurrentWrapperService.createDatabaseShoe();

        Shoe shoe = createdShoe.toEntity();
        game.setShoe(shoe);

        GameDTO gameDTO = new GameDTO(gameDAO.save(game));

        shoeConcurrentWrapperService.createShoeGameWrapper(createdShoe, gameDTO);

        return gameDTO;
    }

    public void deleteGame(GameDTO gameDTO) throws EntityDoesntExistException {
        Game game = new Game(gameDTO);
        gameDAO.delete(game);

        ShoeDTO gameShoeDTO = shoeConcurrentWrapperService.getShoeDTO(gameDTO.getShoeId());

        shoeConcurrentWrapperService.removeShoeGameWrapper(gameShoeDTO, gameDTO);
    }

    public GameStateDTO getGameState(GameDTO gameDTO) throws EntityDoesntExistException {
        Optional<Game> game = gameDAO.findById(gameDTO.getId());

        if (game.isPresent()) {
            Game gameFound = game.get();

            GameStateDTO gameStateDTO = new GameStateDTO(gameFound);

            return gameStateDTO;
        }

        throw new EntityDoesntExistException("Game doesn't exist!");
    }

    public ShoeStatisticDTO calculateDeckStatistics(GameDTO gameDTO) throws EntityDoesntExistException {
        Optional<Game> game = gameDAO.findById(gameDTO.getId());
        if (game.isPresent()) {
            Shoe shoe = game.get().getShoe();

            return new ShoeStatisticDTO(shoe);
        }

        throw new EntityDoesntExistException("Game doesn't exist!");
    }

    public void fireCardDrawThread(GameDTO gameDTO, PlayerDTO playerDTO, int cardsToDeal) throws InterruptedException, EntityDoesntExistException, NotEnoughCardsInShoeExistException {
        CardDrawerThread thread = new CardDrawerThread(this, gameDTO, playerDTO, cardsToDeal);
        thread.start();
    }

    public PlayerDTO dealCardsToPlayer(GameDTO gameDTO, PlayerDTO playerDTO, int cardsToDeal) throws InterruptedException, EntityDoesntExistException, NotEnoughCardsInShoeExistException {
        List<CardDTO> drawnCards = shoeConcurrentWrapperService.drawCards(cardsToDeal, gameDTO);
        return playerService.addCardsToHand(drawnCards, playerDTO);
    }


}
