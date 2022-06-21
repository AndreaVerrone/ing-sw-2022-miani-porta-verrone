package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.gui.controller.CharacterCard;
import it.polimi.ingsw.client.view.gui.controller.CharacterCardView;
import it.polimi.ingsw.client.view.gui.controller.TableView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import javafx.stage.Stage;

import java.io.IOException;

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
            controllerTable.tryCreateTable();
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
}
