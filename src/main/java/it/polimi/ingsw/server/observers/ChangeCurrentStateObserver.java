package it.polimi.ingsw.server.observers;

import it.polimi.ingsw.server.controller.StateType;

/**
 * Interface to implement the observer pattern.
 */
public interface ChangeCurrentStateObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param stateType type of the new state of the game
     */
    void changeCurrentStateObserverUpdate(StateType stateType);

}
