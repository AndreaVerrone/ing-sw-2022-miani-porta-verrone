package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.CoinsBag;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughCoinsException;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import it.polimi.ingsw.server.model.utils.exceptions.ReachedMaxStudentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchoolBoardTest {

    private SchoolBoard schoolBoard = null;

    @BeforeEach
    public void setUp() {
        schoolBoard = new SchoolBoard(false, new CoinsBag(), "player");
    }

    @AfterEach
    public void tearDown() {
        schoolBoard = null;
    }

    @Test
    public void addStudentToEntrance_Green_ShouldAdd(){
        try {
            schoolBoard.addStudentToEntrance(PawnType.GREEN_FROGS);
            int greenInEntrance = schoolBoard.getStudentsInEntrance().getNumOf(PawnType.GREEN_FROGS);
            assertEquals(1, greenInEntrance);
        } catch (ReachedMaxStudentException e) {
            fail();
        }
    }

    @Test
    public void addStudentToEntrance_GreenWithEntranceFull_ShouldThrow(){
        // filling entrance with 7 students
        try{
            for (int i = 0; i < 7; i++) {
                schoolBoard.addStudentToEntrance(PawnType.GREEN_FROGS);
            }
        } catch (ReachedMaxStudentException e){
            fail();
        }

        assertThrows(ReachedMaxStudentException.class,
                () -> schoolBoard.addStudentToEntrance(PawnType.GREEN_FROGS));
    }

    @Test
    public void removeStudentFromEntrance_GreenWithOne_ShouldRemove() {
        // add first green student
        try {
            schoolBoard.addStudentToEntrance(PawnType.GREEN_FROGS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        try {
            schoolBoard.removeStudentFromEntrance(PawnType.GREEN_FROGS);
            int greenInEntrance = schoolBoard.getStudentsInEntrance().getNumOf(PawnType.GREEN_FROGS);
            assertEquals(0, greenInEntrance);
        } catch (NotEnoughStudentException e) {
            fail();
        }
    }

    @Test
    public void removeStudentFromEntrance_WithEntranceEmpty_ShouldThrow(){
        assertThrows(NotEnoughStudentException.class,
                () -> schoolBoard.removeStudentFromEntrance(PawnType.RED_DRAGONS));
    }

    @Test
    public void addStudentToDiningRoom_GreenWithSpaceLeft_ShouldAdd(){
        try{
            schoolBoard.addStudentToDiningRoom(PawnType.GREEN_FROGS);
            assertEquals(1, schoolBoard.getNumStudentsOf(PawnType.GREEN_FROGS));
        } catch (ReachedMaxStudentException e){
            fail();
        }
    }

    @Test
    public void addStudentToDiningRoom_GreenWithAlreadyTwoStudent_ShouldAddCoin(){
        try{
            // add the first two green students
            schoolBoard.addStudentToDiningRoom(PawnType.GREEN_FROGS);
            schoolBoard.addStudentToDiningRoom(PawnType.GREEN_FROGS);

            // add the third
            schoolBoard.addStudentToDiningRoom(PawnType.GREEN_FROGS);
        } catch (ReachedMaxStudentException e){
            fail();
        }
        assertEquals(2, schoolBoard.getCoins());
    }

    @Test
    public void addStudentToDiningRoom_WithTableFull_ShouldThrow(){
        //fill the red table
        try{
            for (int i = 0; i < 10; i++) {
                schoolBoard.addStudentToDiningRoom(PawnType.RED_DRAGONS);
            }
        } catch (ReachedMaxStudentException e){
            fail();
        }

        assertThrows(ReachedMaxStudentException.class,
                () -> schoolBoard.addStudentToDiningRoom(PawnType.RED_DRAGONS));
    }

    @Test
    public void removeStudentFromDiningRoom_WithGreenStudents_ShouldRemove(){
        //add a student in the dining room to remove
        try {
            schoolBoard.addStudentToDiningRoom(PawnType.GREEN_FROGS);
        } catch (ReachedMaxStudentException e){
            fail();
        }

        try {
            schoolBoard.removeStudentFromDiningRoom(PawnType.GREEN_FROGS);
        } catch (NotEnoughStudentException e){
            fail();
        }
        assertEquals(0, schoolBoard.getNumStudentsOf(PawnType.GREEN_FROGS));
    }

    @Test
    public void removeStudentFromDiningRoom_WithEmptyTable_ShouldThrow(){
        assertThrows(NotEnoughStudentException.class,
                () -> schoolBoard.removeStudentFromDiningRoom(PawnType.GREEN_FROGS));
    }

    @Test
    public void removeCoin_WithEnoughCoins_ShouldRemove(){
        try {
            schoolBoard.removeCoin(1,true);
        } catch (NotEnoughCoinsException e) {
            fail();
        }
        assertEquals(0, schoolBoard.getCoins());
    }

    @Test
    public void removeCoin_WithNegativeCost_ShouldThrow(){
        assertThrows(AssertionError.class,
                () -> schoolBoard.removeCoin(-2,true));
    }

    @Test
    public void removeCoin_WithExcessiveCost_ShouldThrow(){
        assertThrows(NotEnoughCoinsException.class,
                () -> schoolBoard.removeCoin(10,true));
    }

    @Test
    public void changeTowerNumber_WithNegativeValueAndEnoughTowers_ShouldRemove(){
        int towers = schoolBoard.getTowersNumber();
        schoolBoard.changeTowerNumber(-1);
        assertEquals(towers-1, schoolBoard.getTowersNumber());
    }

    @Test
    public void changeTowerNumber_WithPositiveValueAndEnoughFreeSpace_ShouldAdd(){
        int towers = schoolBoard.getTowersNumber();
        //free one tower space
        schoolBoard.changeTowerNumber(-1);

        //add one tower in the free space
        schoolBoard.changeTowerNumber(1);

        assertEquals(towers, schoolBoard.getTowersNumber());
    }

    @Test
    public void changeTowerNumber_WithPositiveValueAndNotEnoughSpace_ShouldThrow(){
        assertThrows(AssertionError.class,
                () -> schoolBoard.changeTowerNumber(1));
    }
}