package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiningRoomTest {

    private DiningRoom diningRoom = null;

    @BeforeEach
    public void setUp() {
        diningRoom = new DiningRoom();
    }

    @AfterEach
    public void tearDown() {
        diningRoom = null;
    }

    @Test
    public void addStudentOf_Green_ShouldAdd() {
        try {
            diningRoom.addStudentOf(PawnType.GREEN_FROGS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }
        assertEquals(1, diningRoom.getNumStudentsOf(PawnType.GREEN_FROGS));
    }

    @Test
    public void addStudentOf_GreenWithTwoStudents_ShouldReturnTrue(){
        // initialize table with two students
        try {
            diningRoom.addStudentOf(PawnType.GREEN_FROGS);
            diningRoom.addStudentOf(PawnType.GREEN_FROGS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }
        try {
            boolean onCoin = diningRoom.addStudentOf(PawnType.GREEN_FROGS);
            assertTrue(onCoin);
        } catch (ReachedMaxStudentException e) {
            fail();
        }
    }

    @Test
    public void addStudentOf_GreenWithTableFull_ShouldThrow(){
        // fill table
        try{
            for( int i = 0; i<10;i++)
                diningRoom.addStudentOf(PawnType.GREEN_FROGS);
        } catch (ReachedMaxStudentException e){
            fail();
        }
        assertThrows(ReachedMaxStudentException.class,
                () -> diningRoom.addStudentOf(PawnType.GREEN_FROGS));
    }

    @Test
    public void removeStudentOf_Green_ShouldRemove() {
        // init table with one student
        try {
            diningRoom.addStudentOf(PawnType.GREEN_FROGS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }
        try {
            diningRoom.removeStudentOf(PawnType.GREEN_FROGS);
        } catch (NotEnoughStudentException e) {
            fail();
        }
        assertEquals(0, diningRoom.getNumStudentsOf(PawnType.GREEN_FROGS));
    }

    @Test
    public void removeStudentOf_GreenWithEmptyTable_ShouldThrow(){
        assertThrows(NotEnoughStudentException.class,
                () -> diningRoom.removeStudentOf(PawnType.GREEN_FROGS));
    }
}