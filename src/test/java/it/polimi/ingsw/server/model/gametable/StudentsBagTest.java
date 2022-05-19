package it.polimi.ingsw.server.model.gametable;

import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.exceptions.EmptyBagException;
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
        try {
            StudentList students = new StudentList();
            students.changeNumOf(PawnType.GREEN_FROGS, 2);
            students.changeNumOf(PawnType.YELLOW_GNOMES, 2);
            students.changeNumOf(PawnType.PINK_FAIRIES, 2);
            bag.fillWith(students);
            bag.draw();
            assertEquals(students.numAllStudents() - 1, bag.studentsRemaining());
        } catch (NotEnoughStudentException | EmptyBagException e) {
            fail();
        }
    }

    @Test
    void draw_bagWithOnlyOneStudentGreen_shouldReturnGreen() {
        try {
            StudentList students = new StudentList();
            students.changeNumOf(PawnType.GREEN_FROGS, 1);
            bag.fillWith(students);
            PawnType typeTest = bag.draw();
            assertEquals(PawnType.GREEN_FROGS, typeTest);
        } catch (NotEnoughStudentException | EmptyBagException e) {
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
    void fillWith_studentList_shouldAdd() {
        StudentList students = new StudentList();
        try {
            students.changeNumOf(PawnType.GREEN_FROGS, 2);
            students.changeNumOf(PawnType.YELLOW_GNOMES, 2);
            students.changeNumOf(PawnType.PINK_FAIRIES, 2);
            bag.fillWith(students);
            assertEquals(students.numAllStudents(), bag.studentsRemaining());
        } catch (NotEnoughStudentException e) {
            fail();
        }
    }

}