package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.server.model.utils.PawnType;
import javafx.scene.image.Image;

public enum Professors {

    GREEN("/assets/professors/greenProf3D.png",1),
    RED("/assets/professors/redProf3D.png",2),
    YELLOW("/assets/professors/yellowProf3D.png",3),
    PINK("/assets/professors/pinkProf3D.png",4),
    BLUE("/assets/professors/blueProf3D.png",5);

    private final String path;

    private final int tablePosition;

    Professors(String path, int tablePosition) {
        this.path = path;
        this.tablePosition = tablePosition;
    }

    public Image getImage(){
        return new Image(path, 50, 50, true, false);
    }

    public int getTablePosition() {
        return tablePosition;
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
