package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.regex.Pattern;

public class AskNicknameScreen {


    @FXML
    private Label headerLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField nicknameTextField;

    /**
     * gameID of the game to join
     */
    private int gameID;

    public void setParameters(int gameID){
        this.gameID=gameID;
        headerLabel.setText("Choose a nickname");
        errorLabel.setText("");
    }


    public void takeNickname(){
        errorLabel.setText("");
        if(parseNickname(nicknameTextField.getText())){
            System.out.println("OK");
            // getCli().getClientController().enterGame(nicknameTextField.getText(), gameID); // todo: similar to actual code
            // ClientApplication.getSwitcher().goToExitScreen(List.of("player 1","player 2"));
            // ClientApplication.getSwitcher().goToChooseGameToJoin(List.of("game 1", "game 2", "game 3"));
            ClientApplication.getSwitcher().goToChooseGameScreen(List.of("game 1", "game 2", "game 3"));
        }else{
            errorLabel.setText("it is not valid");
        }
    }


    private boolean parseNickname(String nickname){
        return Pattern.matches("[\\H\\S]+",nickname);
    }


}
