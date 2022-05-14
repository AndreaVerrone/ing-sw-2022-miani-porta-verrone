package it.polimi.ingsw.model;

public interface ChangeCoinNumberInBagObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     */
    public void changeCoinNumberInBagObserverUpdate();
}

