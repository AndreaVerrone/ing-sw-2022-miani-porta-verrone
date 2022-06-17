package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class ChooseGame{

    private Collection<String> games;

    @FXML
    private Label header;

    @FXML
    private ChoiceBox<String> listOfGames;

    public void setListOfGames(Collection<String> games){
        header.setText("choose the game to join");
        this.games=new ArrayList<>(games); // todo: maybe useless
        this.listOfGames.getItems().addAll(new ArrayList<>(games));
    }


    public void goBack(){
        ClientApplication.getSwitcher().goToHomeScreen();
    }


    public void takeGame(ActionEvent actionEvent) {
        System.out.println(listOfGames.getValue());
    }


}
