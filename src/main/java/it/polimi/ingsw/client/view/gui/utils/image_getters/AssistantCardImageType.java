package it.polimi.ingsw.client.view.gui.utils.image_getters;

import javafx.scene.image.Image;

/**
 * Class to get the {@code Image} of an assistant card
 */
public enum AssistantCardImageType {
    CARD1("/assets/assistantCards/Assistente (1).png"),
    CARD2("/assets/assistantCards/Assistente (2).png"),
    CARD3("/assets/assistantCards/Assistente (3).png"),
    CARD4("/assets/assistantCards/Assistente (4).png"),
    CARD5("/assets/assistantCards/Assistente (5).png"),
    CARD6("/assets/assistantCards/Assistente (6).png"),
    CARD7("/assets/assistantCards/Assistente (7).png"),
    CARD8("/assets/assistantCards/Assistente (8).png"),
    CARD9("/assets/assistantCards/Assistente (9).png"),
    CARD10("/assets/assistantCards/Assistente (10).png");

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
}
