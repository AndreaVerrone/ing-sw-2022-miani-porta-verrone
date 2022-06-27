package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.gui.GuiScreen;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * This class is the controller of the screen used to allow the player
 * to choose the game to join.
 */
public class ChooseGame extends GuiScreen implements Initializable{

    /**
     * This label is used for the header of the screen.
     * it is used to ask the user to choose the game to join.
     */
    @FXML
    private Label header;

    /**
     * This is the choice box used to display the available games to join.
     */
    @FXML
    private ChoiceBox<String> listOfGames;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLabels();
    }

    /**
     * This method is used to set the labels of tre screen.
     */
    private void setLabels(){
        header.setText(Translator.getHeaderChooseGameToJoin());
    }

    /**
     * This method is used to fill the choice box with the list
     * of the available games.
     * @param games list of available games to join.
     */
    public void setListOfGames(Collection<String> games){
        this.listOfGames.getItems().addAll(new ArrayList<>(games));
    }

    /**
     * This method is called when the user click
     * the go back arrow image.
     * It allows to go back to the home screen.
     */
    public void goBack(){
        Platform.runLater(() -> {
                    getGui().getScreenBuilder().build(ScreenBuilder.Screen.HOME);
                });
        // getGui().run();
    }

    /**
     * This method is called when the uses click the OK button.
     * It allows to take the game selected by the user.
     */
    public void takeGame() {
        // todo: testing only
        System.out.println(listOfGames.getValue());
        // todo: actual code
        Platform.runLater(()->{
            getGui().getScreenBuilder().build(ScreenBuilder.Screen.ASK_NICKNAME, listOfGames.getValue());
            }
        );
        // getGui().run();
    }

}
