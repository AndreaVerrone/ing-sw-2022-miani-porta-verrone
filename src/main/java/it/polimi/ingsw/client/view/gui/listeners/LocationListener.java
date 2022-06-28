package it.polimi.ingsw.client.view.gui.listeners;

import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import javafx.event.Event;
import javafx.event.EventHandler;

public class LocationListener implements EventHandler {

    private final Location location;

    private final GUI gui;

    private int field = 0;

    /**
     * True if the listener can send messages
     */
    private boolean enable = true;

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
                gui.getClientController().moveMotherNature(field);
            }else {
                gui.getClientController().chooseDestination(position);
            }
        }else{
            System.out.println("Listener disabled in location " + location); //Debugging
        }
    }
}
