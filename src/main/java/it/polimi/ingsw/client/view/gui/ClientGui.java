package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.Switcher;
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

    private static ClientGui currentApplication; // todo: remove

    private Stage primaryStage; // todo: I think it can bre removed

    private static Switcher switcher; // todo: remove

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

        GUI gui = new GUI(primaryStage);
        primaryStage.setTitle("Eriantys");
        //primaryStage.setX((primaryStage.getWidth() - primaryStage.getWidth()) / 2);
        //primaryStage.setY((primaryStage.getHeight() - primaryStage.getHeight()) / 2);
        //primaryStage.centerOnScreen();
        //primaryStage.setX(100);
        primaryStage.getIcons().add(new Image("/assets/logo/eriantys_banner.png"));
        ClientController clientController = new ClientController(gui);

        // GuiScreenBuilder guiScreenBuilder = new GuiScreenBuilder(gui, primaryStage);
        switcher = new Switcher(primaryStage); // todo: remove

        primaryStage.setResizable(false);
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
     * @param stage the current stage
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
            //gui.getClientController().exitFromGame();
            gui.getClientController().closeApplication();
            // terminate the application
            Platform.exit();
            System.exit(0);
            // close the stage
            stage.close();
        }
    }

    // todo: remove
    public static ClientGui getCurrentApplication()
  {
    return currentApplication;
  }

  // todo: remove
    public static Switcher getSwitcher() {
      return switcher;
  }

}





/*
    public void switchToLoginScene() {
      Parent root;
      try {
        root = FXMLLoader.load(getClass().getResource("/LoginScene.fxml"));
      } catch (IOException e) {
        e.printStackTrace();
        return;
      }
      Scene sc = new Scene(root);
      primaryStage.setScene(sc);
      primaryStage.setTitle("Login");
      primaryStage.sizeToScene();
    }


    public void switchToChatScene()
    {
      Parent root;
      try {
        root = FXMLLoader.load(getClass().getResource("/ChatScene.fxml"));
      } catch (IOException e) {
        e.printStackTrace();
        return;
      }
      Scene sc = new Scene(root);
      primaryStage.setScene(sc);
      primaryStage.setTitle("Chat");
      primaryStage.sizeToScene();
    }
     // primaryStage.getIcons().add(new Image("/assets/logo/eriantys_banner.png")); todo: I don't know why it does not work



     // START PART 1
     /*
        currentApplication = this;
      this.primaryStage = primaryStage;
      primaryStage.setOnCloseRequest((event) -> {
        if (serverHandler.isConnected())
          serverHandler.closeConnection();
      });
      this.serverHandler = new ServerHandler();
      this.serverHandler.setConnectionClosedObserver(() -> {
        Platform.runLater(() -> {
          if (primaryStage.isShowing()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "The connection was closed.", ButtonType.OK);
            alert.showAndWait();
            switchToLoginScene();
          }
        });
      });
      switchToLoginScene();
      primaryStage.show();*/


     // START PART 2
             // Parent root = FXMLLoader.load(getClass().getResource("/fxml/ChooseLanguageScreen.fxml"));

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LobbyScreen.fxml"));
        Parent root = loader.load();
        LobbyScreen createGameScreen =loader.getController();
        PlayerView playerView1 = new PlayerView("Ale");
        playerView1.setTowerType(TowerType.BLACK);
        playerView1.setWizard(Wizard.W1);
        PlayerView playerView2 = new PlayerView("Andre");
        //playerView2.setTowerType(TowerType.WHITE);
        playerView2.setWizard(Wizard.W2);

        createGameScreen.setUp(123,2,"easy",
                List.of(
                        playerView1,
                        playerView2
                ));*/

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChooseWizardAndTowerScreen.fxml"));
        Parent root = loader.load();
        ChooseWizardAndTowerScreen chooseWizardAndTowerScreen = (ChooseWizardAndTowerScreen) loader.getController();
        chooseWizardAndTowerScreen.setUp(List.of(Wizard.W1,Wizard.W2,Wizard.W3,Wizard.W4),
                List.of(TowerType.WHITE,TowerType.GREY,TowerType.BLACK));*/

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChooseGame.fxml"));
        Parent root = loader.load();
        ChooseGame chooseGame = (ChooseGame) loader.getController();
        chooseGame.setListOfGames(List.of("Game 1","Game 2", "Game 3", "Game 4", "Game 5", "Game 6"));*/

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ListOfGamesScreen.fxml"));
        Parent root = loader.load();
        ListOfGamesScreen listOfGamesScreen = (ListOfGamesScreen) loader.getController();
        listOfGamesScreen.setComponents(List.of("Game 1","Game 2", "Game 3", "Game 4", "Game 5", "Game 6"));
        */
