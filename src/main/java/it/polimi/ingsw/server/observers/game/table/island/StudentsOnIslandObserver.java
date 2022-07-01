package it.polimi.ingsw.server.observers.game.table.island;

import it.polimi.ingsw.server.model.utils.StudentList;

/**
 * Interface to implement the observer pattern.
 */
public interface StudentsOnIslandObserver {

    /**
     * Notifies that the students on an island have been changed.
     * @param islandID the id of the island
     * @param actualStudents the actual student list on island
     */
    void studentsOnIslandObserverUpdate(int islandID, StudentList actualStudents);
}
