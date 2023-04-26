package com.test.gototechtest.rest.controller;

import com.test.gototechtest.dto.GameDTO;
import com.test.gototechtest.dto.GameStateDTO;
import com.test.gototechtest.dto.PlayerDTO;
import com.test.gototechtest.dto.ShoeStatisticDTO;
import com.test.gototechtest.service.GameService;
import com.test.gototechtest.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("game")
public class GameRestController {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @PutMapping
    public GameDTO createGame() {
        return gameService.createGame();
    }

    @GetMapping("/{id}")
    public GameStateDTO getGame(@PathVariable Long id) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(id);
        return gameService.getGameState(gameDTO);
    }

    @PutMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(id);
        gameService.deleteGame(gameDTO);
    }

    @PutMapping("/{id}/deck")
    public void addDeckToShoe(@PathVariable Long id) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(id);
        gameService.addDeckToShoe(gameDTO);
    }

    @GetMapping("/{id}/deck")
    public ShoeStatisticDTO getDeckStatistics(@PathVariable Long id) {
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
    public PlayerDTO dealCardsToPlayer(@PathVariable Long id, @PathVariable Long playerId, @RequestParam Long amoutOfCardsToDeal) {
        return gameService.dealCardsToPlayer(id, playerId, amoutOfCardsToDeal);
    }
}
