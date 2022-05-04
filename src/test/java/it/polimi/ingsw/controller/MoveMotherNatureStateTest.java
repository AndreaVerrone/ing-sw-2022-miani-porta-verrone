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

    private final Collection<PlayerLoginInfo> players = new ArrayList<>();
    private MoveMotherNatureState state;
    private Game game;

    private class ModelStub extends GameModel {

        public ModelStub(Collection<PlayerLoginInfo> playersLoginInfo) {
            super(playersLoginInfo);

        }

        @Override
        public void nextPlayerTurn() {
        }

        @Override
        public int getMNMovementLimit() {
            return 4;
        }
    }

    private class GameStub extends Game{

        private final GameModel modelStub;
        private final State state = new State() {
        };
        private final State moveStudentState = new State() {
        };
        private final State endState = new State() {
        };

        public GameStub(Collection<PlayerLoginInfo> players, GameModel model) {
            super(players);
            this.modelStub = model;
        }

        @Override
        public GameModel getModel() {
            return modelStub;
        }


        @Override
        protected State getChooseCloudState() {
            return state;
        }

        @Override
        protected State getMoveStudentState() {
            return moveStudentState;
        }

        @Override
        protected State getEndState() {
            return endState;
        }
    }

    @BeforeEach
    void setUp() {
        //Create first player
        PlayerLoginInfo playerLoginInfo1 = new PlayerLoginInfo("Marco");
        //Create second player
        PlayerLoginInfo playerLoginInfo2 = new PlayerLoginInfo("Mattia");
        players.add(playerLoginInfo1);
        players.add(playerLoginInfo2);
        ModelStub modelStub = new ModelStub(players);
        game = new GameStub(players, modelStub);//Create game
        //game = new Game(collection);
        //Use an assistant card with the first player
        //Assistant assistant1 = Assistant.CARD_7;
        //game.getModel().getPlayerList().get(0).useAssistant(assistant1);
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



    @Test
    void changeState_notLastRound_ShouldGoToChooseCloudState(){
        try {
            state.moveMotherNature(4);
            assertEquals(game.getChooseCloudState(),
                    game.getState());
        } catch (NotValidArgumentException e) {
            fail();
        }
    }

    @Test
    void changeState_LastRound_ShouldGoToMoveStudentState(){
        game.setLastRoundFlag();
        try {
            state.moveMotherNature(4);
            assertEquals(game.getMoveStudentState(),
                    game.getState());
        } catch (NotValidArgumentException e) {
            fail();
        }
    }

    @Test
    void changeState_LastRound_ShouldGoToEndStateAfterTwoTurns(){
        game.setLastRoundFlag();
        try {
            //First turn
            state.moveMotherNature(4);
            //Second turn
            state.moveMotherNature(4);
            assertEquals(game.getEndState(),
                    game.getState());
        } catch (NotValidArgumentException e) {
            fail();
        }
    }
}