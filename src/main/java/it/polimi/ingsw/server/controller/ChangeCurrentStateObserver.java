package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.Collection;

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

    void requestChoosePlayerParameter(Collection<TowerType> towersAvailable, Collection<Wizard> wizardsAvailable);
}
