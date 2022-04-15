package it.polimi.ingsw.model.gametable.exceptions;

/**
 * Thrown if you try to unify two not adjacent islands
 */
public class IslandsNotAdjacentException extends Exception{
    /**
     * ID of the island that remains on the {@code GameTable}
     */
    private final int islandIDToKeep;
    /**
     * ID of the island removed
     */
    private final int islandIDToRemove;

    /**
     * Constructor of the class. Saves the ID of the two islands
     * @param ID1 ID of the island that remains
     * @param ID2 ID of the island removed
     */
    public IslandsNotAdjacentException(int ID1, int ID2){
        islandIDToKeep = ID1;
        islandIDToRemove = ID2;
    }

    public int getIslandIDToKeep() {
        return islandIDToKeep;
    }

    public int getIslandIDToRemove() {
        return islandIDToRemove;
    }
}
