package it.polimi.ingsw.server.observers;

import it.polimi.ingsw.server.model.utils.StudentList;

/**
 * Interface to implement the observer pattern.
 */
public interface StudentsInDiningRoomObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     */
    public void studentsInDiningRoomObserverUpdate(String nickname, StudentList actualStudents);
}
