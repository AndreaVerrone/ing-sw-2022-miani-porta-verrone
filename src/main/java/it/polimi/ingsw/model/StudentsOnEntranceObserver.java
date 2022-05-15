package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;

/**
 * Interface to implement the observer pattern.
 */
public interface StudentsOnEntranceObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param player the player that has the school board on which the changes have been happened
     * @param actualStudents the actual student list in entrance
     */
    public void studentsOnEntranceObserverUpdate(Player player, StudentList actualStudents);
}
