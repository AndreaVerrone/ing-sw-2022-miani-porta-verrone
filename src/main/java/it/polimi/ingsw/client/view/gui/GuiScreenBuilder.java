package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.view.gui.controller.ExitScreen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class GuiScreenBuilder extends ScreenBuilder {



    private GUI gui;

    private Stage stage;

    public GuiScreenBuilder(GUI gui,Stage stage) {
        this.gui= gui;
        this.stage=stage;
    }

    public void setStage(Stage stage){
        this.stage=stage;
    }



    private String gerFXMLFilePath(ScreenBuilder.Screen screen){
        String filePath = "";
        switch (screen){
            case HOME -> filePath = "/fxml/MenuScene.fxml";
            case END_GAME -> filePath = "/fxml/ExitScreen.fxml";
        }
        return filePath;
    }



    /**
     * This method allow to go to the home screen.
     */
    public void goToHomeScreen(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuScene.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void goToExitScreen(List<String> winners){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExitScreen.fxml"));
            Parent root = loader.load();
            ExitScreen controllerCard = (ExitScreen) loader.getController();
            controllerCard.setUpExitScreen(winners);
            // display(root);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            //stage.setFullScreen(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Builds and shows the screen corresponding to the specified input
     *
     * @param screen the screen that need to be shown
     */
    @Override
    public void build(Screen screen) {
        switch (screen){
            case HOME -> goToHomeScreen();

        }


    }

    /**
     * Builds and shows a content to ask the client a nickname to enter a game
     *
     * @param screen the content to show
     * @param gameID the id of the game the client wants to join
     */
    @Override
    public void build(Screen screen, String gameID) {

    }

    /**
     * Builds and shows a content to display possible games to join or to notify the ending of the game
     *
     * @param screen the content to show
     * @param inputs the list of inputs
     */
    @Override
    public void build(Screen screen, Collection<String> inputs) {

    }

    /**
     * Reshow the last screen displayed to the client
     */
    @Override
    public void rebuild() {

    }
}
