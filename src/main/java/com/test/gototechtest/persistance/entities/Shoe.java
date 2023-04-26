package com.test.gototechtest.persistance.entities;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

@Entity
@Table(name = "shoe")
public class Shoe {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "shoe")
    private List<Card> cards;
    @Column(name = "number_of_decks")
    private int numberOfDecks;

    public Shoe()
    {
        cards = new Stack<>();
    }

    public void addDeck(List<Card> deck)
    {
        Stack cardsAsStack = getCards();

        for(Card card : deck)
        {
            cardsAsStack.push(card);
        }

        cards = cardsAsStack;
        numberOfDecks++;
    }

    public void shuffle()
    {
        Card[] cardsArray = (Card[])cards.toArray();
        Collections.shuffle(cards);

        Stack shuffledStack = new Stack<>();

        for(Card card : cardsArray)
        {
            shuffledStack.push(card);
        }

        cards = shuffledStack;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Stack<Card> getCards() {
        Stack<Card> cardsAsStack = new Stack<>();

        for(Card card : cards)
        {
            cardsAsStack.push(card);
        }

        return cardsAsStack;
    }

    public void setCards(Stack<Card> cards) {
        this.cards = cards;
    }

    public int getNumberOfDecks() {
        return numberOfDecks;
    }

    public void setNumberOfDecks(int numberOfDecks) {
        this.numberOfDecks = numberOfDecks;
    }
}
