package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.reduced_model.ReducedPlayerLoginInfo;
import it.polimi.ingsw.client.reduced_model.TableRecord;
import it.polimi.ingsw.client.view.gui.controller.PlayerView;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

/**
 * A class to handle the client GUI.
 */
public class GUI extends ClientView {

    /**
     * The stage.
     */
    Stage stage;

    /**
     * The current scene.
     */
    private Scene currentScene;

    /**
     * The controller class of the current screen.
     */
    private GuiScreen currentScreen;


    // MATCHMAKING RELATED ATTRIBUTES
    private Map<String, PlayerView> playerViewMap;

    private String gameID;

    private int numPlayers;

    private boolean isExpert;

    private TableRecord tableRecord;

    private Scene tableScene;

    private GuiScreen tableScreen;

    private StateType currentState;

    private final List<PlayerView> players = new ArrayList<>();

    private Collection<Assistant> deck = new ArrayList<>();

    private Stage useAssistantStage = new Stage();

    public List<Assistant> getDeck() {
        return new ArrayList<>(deck);
    }

    /**
     * The constructor of the class.
     * It will construct the class by taking in input the stage.
     * @param stage stage used to build the gui
     */
    public GUI(Stage stage) {
        this.stage=stage;
        setScreenBuilder(new GuiScreenBuilder(this,stage));
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public Stage getStage() {
        return stage;
    }

    public Stage getUseAssistantStage() {
        return useAssistantStage;
    }

    public GuiScreen getTableScreen() {
        return tableScreen;
    }

    public StateType getCurrentState() {
        return currentState;
    }

    public void setCurrentScreen(GuiScreen screen){
        currentScreen = screen;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        show();
        stage.show();

    }

    /**
     * This method will set the scene to the stage.
     */
    public void show(){
        Platform.runLater(() -> stage.setScene(currentScene));
    }

    private void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exiting from game");
        alert.setHeaderText("You're about to exit from game");
        alert.setContentText("Do you want to exit the game ? ");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }


    public GuiScreen getCurrentScreen() {
        return currentScreen;
    }

    public void useAssistantCard(){

        getScreenBuilder().build(ScreenBuilder.Screen.PLAY_ASSISTANT_CARD);
        Platform.runLater(()->currentScreen.setUp(deck));
        useAssistantStage.close();
        useAssistantStage.setScene(currentScene);
        useAssistantStage.show();
    }

    @Override
    public void currentPlayerOrStateChanged(StateType currentState, String currentPlayer) {
        getClientController().setNickNameCurrentPlayer(currentPlayer);

        //if(currentState.equals(StateType.PLAY_ASSISTANT_STATE)){
            //currentScene = tableScene;
            //currentScreen = tableScreen;
            //getScreenBuilder().build(ScreenBuilder.Screen.PLAY_ASSISTANT_CARD);
            //Platform.runLater(()-> currentScreen.setUp(deck));
        //}
        if(currentState.equals(StateType.MOVE_STUDENT_STATE)||currentState.equals(StateType.PLAY_ASSISTANT_STATE)){
            currentScene = tableScene;
            currentScreen = tableScreen;
            //Platform.runLater(() -> stage.setFullScreen(true));

        } else if (currentState.equals(StateType.MOVE_MOTHER_NATURE_STATE)) {

        } else if (currentState.equals(StateType.CHOOSE_CLOUD_STATE)) {

        } else{
            getScreenBuilder().build(ScreenBuilder.Screen.parse(currentState));
        }
        this.currentState = currentState;
        currentScreen.setCurrentPlayer(currentPlayer);
        currentScreen.updateState(currentState);
        show();
    }


    @Override
    public void displayErrorMessage(String message){
        Platform.runLater(()->currentScreen.showErrorMessage(message));
    }

    /**
     * Displays a generic message on the screen
     *
     * @param message a string representing the message
     */
    @Override
    public void displayMessage(String message) {

    }

    /**
     * Creates the initial view of the game (the matchmaking) using the parameter passed
     *
     * @param playerLoginInfos the list of players currently in the lobby
     * @param numPlayers       the number of players requested in the game
     * @param isExpert         if the game uses expert rules
     * @param currentPlayer    the nickname of the current player
     */
    @Override
    public void createMatchmakingView(Collection<ReducedPlayerLoginInfo> playerLoginInfos, int numPlayers, boolean isExpert, String currentPlayer) {

        getClientController().setNickNameCurrentPlayer(currentPlayer);
        playerViewMap = new HashMap<>(numPlayers, 1);
        for(ReducedPlayerLoginInfo playerLoginInfo : playerLoginInfos){
            playerViewMap.put(playerLoginInfo.nickname(), new PlayerView(playerLoginInfo.nickname()));
        }
        this.numPlayers=numPlayers;
        this.isExpert=isExpert;
        getScreenBuilder().build(ScreenBuilder.Screen.MATCHMAKING_WAIT_PLAYERS);
        Platform.runLater(()->currentScreen.setUp(gameID, numPlayers, isExpert, playerViewMap.values().stream().toList()));
        show();
    }

