package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.utils.image_getters.WizardImageType;
import it.polimi.ingsw.server.model.player.Wizard;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class AssistantCardDeck {

    private final Pane cardPane;

    private final Wizard wizard;

    private final boolean isThirdPlayer;

    public AssistantCardDeck(Wizard wizard, Pane cardPane, boolean isThirdPlayer){
        this.wizard = wizard;
        this.cardPane = cardPane;
        this.isThirdPlayer = isThirdPlayer;

        ImageView wizardView = new ImageView(WizardImageType.typeConverter(wizard).getImage());
        cardPane.getChildren().add(wizardView);
        //If this is the deck of the third player a rotation should be set to the card since it is placed rotated by 90 degrees
        if (isThirdPlayer) {
            wizardView.setOnMouseEntered(mouseEvent -> wizardView.setRotate(90));
            wizardView.setOnMouseExited(mouseEvent -> wizardView.setRotate(0));
        }

    }
}
