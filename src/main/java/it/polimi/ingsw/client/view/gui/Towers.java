package it.polimi.ingsw.client.view.gui;

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
        return new Image(path, 50, 50, true, false);
    }
}
