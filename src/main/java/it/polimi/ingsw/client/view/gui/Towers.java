package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.TowerType;
import javafx.scene.image.Image;

public enum Towers {
    BLACK("/assets/towers/black_tower.png"),
    WHITE("/assets/towers/white_tower.png"),
    GREY("/assets/towers/grey_tower.png");

    private final String path;

    Towers(String path) {
        this.path = path;
    }

    public Image getImage(){
        return new Image(path, 80, 80, true, false);
    }

    public static Towers typeConverter(TowerType type) {
        return switch (type) {
            case BLACK -> BLACK;
            case WHITE -> WHITE;
            case GREY -> GREY;
        };
    }
}
