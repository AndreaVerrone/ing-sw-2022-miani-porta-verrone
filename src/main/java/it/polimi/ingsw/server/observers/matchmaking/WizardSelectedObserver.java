package it.polimi.ingsw.server.observers.matchmaking;

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
     * @param player nickname of the player that selected the wizard
     */
    void wizardSelectedObserverUpdate(String player, Wizard wizard);
}