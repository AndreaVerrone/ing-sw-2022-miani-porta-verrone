package it.polimi.ingsw.client;

import it.polimi.ingsw.client.reduced_model.TableRecord;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.launcher.*;
import it.polimi.ingsw.network.VirtualView;
import it.polimi.ingsw.network.messages.clienttoserver.game.*;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.CreateNewGame;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.EnterGame;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.GetGames;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.ResumeGame;
import it.polimi.ingsw.network.messages.clienttoserver.matchmaking.*;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Class to control the messages from client to server
 */
public class ClientController {
    /**
     * Nickname of the client
     */
    private String nickNameOwner;
    /**
     * Nickname of the current player of the match played by the client
     */
    private String nickNameCurrentPlayer;

    /**
     * The identifier of the game the player is currently in.
     * Used for display purposes
     */
    private String gameID;

    /**
     * The state in which the game is currently in
     */
    private StateType gameState;

    /**
     * Virtual match played by the client
     */
    private ConnectionHandler connectionHandler;

    /**
     * The cli of the client
     */
    private final ClientView view;

    /**
     * Creates a new controller that handles the connection between client and server and
     * check if the inputs are correct.
     * @param view the view of the client
     */
    public ClientController(ClientView view){
        this.view = view;
        view.attachTo(this);

        view.getScreenBuilder().build(ScreenBuilder.Screen.LAUNCHER);
        view.run();
    }

    public String getGameID() {
        return gameID;
    }

    public boolean isInTurn() {
        return Objects.equals(nickNameOwner, nickNameCurrentPlayer);
    }

    /**
     * Tries to connect the client to the server using the specified IP and port number
     * @param ipAddress the IP address of the server
     * @param port the port of the server
     */
    public void createConnection(String ipAddress, int port){
        if (connectionHandler != null)
            return;
        try {
            connectionHandler = new ConnectionHandler(this, ipAddress, port);
            new Thread(connectionHandler).start();
            view.getScreenBuilder().build(ScreenBuilder.Screen.HOME);
        } catch (IOException e) {
            System.out.println("Can't connect to server. Try again\n");
            view.getScreenBuilder().build(ScreenBuilder.Screen.SERVER_SPECS);
        }
    }

    public String getNickNameCurrentPlayer() {
        return nickNameCurrentPlayer;
    }

    public String getNickNameOwner() {
        return nickNameOwner;
    }

    /**
     * Closes all the current tasks and terminates the application
     */
    public void closeApplication(){
        connectionHandler.closeApplication();
    }

    public void showHome() {
        view.showHome();
    }

    /**
     * Method to set the nickname of the current player of the match played by the client
     * @param nickNameCurrentPlayer nickname of the current player of the match played by the client
     */
    public void currentPlayerChanged(String nickNameCurrentPlayer) {
//        if (this.nickNameCurrentPlayer != null)
//            view.changeCurrentPlayer(nickNameCurrentPlayer);
        this.nickNameCurrentPlayer = nickNameCurrentPlayer;
    }

    /**
     * Controls if the client is not the current player
     * @return true if the client is not the current player in the match which is playing
     */
    private boolean wrongPlayerTurn(){
        if(!nickNameOwner.equals(nickNameCurrentPlayer)){
            displayErrorMessage(Translator.getItIsNotYourTurnMessage());
            return true;
        }
        return false;
    }

    /**
     * Sends a message to the server to create a new game and controls the input given is right
     * @param numberOfPlayers number of players given in input
     * @param wantExpert input from the client to tell the server if the game created is in expert mode or not
     */
    public void createGame(int numberOfPlayers, Boolean wantExpert){
        //Control is a valid number of players
        if(numberOfPlayers < 2 || numberOfPlayers > 3){
            displayErrorMessage(Translator.getInputOutOfRangeMessage());
            return;
        }
        connectionHandler.sendMessage(new CreateNewGame(numberOfPlayers, wantExpert));
    }

    /**
     * Prompt the user to provide a nickname in order to enter a specified game
     * @param gameID the ID of the game he wants to enter
     */
    public void askNicknameToEnter(String gameID){
        view.getScreenBuilder().build(ScreenBuilder.Screen.ASK_NICKNAME, gameID);
    }

    /**
     * Saves the nickname of the client and sends a message to the server to enter a game
     * @param nickName nickname of the client
     * @param gameId gameId of the match the client wants to enter
     */
    public void enterGame(String nickName, String gameId){
        nickNameOwner = nickName;
        connectionHandler.sendMessage(new EnterGame(nickName, gameId));
        this.gameID = gameId;
    }

