package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.server.model.utils.PawnType;
import javafx.scene.image.Image;

public enum Professors {

    GREEN("/assets/professors/greenProf3D.png"),
    YELLOW("/assets/professors/yellowProf3D.png"),
    RED("/assets/professors/redProf3D.png"),
    PINK("/assets/professors/pinkProf3D.png"),
    BLUE("/assets/professors/blueProf3D.png");

    private final String path;

    Professors(String path) {
        this.path = path;
    }

    public Image getImage(){
        return new Image(path, 50, 50, true, false);
    }

    public static Professors typeConverter(PawnType type) {
        return switch (type) {
            case GREEN_FROGS -> GREEN;
            case YELLOW_GNOMES -> YELLOW;
            case RED_DRAGONS -> RED;
            case PINK_FAIRIES -> PINK;
            case BLUE_UNICORNS -> BLUE;
        };
    }
}
