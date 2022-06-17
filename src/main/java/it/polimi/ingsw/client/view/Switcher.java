package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.gui.controller.*;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Switcher {

    private TableView controllerTable;

    private final Stage stage;

    public Switcher(Stage stage){
        this.stage = stage;
    }

    public void goToCreateNewGame(){
        stage.setFullScreen(true);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Table.fxml"));
            Parent root = loader.load();
            controllerTable = (TableView) loader.getController();
            display(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToCharacterCardView(CharacterCard card){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CharacterCard.fxml"));
            Parent root = loader.load();
            CharacterCardView controllerCard = (CharacterCardView) loader.getController();
            controllerCard.fillView(card);
            display(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void display(Parent root){
        Scene scene = new Scene(root, Color.CYAN);
        /*
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFieldOfView(50);
        camera.getTransforms().addAll(new Translate(500, 900, -500), new Rotate(45, Rotate.X_AXIS));
        camera.setNearClip(1);
        camera.setFarClip(1000);
        scene.setCamera(camera);
*/
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
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

    public void goToAskServerParameters(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChooseServerParameters.fxml"));
            Parent root = loader.load();

            ChooseServerParameters chooseServerParameters = (ChooseServerParameters) loader.getController();
            chooseServerParameters.setLabels();
            // display(root);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            // stage.setFullScreen(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void goToHomeScreen(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuScene.fxml"));
            Parent root = loader.load();

            // display(root);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            // stage.setFullScreen(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void goToChooseLanguageScreen(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChooseLanguageScreen.fxml"));
            Parent root = loader.load();

            // display(root);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            // stage.setFullScreen(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void goToCreateGameScreen() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateGameScreen.fxml"));
            Parent root = loader.load();

            CreateGameScreen createGameScreen = (CreateGameScreen) loader.getController();
            createGameScreen.setLabels();

            // display(root);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            // stage.setFullScreen(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToAskNicknameScreen(int gameID){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AskNicknameScreen.fxml"));
            Parent root = loader.load();

            AskNicknameScreen askNicknameScreen = (AskNicknameScreen) loader.getController();
            askNicknameScreen.setParameters(gameID);

            // display(root);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            // stage.setFullScreen(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void goToChooseGameToJoin(Collection<String> gameIDs){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ListOfGamesScreen.fxml"));
            Parent root = loader.load();

            ListOfGamesScreen listOfGamesScreen = (ListOfGamesScreen) loader.getController();
            listOfGamesScreen.setComponents(new ArrayList<>(gameIDs));

            // display(root);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void goToChooseWizardAndTower(List<Wizard> wizardsAvailable, List<TowerType> towersAvailble){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChooseWizardAndTowerScreen.fxml"));
            Parent root = loader.load();

            ChooseWizardAndTowerScreen chooseWizardAndTowerScreen = (ChooseWizardAndTowerScreen) loader.getController();
            chooseWizardAndTowerScreen.setUp(wizardsAvailable,towersAvailble);

            // display(root);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void goToChooseGameScreen(Collection<String> games){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChooseGame.fxml"));
            Parent root = loader.load();
            ChooseGame chooseGame = (ChooseGame) loader.getController();
            chooseGame.setListOfGames(List.of("1","2"));

            // display(root);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}