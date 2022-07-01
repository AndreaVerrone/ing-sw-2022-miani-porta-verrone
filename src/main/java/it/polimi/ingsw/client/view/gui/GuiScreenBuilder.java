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
    private final GUI gui;

    /**
     * The stage.
     */
    private final Stage stage;

    /**
     * The path of the fxml file of the current screen.
     */
    private String currentViewPath;

    /**
     * The current scene.
     */
    private  Scene currentScene;

    /**
     * The constructor of the class.
     * It will create the class taking in input the gui and the stage to be used.
     * @param gui the considered gui
     * @param stage the stage that needs to be used
     */
    public GuiScreenBuilder(GUI gui,Stage stage) {
        this.gui= gui;
        this.stage=stage;
    }

    /**
     * Method to load the screen specified by the path.
     * @param path the path of the string to go to
     */
    public void goToScreen(String path){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            GuiScreen screenController = loader.getController();
            gui.setCurrentScreen(screenController);
            screenController.attachTo(gui);

            currentScene = new Scene(root);
            gui.setCurrentScene(currentScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will set the scene on the stage og the gui.
     */
    public void show(){
        Platform.runLater(() -> gui.getStage().setScene(currentScene));
    }

    /**
     * Builds and shows the screen corresponding to the specified input
     *
     * @param screen the screen that need to be shown
     */
    @Override
    public void build(Screen screen) {
        switch (screen){
            case IDLE -> goToScreen("/fxml/WaitScreen.fxml");
            case CHOOSE_GAME_PARAMETERS -> goToScreen("/fxml/CreateGameScreen.fxml");
            case CHOOSE_LANGUAGE -> goToScreen("/fxml/ChooseLanguageScreen.fxml");
            case CONNECTION_ERROR -> goToScreen("/fxml/ConnectionErrorScreen.fxml");
            case LAUNCHER -> goToScreen("/fxml/StartingScreen.fxml");
            case HOME -> goToScreen("/fxml/MenuScene.fxml");
            case SERVER_SPECS -> goToScreen("/fxml/ChooseServerParameters.fxml");
            case MATCHMAKING_WAIT_PLAYERS -> goToScreen("/fxml/LobbyScreen.fxml");
            case CHOOSE_ASSISTANT_CARD -> goToScreen("/fxml/UseAssistanScreen.fxml");
            case MATCHMAKING_ASK_PARAMS -> goToScreen("/fxml/ChooseWizardAndTowerScreen.fxml");
            case PLAY_ASSISTANT_CARD -> goToTable();
            case MOVE_STUDENT -> goToTable();
            case CHOOSE_CHARACTER_CARD -> goToChooseCharacterCard();
            case MOVE_MOTHER_NATURE, USE_CHARACTER_CARD4, USE_CHARACTER_CARD1, USE_CHARACTER_CARD5, USE_CHARACTER_CARD8, USE_CHARACTER_CARD9, USE_CHARACTER_CARD10, USE_CHARACTER_CARD11, USE_CHARACTER_CARD12, CHOOSE_CLOUD -> goToTable();
            case ASK_NICKNAME -> goToScreen("/fxml/AskNicknameScreen.fxml");
            default -> throw new IllegalArgumentException();
        }

        if(screen.equals(Screen.HOME)){
            gui.show();
        }

    }

    /**
     * Method to switch scene to the table view
     */
    private void goToTable(){
        gui.setCurrentScene(gui.getTableScene());
        gui.setCurrentScreen(gui.getTableScreen());
        Platform.runLater(() ->gui.getStage().setFullScreen(true));
    }

    /**
     * Method to switch scene to the character card view
     */
    private void goToChooseCharacterCard(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CharacterCard.fxml"));
            Parent root = loader.load();

            GuiScreen screenController = loader.getController();
            gui.setCharacterCardScreen(screenController);
            screenController.attachTo(gui);

            currentScene = new Scene(root);
            gui.setCurrentScene(currentScene);

        } catch (IOException e) {
            e.printStackTrace();
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

        if(screen.equals(Screen.GAMES_LIST)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChooseGame.fxml"));
                Parent root = loader.load();

                ChooseGame chooseGame = loader.getController();
                chooseGame.setListOfGames(new ArrayList<>(inputs));

                gui.setCurrentScreen(chooseGame);
                chooseGame.attachTo(gui);

                Scene scene = new Scene(root);

                Platform.runLater(
                        () -> {
                            gui.getStage().setScene(scene);
                            stage.setScene(scene);
                        }
                );

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(screen.equals(Screen.END_GAME)){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExitScreen.fxml"));
                Parent root = loader.load();

                GuiScreen exitScreen = loader.getController();
                gui.setCurrentScreen(exitScreen);
                exitScreen.attachTo(gui);
                Platform.runLater(()->exitScreen.setUpExitScreen(new ArrayList<>(inputs)));

                Scene scene = new Scene(root);

                Platform.runLater(
                        () -> {
                            gui.getStage().setScene(scene);
                            gui.getStage().setFullScreen(false);
                        }
                );

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reshow the last screen displayed to the client
     */
    @Override
    public void rebuild() {

    }

}
