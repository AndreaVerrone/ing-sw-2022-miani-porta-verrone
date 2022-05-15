package it.polimi.ingsw.model;

/**
 * Interface to implement the observer pattern.
 */
public interface BanOnIslandObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     * @param islandIDWithBan the island on which a ban has been put or removed
     */
    public void banOnIslandObserverUpdate(int islandIDWithBan);
}
