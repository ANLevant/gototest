package com.test.gototechtest.service;

import com.test.gototechtest.dto.GameDTO;
import com.test.gototechtest.dto.PlayerDTO;
import com.test.gototechtest.persistance.dao.CardDAO;
import com.test.gototechtest.persistance.dao.GameDAO;
import com.test.gototechtest.persistance.dao.PlayerDAO;
import com.test.gototechtest.persistance.dao.ShoeDAO;
import com.test.gototechtest.persistance.entities.Card;
import com.test.gototechtest.persistance.entities.Game;
import com.test.gototechtest.persistance.entities.Player;
import com.test.gototechtest.persistance.entities.Shoe;
import com.test.gototechtest.util.DeckFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerDAO playerDAO;

    public PlayerDTO createPLayer(GameDTO gameDTO)
    {
        Player player = new Player();

        player.setGame(new Game(gameDTO));

        return new PlayerDTO(playerDAO.save(player));
    }

    public void deletePlayer(PlayerDTO playerDTO)
    {
        Player player = new Player(playerDTO);
        playerDAO.delete(player);
    }

    public PlayerDTO getPlayer(PlayerDTO playerDTO)
    {
        Optional<Player> player = playerDAO.findById(playerDTO.getId());
        return new PlayerDTO(player.get());
    }
}
