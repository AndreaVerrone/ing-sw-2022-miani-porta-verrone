package it.polimi.ingsw.client.view.gui.listeners;

import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.model.utils.PawnType;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * This class is used to handle listener of students
 */
public class StudentListener implements EventHandler {

    /**
     * Student listened
     */
    private final PawnType student;

    /**
     * Location of the student listened
     */
    private final Location location;

    /**
     * Gui of the game
     */
    private final GUI gui;

    /**
     * True if the listener can send messages
     */
    private boolean enable = true;

    /**
     * The constructor of the class.
     * @param gui the considered gui
     * @param student the student type
     * @param location the location of the student
     */
    public StudentListener(GUI gui, PawnType student, Location location){
        this.gui = gui;
        this.student = student;
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

    @Override
    public void handle(Event event) {
        Position position = new Position(location);
        if(enable){
            gui.getClientController().chooseStudentFromLocation(student, position);
        }
        event.consume();
    }
}
