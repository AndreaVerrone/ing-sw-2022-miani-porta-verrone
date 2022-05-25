package it.polimi.ingsw.server.controller.matchmaking.observers;

import it.polimi.ingsw.server.model.player.Wizard;

/**
 * Interface to implement the observer pattern.
 */
public interface WizardSelectedObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param wizard wizard selected by the player
     */
    void wizardSelectedObserverUpdate(Wizard wizard);
}