    /**
     * Notifies that the towers and wizards available have been changed
     *
     * @param towersAvailable  the towers available
     * @param wizardsAvailable the wizards available
     */
    @Override
    public void choosePlayerParameter(Collection<TowerType> towersAvailable, Collection<Wizard> wizardsAvailable) {
        if(getClientController().isInTurn()) {
            getScreenBuilder().build(ScreenBuilder.Screen.MATCHMAKING_ASK_PARAMS);
            Platform.runLater(() -> currentScreen.setUp(new ArrayList<>(wizardsAvailable), new ArrayList<>(towersAvailable)));
        }else {
            getScreenBuilder().build(ScreenBuilder.Screen.MATCHMAKING_WAIT_PLAYERS);
            Platform.runLater(() -> currentScreen.setUp(gameID, numPlayers, isExpert, playerViewMap.values().stream().toList()));
        }
        show();
    }

    /**
     * Notifies that the passed card has been used and need to increase it's cost
     *
     * @param characterCardsType the card used
     */
    @Override
    public void coinOnCardAdded(CharacterCardsType characterCardsType) {
        currentScreen.addCoinOnCard(characterCardsType);
    }

    /**
     * Notifies a change in the students on the specified card
     *
     * @param characterCardType the card on which the changes happened
     * @param actualStudents    the students on the card
     */
    @Override
    public void studentsOnCardChanged(CharacterCardsType characterCardType, StudentList actualStudents) {
        currentScreen.updateStudentsOnCard(characterCardType, actualStudents);
    }

    /**
     * Notifies that the selected number of players for the game has changed
     *
     * @param numberOfPlayers the new number of player requested
     */
    @Override
    public void numberOfPlayersChanged(int numberOfPlayers) {

    }

    /**
     * Notifies that the players in the matchmaking have been changed
     *
     * @param players the actual players in the matchmaking
     */
    @Override
    public void playersChanged(Collection<ReducedPlayerLoginInfo> players) {
        playerViewMap.clear();
        for(ReducedPlayerLoginInfo playerLoginInfo : players){
            playerViewMap.put(playerLoginInfo.nickname(), new PlayerView(playerLoginInfo.nickname()));
        }
        boolean lobbyFull = currentScreen.updatePlayerList(playerViewMap.values().stream().toList());
        if (lobbyFull) {
            if(getClientController().isInTurn()) {
                getClientController().nextPhase();
            }
        }

    }

    /**
     * Notifies that a player has selected the tower that he want to use in the game
     *
     * @param player the player that made the decision
     * @param tower  the tower selected
     */
    @Override
    public void towerSelected(String player, TowerType tower) {
        playerViewMap.get(player).setTowerType(tower);
        if(!getClientController().isInTurn()) {
            Platform.runLater(() -> currentScreen.updateTowerType(player, tower));
        }
    }

    /**
     * Notifies that a player has selected a wizard that he want to use in the game
     *
     * @param player the player that made the decision
     * @param wizard the wizard selected
     */
    @Override
    public void wizardSelected(String player, Wizard wizard) {
        playerViewMap.get(player).setWizard(wizard);
        if(!getClientController().isInTurn()){
            Platform.runLater(()->currentScreen.updateWizard(player, wizard));
        }
        for(PlayerView playerView: players){
            if(playerView.getNickname().equals(player)){
                playerView.setWizard(wizard);
            }
        }
    }

    /**
     * Notifies that the number of bans on the specified island has changed
     *
     * @param islandIDWithBan the id of the island
     * @param actualNumOfBans the number of bans on that island
     */
    @Override
    public void numberOfBansOnIslandChanged(int islandIDWithBan, int actualNumOfBans) {
        currentScreen.changeBansOnIsland(islandIDWithBan, actualNumOfBans);
    }

    /**
     * Notifies that the deck of the specified player has changed
     *
     * @param nickName   the nickname of the player
     * @param actualDeck the deck of the player
     */
    @Override
    public void assistantDeckChanged(String nickName, Collection<Assistant> actualDeck) {
        currentScreen=tableScreen;
        currentScreen.updateDeck(new ArrayList<>(actualDeck));
    }

    /**
     * Notifies that the number of coins in the bag has changed
     *
     * @param actualNumOfCoins the new number of coins
     */
    @Override
    public void coinNumberInBagChanged(int actualNumOfCoins) {
    }

    /**
     * Notifies that the number of coins of a player has changed
     *
     * @param nickNameOfPlayer the nickname of the player
     * @param actualNumOfCoins the number of coins
     */
    @Override
    public void coinNumberOfPlayerChanged(String nickNameOfPlayer, int actualNumOfCoins) {
        currentScreen.changeNumberOfCoinsPlayer(nickNameOfPlayer, actualNumOfCoins);
    }

    /**
     * Notifies that the number of towers of a player has changed
     *
     * @param nickName          the nickname of the player
     * @param numOfActualTowers the number of towers
     */
    @Override
    public void towerNumberOfPlayerChanged(String nickName, int numOfActualTowers) {
        currentScreen.updateTowersOnSchoolBoard(nickName, numOfActualTowers);
    }

