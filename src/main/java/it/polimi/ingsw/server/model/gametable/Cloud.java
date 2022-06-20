package it.polimi.ingsw.server.model.gametable;

import it.polimi.ingsw.client.reduced_model.ReducedCloud;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import it.polimi.ingsw.server.observers.StudentsOnCloudObserver;

import java.util.ArrayList;
import java.util.List;

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
    Cloud(int ID){
        this.ID = ID;
        this.students = new StudentList();
    }


    int getID(){
        return ID;
    }

    /**
     * Gets the number of students on this cloud
     * @return the number of students on this cloud
     */
    int numOfStudents(){
        return students.numAllStudents();
    }

    /**
     * Adds to the cloud a student of the given {@code PawnType}
     * @param type the type of the student to add
     */
    void addStudent(PawnType type) {
        try {
            students.changeNumOf(type, 1);
        } catch (NotEnoughStudentException e) {
            e.printStackTrace();
        }
        notifyStudentsOnCloudObservers(this.ID,this.students.clone());
    }

    /**
     * Returns and removes all the students on the cloud
     * @return students on the cloud
     */
    StudentList getAllStudents() {
        StudentList studentsClone =  students.clone();
        students.empty();
        notifyStudentsOnCloudObservers(this.ID,students.clone());
        return studentsClone;
    }

    // MANAGEMENT OF OBSERVERS ON STUDENTS ON CLOUD
    /**
     * List of the observer on students on cloud
     */
    private final List<StudentsOnCloudObserver> studentsOnCloudObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on students on cloud.
     * @param observer the observer to be added
     */
    void addStudentsOnCloudObserver(StudentsOnCloudObserver observer){
        studentsOnCloudObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on students on cloud.
     * @param observer the observer to be removed
     */
    void removeStudentsOnCloudObserver(StudentsOnCloudObserver observer){
        studentsOnCloudObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on students on cloud.
     * @param cloudID the ID of the cloud on which the students have been changed
     * @param actualStudentList the actual student list on the cloud
     */
    private void notifyStudentsOnCloudObservers(int cloudID, StudentList actualStudentList){
        for(StudentsOnCloudObserver observer : studentsOnCloudObservers)
            observer.studentsOnCloudObserverUpdate(cloudID,actualStudentList);
    }

    // CREATION OF THE REDUCED VERSION
    /**
     * Creates a reduced version of this cloud used to represent it client side.
     * @return a reduced version of this cloud
     * @see ReducedCloud
     */
    ReducedCloud createCloudReduction(){
        return new ReducedCloud(ID,students);
    }

}
