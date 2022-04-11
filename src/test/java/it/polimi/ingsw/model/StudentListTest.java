package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentListTest {

    StudentList studentList = null;

    @BeforeEach
    public void setUp() {
        studentList = new StudentList();
    }

    @AfterEach
    public void tearDown() {
        studentList = null;
    }

    @Test
    public void changeNumOf_GreenWithPositiveValue_ShouldAdd() {
        try {
            studentList.changeNumOf(PawnType.GREEN_FROGS, 5);
            int numGreen = studentList.getNumOf(PawnType.GREEN_FROGS);
            assertEquals(5, numGreen);
        } catch (NotEnoughStudentException e) {
            fail();
        }
    }

    @Test
    public void changeNumOf_GreenWithNegativeValue_ShouldRemove() {
        try {
            studentList.changeNumOf(PawnType.GREEN_FROGS, 5); // initialize list

            studentList.changeNumOf(PawnType.GREEN_FROGS, -2);
            int numGreen = studentList.getNumOf(PawnType.GREEN_FROGS);
            assertEquals(3, numGreen);
        } catch (NotEnoughStudentException e) {
            fail();
        }
    }

    @Test
    public void changeNumOf_GreenWithNegativeValue_ShouldThrowException() {
        assertThrows(NotEnoughStudentException.class,
                () -> studentList.changeNumOf(PawnType.GREEN_FROGS, -2));
    }

    @Test
    public void setAllAs_Three_ShouldAddThreeToAll() {
        studentList.setAllAs(3);
        assertEquals(5 * 3, studentList.numAllStudents());
    }

    @Test
    public void setAllAs_WithNegativeValue_ShouldThrowAssertionError() {
        assertThrows(AssertionError.class,
                () -> studentList.setAllAs(-3));
    }

    @Test
    public void add_EmptyList_ShouldDoNothing(){
        int initialStudents = studentList.numAllStudents();
        StudentList emptyList = new StudentList();
        studentList.add(emptyList);
        assertEquals(initialStudents, studentList.numAllStudents());
    }

    @Test
    public void add_NonEmptyList_ShouldAddStudents(){
        StudentList newList = new StudentList();
        newList.setAllAs(2);
        studentList.setAllAs(1);
        studentList.add(newList);
        assertEquals(PawnType.values().length * 3, studentList.numAllStudents());
    }

}