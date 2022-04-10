package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player = null;
    SchoolBoard schoolBoard = null;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void useAssistant_assistant_setLastUsed(){

        // set up the player class
        player = new Player("Player1",schoolBoard);

        // testing
        player.setAssistantDeck(Wizard.W2);
        player.useAssistant(Assistant.CARD_9);
        assertEquals(Assistant.CARD_9,player.getLastAssistant());
    }

    @Test
    public void moveFromEntranceToDiningRoom__ShouldThrowReachedMaxStudentException() {

        // set the school board by filling the BLUE_UNICORN table
        schoolBoard = new SchoolBoard(false);
        for(int i=0; i<10; i=i+1){
            try {
                schoolBoard.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
            } catch (ReachedMaxStudentException e) {
                e.printStackTrace();
            }
        }

        // set up the player class
        player = new Player("Player1",schoolBoard);
        player.setAssistantDeck(Wizard.W2);

        // add one BLUE_UNICORN to the entrance
        try {
            schoolBoard.addStudentToEntrance(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            e.printStackTrace();
        }

        // move the one BLUE_UNICORN from entrance to dining room
        assertThrows(ReachedMaxStudentException.class,
                () -> player.moveFromEntranceToDiningRoom(PawnType.BLUE_UNICORNS));

    }

    @Test
    public void moveFromEntranceToDiningRoom__ShouldThrowNotEnoughStudentException() {

        // set up the school board by adding some BLUE_UNICORNS in the dining room
        // (but in order not to fill the table)
        schoolBoard = new SchoolBoard(false);
        for(int i=0; i<8; i=i+1){
            try {
                schoolBoard.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
            } catch (ReachedMaxStudentException e) {
                e.printStackTrace();
            }
        }

        // set up the player class
        player = new Player("Player1",schoolBoard);
        player.setAssistantDeck(Wizard.W2);

        // try to move the BLUE_UNICORN that is not present at the entrance to the dining room
        assertThrows(NotEnoughStudentException.class, () -> player.moveFromEntranceToDiningRoom(PawnType.BLUE_UNICORNS));

    }

    @Test public void moveFromEntranceToDiningRoom__ShouldMove() {

        // set up the school board by adding one BLUE_UNICORN at the entrance and
        // some students in the dining room (but in order not to fill the table)
        schoolBoard = new SchoolBoard(false);
        try {
            schoolBoard.addStudentToEntrance(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 8; i = i + 1) {
            try {
                schoolBoard.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
            } catch (ReachedMaxStudentException e) {
                e.printStackTrace();
            }
        }

        // this is the number of BLUE_UNICORNS in the dining room before the calling of the method
        int oldNumberOfStudents = schoolBoard.getNumStudentsOf(PawnType.BLUE_UNICORNS);

        // this is the number of BLUE_UNICORNS in the entrance before the calling of the method
        int olnNumberOfStudentsInEntrance = schoolBoard.getStudentsInEntrance().getNumOf(PawnType.BLUE_UNICORNS);

        // set up the player class
        player = new Player("Player1", schoolBoard);
        player.setAssistantDeck(Wizard.W2);

        // move the BLUE_UNICORN from the entrance to the dining room
        try {
            player.moveFromEntranceToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (NotEnoughStudentException | ReachedMaxStudentException e) {
            fail();
        }

        // this is the number of BLUE_UNICORNS in the dining room after the calling of the method
        int numberOfStudents = player.getNumStudentOf(PawnType.BLUE_UNICORNS);

        // this is the number of BLUE_UNICORNS at the entrance after the calling of the method
        int numberOfStudentsInEntrance = player.getStudentsInEntrance().getNumOf(PawnType.BLUE_UNICORNS);

        // check that after the calling of the method
        // - the number of the BLUE_UNICORNS in the dining room has been increased of 1
        // - the number of BLUE_UNICORNS at the entrance has been decreased by 1
        assertTrue(
                numberOfStudents == oldNumberOfStudents + 1 &&
                olnNumberOfStudentsInEntrance == numberOfStudentsInEntrance + 1
        );
    }
}