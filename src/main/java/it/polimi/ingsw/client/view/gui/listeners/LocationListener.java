package it.polimi.ingsw.client.view.gui.listeners;

import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * This class is used to handle listeners of the location.
 */
public class LocationListener implements EventHandler {

    /**
     * The considered location.
     */
    private final Location location;

    /**
     * The considered gui.
     */
    private final GUI gui;

    /**
     * The field of the location.
     */
    private int field = 0;

    /**
     * True if the listener can send messages
     */
    private boolean enable = true;

    /**
     * The constructor of the class.
     * @param gui the considered gui
     * @param location the considered location.
     */
    public LocationListener(GUI gui, Location location){
        this.gui = gui;
        this.location = location;
    }

    /**
     * Method to enable a listener
     */
    public void enableListener(){
        enable = true;
    }

    /**
     * Method to disable a listener
     */
    public void disableListener(){
        enable = false;
    }

    public void setField(int field) {
        this.field = field;
    }

    @Override
    public void handle(Event event) {
        if(enable){
            Position position = new Position(location);
            position.setField(field);
            System.out.println("Location " + location);//Debugging
            if(gui.getCurrentState().equals(StateType.MOVE_MOTHER_NATURE_STATE)){
                gui.getClientController().moveMotherNature((field - gui.getTableScreen().getMotherNatureIsland() + 12)% 12 );
            }else {
                gui.getClientController().chooseDestination(position);
            }
        }else{
            System.out.println("Listener disabled in location " + location); //Debugging
        }
    }
}