    /**
     * Notifies that the current round the clients are playing is the last of this game
     */
    @Override
    public void notifyLastRound() {
        currentScreen.showLastRound();
    }

    /**
     * Notifies that the total number of islands is changed
     *
     * @param actualNumOfIslands the new number of islands
     */
    @Override
    public void islandNumberChanged(int actualNumOfIslands) {

    }

    /**
     * Notifies that two islands have been unified
     *
     * @param islandID        the id of the island that remained on the table
     * @param islandRemovedID the id of the island removed from the table
     * @param finalSize       the size of the island removed
     */
    @Override
    public void islandsUnified(int islandID, int islandRemovedID, int finalSize) {
        currentScreen.unifyIslands(islandID, islandRemovedID, finalSize);
    }

    /**
     * Notifies that the last assistant played of a player has changed
     *
     * @param nickName            the nickname of the player
     * @param actualLastAssistant the last assistant that he used
     */
    @Override
    public void lastAssistantUsedChanged(String nickName, Assistant actualLastAssistant) {
        currentScreen=tableScreen;
        currentScreen.useAssistantCard(nickName, actualLastAssistant);
        System.out.println("Update assistant: " + actualLastAssistant);
    }

    /**
     * Notifies that the position of mother nature has changed
     *
     * @param actualMotherNaturePosition the new position of mother nature
     */
    @Override
    public void motherNaturePositionChanged(int actualMotherNaturePosition) {
        currentScreen.moveMotherNature(actualMotherNaturePosition);
    }

    /**
     * Notifies that the professors of a player changed
     *
     * @param nickName         the nickname of the player
     * @param actualProfessors the professors he owns
     */
    @Override
    public void professorsOfPlayerChanged(String nickName, Collection<PawnType> actualProfessors) {
        currentScreen.updateProfessorsToPlayer(nickName, actualProfessors);
    }

    /**
     * Notifies that the students in the dining room of a player changed
     *
     * @param nickname       the nickname of the player
     * @param actualStudents the students in his dining room
     */
    @Override
    public void studentsInDiningRoomChanged(String nickname, StudentList actualStudents) {
        currentScreen.updateDiningRoomToPlayer(nickname, actualStudents);
    }

    /**
     * Notifies that the students on a cloud changed
     *
     * @param cloudID           the id of the cloud
     * @param actualStudentList the students on the cloud
     */
    @Override
    public void studentsOnCloudChanged(int cloudID, StudentList actualStudentList) {
        currentScreen.updateStudentOnCloud(cloudID, actualStudentList);
    }

    /**
     * Notifies that the students in the entrance of a player changed
     *
     * @param nickname       the nickname of the player
     * @param actualStudents the students in his entrance
     */
    @Override
    public void studentsOnEntranceChanged(String nickname, StudentList actualStudents) {
        currentScreen.updateEntranceToPlayer(nickname, actualStudents);
    }

    /**
     * Notifies that the students on an island changed
     *
     * @param islandID       the id of the island
     * @param actualStudents the students on the island
     */
    @Override
    public void studentsOnIslandChanged(int islandID, StudentList actualStudents) {
        currentScreen.updateStudentsOnIsland(islandID, actualStudents);
    }

    /**
     * Notifies that the tower on an island changed
     *
     * @param islandIDWithChange the id of the island
     * @param actualTower        the new tower on that island
     */
    @Override
    public void towerOnIslandChanged(int islandIDWithChange, TowerType actualTower) {
        currentScreen.updateTowerOnIsland(islandIDWithChange, actualTower);
    }

    /**
     * Notifies that the game has ended and who is the winner
     *
     * @param winners a list containing the nicknames of the winners
     */
    @Override
    public void gameEnded(Collection<String> winners) {
        getScreenBuilder().build(ScreenBuilder.Screen.END_GAME, winners);
        show();
    }

    /**
     * Notifies that the game has been created
     *
     * @param tableRecord the state of the game at that moment
     */
    @Override
    public void gameCreated(TableRecord tableRecord) {
        this.tableRecord = tableRecord;
        this.deck = tableRecord.assistantsList();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Table.fxml"));
            Parent root = null;

            root = loader.load();

            tableScreen = loader.getController();
            tableScreen.attachTo(this);
            List<ReducedPlayerLoginInfo> players = new ArrayList<>();
            players.add(new ReducedPlayerLoginInfo(getClientController().getNickNameOwner(), null, playerViewMap.get(getClientController().getNickNameOwner()).getWizard().get()));
            for(String nickname: playerViewMap.keySet()){
                if(!nickname.equals(getClientController().getNickNameOwner())) {
                    players.add(new ReducedPlayerLoginInfo(nickname, null, playerViewMap.get(nickname).getWizard().get()));
                }
            }
            tableScreen.createTable(tableRecord, isExpert, players);


            tableScene = new Scene(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
