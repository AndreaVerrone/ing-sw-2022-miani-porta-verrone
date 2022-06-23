package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.gui.GuiScreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the controller class for the home of the game.
 */
public class MenuView extends GuiScreen implements Initializable{

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
        // go to screen to choose game parameters.
        getGui().getScreenBuilder().build(ScreenBuilder.Screen.CHOOSE_GAME_PARAMETERS);
        getGui().run();
    }

    /**
     * This method is called when {@code joinButton} is pressed.
     * It will allow to join an existing game.
     */
    public void joinGame(){
        // It will ask the server the list of the available games.
        // In the meantime it will display idle screen.

        // display wit screen
        getGui().getScreenBuilder().build(ScreenBuilder.Screen.IDLE);
        getGui().run();

        // ask available games to server
        getGui().getClientController().getGames();

    }

    /**
     * This method is called when the {@code resumeButton} is pressed.
     * It will allow to resume a game.
     */
    public void resumeGameButton(){
        // display wit screen
        getGui().getScreenBuilder().build(ScreenBuilder.Screen.IDLE);
        getGui().run();

        // send message to resume game
        getGui().getClientController().resumeGame();
    }

    /**
     * This method is used to set up the text of the buttons.
     */
    private void buttonsSetUp(){
        newGameButton.setText(Translator.getCreateButton());
        joinButton.setText(Translator.getJoinButton());
        resumeButton.setText(Translator.getResumeButton());
    }

}
