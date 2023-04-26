package com.test.gototechtest.rest.controller;

import com.test.gototechtest.dto.GameDTO;
import com.test.gototechtest.dto.PlayerDTO;
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
    public GameDTO getGame(@PathVariable Long id) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(id);
        return gameService.getGame(gameDTO);
    }

    @PutMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(id);
        gameService.deleteGame(gameDTO);
    }

    @PutMapping("/{id}/player")
    public PlayerDTO addPlayerToGame(@PathVariable Long id) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(id);
        return playerService.createPLayer(gameDTO);
    }

    @GetMapping("/player/{id}")
    public PlayerDTO getPlayer(@PathVariable Long id) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(id);

        return playerService.getPlayer(playerDTO);
    }


    @PutMapping("player/{id}")
    public void deletePlayerFromGame(@PathVariable Long id) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(id);
        playerService.deletePlayer(playerDTO);
    }
}
