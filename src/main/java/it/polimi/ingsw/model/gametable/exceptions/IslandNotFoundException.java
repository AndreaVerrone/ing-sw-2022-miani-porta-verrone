package it.polimi.ingsw.model.gametable.exceptions;

public class IslandNotFoundException extends Exception{

    private final int islandID;

    public IslandNotFoundException(int islandID){
        this.islandID = islandID;
    }

    public int getIslandID() {
        return islandID;
    }
}
