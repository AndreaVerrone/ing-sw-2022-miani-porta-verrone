package it.polimi.ingsw.client.view.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the controller of the wiat screen.
 */
public class WaitScreen implements Initializable {

    /**
     * The label of the header to display to wait.
     */
    @FXML
    private Label headerLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        headerLabel.setText("Please wait ..."); // todo: add translation
    }
}
