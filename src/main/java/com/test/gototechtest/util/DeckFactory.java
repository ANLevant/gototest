package com.test.gototechtest.util;

import com.test.gototechtest.persistance.entities.Card;
import com.test.gototechtest.enumeration.CardSuite;
import com.test.gototechtest.enumeration.CardValue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeckFactory {

    public List<Card> getDeck()
    {
        List<Card> deckCards = new ArrayList<>();

        for(CardSuite suite : CardSuite.values())
        {
            for(CardValue value : CardValue.values())
            {
                deckCards.add(new Card(value, suite));
            }
        }

        return deckCards;
    }
}
