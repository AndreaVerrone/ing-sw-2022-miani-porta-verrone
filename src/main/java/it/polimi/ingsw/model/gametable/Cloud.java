package it.polimi.ingsw.model.gametable;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.StudentList;

/**
 * Class to handle clouds
 */
class Cloud {
    /**
     * Number to identify uniquely the cloud
     */
    private final int ID;
    /**
     * Students on the cloud
     */
    private final StudentList students;

    /**
     * Class constructor
     * @param ID cloud identification
     */
    public Cloud(int ID){
        this.ID = ID;
        this.students = new StudentList();
    }


    public int getID(){
        return ID;
    }

    /**
     * Adds to the cloud a student of the given {@code PawnType}
     * @param type the type of the student to add
     */
    public void addStudent(PawnType type) {
        try {
            students.changeNumOf(type, 1);
        } catch (NotEnoughStudentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns and removes all the students on the cloud
     * @return students on the cloud
     */
    public StudentList getAllStudents() {
        StudentList studentsClone =  students.clone();
        students.empty();
        return studentsClone;
    }


}
