package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.gui.GuiScreen;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

/**
 * This is the controller class of the exit screen.
 * The exit screen is a screen used to display the winners
 * and allow the player to exit the game.
 */
public class ExitScreen extends GuiScreen {

    /**
     * The anchorPane of this screen
     */
    @FXML
    private AnchorPane scenePane;

    /**
     * This is the label that will contain the winners of the game
     */
    @FXML
    private Label description;

    /**
     * the button to exit the game.
     */
    @FXML
    private Button logoutButton;

    /**
     * This method is used to set up this screen.
     * It will fill the labels with the propert text based on the list of winners and
     * the owner of this GUI.
     * @param winners the list of the winners.
     */
    public void setUpExitScreen(List<String> winners) {
        setDescriptionText(winners);
        logoutButton.setText(Translator.getExitButton());
    }

    /**
     * This is the method that it called when the EXIT button is pressed.
     * It allows to exit the game.
     */
    public void logout(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Translator.getAlertTitle());
        alert.setHeaderText(Translator.getAlertHeader());
        alert.setContentText(Translator.getAlertContent());

        // set text of the cancel button since here it is needed translation
        Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setText(Translator.getTextOfCancelButton());

        if(alert.showAndWait().get() == ButtonType.OK) {
            // 1. exit from game
            getGui().getClientController().closeApplication();
            // 2. terminate the application
            //Platform.exit();
            //System.exit(0);
            // 3. close the stage
            // set the stage to the current one that we are working with
            Stage stage = (Stage) scenePane.getScene().getWindow();
            // System.out.println("you have successfully logged out");
            stage.close();
        }
    }

    /**
     * This method will allow to set the label of the description of this scene.
     * It will display the winners of the game.
     * @param winners the list of winners of the game
     */
    private void setDescriptionText(List<String> winners){

        String ownerPlayer = getGui().getClientController().getNickNameOwner();
        // String ownerPlayer = "player 1"; // todo: only for testing

        int numOfWinners = winners.size();

        // two different situations are possible:

        // *** 1. there is only 1 winner
        if (numOfWinners == 1) {

            // the winner is the owner
            if (winners.contains(ownerPlayer)) {
                description.setText(Translator.getGameHasEndedMessage()+" \n\n "+ Translator.getMessageForTheWinner());
            } else {
                // the winner is not the owner
                description.setText(Translator.getGameHasEndedMessage()+" \n\n"+winners.get(0) + " " + Translator.getHaveWonTheGameMessage());

            }

        } else {
            // *** 2. there is more than 1 winner --> parity situation
            StringBuilder message = new StringBuilder(Translator.getGameHasEndedMessage()+" \n\n "+ Translator.getParityString()+": \n\n");

            for (String winner : winners) {
                message.append(winner).append("\n");
            }
            message.append("\n ").append(Translator.getGameHasEndedMessage());
            description.setText(message.toString());
        }
    }

}
