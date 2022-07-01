package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.gui.ClientGui;
import it.polimi.ingsw.client.view.gui.GuiScreen;
import it.polimi.ingsw.client.view.gui.listeners.StudentListener;
import it.polimi.ingsw.client.view.gui.utils.image_getters.CoinImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.IslandBanImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.StudentImageType;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.model.utils.PawnType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * Class that controls the view of the card when selected
 */
public class CharacterCardView extends GuiScreen {

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
        description.setText(Translator.getEffectDescription(card.getCardType()) + "\n" + Translator.getCostLabel() + card.getCost());
        description.setTextAlignment(TextAlignment.CENTER);
        description.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        description.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.MEDIUM)));
        description.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, new CornerRadii(5), Insets.EMPTY)));
        description.setWrapText(true);
    }

    /**
     * Adds the extras of the card to the view (students or bans if present)
     */
    private void addExtras(){
        for(PawnType color: PawnType.values()){
            for(int number = 0; number < card.getStudents().getNumOf(color); number++){
                ImageView studentView = new ImageView(StudentImageType.typeConverter(color).getImageBigger());
                extras.getChildren().add(studentView);
                Location cardLocation  = switch (card.getCardType()){
                    case CARD1 -> Location.CHARACTER_CARD_1;
                    case CARD9 -> Location.CHARACTER_CARD_9;
                    case CARD8 -> Location.NONE;
                    case CARD11 -> Location.CHARACTER_CARD_11;
                    case CARD12 -> Location.NONE;
                    default -> null;
                };
                studentView.setOnMouseClicked(new StudentListener(getGui(), color, cardLocation));
            }
        }
        for(int numberOfBans=0; numberOfBans < card.getNumberOfBans(); numberOfBans++){
            extras.getChildren().add(new ImageView(IslandBanImageType.BAN.getImage()));
        }
    }

    /**
     * Exit the view if the exit button is clicked
     */
    public void exitView(){
        Platform.runLater(() -> {
            getGui().getUseCardStage().close();
            getGui().getStage().setFullScreen(true);
        });
    }

    /**
     * Uses the card if the use button is clicked
     */
    public void useCharacterCard(){
        Platform.runLater(() -> {
            getGui().getClientController().useCharacterCard(card.getCardType());
            getGui().getUseCardStage().close();
            getGui().getStage().setFullScreen(true);
            System.out.println("Card used");
        });
    }


}
