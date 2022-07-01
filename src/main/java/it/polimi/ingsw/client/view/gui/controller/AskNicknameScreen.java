package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.gui.GuiScreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * This class is the controller for the screen to ask a nickname.
 */
public class AskNicknameScreen extends GuiScreen implements Initializable {

    /**
     * This is the label of the header.
     * It is used to display to insert a nickname.
     */
    @FXML
    private Label headerLabel;

    /**
     * This label is used to display information regarding
     * the requirements that a nickname has to follow.
     */
    @FXML
    private Label notesOnNickname;

    /**
     * This label is used to display an error message if the
     * nickname inserted do not respect the requirements.
     */
    @FXML
    private Label errorLabel;

    /**
     * This is the text field that allow the user to insert a nickname.
     */
    @FXML
    private TextField nicknameTextField;

    /**
     * This is the game ID of the game to join.
     */
    private String gameID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLabels();
    }

    @Override
    public void setGameID(String gameID){
        this.gameID=gameID;
        getGui().setGameID(gameID);
    }

    /**
     * This method is used to set the labels.
     */
    private void setLabels(){
        headerLabel.setText(Translator.getAskNickname());
        notesOnNickname.setText(Translator.getNoteOnNickname());
        errorLabel.setText("");
    }

    /**
     * This method is called when the OK button is pressed.
     * It allows to take the nickname inserted by the user.
     */
    public void takeNickname(){
        // reset error label
        errorLabel.setText("");

        if(nicknameIsValid(nicknameTextField.getText())){
            // if the nickname is correct --> send message to enter the game
            getGui().getClientController().enterGame(nicknameTextField.getText(),gameID);
        }else{
            // otherwise print error message
            errorLabel.setText(Translator.getMessageWrongNickname());
        }
    }

    /**
     * This method allows to validate the nickname inserted by the user.
     * All string that do not contain spaces are accepted.
     * @param nickname the nickname inserted by the user.
     * @return true if it respects the requirements, false otherwise.
     */
    private boolean nicknameIsValid(String nickname){
        return Pattern.matches("[\\H\\S]+",nickname);
    }

}
