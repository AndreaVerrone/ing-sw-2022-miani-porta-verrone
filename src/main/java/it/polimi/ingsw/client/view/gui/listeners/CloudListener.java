package it.polimi.ingsw.client.view.gui.listeners;

import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import javafx.event.Event;
import javafx.event.EventHandler;

public class CloudListener implements EventHandler {



    private final GUI gui;

    private int ID;

    /**
     * True if the listener can send messages
     */
    private boolean enable = true;

    public CloudListener(GUI gui, int ID){

        this.gui = gui;
        this.ID = ID;
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


    @Override
    public void handle(Event event) {
        if(enable){
            gui.getClientController().takeStudentFromCloud(ID);
        }else{
            System.out.println("Listener disabled on cloud:  " + ID); //Debugging
        }
    }
}
