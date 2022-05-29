package it.polimi.ingsw.client.view.gui.utils.image_getters;

import javafx.scene.image.Image;

/**
 * Class to get the {@code Image} of a cloud
 */
public enum CloudImageType {
    CLOUD1("/assets/clouds/cloud1.png"),
    CLOUD2("/assets/clouds/cloud2.png"),
    CLOUD3("/assets/clouds/cloud3.png");

    /**
     * Path of the image
     */
    private final String path;

    /**
     * Saves the path of the image
     * @param path of the image of the cloud
     */
    CloudImageType(String path) {
        this.path = path;
    }

    /**
     * Method to get an {@code Image} of a cloud from its path in the project
     * @return {@code Image} of the cloud
     */
    public Image getImage(){
        return new Image(path, 110, 134, true, false);
    }
}
