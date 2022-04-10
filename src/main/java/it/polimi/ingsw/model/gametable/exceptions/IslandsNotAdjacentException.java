package it.polimi.ingsw.model.gametable.exceptions;

/**
 * Class exception if you try to unify two islands not adjacent
 */
public class IslandsNotAdjacentException extends Exception{
    private final int islandIDToKeep;
    private final int islandIDToRemove;

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
