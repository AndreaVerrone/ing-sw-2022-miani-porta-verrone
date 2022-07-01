package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.Translator;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * The client that is running the game
 */
public class ClientGui extends javafx.application.Application {

    /**
     * The main.
     * <p>
     * Note: When using IntelliJ, don't run the application from here, use the main method in Client
     * @param args string of arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // create the GUI
        GUI gui = new GUI(primaryStage);

        // set title and logo
        primaryStage.setTitle("Eriantys");
        primaryStage.getIcons().add(new Image("/assets/logo/eriantys_banner.png"));


        // create the client controller
        new ClientController(gui);

        // set the stage nor resizable
        primaryStage.setResizable(false);

        // call the logout method of request to close the window
        primaryStage.setOnCloseRequest(
                event -> {
                    event.consume();
                    logout(primaryStage,gui);
                }
        );

    }

    /**
     * This is the method that it called when you try to exit the game closing the window.
     * It allows to exit the game.
     * @param stage the considered stage.
     * @param gui the considered gui
     */
     public void logout(Stage stage, GUI gui) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Translator.getAlertTitle());
        alert.setHeaderText(Translator.getAlertHeader());
        alert.setContentText(Translator.getAlertContent());

        // set text of the cancel button since here it is needed translation
        Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setText(Translator.getTextOfCancelButton());

        if (alert.showAndWait().get() == ButtonType.OK) {
            // exit from game
            gui.getClientController().closeApplication();
            // terminate the application
            Platform.exit();
            // close the window
            stage.close();
        }
    }

}

