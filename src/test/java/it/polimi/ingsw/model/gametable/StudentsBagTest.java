package it.polimi.ingsw.model.gametable;

import it.polimi.ingsw.model.LastRoundException;
import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.StudentList;
import it.polimi.ingsw.model.gametable.exceptions.EmptyBagException;
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
            assertEquals(students, bag.getStudents());
        } catch (NotEnoughStudentException | EmptyBagException | LastRoundException e) {
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
            assertEquals(students, bag.getStudents());
        } catch (NotEnoughStudentException | EmptyBagException | LastRoundException e) {
            fail();
        }
    }

    @Test
    void draw_bagEmpty_shouldThrow(){
        StudentList students = new StudentList();
        bag.fillWith(students);
        assertThrows(EmptyBagException.class, () -> bag.draw());
    }

    @Test
    void draw_bagAlmostEMpty_shouldThrow(){
        StudentList students = new StudentList();
        try {
            students.changeNumOf(PawnType.RED_DRAGONS, 1);
        } catch (NotEnoughStudentException e) {
            fail();
        }
        bag.fillWith(students);
        assertThrows(LastRoundException.class, () -> bag.draw());
    }

    @Test
    void fillWith_studentList_shouldAdd() {
        StudentList students = new StudentList();
        try {
            students.changeNumOf(PawnType.GREEN_FROGS, 2);
            students.changeNumOf(PawnType.YELLOW_GNOMES, 2);
            students.changeNumOf(PawnType.PINK_FAIRIES, 2);
            bag.fillWith(students);
            assertEquals(students, bag.getStudents());
        } catch (NotEnoughStudentException e) {
            fail();
        }
    }

}