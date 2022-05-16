package it.polimi.ingsw.model;

/**
 * Interface to implement the observer pattern.
 */
public interface ChangeTowerNumberObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param nickName the nickname of the player that has the school board on which the changes have been happened.
     * @param numOfActualTowers the actual number of towers
     */
    public void changeTowerNumberUpdate(String nickName, int numOfActualTowers);
}
