package com.test.gototechtest.service;

import com.test.gototechtest.dto.CardDTO;
import com.test.gototechtest.dto.GameDTO;
import com.test.gototechtest.dto.PlayerDTO;
import com.test.gototechtest.error.EntityDoesntExistException;
import com.test.gototechtest.persistance.dao.CardDAO;
import com.test.gototechtest.persistance.dao.PlayerDAO;
import com.test.gototechtest.persistance.entities.Card;
import com.test.gototechtest.persistance.entities.Game;
import com.test.gototechtest.persistance.entities.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerDAO playerDAO;

    @Autowired
    private CardDAO cardDAO;

    public PlayerDTO createPLayer(GameDTO gameDTO) {
        Player player = new Player();

        player.setGame(new Game(gameDTO));

        return new PlayerDTO(playerDAO.save(player));
    }

    public void deletePlayer(PlayerDTO playerDTO) {
        Player player = new Player(playerDTO);
        playerDAO.delete(player);
    }

    public PlayerDTO getPlayer(PlayerDTO playerDTO) throws EntityDoesntExistException {
        Optional<Player> player = playerDAO.findById(playerDTO.getId());
        if (player.isPresent()) {
            return new PlayerDTO(player.get());
        }
        throw new EntityDoesntExistException("Player doesn't exist!");
    }

    public List<CardDTO> getPlayerCards(PlayerDTO playerDTO) throws EntityDoesntExistException {
        Optional<Player> player = playerDAO.findById(playerDTO.getId());

        if (player.isPresent()) {
            List<CardDTO> playerHandDTO = new ArrayList<>();

            for (Card card : player.get().getCards()) {
                playerHandDTO.add(new CardDTO(card));
            }

            return playerHandDTO;
        }

        throw new EntityDoesntExistException("Shoe doesn't exist!");
    }

    public PlayerDTO addCardsToHand(List<CardDTO> cardsToAdd, PlayerDTO playerDTO) throws EntityDoesntExistException {
        Optional<Player> player = playerDAO.findById(playerDTO.getId());

        if (player.isPresent()) {
            for (CardDTO cardDTO : cardsToAdd) {
                Optional<Card> card = cardDAO.findById(cardDTO.getId());

                if (card.isPresent()) {
                    card.get().setPlayer(player.get());
                }

                cardDAO.save(card.get());
            }

            return new PlayerDTO(player.get());
        }

        throw new EntityDoesntExistException("Player doesn't exist!");

    }
}