    /**
     * Sends a message to the server to get all the available games
     */
    public void getGames(){
        connectionHandler.sendMessage(new GetGames());
    }

    /**
     * Shows the ID of the games passed as a parameter.
     * @param gameIDs the listo of game ID to show
     */
    public void displayGames(Collection<String> gameIDs){
        view.getScreenBuilder().build(ScreenBuilder.Screen.GAMES_LIST, gameIDs);
    }

    /**
     * Sends a message to the server to resume the match
     */
    public void resumeGame(){
        connectionHandler.sendMessage(new ResumeGame());
    }

    /**
     * Creates the initial view of the game (the matchmaking) using the parameter passed
     * @param playerLoginInfos the list of players currently in the lobby
     * @param numPlayers the number of players requested in the game
     * @param isExpert if the game uses expert rules
     */
    public void createGameView(Collection<ReducedPlayerLoginInfo> playerLoginInfos, int numPlayers, boolean isExpert) {
        view.createGameView(playerLoginInfos, numPlayers, isExpert, nickNameCurrentPlayer);
    }

    /**
     * Updates the view of the client showing the new players that are currently in the same lobby he is
     * @param newPlayers the new list of players in the lobby
     */
    public void playersMatchmakingChanged(Collection<ReducedPlayerLoginInfo> newPlayers){
        view.playersChanged(newPlayers);
    }
    /**
     * Sends a message to the server to change the number of players and controls the input given is right
     * @param newNumberPlayers new number of players in the game
     */
    public void changeNumPlayers(int newNumberPlayers){
        if(wrongPlayerTurn()) return;
        //Control it is a valid number of players
        if(newNumberPlayers < 2 || newNumberPlayers > 3){
            displayErrorMessage(Translator.getInputOutOfRangeMessage());
            return;
        }
        connectionHandler.sendMessage(new ChangeNumPlayers(newNumberPlayers));
    }

    /**
     * Updates the view of the client to show the changing in the number of players requested in the game
     * @param playersNum the new number of players
     */
    public void numPlayersChanged(int playersNum) {
        view.numberOfPlayersChanged(playersNum);
    }

    /**
     * Sends a message to the server to exit the game
     */
    public void exitFromGame(){
        connectionHandler.sendMessage(new ExitFromGame(nickNameOwner));
    }

    /**
     * Sends a message to the server to go to the next phase of the match
     */
    public void nextPhase() {
        if(wrongPlayerTurn()) return;
        connectionHandler.sendMessage(new NextPhase());
    }

    public void requestChoosePlayerParameter(Collection<TowerType> towersAvailable, Collection<Wizard> wizardsAvailable) {
        view.choosePlayerParameter(towersAvailable, wizardsAvailable);
    }

    /**
     * Sends a message to the server to set the towerType of the client and controls the input given is right
     * @param tower tower color chosen by the client
     */
    public void setTower(TowerType tower){
        if(wrongPlayerTurn()) return;
        connectionHandler.sendMessage(new SetTower(tower));
    }

    public void towerChanged(String nickname, TowerType towerType) {
        view.towerSelected(nickname, towerType);
    }

    /**
     * Sends a message to the server to set the wizard of the client and controls the input give is right
     * @param wizard wizard type chosen by the client
     */
    public void setWizard(Wizard wizard){
        if(wrongPlayerTurn()) return;
        connectionHandler.sendMessage(new SetWizard(wizard));
    }

    public void wizardChanged(String nickname, Wizard wizard) {
        view.wizardSelected(nickname, wizard);
    }

    /**
     * Sends a message to the server to quit the game during the creation of the match
     */
    public void quitGame(){
        connectionHandler.quitGame();
    }

    // METHODS TO SENDS MESSAGES TO THE SERVER REGARDING THE PHASES OF THE GAME

    /**
     * Sends a message to the server to use the assistant card given by the client and controls the input is right
     * @param assistant assistant card chosen by the client given
     */
    public void useAssistant(Assistant assistant){
        if(wrongPlayerTurn()) return;
        connectionHandler.sendMessage(new UseAssistant(assistant));
    }

    /**
     * Sends a message to the server to choose a student from a location and controls the input is right
     * @param student the student from location
     * @param originPosition the position of the student
     */
    public void chooseStudentFromLocation(PawnType student, Position originPosition){
        if(wrongPlayerTurn()){
            return;
        }
        connectionHandler.sendMessage(new ChooseStudentFromLocation(student,originPosition));
    }

