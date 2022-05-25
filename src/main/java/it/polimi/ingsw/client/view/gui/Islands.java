package it.polimi.ingsw.client.view.gui;

import javafx.scene.image.Image;

public enum Islands {
    ISLAND1("/assets/islands/island1.png"),
    ISLAND2("/assets/islands/island2.png"),
    ISLAND3("/assets/islands/island3.png");

    private final String path;

    Islands(String path) {
        this.path = path;
    }

    public Image getImage(){
        return new Image(path, 140, 140, true, false);
    }
}
