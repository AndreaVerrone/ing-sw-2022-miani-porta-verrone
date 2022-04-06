package it.polimi.ingsw.model.gametable;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.StudentList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {
    private Cloud cloud;

    @BeforeEach
    void setUp() {
        cloud = new Cloud(1);
    }

    @AfterEach
    void tearDown() {
        cloud = null;
    }

    @Test
    void getID(){
        int ID = 2;
        Cloud cloudTest = new Cloud(ID);
        assertEquals(ID, cloudTest.getID());
    }


    @Test
    void addStudent_Red() {
        try {
            cloud.addStudent(PawnType.RED_DRAGONS);
            int numOfRed = cloud.getStudents().getNumOf(PawnType.RED_DRAGONS);
            assertEquals(1, numOfRed);
        } catch (NotEnoughStudentException e) {
            fail();
        }
    }

    @Test
    void getAllStudents() {
        StudentList studentsCopyBefore = cloud.getStudents();
        StudentList studentsCopy = cloud.getAllStudents();
        assertEquals(0, cloud.getStudents().numAllStudents());
        assertTrue(studentsCopy.equals(studentsCopyBefore));
    }
}