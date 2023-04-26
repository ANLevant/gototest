package com.test.gototechtest.dto;

import com.test.gototechtest.persistance.entities.Card;
import com.test.gototechtest.persistance.entities.Shoe;

public class ShoeStatisticDTO {

    private Long id;
    private int numberOfHeartsRemaining;
    private int numberOfSpadesRemaining;
    private int numberOfClubsRemaining;
    private int numberOfDiamondsRemaining;

    public ShoeStatisticDTO(Shoe shoeEntity) {
        id = shoeEntity.getId();
        Card[] cardsBuffer = new Card[shoeEntity.getCards().size()];

        for (Card card : shoeEntity.getCards().toArray(cardsBuffer)) {
            switch (card.getCardSuite()) {
                case HEARTS:
                    numberOfHeartsRemaining++;
                    break;
                case SPADES:
                    numberOfSpadesRemaining++;
                    break;
                case CLUBS:
                    numberOfClubsRemaining++;
                    break;
                case DIAMONDS:
                    numberOfDiamondsRemaining++;
                    break;

            }
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfHeartsRemaining() {
        return numberOfHeartsRemaining;
    }

    public void setNumberOfHeartsRemaining(int numberOfHeartsRemaining) {
        this.numberOfHeartsRemaining = numberOfHeartsRemaining;
    }

    public int getNumberOfSpadesRemaining() {
        return numberOfSpadesRemaining;
    }

    public void setNumberOfSpadesRemaining(int numberOfSpadesRemaining) {
        this.numberOfSpadesRemaining = numberOfSpadesRemaining;
    }

    public int getNumberOfClubsRemaining() {
        return numberOfClubsRemaining;
    }

    public void setNumberOfClubsRemaining(int numberOfClubsRemaining) {
        this.numberOfClubsRemaining = numberOfClubsRemaining;
    }

    public int getNumberOfDiamondsRemaining() {
        return numberOfDiamondsRemaining;
    }

    public void setNumberOfDiamondsRemaining(int numberOfDiamondsRemaining) {
        this.numberOfDiamondsRemaining = numberOfDiamondsRemaining;
    }
}

