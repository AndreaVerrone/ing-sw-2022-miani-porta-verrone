package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.gui.GuiScreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the controller of the screen that it is used to display an error due to connection.
 */
public class ConnectionErrorScreen extends GuiScreen implements Initializable {

    /**
     * The header of the screen.
     */
    @FXML
    Label headerLabel;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        headerLabel.setText(Translator.getConnectionError());
    }
}
