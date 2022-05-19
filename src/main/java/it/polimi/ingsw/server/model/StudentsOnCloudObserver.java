package it.polimi.ingsw.server.model;

/**
 * Interface to implement the observer pattern.
 */
public interface StudentsOnCloudObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     * @param cloudID the ID of the cloud on which the students have been changed
     * @param actualStudentList the actual student list on the cloud
     */
    public void studentsOnCloudObserverUpdate(int cloudID, StudentList actualStudentList);
}
