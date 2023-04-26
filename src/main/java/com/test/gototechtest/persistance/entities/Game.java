package com.test.gototechtest.persistance.entities;

import com.test.gototechtest.dto.GameDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "game")
    private List<Player> players;

    @OneToOne
    @JoinColumn(name="shoe_id", nullable=false)
    private Shoe shoe;

    public Game()
    {
        players = new ArrayList<>();
    }

    public Game(GameDTO gameDTO)
    {
        id = gameDTO.getId();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> playerInGame) {
        this.players = playerInGame;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }
}
