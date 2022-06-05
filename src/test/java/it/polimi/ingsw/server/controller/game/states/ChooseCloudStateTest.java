package it.polimi.ingsw.server.controller.game.states;

import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.controller.game.Game;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import it.polimi.ingsw.server.model.utils.exceptions.ReachedMaxStudentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ChooseCloudStateTest {

    private ChooseCloudState chooseCloudState = null;
    private Game game = null;

    @BeforeEach
    void setUp() {
        Collection<PlayerLoginInfo> players = List.of(
                new PlayerLoginInfo("player 1"),
                new PlayerLoginInfo("player 2")
        );
        game = new Game(players);
        chooseCloudState = new ChooseCloudState(game);

        // TODO: 03/06/2022 remove after merging of game initialization
        StudentList studentList = new StudentList();
        studentList.setAllAs(24);
        game.getModel().getGameTable().fillBag(studentList);
        game.getModel().getGameTable().fillClouds();
    }

    @AfterEach
    void tearDown() {
        game = null;
        chooseCloudState = null;
    }

    @Nested
    class PartialPlayerTurn{

        @BeforeEach
        public void setUp(){
// remove random students until in the entrance there are 5 students
            Player currentPlayer = game.getModel().getCurrentPlayer();
            // TODO: 03/06/2022 switch after merging of game initialization
            while (currentPlayer.getStudentsInEntrance().numAllStudents()<5) {
//            while (currentPlayer.getStudentsInEntrance().numAllStudents() > 5) {
                int randomStudent = new Random().nextInt(PawnType.values().length);
                // TODO: 03/06/2022 switch after merging of game initialization
                try {
                    currentPlayer.addStudentToEntrance(PawnType.values()[randomStudent]);
                } catch (ReachedMaxStudentException e) {}
//                try {
//                    currentPlayer.removeStudentFromEntrance(PawnType.values()[randomStudent]);
//                } catch (NotEnoughStudentException e) {}
            }
        }

        @Test
        public void skipTurn_ShouldFillPlayerEntrance(){
            Player currentPlayer = game.getModel().getCurrentPlayer();
            assertEquals(5, currentPlayer.getStudentsInEntrance().numAllStudents());
            chooseCloudState.skipTurn();
            assertEquals(7, currentPlayer.getStudentsInEntrance().numAllStudents());
        }

    }

    @Test
    public void skipTurn_shouldChangeCurrentPlayer(){
        Player currentPlayer = game.getModel().getCurrentPlayer();
        chooseCloudState.skipTurn();
        assertNotEquals(currentPlayer, game.getModel().getCurrentPlayer());
    }

    @Test
    public void skipTurn_ShouldChangeState(){
        chooseCloudState.skipTurn();
        assertNotEquals(chooseCloudState.getType(), game.getState().getType());
    }
}