package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.server.model.utils.PawnType;
import javafx.scene.image.Image;

public enum Students {

    GREEN("/assets/students/greenStudent3D.png",1),
    RED("/assets/students/redStudent3D.png",2),
    YELLOW("/assets/students/yellowStudent3D.png",3),
    PINK("/assets/students/pinkStudent3D.png",4),
    BLUE("/assets/students/blueStudent3D.png",5);

    private final String path;

    private final int tablePosition;

    Students(String path, int tablePosition) {
        this.path = path;
        this.tablePosition = tablePosition;
    }

    public Image getImage(){
        return new Image(path, 45, 45, true, false);
    }

    public int getTablePosition() {
        return tablePosition;
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
