package it.polimi.ingsw.model.gametable;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.StudentList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentsBagTest {

    StudentsBag bag;

    @BeforeEach
    void setUp() {
        bag = new StudentsBag();
    }

    @AfterEach
    void tearDown() {
        bag = null;
    }

    @Test
    void draw_bagNotEmpty_shouldRemove() {
        StudentList students = new StudentList();
        try {
            students.changeNumOf(PawnType.GREEN_FROGS, 2);
            students.changeNumOf(PawnType.YELLOW_GNOMES, 2);
            students.changeNumOf(PawnType.PINK_FAIRIES, 2);
            bag.fillWith(students);
            PawnType typeTest = bag.draw();
            assertEquals(students.getNumOf(typeTest)-1, bag.getStudents().getNumOf(typeTest));
            students.changeNumOf(typeTest, - 1);
            assertTrue(students.equals(bag.getStudents()));

        } catch (NotEnoughStudentException | EmptyBagException e) {
            fail();
        }
    }

    @Test
    void draw_bagWithOnlyOneStudentGreen_shouldRemove() {
        StudentList students = new StudentList();
        try {
            students.changeNumOf(PawnType.GREEN_FROGS, 1);
            bag.fillWith(students);
            PawnType typeTest = bag.draw();
            assertEquals(PawnType.GREEN_FROGS, typeTest);
            students.empty();
            assertTrue(students.equals(bag.getStudents()));

        } catch (NotEnoughStudentException | EmptyBagException e) {
            fail();
        }
    }

    @Test
    void draw_bagEmpty_shouldThrowException() {
        StudentList students = new StudentList();
        try {

            bag.fillWith(students);
            assertThrows(EmptyBagException.class, () -> bag.draw());
        } catch (NotEnoughStudentException e) {
            fail();
        }
    }

    @Test
    void fillWith_studentList_shouldAdd() {
        StudentList students = new StudentList();
        try {
            students.changeNumOf(PawnType.GREEN_FROGS, 2);
            students.changeNumOf(PawnType.YELLOW_GNOMES, 2);
            students.changeNumOf(PawnType.PINK_FAIRIES, 2);
            bag.fillWith(students);
            assertTrue(students.equals(bag.getStudents()));

        } catch (NotEnoughStudentException e) {
            fail();
        }

    }
}