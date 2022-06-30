package it.polimi.ingsw.client.view.gui.utils.image_getters;

import it.polimi.ingsw.server.model.player.Wizard;
import javafx.scene.image.Image;

/**
 * Class to get the {@code Image} of wizard
 */
public enum WizardImageType{
    WIZARD1("/assets/wizards/wizard1.png","assets/wizards/sorcerer_no_bg.png"),
    WIZARD2("/assets/wizards/wizard2.png", "assets/wizards/king_no_bg.png"),
    WIZARD3("/assets/wizards/wizard3.png", "assets/wizards/pixie_no_bg.png"),
    WIZARD4("/assets/wizards/wizard4.png", "assets/wizards/wizard_no_bg.png"),
    MOTHER_NATURE("/assets/wizards/motherNature.png", null);

    /**
     * Path of the image of the wizard with the card
     */
    private final String pathNormalImage;

    /**
     * Path of the image of the wizard without the card background
     */
    private final String pathImageWithoutBackground;

    public String getPath() {
        return pathNormalImage;
    }

    /**
     * Saves the path of the image
     * @param pathNormalImage path of the image of the wizard with the card
     * @param pathImageWithoutBackground path of the image of the wizard without the card background
     */
    WizardImageType(String pathNormalImage, String pathImageWithoutBackground) {
        this.pathNormalImage = pathNormalImage;
        this.pathImageWithoutBackground = pathImageWithoutBackground;
    }

    /**
     * Method to get an {@code Image} of a wizard from its path in the project
     * @return {@code Image} of the wizard
     */
    public Image getImage(){
        return new Image(pathNormalImage, 320, 320, true, false);
    }

    /**
     * Method to get an {@code Image} of a wizard without the background from its path in the project
     * @return {@code Image} of the wizard without the background
     */
    public Image getImageWithoutBackground(){
        return new Image(pathImageWithoutBackground);
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
