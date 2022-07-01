package it.polimi.ingsw.client.view.gui.listeners;

import it.polimi.ingsw.client.view.gui.GUI;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * This class is used to handle listener on the cloud.
 */
public class CloudListener implements EventHandler {

    /**
     * The considered gui.
     */
    private final GUI gui;

    /**
     * The ID of the cloud.
     */
    private int ID;

    /**
     * True if the listener can send messages
     */
    private boolean enable = true;

    /**
     * The constructor of the class.
     * It will create the class taking in input the considered gui and the
     * id of the cloud
     * @param gui the considered gui
     * @param ID the id of the cloud
     */
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
        }
    }
}
