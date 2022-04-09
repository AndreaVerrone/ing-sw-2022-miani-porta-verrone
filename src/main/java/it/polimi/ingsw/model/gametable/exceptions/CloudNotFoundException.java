package it.polimi.ingsw.model.gametable.exceptions;

public class CloudNotFoundException extends Exception{
    private final int cloudID;

    public CloudNotFoundException(int cloudID){
        this.cloudID = cloudID;
    }

    public int getCloudID() {
        return cloudID;
    }
}
