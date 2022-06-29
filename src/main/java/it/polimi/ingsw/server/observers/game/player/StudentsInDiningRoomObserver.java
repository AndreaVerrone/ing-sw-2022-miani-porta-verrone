package it.polimi.ingsw.server.observers.game.player;

import it.polimi.ingsw.server.model.utils.StudentList;

/**
 * Interface to implement the observer pattern.
 */
public interface StudentsInDiningRoomObserver {

    /**
     * Notifies that the students in the dining room of a player has changed.
     * @param nickname the nickname of the player
     * @param actualStudents the actual students
     */
    void studentsInDiningRoomObserverUpdate(String nickname, StudentList actualStudents);
}
