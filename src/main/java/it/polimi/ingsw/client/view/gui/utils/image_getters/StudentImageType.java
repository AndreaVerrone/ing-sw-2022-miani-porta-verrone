package it.polimi.ingsw.client.view.gui.utils.image_getters;

import it.polimi.ingsw.server.model.utils.PawnType;
import javafx.scene.image.Image;

/**
 * Class to get the {@code Image} of student
 */
public enum StudentImageType {

    GREEN("/assets/students/greenStudent3D.png",1),
    RED("/assets/students/redStudent3D.png",2),
    YELLOW("/assets/students/yellowStudent3D.png",3),
    PINK("/assets/students/pinkStudent3D.png",4),
    BLUE("/assets/students/blueStudent3D.png",5);

    /**
     * Path of the image
     */
    private final String path;

    /**
     * Position of the table of the student in the dining room from top to down
     */
    private final int tablePosition;

    /**
     * Saves the path of the image and the table of the student in the view
     * @param path of the image of student
     * @param tablePosition position of the student in the dining room from top to down
     */
    StudentImageType(String path, int tablePosition) {
        this.path = path;
        this.tablePosition = tablePosition;
    }

    /**
     * Method to get an {@code Image} of a student from its path in the project
     * @return {@code Image} of the student
     */
    public Image getImage(){
        return new Image(path, 45, 45, true, false);
    }

    public int getTablePosition() {
        return tablePosition;
    }

    /**
     * Method to convert a {@code PawnType} to a {@code StudentImageType}
     * @param type {@code PawnType} type of the  student to convert
     * @return the {@code StudentImageType} of the same color
     */
    public static StudentImageType typeConverter(PawnType type) {
        return switch (type) {
            case GREEN_FROGS -> GREEN;
            case YELLOW_GNOMES -> YELLOW;
            case RED_DRAGONS -> RED;
            case PINK_FAIRIES -> PINK;
            case BLUE_UNICORNS -> BLUE;
        };
    }
}
