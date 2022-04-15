package it.polimi.ingsw.model.gametable.exceptions;

/**
 * Thrown if the cloud asked doesn't exist
 */
public class CloudNotFoundException extends Exception{
    /**
     * ID of the cloud not found
     */
    private final int cloudID;

    /**
     * Constructor of the class. Saves the ID of the cloud not found
     * @param cloudID ID of the cloud not found
     */
    public CloudNotFoundException(int cloudID){
        this.cloudID = cloudID;
    }

    public int getCloudID(){
        return cloudID;
    }
}
