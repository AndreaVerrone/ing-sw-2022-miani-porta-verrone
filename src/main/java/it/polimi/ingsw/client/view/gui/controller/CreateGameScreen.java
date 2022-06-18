package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * This class is the controller of the screen to create the game.
 * (i.e., the screen that asks the numbers of players and the difficulty)
 */
public class CreateGameScreen implements Initializable {

    /**
     * This is the label of the header.
     * It says that this screen is the one to create a new game.
     */
    @FXML
    private Label headerLabel;

    /**
     * This is the label to ask the number of players.
     */
    @FXML
    private Label numOfPlayersLabel;

    /**
     * This is the label to ask the difficulty.
     */
    @FXML
    private Label difficultyLabel;

    /**
     * This is the choice box for the number of players.
     */
    @FXML
    private ChoiceBox<Integer> numOfPlayers = new ChoiceBox<>();

    /**
     * This is the choice box for the difficulty.
     */
    @FXML
    private ChoiceBox<String> difficulty = new ChoiceBox<>();

    /**
     * This is the label to display an error message on the number of players.
     */
    @FXML
    private Label errorNumOfPlayers;

    /**
     * This is the label to display an error message on the difficulty.
     */
    @FXML
    private Label errorDifficulty;

    /**
     * This is an array that contains the elements to fill the choice box of number of players.
     */
    private final Integer[] numOfPlayersList = {2,3};

    /**
     * This is an array that contains the elements to fill the choice box the difficulty.
     */
    private final String[] difficultyList = {"easy","expert"}; // todo translate


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize the choice box for the number of players
        numOfPlayers.getItems().addAll(numOfPlayersList);
        // initialize the choice box for the difficulty.
        difficulty.getItems().addAll(difficultyList);
        // set labels
        setLabels();
    }

    /**
     * This method is called when the OK button is pressed.
     * It allows to confirm the parameters of the game.
     */
    public void getGameParameter() {

        // reset error labels
        errorNumOfPlayers.setText("");
        errorDifficulty.setText("");


        if(numOfPlayers.getValue()==null){
            errorNumOfPlayers.setText("it is not optional"); // todo: add translation
        }

        if(difficulty.getValue()==null){
            errorDifficulty.setText("it is not optional"); // todo: add tranlation
            return;
        }

        boolean expertMode = difficulty.getValue().equals("expert"); // todo: add translation
        // todo: similar to actual code
        // send message
        // getClientController().createGame(numOfPlayers.getValue(), expertMode);
        // show idle screen

        // todo: only for testing
        System.out.println(numOfPlayers.getValue());
        System.out.println(difficulty.getValue());
        ClientApplication.getSwitcher().goToAskNicknameScreen(4);
    }

    /**
     * This method os called when the go back arrow image is pressed.
     * It allows the player to go back to the home screen.
     */
    public void goBack() {
        ClientApplication.getSwitcher().goToHomeScreen();
    }

    /**
     * This method allow to set the labels of the screen.
     */
    private void setLabels(){
        // todo: add translation
        headerLabel.setText("Creation of a new game");
        numOfPlayersLabel.setText("Choose number of players");
        difficultyLabel.setText("Choose difficulty");
        errorDifficulty.setText("");
        errorNumOfPlayers.setText("");
    }
}
