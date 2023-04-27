package com.test.gototechtest.multithread;

import com.test.gototechtest.dto.GameDTO;
import com.test.gototechtest.dto.PlayerDTO;
import com.test.gototechtest.error.EntityDoesntExistException;
import com.test.gototechtest.error.NotEnoughCardsInShoeExistException;
import com.test.gototechtest.service.GameService;

public class CardDrawerThread extends Thread {

    private GameService callbackGameService;

    private GameDTO gameDTOParam;
    private PlayerDTO playerDTOParam;
    private int cardsToDealParam;

    public CardDrawerThread(GameService callbackGameService, GameDTO gameDTOParam, PlayerDTO playerDTOParam, int cardsToDealParam) {
        super();

        this.callbackGameService = callbackGameService;
        this.gameDTOParam = gameDTOParam;
        this.playerDTOParam = playerDTOParam;
        this.cardsToDealParam = cardsToDealParam;
    }

    public void run() {
        while (true) {
            try {
                callbackGameService.dealCardsToPlayerThread(gameDTOParam, playerDTOParam, cardsToDealParam);
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (EntityDoesntExistException e) {
                throw new RuntimeException(e);
            } catch (NotEnoughCardsInShoeExistException e) {
                /**Hide exception since it means shoe is empty and execution of thread ends*/
                return;
            }
        }
    }
}
