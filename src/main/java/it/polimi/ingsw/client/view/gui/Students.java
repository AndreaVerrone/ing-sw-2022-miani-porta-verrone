package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.server.model.utils.PawnType;
import javafx.scene.image.Image;

public enum Students {
    GREEN("/assets/students/greenStudent3D.png"),
    YELLOW("/assets/students/yellowStudent3D.png"),
    RED("/assets/students/redStudent3D.png"),
    PINK("/assets/students/pinkStudent3D.png"),
    BLUE("/assets/students/blueStudent3D.png");

    private final String path;

    Students(String path) {
        this.path = path;
    }

    public Image getImage(){
        return new Image(path, 45, 45, true, false);
    }

    public static Students typeConverter(PawnType type) {
        return switch (type) {
            case GREEN_FROGS -> GREEN;
            case YELLOW_GNOMES -> YELLOW;
            case RED_DRAGONS -> RED;
            case PINK_FAIRIES -> PINK;
            case BLUE_UNICORNS -> BLUE;
        };
    }
}
