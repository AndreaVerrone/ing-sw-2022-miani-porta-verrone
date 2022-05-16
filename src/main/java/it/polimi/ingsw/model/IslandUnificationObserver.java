package it.polimi.ingsw.model;

/**
 * Interface to implement the observer pattern.
 */
public interface IslandUnificationObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param islandRemovedID  ID of the island that has been removed
     * @param finalNumOfBan the num of ban after the unification
     * @param finalSize the size of the island after unification
     * @param finalStudentList the students on island after unification
     */
    public void islandUnificationObserverUpdate(int islandRemovedID, int finalNumOfBan, int finalSize, StudentList finalStudentList);
}
