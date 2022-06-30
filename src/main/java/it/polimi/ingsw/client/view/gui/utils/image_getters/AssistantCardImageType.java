package it.polimi.ingsw.client.view.gui.utils.image_getters;

import it.polimi.ingsw.server.model.player.Assistant;
import javafx.scene.image.Image;

/**
 * Class to get the {@code Image} of an assistant card
 */
public enum AssistantCardImageType {
    CARD1("/assets/assistantCards/Assistente_1.png"),
    CARD2("/assets/assistantCards/Assistente_2.png"),
    CARD3("/assets/assistantCards/Assistente_3.png"),
    CARD4("/assets/assistantCards/Assistente_4.png"),
    CARD5("/assets/assistantCards/Assistente_5.png"),
    CARD6("/assets/assistantCards/Assistente_6.png"),
    CARD7("/assets/assistantCards/Assistente_7.png"),
    CARD8("/assets/assistantCards/Assistente_8.png"),
    CARD9("/assets/assistantCards/Assistente_9.png"),
    CARD10("/assets/assistantCards/Assistente_10.png");

    /**
     * Path of the image in the project
     */
    private final String path;

    /**
     * Saves the path of the image
     * @param path of the image of the assistant card
     */
    AssistantCardImageType(String path) {
        this.path = path;
    }

    /**
     * Method to get an {@code Image} of a card from its path in the project
     * @return {@code Image} of the card
     */
    public Image getImage(){
        return new Image(path, 320, 320, true, false);
    }


    public String getPath(){
        return path;
    }

    /**
     * Method to convert a {@code Assistant} to a {@code AssistantCardImageType}
     * @param card {@code Assistant} type of the assistant card to convert
     * @return the {@code AsssistantCardImageType} of the same card
     */
    public static AssistantCardImageType typeConverter(Assistant card) {
        return switch (card) {
            case CARD_1 -> CARD1;
            case CARD_2 -> CARD2;
            case CARD_3 -> CARD3;
            case CARD_4 -> CARD4;
            case CARD_5 -> CARD5;
            case CARD_6 -> CARD6;
            case CARD_7 -> CARD7;
            case CARD_8 -> CARD8;
            case CARD_9 -> CARD9;
            case CARD_10 -> CARD10;
        };
    }
}
