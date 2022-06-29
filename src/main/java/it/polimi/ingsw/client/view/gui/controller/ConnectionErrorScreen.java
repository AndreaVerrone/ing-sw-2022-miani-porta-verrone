package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.gui.GuiScreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionErrorScreen extends GuiScreen implements Initializable {

    @FXML
    Label headerLabel;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        headerLabel.setText(Translator.getConnectionError());
    }
}
