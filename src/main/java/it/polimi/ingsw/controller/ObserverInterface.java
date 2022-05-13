package it.polimi.ingsw.controller;

/**
 * This is the interface that allows to implement the observer pattern.
 */
public interface ObserverInterface {

    /**
     * This method allow to update the observer when the subject notify a change
     */
    public void update();
}
