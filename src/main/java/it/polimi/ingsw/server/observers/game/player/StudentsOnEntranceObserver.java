package it.polimi.ingsw.server.observers.game.player;

import it.polimi.ingsw.server.model.utils.StudentList;

/**
 * Interface to implement the observer pattern.
 */
public interface StudentsOnEntranceObserver {

    /**
     * Notifies that the students in the entrance of a player has changed.
     *
     * @param nickname the nickname of the player
     * @param actualStudents the actual students
     */
    void studentsOnEntranceObserverUpdate(String nickname, StudentList actualStudents);
}
