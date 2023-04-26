package com.test.gototechtest.dto;

import com.test.gototechtest.persistance.entities.Game;
import com.test.gototechtest.persistance.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class GameDTO {

    private Long id;
    private Long shoeId;
    private List<Long> playerIdInGame;

    public GameDTO() {
    }

    public GameDTO(Game gameEntity) {
        id = gameEntity.getId();
        shoeId = gameEntity.getShoe().getId();
        playerIdInGame = new ArrayList<>();

        for(Player player : gameEntity.getPlayers()) {
            playerIdInGame.add(player.getId());
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getPlayerIdInGame() {
        return playerIdInGame;
    }

    public void setPlayerIdInGame(List<Long> playerIdInGame) {
        this.playerIdInGame = playerIdInGame;
    }
}
