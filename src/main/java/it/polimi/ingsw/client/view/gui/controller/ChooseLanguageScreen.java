package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

import java.util.List;

public class ChooseLanguageScreen {

    @FXML
    private RadioButton itaButton, engButton;

    public void getLanguage(ActionEvent actionEvent) {

        if(itaButton.isSelected()){
            // Translator.setLanguage(Language.ITALIANO) // todo: actual code
            System.out.println("ITALIAN SELECTED");
        }else if(engButton.isSelected()){
            // Translator.setLanguage(Language.ENGLISH) // todo: actual code
            System.out.println("ENGLISH SELECTED");
        }

        ClientApplication.getSwitcher().goToAskServerParameters();
    }
}
