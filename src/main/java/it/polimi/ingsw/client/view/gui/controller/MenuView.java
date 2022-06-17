package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientApplication;
import it.polimi.ingsw.client.ConnectionHandler;
import it.polimi.ingsw.client.view.Switcher;
import it.polimi.ingsw.network.VirtualView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.List;

public class MenuView implements VirtualView {

    @FXML
    Button newGameButton;

    @FXML
    Button joinButton;

    @FXML
    Button resumeGameButton;


    public void createNewGame(ActionEvent event){
        ClientApplication.getSwitcher().goToCreateNewGame();
    }

    public void joinGame(ActionEvent event){
        System.out.println("Join game");
    }

    public void resumeGame(ActionEvent event){
        ClientApplication.getSwitcher().goToCreateGameScreen();
        // ClientApplication.getSwitcher().goToExitScreen(List.of("Player 1"));
        //System.out.println("Resume game");

    }

}
