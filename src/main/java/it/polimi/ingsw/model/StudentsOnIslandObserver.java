package it.polimi.ingsw.model;

/**
 * Interface to implement the observer pattern.
 */
public interface StudentsOnIslandObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     * @param islandID the islandID of the island on which the student has been added
     * @param studentChanged the pawn type of the student that has been added on island
     */
    public void studentsOnIslandObserverUpdate(int islandID, PawnType studentChanged);
}
