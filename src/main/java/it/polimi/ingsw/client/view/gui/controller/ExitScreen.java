package it.polimi.ingsw.client.view.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;


public class ExitScreen {


    @FXML
    private AnchorPane scenePane;

    @FXML
    private Label description;

    @FXML
    private Button logoutButton;

    Stage stage;



    private void setDescriptionText(List<String> winners){

        // String ownerPlayer = getGui().getClientController().getNickNameOwner(); // todo: actual code
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


    public void setUpExitScreen(List<String> winners) {
        setDescriptionText(winners);
        logoutButton.setText("Exit from Game"); // logoutButton.setText(Translator.getExitButton());
    }

    public void logout(ActionEvent event){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit from game");
        alert.setHeaderText("You're about to exit from game");
        alert.setContentText("Do you want to exit the game ?: ");

        if(alert.showAndWait().get() == ButtonType.OK) {
            // set the stage to the current one that we are working with
            stage = (Stage) scenePane.getScene().getWindow();
            // System.out.println("you have successfully logged out");
            stage.close();
        }
    }
}
