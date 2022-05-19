package it.polimi.ingsw.server.observers;

/**
 * Interface to implement the observer pattern.
 */
public interface ChangeCurrentPlayerObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     * @param actualCurrentPlayerNickname the actual current player's nickname
     */
    void changeCurrentPlayerObserverUpdate(String actualCurrentPlayerNickname);
}
