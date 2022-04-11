package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.TowerType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player = null;

    @BeforeEach
    void setUp() {
        player = new Player("Player1",false);
    }

    @AfterEach
    void tearDown() {
        player = null;
    }

    @Test
    public void useAssistant_assistant_setLastUsed(){
        player.setAssistantDeck(Wizard.W2);
        player.useAssistant(Assistant.CARD_9);
        assertEquals(Assistant.CARD_9,player.getLastAssistant());
    }

    @Test
    public void moveFromEntranceToDiningRoom_FullTableInDiningRoom_ShouldThrowReachedMaxStudentException() {

        // set the school board by filling the BLUE_UNICORN table
        for(int i=0; i<10; i=i+1){
            try {
                player.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
            } catch (ReachedMaxStudentException e) {
                fail();
            }
        }

        // add one BLUE_UNICORN to the entrance
        try {
            player.addStudentToEntrance(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // move the one BLUE_UNICORN from entrance to dining room
        assertThrows(ReachedMaxStudentException.class,
                () -> player.moveFromEntranceToDiningRoom(PawnType.BLUE_UNICORNS));
    }

    @Test
    public void moveFromEntranceToDiningRoom__EntranceAndDiningRoomAreEmpty_ShouldThrowNotEnoughStudentException() {

        // try to move the BLUE_UNICORN that is not present at the entrance to the dining room
        assertThrows(NotEnoughStudentException.class, () -> player.moveFromEntranceToDiningRoom(PawnType.BLUE_UNICORNS));

    }

    @Test public void moveFromEntranceToDiningRoom__EntranceNonEmptyAndTwoStudentsInDiningRoom_ShouldMove() {

        // set up the school board by adding one BLUE_UNICORN at the entrance and
        // 2 in the dining room (but in order not to fill the table)
        try {
            player.addStudentToEntrance(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }
        try {
            player.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
            player.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // this is the number of BLUE_UNICORNS in the dining room before the calling of the method
        int oldNumberOfStudents = player.getNumStudentOf(PawnType.BLUE_UNICORNS);

        // this is the number of BLUE_UNICORNS in the entrance before the calling of the method
        int oldNumberOfStudentsInEntrance = player.getStudentsInEntrance().getNumOf(PawnType.BLUE_UNICORNS);

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
        assertTrue(numberOfStudents == oldNumberOfStudents + 1,
                " fail due to condition on dining room student's number");
        assertTrue(oldNumberOfStudentsInEntrance == numberOfStudentsInEntrance + 1,
                "fail due to condition on number of students in entrance");
    }

}