package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentListTest {

    StudentList studentList = null;

    @BeforeEach
    public void setUp(){
        studentList = new StudentList();
    }

    @AfterEach
    public void tearDown(){
        studentList = null;
    }

    @Test
    public void changeNumOf_GreenWithPositiveValue_ShouldAdd(){
        studentList.changeNumOf(PawnType.GREEN_FROGS, 5);
        int numGreen = studentList.getNumOf(PawnType.GREEN_FROGS);
        assertEquals(5, numGreen);
    }

    @Test
    public void changeNumOf_GreenWithNegativeValue_ShouldRemove(){
        studentList.changeNumOf(PawnType.GREEN_FROGS, 5); // initialize list

        studentList.changeNumOf(PawnType.GREEN_FROGS, -2);
        int numGreen = studentList.getNumOf(PawnType.GREEN_FROGS);
        assertEquals(3, numGreen);
    }

    @Test
    public void changeNumOf_GreenWithNegativeValue_ShouldThrowException(){
        try{
            studentList.changeNumOf(PawnType.GREEN_FROGS, -2);
        } catch (NotEnoughStudentException e){
            assertTrue(true);
            return;
        }
        fail();
    }

    @Test
    public void setAllAs_Three_ShouldAddThreeToAll(){
        studentList.setAllAs(3);
        assertEquals(5*3, studentList.numAllStudents());
    }

    @Test
    public void setAllAs_WithNegativeValue_ShouldThrowAssertionError(){
        try {
            studentList.setAllAs(-3);
        }catch (AssertionError e){
            assertTrue(true);
            return;
        }
        fail();
    }

}