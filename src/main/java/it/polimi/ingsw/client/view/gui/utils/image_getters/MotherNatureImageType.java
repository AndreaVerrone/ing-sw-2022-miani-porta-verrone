package it.polimi.ingsw.client.view.gui.utils.image_getters;

import javafx.scene.image.Image;

/**
 * Class to get the {@code Image} of mother nature
 */
public enum MotherNatureImageType {

    MOTHER_NATURE("/assets/mothernature/mother_nature.png");

    /**
     * Path of the image
     */
    private final String path;

    /**
     * Saves the path of the image
     * @param path of the image of mother nature
     */
    MotherNatureImageType(String path) {
        this.path = path;
    }

    /**
     * Method to get an {@code Image} of mother nature from its path in the project
     * @return {@code Image} of mother nature
     */
    public Image getImage(){
        return new Image(path, 50, 50, true, false);
    }
}
