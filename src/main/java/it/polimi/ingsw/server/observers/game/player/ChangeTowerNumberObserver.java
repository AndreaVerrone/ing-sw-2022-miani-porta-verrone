package it.polimi.ingsw.server.observers.game.player;

/**
 * Interface to implement the observer pattern.
 */
public interface ChangeTowerNumberObserver {

    /**
     * Notifies that the number of towers of a player has changed.
     *
     * @param nickName the nickname of the player
     * @param numOfActualTowers the actual number of towers
     */
    void changeTowerNumberUpdate(String nickName, int numOfActualTowers);
}
