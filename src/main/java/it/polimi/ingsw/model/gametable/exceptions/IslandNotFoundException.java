package it.polimi.ingsw.model.gametable.exceptions;

/**
 * Thrown if the island asked doesn't exist
 */
public class IslandNotFoundException extends Exception{

    /**
     * ID of the island not found
     */
    private final int islandID;

    /**
     * Contructor of the class. Saves the ID of the island not found
     * @param islandID ID of the island not found
     */
    public IslandNotFoundException(int islandID){
        this.islandID = islandID;
    }

    public int getIslandID() {
        return islandID;
    }
}
