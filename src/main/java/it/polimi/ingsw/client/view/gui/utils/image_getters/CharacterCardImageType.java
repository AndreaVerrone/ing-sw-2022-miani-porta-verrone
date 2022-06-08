package it.polimi.ingsw.client.view.gui.utils.image_getters;

import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import javafx.scene.image.Image;

/**
 * Class to get the {@code Image} of a character card
 */
public enum CharacterCardImageType {
    CARD1("/assets/characterCards/CarteTOT_front.jpg"),
    CARD2("/assets/characterCards/CarteTOT_front2.jpg"),
    CARD3("/assets/characterCards/CarteTOT_front3.jpg"),
    CARD4("/assets/characterCards/CarteTOT_front4.jpg"),
    CARD5("/assets/characterCards/CarteTOT_front5.jpg"),
    CARD6("/assets/characterCards/CarteTOT_front6.jpg"),
    CARD7("/assets/characterCards/CarteTOT_front7.jpg"),
    CARD8("/assets/characterCards/CarteTOT_front8.jpg"),
    CARD9("/assets/characterCards/CarteTOT_front9.jpg"),
    CARD10("/assets/characterCards/CarteTOT_front10.jpg"),
    CARD11("/assets/characterCards/CarteTOT_front11.jpg"),
    CARD12("/assets/characterCards/CarteTOT_front12.jpg");

    /**
     * Path of the image
     */
    private final String path;

    /**
    * Saves the path of the image
    * @param path of the image of the character card
    */
    CharacterCardImageType(String path) {
        this.path = path;
    }

    /**
     * Method to get an {@code Image} of a card from its path in the project
     * @return {@code Image} of the card
     */
    public Image getImage(){
        return new Image(path, 300, 300, true, false);
    }

    /**
     * Method to get a bigger {@code Image} of a card from its path in the project
     * @return {@code Image} of the card bigger
     */
    public Image getImageBigger(){
        return new Image(path, 500, 500, true, false);
    }
    /**
     * Method to convert a {@code CharacterCardsType} to a {@code CharacterCardImageType}
     * @param type {@code CharacterCardsType} type of the character card to convert
     * @return the {@code CharacterCardImageType} of the same card
     */
    public static CharacterCardImageType typeConverter(CharacterCardsType type) {
        return switch (type) {
            case CARD1 -> CARD1;
            case CARD2 -> CARD2;
            case CARD3 -> CARD3;
            case CARD4 -> CARD4;
            case CARD5 -> CARD5;
            case CARD6 -> CARD6;
            case CARD7 -> CARD7;
            case CARD8 -> CARD8;
            case CARD9 -> CARD9;
            case CARD10 -> CARD10;
            case CARD11 -> CARD11;
            case CARD12 -> CARD12;
        };
    }

}
