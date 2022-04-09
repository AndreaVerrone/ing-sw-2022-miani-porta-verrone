package it.polimi.ingsw.model.gametable;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.StudentList;

/**
 * Class to handle clouds
 */
class Cloud {
    private final int ID;
    private final StudentList students;

    /**
     * Class constructor
     * @param ID cloud identification
     */
    public Cloud(int ID){
        this.ID = ID;
        this.students = new StudentList();
    }

    /**
     * ID attribute getter
     * @return the cloud ID
     */
    public int getID(){
        return ID;
    }

    /**
     * Gives a copy of the {@code students} attribute without modifying it
     * @return a copy of {@code students}
     */
    public StudentList getStudents() {
        return students.clone();
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
     * Gives a copy of the {@code students} attribute and empties the list by calling the {@code students.empty()} method
     * @return the copy of the {@code students} attribute
     */
    public StudentList getAllStudents() {
        StudentList studentsClone =  students.clone();
        students.empty();
        return studentsClone;
    }


}
