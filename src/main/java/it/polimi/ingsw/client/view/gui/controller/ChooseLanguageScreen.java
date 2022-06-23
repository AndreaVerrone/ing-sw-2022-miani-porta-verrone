package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.gui.GuiScreen;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

/**
 * This class is the controller of the screen used to make the user choose the language
 * of the game.
 */
public class ChooseLanguageScreen extends GuiScreen {

    /**
     * Radio buttons.
     */
    @FXML
    private RadioButton itaButton, engButton;

    /**
     * this method is called when the {@code okButton} button is pressed.
     * It will set the language, and it will allow to pass to the screen to choose the server.
     */
    public void setLanguageAndChangeScreen() {

        // set the language
        if(itaButton.isSelected()){
            Translator.setLanguage(Translator.Language.ITALIANO);
        }else if(engButton.isSelected()){
            Translator.setLanguage(Translator.Language.ENGLISH);
        }

        // go to screen to choose the server.
        getGui().getScreenBuilder().build(ScreenBuilder.Screen.SERVER_SPECS);
        getGui().run();
    }
}
