package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.view.gui.ClientGui;
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
        getGui().getScreenBuilder().build(ScreenBuilder.Screen.HOME);
        getGui().run();
        /*ClientApplication.getSwitcher().goToChooseWizardAndTower(
                List.of(Wizard.W1,Wizard.W2,Wizard.W3,Wizard.W4),
                List.of(TowerType.WHITE,TowerType.GREY,TowerType.BLACK)
        );*
        //ClientApplication.getSwitcher().goToChooseGameScreen(List.of("game 1", "game 2", "game 3"));
        //ClientApplication.getSwitcher().goToChooseWizardAndTower();*/
    }
}
