package it.polimi.ingsw.server.observers;

/**
 * Interface to implement the observer pattern.
 */
public interface BanOnIslandObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param islandIDWithBan the island on which a ban has been put or removed
     * * @param actualNumOfBans the actual num of bans on the island
     */
    public void banOnIslandObserverUpdate(int islandIDWithBan, int actualNumOfBans);
}
