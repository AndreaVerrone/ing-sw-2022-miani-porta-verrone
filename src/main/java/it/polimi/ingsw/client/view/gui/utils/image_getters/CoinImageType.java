package it.polimi.ingsw.client.view.gui.utils.image_getters;

import javafx.scene.image.Image;

/**
 * Class to get the {@code Image} of a coin
 */
public enum CoinImageType {

    COIN("/assets/coin/Moneta_base.png");

    /**
     * Path of the image
     */
    private final String path;

    /**
     * Saves the path of the image
     * @param path of the image of a coin
     */
     CoinImageType(String path) {
        this.path = path;
    }

    /**
     * Method to get an {@code Image} of a coin from its path in the project
     * @return {@code Image} of mother nature
     */
    public Image getImage(){
        return new Image(path, 100, 100, true, false);
    }
}