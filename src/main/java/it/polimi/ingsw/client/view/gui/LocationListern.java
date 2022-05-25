package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import javafx.event.Event;
import javafx.event.EventHandler;

public class LocationListern implements EventHandler {

    private final Location location;

    public LocationListern(Location location){
        this.location = location;
    }


    @Override
    public void handle(Event event) {
        System.out.println("Location " + location);
    }
}