    /**
     * Sends a message to the server to choose a destination position and controls the input is right
     * @param destination the position
     */
    public void chooseDestination(Position destination){
        if(wrongPlayerTurn()){
            return;
        }
        connectionHandler.sendMessage(new ChooseDestination(destination));
    }

    /**
     * Sends a message to the server to move mother nature of a given number of movements and controls the input given is right
     * @param movements movements of mother nature given
     */
    public void moveMotherNature(int movements){
        if(wrongPlayerTurn()) return;
        //Control the number of movements given is positive
        if(movements<=0){
            displayErrorMessage(Translator.getWrongMotherNatureMovementMessage());
            return;
        }
        connectionHandler.sendMessage(new MoveMotherNature(movements));
    }

    /**
     * Sends a message to the server to take all the student from the cloud given and controls the input is right
     * @param cloudId ID of the cloud from where the client wants to take all the students
     */
    public void takeStudentFromCloud(int cloudId){
        if(wrongPlayerTurn()) return;
        //Control it is a valid ID for a cloud
        if(cloudId<0 || cloudId>3){
            displayErrorMessage(Translator.getInputOutOfRangeMessage());
            return;
        }
        connectionHandler.sendMessage(new TakeStudentsFromCloud(cloudId));
    }

    /**
     * Updates the view of the client to reflect a change in the number of coins on the table
     * @param coinsNum the number of coins on the table
     */
    public void coinsInBagChanged(int coinsNum) {
        view.coinNumberInBagChanged(coinsNum);
    }

    /**
     * Updates the view of the client to indicate that a card has been used, and it's cost must be increased
     * @param card the card that has been used
     */
    public void coinOnCardAdded(CharacterCardsType card) {
        view.coinOnCardAdded(card);
    }

    /**
     * Updates the view of the client to indicate that the students on a card has changed.
     * @param card the card on which the change occurred
     * @param students the students on that card
     */
    public void studentsOnCardChanged(CharacterCardsType card, StudentList students) {
        view.studentsOnCardChanged(card, students);
    }

    // METHODS TO UPDATE SCREENS OF THE GAME
    public void gameStateChanged(String currentPlayerNickname, StateType currentState) {
        gameState = currentState;
        ScreenBuilder.Screen nextScreen = switch (currentState){
            case CHANGE_PLAYER_STATE -> ScreenBuilder.Screen.MATCHMAKING_WAIT_PLAYERS;
            case SET_PLAYER_PARAMETER_STATE -> ScreenBuilder.Screen.MATCHMAKING_ASK_PARAMS;
            case PLAY_ASSISTANT_STATE -> ScreenBuilder.Screen.PLAY_ASSISTANT_CARD;
            case MOVE_STUDENT_STATE -> ScreenBuilder.Screen.MOVE_STUDENT;
            case MOVE_MOTHER_NATURE_STATE -> ScreenBuilder.Screen.MOVE_MOTHER_NATURE;
            case CHOOSE_CLOUD_STATE -> ScreenBuilder.Screen.CHOOSE_CLOUD;
            default -> null;
            /*
            // todo: implement
            case USE_CHARACTER_CARD1_STATE -> null;
            case USE_CHARACTER_CARD4_STATE -> null;
            case USE_CHARACTER_CARD5_STATE -> null;
            case USE_CHARACTER_CARD8_STATE -> null;
            case USE_CHARACTER_CARD9_STATE -> null;
            case USE_CHARACTER_CARD10_STATE -> null;
            case USE_CHARACTER_CARD11_STATE -> null;
            case USE_CHARACTER_CARD12_STATE -> null;
             */
        };
        view.getScreenBuilder().build(nextScreen);
    }

    /**
     * this method will display the end game screen
     * @param winners the list of the winners of the game
     */
    public void displayEndGameScreen(List<String> winners){
        view.gameEnded(winners);
    }

    // METHODS TO DISPLAY MESSAGES

    /**
     * this method is used to display an error message.
     * @param errorMessage string containing the error message to print
     */
    public void displayErrorMessage(String errorMessage){
        view.displayErrorMessage(errorMessage);
        view.currentPlayerOrStateChanged(gameState, nickNameCurrentPlayer);
    }

    /**
     * this method is used to print a generic message
     * @param message string containing the message to print
     */
    public void displayMessage(String message){
        view.displayMessage(message);
    }

    // METHODS TO MODIFY COMPONENTS OF THE TABLE OF THE GAME

