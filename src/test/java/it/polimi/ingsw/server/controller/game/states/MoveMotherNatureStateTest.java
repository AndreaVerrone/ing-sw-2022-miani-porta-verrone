package it.polimi.ingsw.server.controller.game.states;

import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.Game;
import it.polimi.ingsw.server.model.player.Assistant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class MoveMotherNatureStateTest {

    private Collection<PlayerLoginInfo> players = new ArrayList<>();
    private Game game;


    @BeforeEach
    void setUp() {
        //Create first player
        PlayerLoginInfo playerLoginInfo1 = new PlayerLoginInfo("Marco");
        //Create second player
        PlayerLoginInfo playerLoginInfo2 = new PlayerLoginInfo("Mattia");
        players.add(playerLoginInfo1);
        players.add(playerLoginInfo2);
        game = new Game(players);//Create game
        game.getModel().getCurrentPlayer().useAssistant(Assistant.CARD_7);
        //Create the state
        game.setState(game.getMoveMotherNatureState());
    }

    @AfterEach
    void tearDown() {
        game = null;
        players = null;
    }

    @Test
    void moveMotherNature_allowedNumberOfPositions_ShouldMove() {
        // initial position of mother nature
        int motherNaturePosition = game.getModel().getGameTable().getMotherNaturePosition();

        // the number of islands
        int numOfIslands =game.getModel().getGameTable().getNumberOfIslands();

        try {
            game.moveMotherNature(4);
            //Now it should be on position four
            assertEquals((motherNaturePosition+4)%numOfIslands, game.getModel().getGameTable().getMotherNaturePosition());
        } catch (NotValidArgumentException | NotValidOperationException e) {
            fail();
        }
    }

    @Test
    void moveMotherNature_notPositiveNumberOfPositions_ShouldThrow(){
        assertThrows(NotValidArgumentException.class, () -> game.moveMotherNature(0));
    }

    @Test
    void moveMotherNature_overLimitNumberOfPositions_ShouldThrow(){
        assertThrows(NotValidArgumentException.class, () -> game.moveMotherNature(5));
    }



    @Test
    void changeState_notLastRound_ShouldGoToChooseCloudState(){
        try {
            game.moveMotherNature(4);
            assertEquals(StateType.CHOOSE_CLOUD_STATE,
                    game.getState().getType());
        } catch (NotValidArgumentException | NotValidOperationException e) {
            fail();
        }
    }

    @Test
    void changeState_LastRound_ShouldGoToMoveStudentState(){
        game.setLastRoundFlag();
        try {
            game.moveMotherNature(4);
            assertEquals(StateType.MOVE_STUDENT_STATE,
                    game.getState().getType());
        } catch (NotValidArgumentException | NotValidOperationException e) {
            fail();
        }
    }

    @Test
    void changeState_LastRound_ShouldGoToEndStateAfterTwoTurns(){
        game.setLastRoundFlag();
        try {
            //First turn
            game.moveMotherNature(4);
            game.setState(game.getMoveMotherNatureState());
            game.getModel().getCurrentPlayer().useAssistant(Assistant.CARD_7);
            //Second turn
            game.moveMotherNature(4);
            assertInstanceOf(EndState.class, game.getState());
        } catch (NotValidArgumentException | NotValidOperationException e) {
            fail();
        }
    }
}