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

public class CreateGameScreen implements Initializable {


    @FXML
    private Label headerLabel;

    @FXML
    private Label numOfPlayersLabel;

    @FXML
    private Label difficultyLabel;

    @FXML
    private ChoiceBox<Integer> numOfPlayers = new ChoiceBox<>();

    @FXML
    private ChoiceBox<String> difficulty = new ChoiceBox<>();

    private Integer[] numOfPlayersList = {2,3};

    private String[] difficultyList = {"easy","expert"}; // todo translate

    @FXML
    private Label errorNumOfPlayers;

    @FXML
    private Label errorDifficulty;


    public void setLabels(){
        // todo: add translation
        headerLabel.setText("Creation of a new game");
        numOfPlayersLabel.setText("Choose number of players");
        difficultyLabel.setText("Choose difficulty");
        errorDifficulty.setText("");
        errorNumOfPlayers.setText("");
        // backgroundPane.setBackground(Background.fill(Color.CYAN));
    }


    public void getGameParameter() {

        errorNumOfPlayers.setText("");
        errorDifficulty.setText("");

        if(numOfPlayers.getValue()==null){
            errorNumOfPlayers.setText("it is not optional");
            System.out.println("INSERT ALL PARMETERS");
        }

        if(difficulty.getValue()==null){
            errorDifficulty.setText("it is not optional");
            System.out.println("INSERT ALL PARMETERS");
            return;
        }

        System.out.println(numOfPlayers.getValue());
        System.out.println(difficulty.getValue());

        boolean expertMode;
        if(difficulty.getValue().equals("expert")){
            expertMode=true;
        }

        // todo: similar to actual code
        //getCli().getClientController().createGame(numOfPlayers.getValue(), expertMode);

        ClientApplication.getSwitcher().goToAskNicknameScreen(4);

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numOfPlayers.getItems().addAll(numOfPlayersList);
        difficulty.getItems().addAll(difficultyList);
    }

    public void goBack(MouseEvent mouseEvent) {
        ClientApplication.getSwitcher().goToHomeScreen();
    }
}
