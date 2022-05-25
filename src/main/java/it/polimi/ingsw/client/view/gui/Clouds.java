package it.polimi.ingsw.client.view.gui;

import javafx.scene.image.Image;

public enum Clouds {
    CLOUD1("/assets/clouds/cloud1.png"),
    CLOUD2("/assets/clouds/cloud2.png"),
    CLOUD3("/assets/clouds/cloud3.png");

    private final String path;

    Clouds(String path) {
        this.path = path;
    }

    public Image getImage(){
        return new Image(path, 110, 134, true, false);
    }
}
