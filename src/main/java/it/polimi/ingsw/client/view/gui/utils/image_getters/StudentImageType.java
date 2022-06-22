package it.polimi.ingsw.client.view.gui.utils.image_getters;

import it.polimi.ingsw.server.model.utils.PawnType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Class to get the {@code Image} of student
 */
public enum StudentImageType {

    GREEN("/assets/students/greenStudent3D.png",1, Color.GREEN),
    RED("/assets/students/redStudent3D.png",2, Color.RED),
    YELLOW("/assets/students/yellowStudent3D.png",3, Color.SANDYBROWN),
    PINK("/assets/students/pinkStudent3D.png",4, Color.DEEPPINK),
    BLUE("/assets/students/blueStudent3D.png",5, Color.BLUE);

    /**
     * Path of the image
     */
    private final String path;

    /**
     * Position of the table of the student in the dining room from top to down
     */
    private final int tablePosition;

    /**
     * Color of the pawn
     */
    private final Color color;
    /**
     * Saves the path of the image, the table of the student in the view and its color
     * @param path of the image of student
     * @param tablePosition position of the student in the dining room from top to down
     * @param color color of the pawn
     */
    StudentImageType(String path, int tablePosition, Color color) {
        this.path = path;
        this.tablePosition = tablePosition;
        this.color = color;
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

    /**
     * Method to get an image of the student resized as an icon
     * @return the image of the student with the dimensions of an icon
     */
    public Image  getIcon(){
        return new Image(path, 20, 20, true, false);
    }

    /**
     * Method to get a bigget {@code Image} of a student from its path in the project
     * @return {@code Image} of the student bigger
     */
    public Image getImageBigger(){
        return new Image(path, 60, 60, true, false);
    }



    /**
     * Method to get the color of the pawn
     * @return {@code Color} of the pawn
     */
    public Color getColor(){
        return color;
    }
}
