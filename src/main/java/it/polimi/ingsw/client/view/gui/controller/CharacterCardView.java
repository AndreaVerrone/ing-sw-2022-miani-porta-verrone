package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientApplication;
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

public class CharacterCardView {

    @FXML
    Pane cardPane;

    @FXML
    Label description;

    @FXML
    FlowPane extras;

    @FXML
    AnchorPane backgroundPane;

    CharacterCard card;

    public void fillView(CharacterCard card){
        this.card = card;
        backgroundPane.setBackground(Background.fill(Color.NAVAJOWHITE));
        addCard();
        addDescription();
        addExtras();
    }
    private void addCard(){
        cardPane.getChildren().add(card.getCharacterCardView());
        if(card.isCoinOnCard()){
            ImageView cardView = new ImageView(new Image("/assets/coin/Moneta_base.png", 100, 100, true, false));
            cardPane.getChildren().add(cardView);
            cardView.setTranslateX(20);
        }
    }

    private void addDescription(){
        description.setText(card.getCardType().getDescription().toUpperCase() + "\nCOST = " + card.getCost());
        description.setTextAlignment(TextAlignment.CENTER);
        description.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        description.setWrapText(true);
    }

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

    public void exitView(){
        ClientApplication.getSwitcher().goToCreateNewGame();
    }

    public void useCharacterCard(){
        //TODO: use character card
        System.out.println("Card used");
    }


}
