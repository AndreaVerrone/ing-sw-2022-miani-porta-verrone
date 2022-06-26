package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.view.gui.GuiScreen;

/**
 * This class is the controller of the starting screen of the game
 */
public class StartingScreen extends GuiScreen {

    /**
     * This method is called when the {@code startButton} is pressed.
     * It allows to go to the screen to choose the language.
     */
    public void startGame(){
        // go to choose language screen
        getGui().getScreenBuilder().build(ScreenBuilder.Screen.CHOOSE_LANGUAGE);
        getGui().show();

    }
}
