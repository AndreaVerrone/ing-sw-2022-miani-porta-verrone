package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class MoveMotherNatureStateTest {

    private Game game;
    private MoveMotherNatureState state;

    @BeforeEach
    void setUp() {
        //Create first player
        PlayerLoginInfo playerLoginInfo1 = new PlayerLoginInfo("Marco");
        playerLoginInfo1.setWizard(Wizard.W1);
        //Create second player
        PlayerLoginInfo playerLoginInfo2 = new PlayerLoginInfo("Mattia");
        Collection<PlayerLoginInfo> collection = new ArrayList<>();
        collection.add(playerLoginInfo1);
        collection.add(playerLoginInfo2);
        //Create game
        game = new Game(collection);
        //Use an assistant card with the first player
        Assistant assistant1 = Assistant.CARD_7;
        game.getModel().getPlayerList().get(0).useAssistant(assistant1);
        //Create the state
        state = new MoveMotherNatureState(game);
    }

    @AfterEach
    void tearDown() {
        game = null;
        state = null;
    }

    @Test
    void moveMotherNature_allowedNumberOfPositions_ShouldMove() {
        //Mother nature starts from position zero
        try {
            state.moveMotherNature(4);
            //Now it should be on position four
            assertEquals(4, game.getModel().getGameTable().getMotherNaturePosition());
        } catch (NotValidArgumentException e) {
            fail();
        }
    }

    @Test
    void moveMotherNature_notPositiveNumberOfPositions_ShouldThrow(){
        assertThrows(NotValidArgumentException.class, () -> state.moveMotherNature(0));
    }

    @Test
    void moveMotherNature_overLimitNumberOfPositions_ShouldThrow(){
        assertThrows(NotValidArgumentException.class, () -> state.moveMotherNature(5));
    }
}