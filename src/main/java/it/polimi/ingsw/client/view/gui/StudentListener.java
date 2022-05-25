package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.model.utils.PawnType;
import javafx.event.Event;
import javafx.event.EventHandler;

public class StudentListener implements EventHandler {

    private final PawnType student;

    private final Location location;

    public StudentListener(PawnType student, Location location){
        this.student = student;
        this.location = location;
    }


    @Override
    public void handle(Event event) {
        System.out.println("Student " + student + " in location " + location);
        event.consume();
    }
}
