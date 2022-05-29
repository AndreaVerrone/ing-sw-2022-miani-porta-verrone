package it.polimi.ingsw.client.view.gui.utils.image_getters;

import it.polimi.ingsw.server.model.utils.PawnType;
import javafx.scene.image.Image;

/**
 * Class to get the {@code Image} of professor
 */
public enum ProfessorImageType {

    GREEN("/assets/professors/greenProf3D.png",1),
    RED("/assets/professors/redProf3D.png",2),
    YELLOW("/assets/professors/yellowProf3D.png",3),
    PINK("/assets/professors/pinkProf3D.png",4),
    BLUE("/assets/professors/blueProf3D.png",5);

    /**
     * Path of the image
     */
    private final String path;

    /**
     * Position of the table of the professor in the dining room from top to down
     */
    private final int tablePosition;

    /**
     * Saves the path of the image and the table of the professor in the view
     * @param path of the image of professor
     * @param tablePosition position of the professor in the dining room from top to down
     */
    ProfessorImageType(String path, int tablePosition) {
        this.path = path;
        this.tablePosition = tablePosition;
    }

    /**
     * Method to get an {@code Image} of a professor from its path in the project
     * @return {@code Image} of the professor
     */
    public Image getImage(){
        return new Image(path, 50, 50, true, false);
    }

    public int getTablePosition() {
        return tablePosition;
    }

    /**
     * Method to convert a {@code PawnType} to a {@code ProfessorImageType}
     * @param type {@code PawnType} type of the student to convert
     * @return the {@code ProfessorImageType} of the same color
     */
    public static ProfessorImageType typeConverter(PawnType type) {
        return switch (type) {
            case GREEN_FROGS -> GREEN;
            case YELLOW_GNOMES -> YELLOW;
            case RED_DRAGONS -> RED;
            case PINK_FAIRIES -> PINK;
            case BLUE_UNICORNS -> BLUE;
        };
    }
}
