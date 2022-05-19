package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.utils.exceptions.ReachedMaxStudentException;
import it.polimi.ingsw.server.model.strategies.check_professor.CheckProfessorCharacter;
import it.polimi.ingsw.server.model.utils.PawnType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckProfessorCharacterTest {

    Player currentPlayer;
    Player player2;
    Player player3;

    GameModel gameModel;
    CheckProfessorCharacter checkProfessorCharacter;

    @BeforeEach
    void setUp() {

        Collection<PlayerLoginInfo> playerLoginInfo = new ArrayList<>(
                List.of(
                        new PlayerLoginInfo("Player1"),
                        new PlayerLoginInfo("Player2"),
                        new PlayerLoginInfo("Player3")
                )
        );

        gameModel = new GameModel(playerLoginInfo);

        checkProfessorCharacter = new CheckProfessorCharacter(gameModel);

        gameModel.setCheckProfessorStrategy(checkProfessorCharacter);

        player3 = gameModel.getCurrentPlayer();
        gameModel.nextPlayerTurn();
        player2 = gameModel.getCurrentPlayer();
        gameModel.nextPlayerTurn();
        currentPlayer = gameModel.getCurrentPlayer();
    }

    @AfterEach
    void tearDown() {
        currentPlayer = null;
        player2 = null;
        player3 = null;
        gameModel=null;
        checkProfessorCharacter=null;
    }

    @Test public void checkProfessor_BLUEUNICORN_CurrentPlayerHasProfessor_ProfessorShouldRemain(){

        // DESCRIPTION OF THE INITIAL SITUATION
        // the current player has 1 BLUE UNICORN student in the dining room, while
        // all the other players has not any student and so the current player has the BLUE UNICORN professor.
        // Another player, the player2 has the RED DRAGON professor and a student of that color

        // SET UP
        // 1. add one BLUE UNICORN student to the dining room of the current player
        try {
            currentPlayer.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // 2. add the BLUE UNICOR professor to the current player
        currentPlayer.addProfessor(PawnType.BLUE_UNICORNS);

        // 3. add the RED DRAGON student to dining room of player 2
        try {
            player2.addStudentToDiningRoom(PawnType.RED_DRAGONS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // 4. add the RED DRAGON PROFESSOR
        player2.addProfessor(PawnType.RED_DRAGONS);

        // call the method to test
        checkProfessorCharacter.checkProfessor(PawnType.BLUE_UNICORNS);

        // CHECKS
        // 1. assert that the current player still has the professor
        assertTrue(currentPlayer.getProfessors().contains(PawnType.BLUE_UNICORNS));

        // 2. assert that the other players has no the BLUE UNICORN PROFESSOR
        assertFalse(player2.getProfessors().contains(PawnType.BLUE_UNICORNS));
        assertFalse(player3.getProfessors().contains(PawnType.BLUE_UNICORNS));

        // 3. assert that player 2 still has the RED dragon professor
        assertTrue(player2.getProfessors().contains(PawnType.RED_DRAGONS));

    }

    @Test public void checkProfessor_BLUEUNICORN_NoOneHasBLUEProfessor_ProfessorShouldBeAddedToCurrentPlayer(){

        // DESCRIPTION OF THE INITIAL SITUATION
        // No one has BLUE UNICORN professors and so any BLUE UNICORN students.
        // Player2 has one RED DRAGON student and professor.
        // Then a BLUE UNICORN student will be added to the dining room of the current player.

        // SET UP
        // 1. add one BLUE UNICORN student to the dining room of the current player
        try {
            currentPlayer.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // 2. add the RED DRAGON student to dining room of player 2
        try {
            player2.addStudentToDiningRoom(PawnType.RED_DRAGONS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // 3. add the RED DRAGON PROFESSOR
        player2.addProfessor(PawnType.RED_DRAGONS);

        // call the method to test
        checkProfessorCharacter.checkProfessor(PawnType.BLUE_UNICORNS);

        // CHECKS
        // 1. assert that the current player has the BLUE UNICORN professor
        assertTrue(currentPlayer.getProfessors().contains(PawnType.BLUE_UNICORNS));

        // 2. assert that the other players has no the BLUE UNICORN PROFESSOR
        assertFalse(player2.getProfessors().contains(PawnType.BLUE_UNICORNS));
        assertFalse(player3.getProfessors().contains(PawnType.BLUE_UNICORNS));

        // 3. assert that player 2 still has the RED dragon professor
        assertTrue(player2.getProfessors().contains(PawnType.RED_DRAGONS));

    }

    @Test public void checkProfessor_BLUEUNICORN_Parity_ProfessorShouldBeAddedToCurrent(){

        // DESCRIPTION OF THE INITIAL SITUATION
        // the current player has no professor and no student in the dining room,
        // while another player has 1 blue student in the dining room, and therefore it has also the
        // corresponding professor.
        // Player 2 has also a RED DRAGON student and professor.
        // Then a BLUE unicorn student will be added to the current player

        // SET UP
        // 1. add one BLUE UNICORN student to the dining room of the player2
        try {
            player2.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // 2. add the BLUE UNICOR professor to the player2
        player2.addProfessor(PawnType.BLUE_UNICORNS);

        // 3. add the RED DRAGON student to dining room of player 2
        try {
            player2.addStudentToDiningRoom(PawnType.RED_DRAGONS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // 4. add the RED DRAGON PROFESSOR
        player2.addProfessor(PawnType.RED_DRAGONS);

        // 5. add one BLUE UNICORN student to the dining room of the current player
        // now both players have the same number of students in the dining room
        try {
            currentPlayer.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // calling of the method:
        checkProfessorCharacter.checkProfessor(PawnType.BLUE_UNICORNS);

        // CHECKS
        // 1. assert that the professor belong to current player
        assertTrue(currentPlayer.getProfessors().contains(PawnType.BLUE_UNICORNS));

        // 2. assert that any other player has not the BLUE UNICORN
        assertFalse(player2.getProfessors().contains(PawnType.BLUE_UNICORNS));
        assertFalse(player3.getProfessors().contains(PawnType.BLUE_UNICORNS));

        // 3. assert that player 2 still has the RED dragon professor
        assertTrue(player2.getProfessors().contains(PawnType.RED_DRAGONS));
    }

    @Test public void checkProfessor_BLUEUNICORN_CurrentPlayerHasMoreStudent_ProfessorShouldBeMoved(){

        // DESCRIPTION OF THE INITIAL SITUATION
        // the current player has no professor and 1 student in the dining room,
        // while another player has 1 blue student in the dining room, and therefore it has also the
        // corresponding professor.
        // Then one student will be added to the dining room of the current player
        // and the method to test will be called.

        // SET UP
        // 1. add one BLUE UNICORN student to the dining room of the current player
        // now both players have the same number of students in the dining room
        try {
            currentPlayer.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // 2. add one BLUE UNICORN student to the dining room of the player2
        try {
            player2.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // 3. add the BLUE UNICOR professor to the player2
        player2.addProfessor(PawnType.BLUE_UNICORNS);

        // 4. add the RED DRAGON student to dining room of player 2
        try {
            player2.addStudentToDiningRoom(PawnType.RED_DRAGONS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // 5. add the RED DRAGON PROFESSOR
        player2.addProfessor(PawnType.RED_DRAGONS);

        // 6. add one BLUE UNICORN student to the dining room of the current player
        // now the current player has more students in the dining room
        try {
            currentPlayer.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // calling of the method:
        checkProfessorCharacter.checkProfessor((PawnType.BLUE_UNICORNS));

        // CHECKS
        // 1. assert that the professor now belong to current player
        assertTrue(currentPlayer.getProfessors().contains(PawnType.BLUE_UNICORNS));

        // 2. assert that any other player has not the BLUE UNICORN
        assertFalse(player2.getProfessors().contains(PawnType.BLUE_UNICORNS));
        assertFalse(player3.getProfessors().contains(PawnType.BLUE_UNICORNS));

        // 3. assert that player 2 still has the RED dragon professor
        assertTrue(player2.getProfessors().contains(PawnType.RED_DRAGONS));
    }

    @Test public void checkProfessor_BLUEUNICORN_CurrentPlayerHasLessStudents_ProfessorShouldRemainWhereTheyWere(){

        // DESCRIPTION OF THE INITIAL SITUATION
        // the current player has no professor and no student in the dining room,
        // while another player has 1 blue student in the dining room, and therefore it has also the
        // corresponding professor.
        // Player 2 has also a RED DRAGON student and professor.

        // SET UP
        // 1. add one BLUE UNICORN student to the dining room of the player2
        try {
            player2.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // 2. add the BLUE UNICOR professor to the player2
        player2.addProfessor(PawnType.BLUE_UNICORNS);

        // 3. add the RED DRAGON student to dining room of player 2
        try {
            player2.addStudentToDiningRoom(PawnType.RED_DRAGONS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // 4. add the RED DRAGON PROFESSOR
        player2.addProfessor(PawnType.RED_DRAGONS);

        // calling of the method:
        checkProfessorCharacter.checkProfessor((PawnType.BLUE_UNICORNS));

        // CHECKS
        // 1. assert that the professor still remain to "player2"
        assertTrue(player2.getProfessors().contains(PawnType.BLUE_UNICORNS));

        // 2. assert that any other player has not the BLUE UNICORN
        assertFalse(currentPlayer.getProfessors().contains(PawnType.BLUE_UNICORNS));
        assertFalse(player3.getProfessors().contains(PawnType.BLUE_UNICORNS));

        // 3. assert that player 2 still has the RED dragon professor
        assertTrue(player2.getProfessors().contains(PawnType.RED_DRAGONS));
    }
}