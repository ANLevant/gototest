package com.test.gototechtest.rest.controller;

import com.test.gototechtest.dto.CardDTO;
import com.test.gototechtest.dto.PlayerDTO;
import com.test.gototechtest.error.EntityDoesntExistException;
import com.test.gototechtest.error.NotEnoughCardsInShoeExistException;
import com.test.gototechtest.service.PlayerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("player")
public class PlayerRestController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/{id}")
    public PlayerDTO getPlayer(@PathVariable Long id) throws EntityDoesntExistException {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(id);

        return playerService.getPlayer(playerDTO);
    }

    @PutMapping("/{id}")
    public void deletePlayerFromGame(@PathVariable Long id) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(id);
        playerService.deletePlayer(playerDTO);
    }

    @GetMapping("/{id}/cards")
    public List<CardDTO> getPlayerCards(@PathVariable Long id) throws EntityDoesntExistException {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(id);

        return playerService.getPlayerCards(playerDTO);
    }

    @ExceptionHandler({EntityDoesntExistException.class, NotEnoughCardsInShoeExistException.class})
    public void handleException(HttpServletResponse res, Exception ex) throws IOException {
        ex.printStackTrace();
        res.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
    }
}
