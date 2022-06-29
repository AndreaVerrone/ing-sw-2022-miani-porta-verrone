package it.polimi.ingsw.server.model.gametable;

import it.polimi.ingsw.client.reduced_model.ReducedCloud;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void addStudent_Red() {
        cloud.addStudent(PawnType.RED_DRAGONS);
        int numOfRed = cloud.getAllStudents().getNumOf(PawnType.RED_DRAGONS);
        assertEquals(1, numOfRed);
    }

    @Test
    void getAllStudents_ShouldReturnAllStudents() {
        cloud.addStudent(PawnType.GREEN_FROGS);
        cloud.addStudent(PawnType.RED_DRAGONS);
        StudentList students = cloud.getAllStudents();
        assertEquals(1, students.getNumOf(PawnType.GREEN_FROGS));
        assertEquals(1, students.getNumOf(PawnType.RED_DRAGONS));
    }

    @Test
    void getAllStudents_ShouldEmptyCloud(){
        cloud.addStudent(PawnType.GREEN_FROGS);
        cloud.getAllStudents();
        assertEquals(0, cloud.getAllStudents().numAllStudents());
    }

    @Test
    public void createCloudReduction_ShouldCreateACopy(){
        for (PawnType student : PawnType.values())
            cloud.addStudent(student);

        ReducedCloud reducedCloud = cloud.createCloudReduction();
        StudentList studentsOnCloud = cloud.getAllStudents();
        assertEquals(cloud.getID(), reducedCloud.ID());
        assertEquals(studentsOnCloud, reducedCloud.students());
    }
}