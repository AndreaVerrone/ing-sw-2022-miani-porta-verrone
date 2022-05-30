package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.Switcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


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
      Parent root = FXMLLoader.load(getClass().getResource("/fxml/MenuScene.fxml"));
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
      //primaryStage.setMaxHeight(800);
      //primaryStage.setMaxWidth(1200);
      //primaryStage.setResizable(false);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
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
