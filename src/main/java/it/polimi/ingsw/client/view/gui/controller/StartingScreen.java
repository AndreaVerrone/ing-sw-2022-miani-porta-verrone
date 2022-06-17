package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientApplication;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.List;

/**
 * This class is the controller of the starting screen of the game
 */
public class StartingScreen {

    /**
     * This method is called when the {@code startButton} is pressed.
     * It allows to go to the screen to choose the language.
     */
    public void startGame(){
        ClientApplication.getSwitcher().goToChooseLanguageScreen();
        /*ClientApplication.getSwitcher().goToChooseWizardAndTower(
                List.of(Wizard.W1,Wizard.W2,Wizard.W3,Wizard.W4),
                List.of(TowerType.WHITE,TowerType.GREY,TowerType.BLACK)
        );*
        //ClientApplication.getSwitcher().goToChooseGameScreen(List.of("game 1", "game 2", "game 3"));
        //ClientApplication.getSwitcher().goToChooseWizardAndTower();*/
    }
}