package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameModelTest {

    GameModel gameModel;
    CoinsBag coinsBag;
    Player player1;
    Player player2;
    Player player3;

    @BeforeEach
    void setUp() {
        boolean threePlayers = true;
        coinsBag = new CoinsBag();
        player1 = new Player("player 1", threePlayers, coinsBag);
        player1.setAssistantDeck(Wizard.W1);
        player2 = new Player("player 2", threePlayers, coinsBag);
        player2.setAssistantDeck(Wizard.W2);
        player3 = new Player("player 3", threePlayers, coinsBag);
        player3.setAssistantDeck(Wizard.W3);
        gameModel = new GameModel(List.of(player1, player2, player3));
    }

    @AfterEach
    void tearDown() {
        coinsBag = null;
        player1 = null;
        player2 = null;
        gameModel = null;
    }

    @Test
    public void calculatePlayersOrder_WithPlayer3UsingLowestAssistant_ShouldBeFirstPlayer(){
        player1.useAssistant(Assistant.CARD_9);
        player2.useAssistant(Assistant.CARD_5);
        player3.useAssistant(Assistant.CARD_1);

        gameModel.calculatePlayersOrder();

        assertEquals("player 3", gameModel.getCurrentPlayer().getNickname());
    }

    @Test
    public void nextPlayerTurn_WithPlayerOrder123_ShouldSetCurrentPlayerAs2(){
        gameModel.nextPlayerTurn();
        assertEquals("player 2", gameModel.getCurrentPlayer().getNickname());
    }

}