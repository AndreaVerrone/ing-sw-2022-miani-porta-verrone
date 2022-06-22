package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientApplication;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * This class is the controller for the screen to ask a nickname.
 */
public class AskNicknameScreen implements Initializable {

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
    private int gameID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLabels();
    }

    public void setGameID(int gameID){
        this.gameID=gameID;
    }

    /**
     * This method is used to set the labels.
     */
    private void setLabels(){
        headerLabel.setText("Choose a nickname");
        notesOnNickname.setText("it can contains any character except for space");
        errorLabel.setText("");
    }


    /**
     * This method is called when the OK button is pressed.
     * It allows to take the nickname inserted by the user.
     */
    public void takeNickname(){
        // reset error label
        errorLabel.setText("");

        if(parseNickname(nicknameTextField.getText())){
            // if the nickname is correct:
            System.out.println("OK"); // todo: only for testing
            // todo: only for testing
            ClientApplication.getSwitcher().goToChooseWizardAndTower(List.of(Wizard.values()), List.of(TowerType.values()));
            // todo: actual code
            // getClientController().enterGame(nicknameTextField.getText(),gameID);
            // display lobby screen
        }else{
            // print error message
            errorLabel.setText("it is not valid: you cannot insert spaces");
        }
    }

    /**
     * This method allows to validate the nickname inserted by the user.
     * All string that do not contain spaces are accepted.
     * @param nickname the nickname inserted by the user.
     * @return true if it respects the requirements, false otherwise.
     */
    private boolean parseNickname(String nickname){
        return Pattern.matches("[\\H\\S]+",nickname);
    }

}
