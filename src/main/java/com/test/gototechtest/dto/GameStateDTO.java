package com.test.gototechtest.dto;

import com.test.gototechtest.persistance.entities.Game;
import com.test.gototechtest.persistance.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class GameStateDTO {

    private Long id;
    private List<PlayerStateDTO> playerStateDTOS;

    public GameStateDTO(Game gameEntity) {
        id = gameEntity.getId();
        playerStateDTOS = new ArrayList<>();

        for (Player player : gameEntity.getPlayers()) {
            playerStateDTOS.add(new PlayerStateDTO(player));
        }

        quickSort(playerStateDTOS, 0, playerStateDTOS.size() - 1);
    }

    public void quickSort(List<PlayerStateDTO> arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private int partition(List<PlayerStateDTO> arr, int begin, int end) {
        long pivot = arr.get(end).getScore();
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr.get(j).getScore() >= pivot) {
                i++;

                PlayerStateDTO swapTemp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, swapTemp);
            }
        }

        PlayerStateDTO swapTemp = arr.get(i + 1);
        arr.set(i + 1, arr.get(end));
        arr.set(end, swapTemp);

        return i + 1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PlayerStateDTO> getPlayerStateDTOS() {
        return playerStateDTOS;
    }

    public void setPlayerStateDTOS(List<PlayerStateDTO> playerStateDTOS) {
        this.playerStateDTOS = playerStateDTOS;
    }
}

