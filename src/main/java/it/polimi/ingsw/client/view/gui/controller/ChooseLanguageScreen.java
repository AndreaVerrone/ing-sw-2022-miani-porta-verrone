package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.ClientGui;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

/**
 * This class is the controller of the screen used to make the user choose the language
 * of the game.
 */
public class ChooseLanguageScreen {

    @FXML
    private RadioButton itaButton, engButton;

    /**
     * this method is called when the {@code okButton} button is pressed.
     * It will set the language, and it will allow to pass to the screen to choose the server.
     */
    public void setLanguageAndChangeScreen() {

        // set the language
        if(itaButton.isSelected()){
            // Translator.setLanguage(Language.ITALIANO) // todo: actual code
            System.out.println("ITALIAN SELECTED"); // todo: testing code
        }else if(engButton.isSelected()){
            // Translator.setLanguage(Language.ENGLISH) // todo: actual code
            System.out.println("ENGLISH SELECTED"); // todo: testing code
        }

        // go to screen to choose the server.
        ClientGui.getSwitcher().goToAskServerParameters();
    }
}
