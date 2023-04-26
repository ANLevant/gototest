package com.test.gototechtest.dto;

import com.test.gototechtest.enumeration.CardSuite;
import com.test.gototechtest.enumeration.CardValue;
import com.test.gototechtest.persistance.entities.Card;

public class CardDTO {

    private Long id;
    private CardValue cardValue;
    private CardSuite cardSuite;
    private Long playerId;

    private Long shoeId;

    public CardDTO() {
    }

    public CardDTO(Card card) {
        this.cardValue = card.getCardValue();
        this.cardSuite = card.getCardSuite();
        this.id = card.getId();
        shoeId = card.getShoe().getId();

        if (card.getPlayer() != null) {

            playerId = card.getPlayer().getId();
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public CardValue getCardValue() {
        return cardValue;
    }

    public void setCardValue(CardValue cardValue) {
        this.cardValue = cardValue;
    }

    public CardSuite getCardSuite() {
        return cardSuite;
    }

    public void setCardSuite(CardSuite cardSuite) {
        this.cardSuite = cardSuite;
    }

    public Long getShoeId() {
        return shoeId;
    }

    public void setShoeId(Long shoeId) {
        this.shoeId = shoeId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
}
