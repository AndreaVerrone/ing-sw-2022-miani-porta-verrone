package it.polimi.ingsw.client.view.gui.utils.image_getters;

import javafx.scene.image.Image;

/**
 * Class to get the {@code Image} of an island
 */
public enum IslandImageType {
    ISLAND1("/assets/islands/island1.png"),
    ISLAND2("/assets/islands/island2.png"),
    ISLAND3("/assets/islands/island3.png");

    /**
     * Path of the image
     */
    private final String path;

    /**
     * Saves the path of the image
     * @param path of the image of the island
     */
    IslandImageType(String path) {
        this.path = path;
    }

    /**
     * Method to get an {@code Image} of an island from its path in the project
     * @return {@code Image} of the island
     */
    public Image getImage(){
        return new Image(path, 140, 140, true, false);
    }
}
