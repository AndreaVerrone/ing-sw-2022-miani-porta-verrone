package it.polimi.ingsw.server.controller.game.states;

import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.controller.game.Game;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.exceptions.IslandNotFoundException;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import it.polimi.ingsw.server.model.utils.exceptions.ReachedMaxStudentException;
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

        Collection<PlayerLoginInfo> playerLoginInfo = new ArrayList<>(List.of(
                new PlayerLoginInfo("Player1"),
                new PlayerLoginInfo("Player2"),
                new PlayerLoginInfo("Player3")
        ));

        game = new Game(playerLoginInfo);

        // set the current state of the game to moveStudentState
        game.setState(game.getMoveStudentState());
    }

    /**
     * This method will remove all the students from entrance of the school board of the player
     * specified in parameter
     * @param currentPlayer the player from which remove students in entrance
     */
    private void removeAllStudentsFromEntrance(Player currentPlayer){
        for(PawnType color : PawnType.values()){
            int numOfStudents;
            numOfStudents = currentPlayer.getStudentsInEntrance().getNumOf(color);
            for(int i=0;i<numOfStudents;i++) {
                try {
                    currentPlayer.removeStudentFromEntrance(color);
                } catch (NotEnoughStudentException e) {
                    fail();
                }
            }
        }
    }

    @AfterEach
    void tearDown() {
        game=null;
    }

    @Test
    public void moveStudentToIsland_BLUEUNICORN_Island1_ShouldMove(){

        // removeAllStudentsFromEntrance all students from entrance to start from a clean situation
        removeAllStudentsFromEntrance(game.getModel().getCurrentPlayer());

        // add 1 BLUE UNICORN on entrance of current player
        try {
            game.getModel().getCurrentPlayer().addStudentToEntrance(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // the num of BLUE UNICORN on island 1 before the calling of the method to test
        int oldNumOfBlueUnicornOnIsland1 = 0;
        try {
            oldNumOfBlueUnicornOnIsland1 = game.getModel().getGameTable().getIsland(1).numStudentsOf(PawnType.BLUE_UNICORNS);
        } catch (IslandNotFoundException e) {
            fail();
        }

        // set island
        Position island1 = new Position(Location.ISLAND);
        island1.setField(1);

        // use the method to test
        try {
            game.getMoveStudentState().choseStudentFromLocation(PawnType.BLUE_UNICORNS,new Position(Location.ENTRANCE));
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }
        try {
            game.getMoveStudentState().chooseDestination(island1);
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }

        // CHECKS
        // 1. the num of BLUE UNICORNS at entrance has decreased by 1 (i.e., one was removed)
        assertEquals(0,game.getModel().getCurrentPlayer().getStudentsInEntrance().getNumOf(PawnType.BLUE_UNICORNS));

        // 2. check that the num of BLUE UNICORN on island has been increased by 1
        try {
            assertEquals(oldNumOfBlueUnicornOnIsland1+1,game.getModel().getGameTable().getIsland(1).numStudentsOf(PawnType.BLUE_UNICORNS));
        } catch (IslandNotFoundException e) {
            fail();
        }
    }

    @Test
    public void moveStudentToIsland_BLUEUNICORN_NotExistingIsland_ShouldThrowAndDoNothing(){

        // removeAllStudentsFromEntrance all students from entrance to start from a clean situation
        removeAllStudentsFromEntrance(game.getModel().getCurrentPlayer());

        // add 1 BLUE UNICORN on entrance of current player
        try {
            game.getModel().getCurrentPlayer().addStudentToEntrance(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // set island
        Position island100 = new Position(Location.ISLAND);
        island100.setField(100);

        // use the method to test
        try {
            game.chooseStudentFromLocation(PawnType.BLUE_UNICORNS,new Position(Location.ENTRANCE));
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }
        // CHECKS
        // 1. NotValidArgumentException has been thrown
        assertThrows(
                NotValidArgumentException.class,
                ()->game.getMoveStudentState().chooseDestination(island100)
        );
        // 2. there are still the same num of BLUE UNICORN in the entrance of the current player
        assertEquals(1,game.getModel().getCurrentPlayer().getStudentsInEntrance().getNumOf(PawnType.BLUE_UNICORNS));
    }

    @Test
    public void moveStudentToIsland_BLUEUNICORN_NotPresentStudent_ShouldThrowAndDoNothing(){

        // removeAllStudentsFromEntrance all students from entrance to start from a clean situation
        removeAllStudentsFromEntrance(game.getModel().getCurrentPlayer());

        // the num of BLUE UNICORN on island 1 before the calling of the method to test
        int oldNumOfBlueUnicornOnIsland1 = 0;
        try {
            oldNumOfBlueUnicornOnIsland1 = game.getModel().getGameTable().getIsland(1).numStudentsOf(PawnType.BLUE_UNICORNS);
        } catch (IslandNotFoundException e) {
            fail();
        }

        // CHECKS
        // 1. NotValidOperationException has been thrown
        assertThrows(
                NotValidArgumentException.class,
                ()->game.getMoveStudentState().choseStudentFromLocation(PawnType.BLUE_UNICORNS,new Position(Location.ENTRANCE))
        );
        // 2. there are the same number of BLUE UNICORN on island 1 that there were before the calling of the method
        try {
            assertEquals(oldNumOfBlueUnicornOnIsland1,game.getModel().getGameTable().getIsland(1).numStudentsOf(PawnType.BLUE_UNICORNS));
        } catch (IslandNotFoundException e) {
            fail();
        }

    }

    @Test
    public void moveStudentToDiningRoom_BLUEUNICORN_DiningRoomFull_ShouldThrow(){

        // removeAllStudentsFromEntrance all students from entrance to start from a clean situation
        removeAllStudentsFromEntrance(game.getModel().getCurrentPlayer());

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
        try {
            game.chooseStudentFromLocation(PawnType.BLUE_UNICORNS,new Position(Location.ENTRANCE));
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }
        assertThrows(
                NotValidArgumentException.class,
                ()->game.getMoveStudentState().chooseDestination(new Position(Location.DINING_ROOM))
        );
    }

    @Test
    public void moveStudentToDiningRoom_BLUEUNICORN_NoErrors_ShouldMove(){

        // removeAllStudentsFromEntrance all students from entrance to start from a clean situation
        removeAllStudentsFromEntrance(game.getModel().getCurrentPlayer());

        // add 1 student to entrance
        try {
            game.getModel().getCurrentPlayer().addStudentToEntrance(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // compute num of BLUE UNICORN at the entrance
        int oldNumOfBlueUnicorns = game.getModel().getCurrentPlayer().getStudentsInEntrance().getNumOf(PawnType.BLUE_UNICORNS);

        // apply the method to test
        try {
            game.getMoveStudentState().choseStudentFromLocation(PawnType.BLUE_UNICORNS,new Position(Location.ENTRANCE));
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }
        try {
            game.getMoveStudentState().chooseDestination(new Position(Location.DINING_ROOM));
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }

        // 1. num of BLUE UNICORN at the entrance has decreased by 1
        assertEquals(oldNumOfBlueUnicorns-1,game.getModel().getCurrentPlayer().getStudentsInEntrance().getNumOf(PawnType.BLUE_UNICORNS));
        // 2. there is one BLUE UNICORN in the dining room
        assertEquals(1,game.getModel().getCurrentPlayer().getNumStudentOf(PawnType.BLUE_UNICORNS));

    }

    @Test
    public void MoveStudentToIsland_called4Times_ShouldChangeState(){

        // removeAllStudentsFromEntrance all students from entrance to start from a clean situation
        removeAllStudentsFromEntrance(game.getModel().getCurrentPlayer());

        // add 4 BLUE UNICORN on entrance of current player
        for (int i=0;i<4;i++){
            try {
                game.getModel().getCurrentPlayer().addStudentToEntrance(PawnType.BLUE_UNICORNS);
            } catch (ReachedMaxStudentException e) {
                fail();
            }
        }

        // set island
        Position island1 = new Position(Location.ISLAND);
        island1.setField(1);

        // move 4 student on island
        for (int i=0;i<4;i++) {
            try {
                game.getMoveStudentState().choseStudentFromLocation(PawnType.BLUE_UNICORNS,new Position(Location.ENTRANCE));
                game.getMoveStudentState().chooseDestination(island1);
            } catch (NotValidOperationException | NotValidArgumentException e) {
                fail();
            }
        }

        // check that the state has been changed into MoveMotherNatureState
        assertEquals(game.getMoveMotherNatureState(),game.getState());
    }

    @Test
    public void MoveStudentToIsland_called1Time_ShouldNotChangeState(){

        // removeAllStudentsFromEntrance all students from entrance to start from a clean situation
        removeAllStudentsFromEntrance(game.getModel().getCurrentPlayer());

        // add 4 BLUE UNICORN on entrance of current player
        for (int i=0;i<4;i++){
            try {
                game.getModel().getCurrentPlayer().addStudentToEntrance(PawnType.BLUE_UNICORNS);
            } catch (ReachedMaxStudentException e) {
                fail();
            }
        }

        // set island
        Position island1 = new Position(Location.ISLAND);
        island1.setField(1);

        // move 1 student to island
        try {
            game.getMoveStudentState().choseStudentFromLocation(PawnType.BLUE_UNICORNS,new Position(Location.ENTRANCE));
            game.getMoveStudentState().chooseDestination(island1);
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }

        // check that the state has not been changed
        assertEquals(game.getMoveStudentState(),game.getState());
    }

    @Test
    public void MoveStudentToDiningRoom_called4Times_ShouldChangeState(){

        // removeAllStudentsFromEntrance all students from entrance to start from a clean situation
        removeAllStudentsFromEntrance(game.getModel().getCurrentPlayer());

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
                game.getMoveStudentState().choseStudentFromLocation(PawnType.BLUE_UNICORNS,new Position(Location.ENTRANCE));
                game.getMoveStudentState().chooseDestination(new Position(Location.DINING_ROOM));
            } catch (NotValidOperationException | NotValidArgumentException e) {
                fail();
            }
        }

        // check that the state has been changed into MoveMotherNatureState
        assertEquals(game.getMoveMotherNatureState(),game.getState());
    }

    @Test
    public void MoveStudentToDiningRoom_called1Time_ShouldNotChangeState(){

        // removeAllStudentsFromEntrance all students from entrance to start from a clean situation
        removeAllStudentsFromEntrance(game.getModel().getCurrentPlayer());

        // add 1 BLUE UNICORN on entrance of current player

        try {
            game.getModel().getCurrentPlayer().addStudentToEntrance(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }


        // move 1 student to dining room
        try {
            game.getMoveStudentState().choseStudentFromLocation(PawnType.BLUE_UNICORNS,new Position(Location.ENTRANCE));
            game.getMoveStudentState().chooseDestination(new Position(Location.DINING_ROOM));
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }

        // check that the state has not been changed
        assertEquals(game.getMoveStudentState(),game.getState());
    }


    @Test
    public void Move4SomeToIslandAndSomeOtherInDiningRoom_ShouldChangeState() {

        // removeAllStudentsFromEntrance all students from entrance to start from a clean situation
        removeAllStudentsFromEntrance(game.getModel().getCurrentPlayer());

        // set island
        Position island1 = new Position(Location.ISLAND);
        island1.setField(1);

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
            game.getMoveStudentState().choseStudentFromLocation(PawnType.BLUE_UNICORNS,new Position(Location.ENTRANCE));
            game.getMoveStudentState().chooseDestination(new Position(Location.DINING_ROOM));
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }

        // move 1 student to island
        try {
            game.getMoveStudentState().choseStudentFromLocation(PawnType.BLUE_UNICORNS,new Position(Location.ENTRANCE));
            game.getMoveStudentState().chooseDestination(island1);
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }

        // move 1 student to dining room
        try {
            game.getMoveStudentState().choseStudentFromLocation(PawnType.BLUE_UNICORNS,new Position(Location.ENTRANCE));
            game.getMoveStudentState().chooseDestination(new Position(Location.DINING_ROOM));
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }

        // move 1 student to dining room
        try {
            game.getMoveStudentState().choseStudentFromLocation(PawnType.BLUE_UNICORNS,new Position(Location.ENTRANCE));
            game.getMoveStudentState().chooseDestination(new Position(Location.DINING_ROOM));
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }

        // check that the state has been changed into MoveMotherNatureState
        assertEquals(game.getMoveMotherNatureState(), game.getState());
    }
}