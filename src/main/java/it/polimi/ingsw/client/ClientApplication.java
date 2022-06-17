package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.Switcher;
import it.polimi.ingsw.client.view.gui.controller.*;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class ClientApplication extends javafx.application.Application
{
  private static ClientApplication currentApplication;
  private Stage primaryStage;
  private ConnectionHandler connectionHandler;
  private static Switcher switcher;
  private static Controller controller;

  // When using IntelliJ, don't run the application from here, use the main method in Client
  public static void main(String[] args)
  {
    launch(args);
  }


  @Override
  public void start(Stage primaryStage)
  {
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

    try {
      switcher = new Switcher(primaryStage);
      controller = new Controller(connectionHandler);
      Group rootP = new Group();
      // Parent root = FXMLLoader.load(getClass().getResource("/fxml/StartingScreen.fxml"));

      /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateGameScreen.fxml"));
      Parent root = loader.load();
      CreateGameScreen createGameScreen = (CreateGameScreen) loader.getController();
      createGameScreen.setLabels();*/

      /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChooseWizardAndTowerScreen.fxml"));
      Parent root = loader.load();
      ChooseWizardAndTowerScreen chooseWizardAndTowerScreen = (ChooseWizardAndTowerScreen) loader.getController();
      chooseWizardAndTowerScreen.setUp(List.of(Wizard.W1,Wizard.W2,Wizard.W3,Wizard.W4),
              List.of(TowerType.WHITE,TowerType.GREY,TowerType.BLACK));*/

      /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChooseGame.fxml"));
      Parent root = loader.load();
      ChooseGame chooseGame = (ChooseGame) loader.getController();
      chooseGame.setListOfGames(List.of("Game 1","Game 2", "Game 3", "Game 4", "Game 5", "Game 6"));*/

      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ListOfGamesScreen.fxml"));
      Parent root = loader.load();
      ListOfGamesScreen listOfGamesScreen = (ListOfGamesScreen) loader.getController();
      listOfGamesScreen.setComponents(List.of("Game 1","Game 2", "Game 3", "Game 4", "Game 5", "Game 6"));

      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
      //primaryStage.setMaxHeight(800);
      //primaryStage.setMaxWidth(1200);
      primaryStage.setResizable(false);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }

    primaryStage.setOnCloseRequest(
            event -> {
              event.consume();
              logout(primaryStage);
            });
  }

  public void logout(Stage stage) {


    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Exiting from game");
    alert.setHeaderText("You're about to exit from game");
    alert.setContentText("Do you want to exit the game ? ");


    if (alert.showAndWait().get() == ButtonType.OK) {
      // System.out.println("you have successfully logged out");
      stage.close();
    }
  }


  public static ClientApplication getCurrentApplication()
  {
    return currentApplication;
  }

  public static Controller getController() {
    return controller;
  }

  public ConnectionHandler getServerHandler()
  {
    return connectionHandler;
  }

  public static Switcher getSwitcher() {
    return switcher;
  }

  public void switchToLoginScene()
  {
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
}
