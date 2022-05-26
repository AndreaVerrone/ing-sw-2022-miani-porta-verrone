package it.polimi.ingsw.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import javafx.stage.Stage;

import java.io.IOException;

public class Switcher {

    private final Stage stage;

    public Switcher(Stage stage){
        this.stage = stage;
    }

    public void goToCreateNewGame(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Table.fxml"));
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
