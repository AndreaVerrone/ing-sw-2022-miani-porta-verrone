package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientApplication;
import it.polimi.ingsw.client.view.gui.utils.image_getters.CoinImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.StudentImageType;
import it.polimi.ingsw.server.model.utils.PawnType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * Class that controls the view of the card when selected
 */
public class CharacterCardView {

    /**
     * Pane where the card is located
     */
    @FXML
    Pane cardPane;

    /**
     * Label where the description of the card is placed
     */
    @FXML
    Label description;

    /**
     * Pane where extras of the card(such as students or bans) are placed
     */
    @FXML
    FlowPane extras;

    /**
     * Pane containing all the view of the card
     */
    @FXML
    AnchorPane backgroundPane;

    /**
     * Card shown on the view
     */
    CharacterCard card;

    /**
     * Fills the view with the properties of the given card(es. description, extras, cost)
     * @param card card used on the view
     */
    public void fillView(CharacterCard card){
        this.card = card;
        backgroundPane.setBackground(Background.fill(Color.GOLDENROD));
        addCard();
        addDescription();
        addExtras();
    }

    /**
     * Adds the image of the card to the view
     */
    private void addCard(){
        cardPane.getChildren().add(card.getCharacterCardView());
        if(card.isCoinOnCard()){
            ImageView cardView = new ImageView(CoinImageType.COIN.getImage());
            cardPane.getChildren().add(cardView);
            cardView.setTranslateX(20);
        }
    }

    /**
     * Adds the description of the card to the label
     */
    private void addDescription(){
        description.setText(card.getCardType().getDescription().toUpperCase() + "\nCOST = " + card.getCost());
        description.setTextAlignment(TextAlignment.CENTER);
        description.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        description.setWrapText(true);
    }

    /**
     * Adds the extras of the card to the view (students or bans if present)
     */
    private void addExtras(){
        for(PawnType color: PawnType.values()){
            for(int number = 0; number < card.getStudents().getNumOf(color); number++){
                extras.getChildren().add(new ImageView(StudentImageType.typeConverter(color).getImageBigger()));
            }
        }
        for(int numberOfBans=0; numberOfBans < card.getNumberOfBans(); numberOfBans++){
            extras.getChildren().add(new ImageView(new Image("/assets/islands/deny_island_icon.png", 80, 80, true, false)));
        }
    }

    /**
     * Exit the view if the exit button is clicked
     */
    public void exitView(){
        ClientApplication.getSwitcher().goToCreateNewGame();
    }

    /**
     * Uses the card if the use button is clicked
     */
    public void useCharacterCard(){
        //TODO: use character card
        System.out.println("Card used");
    }


}
