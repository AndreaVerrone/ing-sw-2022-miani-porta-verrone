package it.polimi.ingsw.client.view.gui.utils.image_getters;

import it.polimi.ingsw.server.model.utils.TowerType;
import javafx.scene.image.Image;

/**
 * Class to get the {@code Image} of tower
 */
public enum TowerImageType {
    BLACK("/assets/towers/black_tower.png"),
    WHITE("/assets/towers/white_tower.png"),
    GREY("/assets/towers/grey_tower.png");

    /**
     * Path of the image
     */
    private final String path;

    /**
     * Saves the path of the image
     * @param path of the image of the tower
     */
    TowerImageType(String path) {
        this.path = path;
    }

    /**
     * Method to get an {@code Image} of a tower from its path in the project
     * @return {@code Image} of the tower
     */
    public Image getImage(){
        return new Image(path, 80, 80, true, false);
    }

    /**
     * Method to convert a {@code TowerType} to a {@code TowerImageType}
     * @param type {@code TowerType} type of the  tower to convert
     * @return the {@code TowerImageType} of the same color
     */
    public static TowerImageType typeConverter(TowerType type) {
        return switch (type) {
            case BLACK -> BLACK;
            case WHITE -> WHITE;
            case GREY -> GREY;
        };
    }
}
