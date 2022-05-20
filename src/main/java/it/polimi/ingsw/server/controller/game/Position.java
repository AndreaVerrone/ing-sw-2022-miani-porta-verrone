package it.polimi.ingsw.server.controller.game;

import java.io.Serializable;

/**
 * This class represent a position of the game.
 */
public class Position implements Serializable {

    /**
     * the location of the position
     */
    private final Location location;

    /**
     * an integer filed to carry additional information regarding the location.
     * (e.g., if the location is an island this is the islandID)
     * <p>
     * note: this attribute may be used or not depending on the location.
     */
    private int field;

    /**
     * The constructor of the class takes in input the location.
     * @param location the location
     */
    public Position(Location location){
        this.location=location;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }

    /**
     * This method returns true if the location passed in the parameter
     * is the same of the location of this position
     * @param location the location that must be compared with the location field
     * @return if the location passed in the parameter is the same of the location of this position
     */
    public boolean isLocation(Location location){
        return this.location == location;
    }
}
