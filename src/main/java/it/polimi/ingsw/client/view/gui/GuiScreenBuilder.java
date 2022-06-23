package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.view.cli.game.ChooseCloudScreen;
import it.polimi.ingsw.client.view.cli.game.MoveMotherNatureScreen;
import it.polimi.ingsw.client.view.cli.game.MoveStudentsPhaseScreen;
import it.polimi.ingsw.client.view.cli.game.PlanningPhaseScreen;
import it.polimi.ingsw.client.view.cli.launcher.AskServerSpecificationScreen;
import it.polimi.ingsw.client.view.cli.launcher.HomeScreen;
import it.polimi.ingsw.client.view.cli.launcher.LauncherScreen;
import it.polimi.ingsw.client.view.cli.matchmaking.ChooseParametersScreen;
import it.polimi.ingsw.client.view.cli.matchmaking.LobbyScreen;
import it.polimi.ingsw.client.view.cli.waiting.ConnectionErrorScreen;
import it.polimi.ingsw.client.view.gui.controller.*;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GuiScreenBuilder extends ScreenBuilder {



    private GUI gui;

    private Stage stage;

    private String currentViewPath;

    public GuiScreenBuilder(GUI gui,Stage stage) {
        this.gui= gui;
        this.stage=stage;
    }

    public void setStage(Stage stage){
        this.stage=stage;
    }





    public void goToScreen(String path){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            GuiScreen screenController = loader.getController();
            gui.setNextScreen(screenController);

            Scene scene = new Scene(root);
            gui.getStage().setScene(scene);

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
        String filePath = "";
        currentViewPath = switch (screen){
            case CONNECTION_ERROR -> "";//todo: add screen here
            case LAUNCHER -> "/assets/StartingScreen.fxml";
            case HOME -> "/assets/.fxml";//todo: dont know here
            case SERVER_SPECS -> "/assets/ChooseServerParameters.fxml";
            case MATCHMAKING_WAIT_PLAYERS -> "/assets/LobbyScreen.fxml";
            case MATCHMAKING_ASK_PARAMS -> "/assets/ChooseWizardAndTowerScreen.fxml";
            case PLAY_ASSISTANT_CARD -> "";//TODO missing;
            case MOVE_STUDENT -> "/assets/Table.fxml";
            case MOVE_MOTHER_NATURE -> "/assets/Table.fxml";//TODO ITS THE SAME SCREEN, SEE WHAT TO DO
            case CHOOSE_CLOUD -> "/assets/Table.fxml";
            default -> throw new IllegalArgumentException();
        };
        currentViewPath = filePath;
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
