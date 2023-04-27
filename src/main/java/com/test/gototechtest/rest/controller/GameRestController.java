package com.test.gototechtest.rest.controller;

import com.test.gototechtest.dto.GameDTO;
import com.test.gototechtest.dto.GameStateDTO;
import com.test.gototechtest.dto.PlayerDTO;
import com.test.gototechtest.dto.ShoeStatisticDTO;
import com.test.gototechtest.error.EntityDoesntExistException;
import com.test.gototechtest.service.GameService;
import com.test.gototechtest.service.PlayerService;
import com.test.gototechtest.service.ShoeConcurrentWrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("game")
public class GameRestController {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ShoeConcurrentWrapperService shoeConcurrentWrapperService;

    @PutMapping
    public GameDTO createGame() throws EntityDoesntExistException {
        return gameService.createGame();
    }

    @GetMapping("/{id}")
    public GameStateDTO getGame(@PathVariable Long id) throws EntityDoesntExistException {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(id);

        return gameService.getGameState(gameDTO);
    }

    @PutMapping("/{id}")
    public void deleteGame(@PathVariable Long id) throws EntityDoesntExistException {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(id);

        gameService.deleteGame(gameDTO);
    }

    @PutMapping("/{id}/deck")
    public void addDeckToShoe(@PathVariable Long id) throws EntityDoesntExistException {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(id);

        shoeConcurrentWrapperService.addDeckToShoe(gameDTO);
    }

    @GetMapping("/{id}/deck")
    public ShoeStatisticDTO getDeckStatistics(@PathVariable Long id) throws EntityDoesntExistException {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(id);

        return gameService.calculateDeckStatistics(gameDTO);
    }

    @PutMapping("/{id}/player")
    public PlayerDTO addPlayerToGame(@PathVariable Long id) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(id);

        return playerService.createPLayer(gameDTO);
    }

    @PutMapping("{id}/player/{playerId}")
    public PlayerDTO dealCardsToPlayer(@PathVariable Long id, @PathVariable Long playerId, @RequestParam int cardsToDeal) throws InterruptedException, EntityDoesntExistException {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(id);

        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(playerId);

        return gameService.dealCardsToPlayer(gameDTO, playerDTO, cardsToDeal);
    }
}
