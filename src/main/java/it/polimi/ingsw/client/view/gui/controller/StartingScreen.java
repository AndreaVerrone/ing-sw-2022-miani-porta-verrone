package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientApplication;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.List;

public class StartingScreen {
    public void startGame(){
        ClientApplication.getSwitcher().goToChooseWizardAndTower(
                List.of(Wizard.W1,Wizard.W2,Wizard.W3,Wizard.W4),
                List.of(TowerType.WHITE,TowerType.GREY,TowerType.BLACK)
        );
        //ClientApplication.getSwitcher().goToChooseGameScreen(List.of("game 1", "game 2", "game 3"));
        //ClientApplication.getSwitcher().goToChooseWizardAndTower();
        //ClientApplication.getSwitcher().goToChooseLanguageScreen();
    }
}
