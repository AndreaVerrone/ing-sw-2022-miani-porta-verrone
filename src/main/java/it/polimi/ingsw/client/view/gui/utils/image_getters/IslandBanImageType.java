package it.polimi.ingsw.client.view.gui.utils.image_getters;

import javafx.scene.image.Image;

/**
 * Class to get the {@code Image} of a ban
 */
public enum IslandBanImageType {


    BAN("/assets/islands/deny_island_icon.png");

    /**
     * Path of the image
     */
    private final String path;

    /**
     * Saves the path of the image
     * @param path of the image of a ban
     */
    IslandBanImageType(String path) {
        this.path = path;
    }

    /**
     * Method to get an {@code Image} of a coin from its path in the project
     * @return {@code Image} of a ban
     */
    public Image getImage(){
        return new Image(path, 80, 80, true, false);
    }

    /**
     * Method to get an image of a ban resized as an icon
     * @return the image of a ban with the dimensions of an icon
     */
    public Image  getIcon(){
        return new Image(path, 20, 20, true, false);
    }
}
