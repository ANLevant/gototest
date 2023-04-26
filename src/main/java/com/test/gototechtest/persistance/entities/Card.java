package com.test.gototechtest.persistance.entities;

import com.test.gototechtest.enumeration.CardSuite;
import com.test.gototechtest.enumeration.CardValue;
import jakarta.persistence.*;

@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "card_value")
    private CardValue cardValue;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "card_suite")
    private CardSuite cardSuite;

    @ManyToOne
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "shoe_id")
    private Shoe shoe;

    public Card() {
    }

    public Card(CardValue cardValue, CardSuite cardSuite) {
        this.cardValue = cardValue;
        this.cardSuite = cardSuite;
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

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
