package com.test.gototechtest.dto;

import com.test.gototechtest.persistance.entities.Card;
import com.test.gototechtest.persistance.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerDTO {

    private Long id;

    private List<Long> cardIds;

    private Long gameId;

    public PlayerDTO()
    {
        cardIds = new ArrayList<>();
    }

    public PlayerDTO(Player player)
    {
        cardIds = new ArrayList<>();

        id = player.getId();
        gameId = player.getId();

        for(Card card : player.getCards())
        {
            cardIds.add(card.getId());
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<Long> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<Long> cardIds) {
        this.cardIds = cardIds;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
