package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.view.gui.utils.image_getters.AssistantCardImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.WizardImageType;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Wizard;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent the assistant card deck of a player
 */
public class AssistantCardDeck {

    /**
     * Pane where the card is placed
     */
    private final Pane cardPane;

    /**
     * Wizard type of the card
     */
    private final Wizard wizard;

    /**
     * Represents the number of the player that owns the deck, if it is 2 it's the client, if its 2 it's the player in front
     * of the client and if it's 3 it's the one on his left
     */
    private final int numberPlayer;

    /**
     * {@code ImageView} of the last assistant card use
     */
    private ImageView lastAssistantCard = null;

    /**
     * List of the cards still available to use
     */
    private ArrayList<Assistant> cardsAvailable = new ArrayList<>(List.of(Assistant.values()));

    /**
     * Gui of the game
     */
    private GUI gui;

    /**
     * This class handles the deck of the player, allowing him to use an assistant card
     * @param gui Gui of the game
     * @param wizard wizard selected by the player
     * @param cardPane pane where the card is placed
     * @param numberPlayer represents the number of the player
     */
    public AssistantCardDeck(GUI gui, Wizard wizard, Pane cardPane, int numberPlayer){
        this.gui = gui;
        this.wizard = wizard;
        this.cardPane = cardPane;
        this.numberPlayer= numberPlayer;

        ImageView wizardView = new ImageView(WizardImageType.typeConverter(wizard).getImage());
        cardPane.getChildren().add(wizardView);
        if(numberPlayer == 1) {
            wizardView.setOnMouseClicked(mouseEvent -> gui.useAssistantCard());
        }
    }

    /**
     * Allows to use an assistant card, removing the last one and adding the new one
     * @param card card selected by the player
     */
    public void useAssistantCard(Assistant card){
        //Remove the old assistant card
        if(lastAssistantCard != null){
            cardPane.getChildren().remove(lastAssistantCard);
            lastAssistantCard = null;
        }
        if(cardsAvailable.size() == 1){
            cardPane.getChildren().clear();
        }
        //Add the new card
        ImageView cardView = new ImageView(AssistantCardImageType.typeConverter(card).getImage());
        lastAssistantCard = cardView;
        cardPane.getChildren().add(cardView);
        if(numberPlayer == 1) {
            cardView.setOnMouseClicked(mouseEvent -> gui.useAssistantCard());
        }
        //Remove the card from the ones available
        cardsAvailable.remove(card);
        //If this is the deck of the second player a rotation should be set to the card since it is placed rotated by 180 degrees
        if (numberPlayer == 2) {
            cardView.setOnMouseEntered(mouseEvent -> cardView.setRotate(180));
            cardView.setOnMouseExited(mouseEvent -> cardView.setRotate(0));
        }
        //If this is the deck of the third player a rotation should be set to the card since it is placed rotated by 90 degrees
        if (numberPlayer == 3) {
            cardView.setOnMouseEntered(mouseEvent -> cardView.setRotate(-90));
            cardView.setOnMouseExited(mouseEvent -> cardView.setRotate(0));
        }


    }
}
