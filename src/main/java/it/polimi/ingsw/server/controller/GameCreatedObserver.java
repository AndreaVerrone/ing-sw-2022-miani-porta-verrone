package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.client.reduced_model.ReducedModel;

/**
 * Interface to implement the observer pattern.
 */
public interface GameCreatedObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     * @param nickname the nickname of the player to notify
     * @param table the table of the game
     */
    void gameCreatedObserverUpdate(String nickname, ReducedModel table);

}
