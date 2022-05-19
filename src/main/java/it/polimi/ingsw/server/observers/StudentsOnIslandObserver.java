package it.polimi.ingsw.server.observers;

import it.polimi.ingsw.server.model.utils.StudentList;

/**
 * Interface to implement the observer pattern.
 */
public interface StudentsOnIslandObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     * @param islandID the islandID of the island on which the student has been added
     * @param actualStudents the actual student list on island
     */
    public void studentsOnIslandObserverUpdate(int islandID, StudentList actualStudents);
}
