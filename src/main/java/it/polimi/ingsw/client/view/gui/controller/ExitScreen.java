package it.polimi.ingsw.client.view.gui.controller;

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
public class ExitScreen {

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
        logoutButton.setText("Exit from Game"); // todo: actual code logoutButton.setText(Translator.getExitButton());
    }

    /**
     * This is the method that it called when the EXIT button is pressed.
     * It allows to exit the game.
     */
    public void logout(){
        // todo: add translation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit from game");
        alert.setHeaderText("You're about to exit from game");
        alert.setContentText("Do you want to exit the game ?");

        if(alert.showAndWait().get() == ButtonType.OK) {
            // todo: maybe use also the proper method of the controller to exit the game !
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

        // String ownerPlayer = getClientController().getNickNameOwner(); // todo: actual code
        String ownerPlayer = "player 1"; // todo: only for testing

        int numOfWinners = winners.size();

        // two different situations are possible:

        // *** 1. there is only 1 winner
        if (numOfWinners == 1) {

            // the winner is the owner
            if (winners.contains(ownerPlayer)) {
                description.setText("The game has ended \n\n Congratulation, you have won the game !"); // todo: testing only
                // description.setText(Translator.getMessageForTheWinner()); todo: actual code
            } else {
                // the winner is not the owner
                // description.setText(winners.get(0) + " " + Translator.getMessageForTheLosers()); // todo: actual code
                description.setText("The game has ended \n\n"+winners.get(0) + " " + "have won the game"); // todo: testing only

            }

        } else {
            // *** 2. there is more than 1 winner --> parity situation
            //StringBuilder message = new StringBuilder(Translator.getMessageForParity() +": \n"); // todo: actaul code
            StringBuilder message = new StringBuilder("The game has ended \n\n PARITY" +": \n\n"); // todo: testing only

            for (String winner : winners) {
                message.append(winner).append("\n");
            }
            message.append("\n have won the game");
            description.setText(message.toString());
        }
    }

}
