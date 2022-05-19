package it.polimi.ingsw.server.observers;

import it.polimi.ingsw.server.model.utils.StudentList;

/**
 * Interface to implement the observer pattern.
 */
public interface StudentsOnEntranceObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param nickname the nickname of the player that has the school board on which the changes have been happened
     * @param actualStudents the actual student list in entrance
     */
    public void studentsOnEntranceObserverUpdate(String nickname, StudentList actualStudents);
}
