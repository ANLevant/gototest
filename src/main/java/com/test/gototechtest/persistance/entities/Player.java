package com.test.gototechtest.persistance.entities;

import com.test.gototechtest.dto.PlayerDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "player")
    private List<Card> cards;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    public Player() {
        cards = new ArrayList<>();
    }

    public Player(PlayerDTO playerDTO){
        id = playerDTO.getId();
        cards = new ArrayList<>();
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
