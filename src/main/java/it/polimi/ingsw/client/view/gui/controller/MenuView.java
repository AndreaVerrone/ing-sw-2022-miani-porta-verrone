package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientApplication;
import it.polimi.ingsw.client.ConnectionHandler;
import it.polimi.ingsw.client.view.Switcher;
import it.polimi.ingsw.network.VirtualView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MenuView implements Initializable{

    /**
     * button to create a new game
     */
    @FXML
    Button newGameButton;

    /**
     * button to join an existing game
     */
    @FXML
    Button joinButton;

    /**
     * button to resume a game
     */
    @FXML
    Button resumeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonsSetUp();
    }

    /**
     * This method is called when {@code newGameButton} is pressed.
     * It allows to pass to the screen to create the game (i.e., choose
     * number of players and difficulty)
     */
    public void createNewGame(){
        ClientApplication.getSwitcher().goToCreateGameScreen();
    }

    /**
     * This method is called when {@code joinButton} is pressed.
     * It will allow to join an existing game.
     */
    public void joinGame(){
        // It will ask the server the list of the available games.
        // In the meantime it will display idle screen.
        // todo: this is only for testing
        ClientApplication.getSwitcher().goToChooseGameScreen(List.of(1627,21289182,91192,99198));
        // todo: actual code
        // display wit screen
        // ClientApplication.getSwitcher().goToWaitScreen();
        // ask available games to server
        // getClientController().getGames();

    }

    /**
     * This method is called when the {@code resumeButton} is pressed.
     * It will allow to resume a game.
     */
    public void resumeGameButton(){
        // todo: only for testing
        ClientApplication.getSwitcher().goToCreateGameScreen();
        // ClientApplication.getSwitcher().goToExitScreen(List.of("Player 1"));
        //System.out.println("Resume game");
        // todo: actual code
        // display wit screen
        // ClientApplication.getSwitcher().goToWaitScreen();
        // send message to resume
        // getClientController().resumeGame();
    }

    /**
     * This method is used to set up the text of the buttons.
     */
    private void buttonsSetUp(){
        // todo: add translation
        newGameButton.setText("NEW GAME");
        joinButton.setText("JOIN");
        resumeButton.setText("RESUME");
    }

}
