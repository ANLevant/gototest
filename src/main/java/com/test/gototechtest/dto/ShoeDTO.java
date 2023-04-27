package com.test.gototechtest.dto;

import com.test.gototechtest.persistance.entities.Card;
import com.test.gototechtest.persistance.entities.Shoe;

import java.util.ArrayList;
import java.util.List;

public class ShoeDTO {

    private Long id;
    private int numberOfDecks;
    private List<Long> cardIds;

    public ShoeDTO() {
        cardIds = new ArrayList<>();
    }

    public ShoeDTO(Shoe shoe) {
        id = shoe.getId();
        numberOfDecks = shoe.getNumberOfDecks();
        cardIds = new ArrayList<>();

        for (Card card : shoe.getCards()) {
            cardIds.add(card.getId());
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public int getNumberOfDecks() {
        return numberOfDecks;
    }

    public void setNumberOfDecks(int numberOfDecks) {
        this.numberOfDecks = numberOfDecks;
    }

    public List<Long> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<Long> cardIds) {
        this.cardIds = cardIds;
    }

    public Shoe toEntity() {
        Shoe shoe = new Shoe();

        shoe.setId(id);
        shoe.setNumberOfDecks(numberOfDecks);

        return shoe;
    }
}