    /**
     * this method allow to update the assistant deck of the player.
     *
     * @param assistantsList actual deck of the player
     * @param owner the player that has the deck that has been changed
     */
    public void setAssistantsList(Collection<Assistant> assistantsList, String owner) {
        // update the view only if the deck involved it is the one of the player
        if(owner.equals(nickNameOwner)){
            view.assistantDeckChanged(owner, assistantsList);
        }
    }

    /**
     * this method allow to update the last assistant used of the player specified in the parameters
     * @param owner the player
     * @param assistantUsed the actual last assistant used
     */
    public void setAssistantsUsed(String owner, Assistant assistantUsed) {
        view.lastAssistantUsedChanged(owner,assistantUsed);
    }

    /**
     * this method allow to update the students on the cloud specified in parameters
     * @param ID the id of the cloud
     * @param studentList the actual student list on cloud
     */
    public void setClouds(int ID, StudentList studentList) {
        view.studentsOnCloudChanged(ID, studentList);
    }

    /**
     * this method allow to update the student on entrance of the school board of the player specified in parameters
     * @param owner the player
     * @param studentsInEntrance the actual students on entrance
     */
    public void setEntranceList(String owner, StudentList studentsInEntrance) {
        view.studentsOnEntranceChanged(owner, studentsInEntrance);
    }

    /**
     * this method allow to update the students in the dining room of the school board of the player
     * specified in the parameters.
     * @param owner the player
     * @param studentsInDiningRoom the actual students in dining room
     */
    public void setDiningRoomList(String owner, StudentList studentsInDiningRoom) {
        view.studentsInDiningRoomChanged(owner,studentsInDiningRoom);
    }

    /**
     * this method allow to update the professors in the school board of the player specified in the
     * parameters
     * @param owner the player
     * @param professors the actual collection of professors
     */
    public void setProfTableList(String owner, Collection<PawnType> professors) {
        view.professorsOfPlayerChanged(owner, professors);
    }

    /**
     * this method allow to update the number of the towers in the school board of the
     * player specified in the parameters
     * @param owner the player
     * @param numOfTowers the actual number of towers
     */
    public void setTowerNumberList(String owner, int numOfTowers) {
        view.towerNumberOfPlayerChanged(owner, numOfTowers);
    }

    /**
     * this method will allow to update the number of the coins in the school board
     * of the player specified in the parameters.
     * @param owner the player
     * @param numOfCoins the actual number of coins
     */
    public void setCoinNumberList(String owner, int numOfCoins) {
        view.coinNumberOfPlayerChanged(owner, numOfCoins);
    }

    /**
     * this method will update the number of the ban on the island with the ID specified
     * in the parameters.
     * @param ID the island on which the change has been happened
     * @param actualNumOfBan the actual number of bans on the specified island
     */
    public void updateBanOnIsland(int ID, int actualNumOfBan){
        view.numberOfBansOnIslandChanged(ID, actualNumOfBan);
    }

    /**
     * this method will update the color of the tower on the island with the ID specified
     * in the parameters.
     * @param ID the island on which the change has been happened
     * @param actualTowerColor the actual color of the tower of the island (null if the tower is not present)
     */
    public void updateTowerType(int ID, TowerType actualTowerColor){
        view.towerOnIslandChanged(ID,actualTowerColor);
    }

    /**
     * This method will update the students that are on the island with the ID specified
     * in the parameter.
     * @param ID the island on which the change has been happened
     * @param actualStudentsOnIsland the actual students on the island
     */
    public void updateStudents(int ID, StudentList actualStudentsOnIsland){
        view.studentsOnIslandChanged(ID, actualStudentsOnIsland);
    }

    /**
     * this method will update the position of mother nature
     * @param ID the ID of the island on which mother nature should be moved
     */
    public void updateMotherNaturePosition(int ID){
        view.motherNaturePositionChanged(ID);
    }

    /**
     * this method will update the screen with unified islands.
     * @param ID the ID of the island kept
     * @param IDIslandRemoved the ID of the island removed
     * @param sizeIslandRemoved the size of the island removed
     */
    public void islandUnification(int ID, int IDIslandRemoved,int sizeIslandRemoved){
        view.islandsUnified(ID, IDIslandRemoved, sizeIslandRemoved);
    }

    /**
     * this method will allow to initialize the table of the game.
     * @param tableRecord the table record used to initialize the table
     */
    public void initializeTable(TableRecord tableRecord){
        view.gameCreated(tableRecord);
    }

    // METHOD TO NOFIFY LAST ROUND
    /**
     * this method will notify the client that this is the last round
     */
    public void notifyLastRound(){
        view.notifyLastRound();
    }

}
