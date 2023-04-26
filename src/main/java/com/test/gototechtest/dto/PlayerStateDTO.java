package com.test.gototechtest.dto;

import com.test.gototechtest.persistance.entities.Card;
import com.test.gototechtest.persistance.entities.Player;

public class PlayerStateDTO {

    private Long id;

    private long score;

    public PlayerStateDTO(Player player) {

        id = player.getId();

        for (Card card : player.getCards()) {
            score += card.getCardValue().getNumericValue();
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
