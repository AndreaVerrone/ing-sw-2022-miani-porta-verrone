package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest
    @EnumSource
    public void changeNumOf_WithPositiveValue_ShouldAdd(PawnType type) {
        try {
            studentList.changeNumOf(type, 5);
            int numGreen = studentList.getNumOf(type);
            assertEquals(5, numGreen);
        } catch (NotEnoughStudentException e) {
            fail();
        }
    }

    @ParameterizedTest
    @EnumSource
    public void changeNumOf_WithNegativeValueAndEnough_ShouldRemove(PawnType type) {
        try {
            studentList.changeNumOf(type, 5); // initialize list

            studentList.changeNumOf(type, -2);
            int numStudents = studentList.getNumOf(type);
            assertEquals(3, numStudents);
        } catch (NotEnoughStudentException e) {
            // enough
            fail();
        }
    }

    @ParameterizedTest
    @EnumSource
    public void changeNumOf_WithNegativeValueAndNotEnough_ShouldThrowException(PawnType type) {
        assertThrows(NotEnoughStudentException.class,
                () -> studentList.changeNumOf(type, -2));
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

    @Test
    public void twoEquallyFilledList_ShouldBeEquals() {
        studentList.setAllAs(2);
        StudentList otherList = new StudentList();
        otherList.setAllAs(2);
        assertEquals(studentList, otherList);
        assertEquals(studentList.hashCode(), otherList.hashCode());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void forEach_ShouldIterateOnEveryStudent(int numStudentPerColor) {
        studentList.setAllAs(numStudentPerColor);
        StudentList testList = new StudentList();
        studentList.forEach(color -> {
            try {
                testList.changeNumOf(color, 1);
            } catch (NotEnoughStudentException e) {
                fail();
            }
        });
        assertEquals(studentList, testList);
    }

}