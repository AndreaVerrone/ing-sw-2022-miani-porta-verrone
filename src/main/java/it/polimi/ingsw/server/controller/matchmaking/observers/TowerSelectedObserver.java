package it.polimi.ingsw.server.controller.matchmaking.observers;

import it.polimi.ingsw.server.model.utils.TowerType;

/**
 * Interface to implement the observer pattern.
 */
public interface TowerSelectedObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param tower tower type selected by the player
     */
    void towerSelectedObserverUpdate(TowerType tower);
}
