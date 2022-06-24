package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.view.gui.controller.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


/**
 * The builder of the screens of the GUI.
 */
public class GuiScreenBuilder extends ScreenBuilder {

    /**
     * The GUI.
     */
    private GUI gui;

    private Stage stage;

    /**
     * The path of the fxml file of the current screen.
     */
    private String currentViewPath;

    public GuiScreenBuilder(GUI gui,Stage stage) {
        this.gui= gui;
        this.stage=stage;
    }

    public void setStage(Stage stage){
        this.stage=stage;
    }

    /**
     * Method to load the screen specified by the path.
     * @param path path of the fxml file to load.
     */
    public void goToScreen(String path){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            GuiScreen screenController = loader.getController();
            gui.setCurrentScreen(screenController);
            screenController.attachTo(gui);

            Scene scene = new Scene(root);

            Platform.runLater(
                    () -> {
                        gui.getStage().setScene(scene);
                    });


            // gui.show();


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
        currentViewPath = switch (screen){
            case IDLE -> "/fxml/WaitScreen.fxml";
            case CHOOSE_GAME_PARAMETERS -> "/fxml/CreateGameScreen.fxml";
            case CHOOSE_LANGUAGE -> "/fxml/ChooseLanguageScreen.fxml";
            case CONNECTION_ERROR -> "";//todo: add screen here??
            case LAUNCHER -> "/fxml/StartingScreen.fxml";
            case HOME -> "/fxml/MenuScene.fxml";
            case SERVER_SPECS -> "/fxml/ChooseServerParameters.fxml";
            case MATCHMAKING_WAIT_PLAYERS -> "/fxml/LobbyScreen.fxml";
            case MATCHMAKING_ASK_PARAMS -> "/fxml/ChooseWizardAndTowerScreen.fxml";
            case PLAY_ASSISTANT_CARD -> "";//TODO missing;
            case MOVE_STUDENT -> "/fxml/Table.fxml";
            case MOVE_MOTHER_NATURE -> "/fxml/Table.fxml";//TODO ITS THE SAME SCREEN, SEE WHAT TO DO
            case CHOOSE_CLOUD -> "/fxml/Table.fxml";
            default -> throw new IllegalArgumentException();
        };
        goToScreen(currentViewPath);

    }

    /**
     * Builds and shows a content to ask the client a nickname to enter a game
     *
     * @param screen the content to show
     * @param gameID the id of the game the client wants to join
     */
    @Override
    public void build(Screen screen, String gameID) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AskNicknameScreen.fxml"));
                Parent root = loader.load();


                GuiScreen askNicknameScreen = loader.getController();
                gui.setCurrentScreen(askNicknameScreen);
                askNicknameScreen.attachTo(gui);
                askNicknameScreen.setGameID(gameID);

                Scene scene = new Scene(root);
                Platform.runLater(() -> gui.getStage().setScene(scene));


            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    /**
     * Builds and shows a content to display possible games to join or to notify the ending of the game
     *
     * @param screen the content to show
     * @param inputs the list of inputs
     */
    @Override
    public void build(Screen screen, Collection<String> inputs) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChooseGame.fxml"));
            Parent root = loader.load();

            ChooseGame chooseGame = loader.getController();
            chooseGame.setListOfGames(new ArrayList<>(inputs));

            gui.setCurrentScreen(chooseGame);
            chooseGame.attachTo(gui);

            Scene scene = new Scene(root);


            Platform.runLater(()->{
                        gui.getStage().setScene(scene);
                        stage.setScene(scene);
                    }
                    );


            //stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reshow the last screen displayed to the client
     */
    @Override
    public void rebuild() {

    }

}
