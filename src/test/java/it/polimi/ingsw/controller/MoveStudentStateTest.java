package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.gametable.exceptions.IslandNotFoundException;
import it.polimi.ingsw.model.player.ReachedMaxStudentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveStudentStateTest {
    Game game;

    @BeforeEach
    void setUp() {

        Collection<PlayerLoginInfo> playerLoginInfo = new ArrayList<>();
        playerLoginInfo.addAll(
                List.of(
                        new PlayerLoginInfo("Player1"),
                        new PlayerLoginInfo("Player2"),
                        new PlayerLoginInfo("Player3")
                )
        );

        game = new Game(playerLoginInfo);

        // set the current state of the game to moveStudentState
        game.setState(game.getMoveStudentState());
    }

    @AfterEach
    void tearDown() {
        game=null;
    }

    @Test
    public void moveStudentToIsland_BLUEUNICORN_Island1_ShouldMove(){

        // add 3 BLUE UNICORN on entrance of current player
        for (int i=0;i<3;i++){
            try {
                game.getModel().getCurrentPlayer().addStudentToEntrance(PawnType.BLUE_UNICORNS);
            } catch (ReachedMaxStudentException e) {
                fail();
            }
        }

        // use the method to test
        try {
            game.getMoveStudentState().moveStudentToIsland(PawnType.BLUE_UNICORNS,1);
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }

        // CHECKS
        // 1. there are 2 student in the entrance of the current player (i.e., one was removed)
        assertEquals(2,game.getModel().getCurrentPlayer().getStudentsInEntrance().getNumOf(PawnType.BLUE_UNICORNS));
        // 2. check that there is 1 blue student on island
        try {
            assertEquals(1,game.getModel().getGameTable().getIsland(1).numStudentsOf(PawnType.BLUE_UNICORNS));
        } catch (IslandNotFoundException e) {
            fail();
        }
    }

    @Test
    public void moveStudentToIsland_BLUEUNICORN_NotExistingIsland_ShouldThrowAndDoNothing(){

        // add 3 BLUE UNICORN on entrance of current player
        for (int i=0;i<3;i++){
            try {
                game.getModel().getCurrentPlayer().addStudentToEntrance(PawnType.BLUE_UNICORNS);
            } catch (ReachedMaxStudentException e) {
                fail();
            }
        }

        // use the method to test
        // CHECKS
        // 1. NotValidArgumentException has been thrown
        assertThrows(
                NotValidArgumentException.class,
                ()->game.getMoveStudentState().moveStudentToIsland(PawnType.BLUE_UNICORNS,100)
        );
        // 2. there are still 3 BLUE UNICORN in the entrance of the current player
        assertEquals(3,game.getModel().getCurrentPlayer().getStudentsInEntrance().getNumOf(PawnType.BLUE_UNICORNS));
    }

    @Test
    public void moveStudentToIsland_BLUEUNICORN_NotPresentStudent_ShouldThrowAndDoNothing(){

        // CHECKS
        // 1. NotValidArgumentException has been thrown
        assertThrows(
                NotValidArgumentException.class,
                ()->game.getMoveStudentState().moveStudentToIsland(PawnType.BLUE_UNICORNS,1)
        );
        // 2. there are no BLUE UNICORN on island 1
        try {
            assertEquals(0,game.getModel().getGameTable().getIsland(1).numStudentsOf(PawnType.BLUE_UNICORNS));
        } catch (IslandNotFoundException e) {
            fail();
        }

    }

    @Test
    public void moveStudentToDiningRoom_BLUEUNICORN_IsNotPresent_ShouldThrow(){

        // CHECK
        // 1. assertion has been thrown
        assertThrows(
                NotValidArgumentException.class,
                ()->game.getMoveStudentState().moveStudentToDiningRoom(PawnType.BLUE_UNICORNS)
        );
        // there aren't any BLUE UNICORN in dining room
        assertEquals(0,game.getModel().getCurrentPlayer().getNumStudentOf(PawnType.BLUE_UNICORNS));

    }

    @Test
    public void moveStudentToDiningRoom_BLUEUNICORN_DiningRoomFull_ShouldThrow(){

        // add 1 BLUE UNICORN at the entrance
        try {
            game.getModel().getCurrentPlayer().addStudentToEntrance(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // fill the BLUE UNICORN table
        for(int i=0;i<10;i++) {
            try {
                game.getModel().getCurrentPlayer().addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
            } catch (ReachedMaxStudentException e) {
                fail();
            }
        }

        // apply the method
        assertThrows(
                NotValidArgumentException.class,
                ()->game.getMoveStudentState().moveStudentToDiningRoom(PawnType.BLUE_UNICORNS)
        );

    }

    @Test
    public void moveStudentToDiningRoom_BLUEUNICORN_NoErrors_ShouldMove(){

        // add 1 student to entrance
        try {
            game.getModel().getCurrentPlayer().addStudentToEntrance(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // apply the method to test
        try {
            game.getMoveStudentState().moveStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }

        // 1. there aren't BLUE UNICORN at the entrance
        assertEquals(0,game.getModel().getCurrentPlayer().getStudentsInEntrance().getNumOf(PawnType.BLUE_UNICORNS));
        // 2. there is one BLUE UNICORN in the dining room
        assertEquals(1,game.getModel().getCurrentPlayer().getNumStudentOf(PawnType.BLUE_UNICORNS));

    }

    @Test
    public void MoveStudentToIsland_called4Times_ShouldChangeState(){

        // add 4 BLUE UNICORN on entrance of current player
        for (int i=0;i<4;i++){
            try {
                game.getModel().getCurrentPlayer().addStudentToEntrance(PawnType.BLUE_UNICORNS);
            } catch (ReachedMaxStudentException e) {
                fail();
            }
        }

        // move 4 student on island
        for (int i=0;i<4;i++) {
            try {
                game.getMoveStudentState().moveStudentToIsland(PawnType.BLUE_UNICORNS, 1);
            } catch (NotValidOperationException | NotValidArgumentException e) {
                fail();
            }
        }

        // check that the state has been changed into MoveMotherNatureState
        assertEquals(game.getMoveMotherNatureState(),game.getState());
    }

    @Test
    public void MoveStudentToIsland_called1Time_ShouldNotChangeState(){

        // add 4 BLUE UNICORN on entrance of current player
        for (int i=0;i<4;i++){
            try {
                game.getModel().getCurrentPlayer().addStudentToEntrance(PawnType.BLUE_UNICORNS);
            } catch (ReachedMaxStudentException e) {
                fail();
            }
        }

        // move 1 student to island
            try {
                game.getMoveStudentState().moveStudentToIsland(PawnType.BLUE_UNICORNS, 1);
            } catch (NotValidOperationException | NotValidArgumentException e) {
                fail();
            }

        // check that the state has not been changed
        assertEquals(game.getMoveStudentState(),game.getState());
    }

    @Test
    public void MoveStudentToDiningRoom_called4Times_ShouldChangeState(){

        // add 4 BLUE UNICORN on entrance of current player
        for (int i=0;i<4;i++){
            try {
                game.getModel().getCurrentPlayer().addStudentToEntrance(PawnType.BLUE_UNICORNS);
            } catch (ReachedMaxStudentException e) {
                fail();
            }
        }

        // move 4 student to dining room
        for (int i=0;i<4;i++) {
            try {
                game.getMoveStudentState().moveStudentToDiningRoom(PawnType.BLUE_UNICORNS);
            } catch (NotValidOperationException | NotValidArgumentException e) {
                fail();
            }
        }

        // check that the state has been changed into MoveMotherNatureState
        assertEquals(game.getMoveMotherNatureState(),game.getState());
    }

    @Test
    public void MoveStudentToDiningRoom_called1Time_ShouldNotChangeState(){

        // add 4 BLUE UNICORN on entrance of current player
        for (int i=0;i<4;i++){
            try {
                game.getModel().getCurrentPlayer().addStudentToEntrance(PawnType.BLUE_UNICORNS);
            } catch (ReachedMaxStudentException e) {
                fail();
            }
        }

        // move 1 student to dining room
        try {
            game.getMoveStudentState().moveStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }

        // check that the state has not been changed
        assertEquals(game.getMoveStudentState(),game.getState());
    }


    @Test
    public void Move4SomeToIslandAndSomeOtherInDiningRoom_ShouldChangeState() {

        // add 3 BLUE UNICORN on entrance of current player
        for (int i = 0; i < 4; i++) {
            try {
                game.getModel().getCurrentPlayer().addStudentToEntrance(PawnType.BLUE_UNICORNS);
            } catch (ReachedMaxStudentException e) {
                fail();
            }
        }

        // move 1 student to dining room
        try {
            game.getMoveStudentState().moveStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }

        // move 1 student to island
        try {
            game.getMoveStudentState().moveStudentToIsland(PawnType.BLUE_UNICORNS, 1);
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }

        // move 1 student to dining room
        try {
            game.getMoveStudentState().moveStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }

        // move 1 student to dining room
        try {
            game.getMoveStudentState().moveStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }

        // check that the state has been changed into MoveMotherNatureState
        assertEquals(game.getMoveMotherNatureState(), game.getState());
    }
}