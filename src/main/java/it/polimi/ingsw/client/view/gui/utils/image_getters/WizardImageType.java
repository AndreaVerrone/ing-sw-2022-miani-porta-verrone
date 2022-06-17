package it.polimi.ingsw.client.view.gui.utils.image_getters;

import it.polimi.ingsw.server.model.player.Wizard;
import javafx.scene.image.Image;

/**
 * Class to get the {@code Image} of wizard
 */
public enum WizardImageType{
    WIZARD1("/assets/wizards/wizard1.png"),
    WIZARD2("/assets/wizards/wizard2.png"),
    WIZARD3("/assets/wizards/wizard3.png"),
    WIZARD4("/assets/wizards/wizard4.png"),
    MOTHER_NATURE("/assets/wizards/motherNature.png");

    /**
     * Path of the image
     */
    private final String path;

    public String getPath() {
        return path;
    }

    /**
     * Saves the path of the image
     * @param path of the image of the wizard
     */
    WizardImageType(String path) {
        this.path = path;
    }

    /**
     * Method to get an {@code Image} of a wizard from its path in the project
     * @return {@code Image} of the wizard
     */
    public Image getImage(){
        return new Image(path, 320, 320, true, false);
    }

    /**
     * Method to convert a {@code Wizard} to a {@code WizardImageType}
     * @param type {@code Wizard} type of the wizard to convert
     * @return the {@code WizardImageType} of the same wizard
     */
    public static WizardImageType typeConverter(Wizard type) {
        return switch (type) {
            case W1 -> WIZARD1;
            case W2 -> WIZARD2;
            case W3 -> WIZARD3;
            case W4 -> WIZARD4;
        };
    }
}
