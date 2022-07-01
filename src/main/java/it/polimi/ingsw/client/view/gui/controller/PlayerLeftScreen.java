package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.gui.GuiScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Screen to notify the other players that a player has left the game
 */
public class PlayerLeftScreen extends GuiScreen {


    /**
     * Label to show which player has left the game
     */
    @FXML
    Label playerHasLeftLabel;

    /**
     * Button to go back to the Home screen
     */
    @FXML
    Button goHomeScreenButton;

    /**
     * Method to go back to the home screen
     */
    public void goToHomeScreen(){
        getGui().getScreenBuilder().build(ScreenBuilder.Screen.HOME);
        getGui().show();
    }

    public void setNicknamePlayerLeft(String nicknamePlayerLeft){
        playerHasLeftLabel.setText(nicknamePlayerLeft + Translator.getLeftGameMessage());
        playerHasLeftLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        goHomeScreenButton.setText(Translator.getExitButton());

    }


}
