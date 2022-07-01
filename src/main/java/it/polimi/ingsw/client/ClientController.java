package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.network.messages.clienttoserver.game.*;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.CreateNewGame;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.EnterGame;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.GetGames;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.ResumeGame;
import it.polimi.ingsw.network.messages.clienttoserver.matchmaking.ChangeNumPlayers;
import it.polimi.ingsw.network.messages.clienttoserver.matchmaking.NextPhase;
import it.polimi.ingsw.network.messages.clienttoserver.matchmaking.SetTower;
import it.polimi.ingsw.network.messages.clienttoserver.matchmaking.SetWizard;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.io.IOException;
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
     * If the client is playing an expert game
     */
    private boolean isForExpertGame;

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

    public boolean isForExpertGame() {
        return isForExpertGame;
    }

    public String getNickNameCurrentPlayer() {
        return nickNameCurrentPlayer;
    }

    public String getNickNameOwner() {
        return nickNameOwner;
    }

    public boolean isInTurn() {
        return Objects.equals(nickNameOwner, nickNameCurrentPlayer);
    }

    public void setNickNameCurrentPlayer(String currentPlayer) {
        nickNameCurrentPlayer = currentPlayer;
    }

    public void setNickNameOwner(String nickNameOwner) {
        this.nickNameOwner = nickNameOwner;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public void setForExpertGame(boolean forExpertGame) {
        isForExpertGame = forExpertGame;
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
            connectionHandler = new ConnectionHandler(view, ipAddress, port);
            new Thread(connectionHandler).start();
            view.getScreenBuilder().build(ScreenBuilder.Screen.HOME);
        } catch (IOException e) {
            view.displayErrorMessage(Translator.getErrorConnectionMessage());
        }
    }

    /**
     * Closes all the current tasks and terminates the application
     */
    public void closeApplication(){
        if(connectionHandler!=null) {
            connectionHandler.closeApplication();
        }
    }

    /**
     * Controls if the client is not the current player
     * @return true if the client is not the current player in the match which is playing
     */
    private boolean wrongPlayerTurn(){
        if(!nickNameOwner.equals(nickNameCurrentPlayer)){
            view.displayErrorMessage(Translator.getItIsNotYourTurnMessage());
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
            view.displayErrorMessage(Translator.getInputOutOfRangeMessage());
            return;
        }
        connectionHandler.sendMessage(new CreateNewGame(numberOfPlayers, wantExpert));
    }

    /**
     * Saves the nickname of the client and sends a message to the server to enter a game
     * @param nickName nickname of the client
     * @param gameId gameId of the match the client wants to enter
     */
    public void enterGame(String nickName, String gameId){
        nickNameOwner = nickName;
        this.gameID = gameId;
        connectionHandler.sendMessage(new EnterGame(nickName, gameId));
    }

    /**
     * Sends a message to the server to get all the available games
     */
    public void getGames(){
        connectionHandler.sendMessage(new GetGames());
    }

    /**
     * Sends a message to the server to resume the match
     */
    public void resumeGame(){
        connectionHandler.sendMessage(new ResumeGame());
    }

    /**
     * Sends a message to the server to change the number of players and controls the input given is right
     * @param newNumberPlayers new number of players in the game
     */
    public void changeNumPlayers(int newNumberPlayers){
        if(wrongPlayerTurn()) return;
        //Control it is a valid number of players
        if(newNumberPlayers < 2 || newNumberPlayers > 3){
            view.displayErrorMessage(Translator.getInputOutOfRangeMessage());
            return;
        }
        connectionHandler.sendMessage(new ChangeNumPlayers(newNumberPlayers));
    }

    /**
     * Sends a message to the server to go to the next phase of the match
     */
    public void nextPhase() {
        if(wrongPlayerTurn()) return;
        connectionHandler.sendMessage(new NextPhase());
    }

    /**
     * Sends a message to the server to set the towerType of the client and controls the input given is right
     * @param tower tower color chosen by the client
     */
    public void setTower(TowerType tower){
        if(wrongPlayerTurn()) return;
        connectionHandler.sendMessage(new SetTower(tower));
    }

    /**
     * Sends a message to the server to set the wizard of the client and controls the input give is right
     * @param wizard wizard type chosen by the client
     */
    public void setWizard(Wizard wizard){
        if(wrongPlayerTurn()) return;
        connectionHandler.sendMessage(new SetWizard(wizard));
    }

    /**
     * Sends a message to the server to quit the game regardless of the state of it.
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
            view.displayErrorMessage(Translator.getWrongMotherNatureMovementMessage());
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
            view.displayErrorMessage(Translator.getInputOutOfRangeMessage());
            return;
        }
        connectionHandler.sendMessage(new TakeStudentsFromCloud(cloudId));
    }

    /**
     * Make the client use a character card
     * @param cardsType the card to use
     */
    public void useCharacterCard(CharacterCardsType cardsType) {
        if (wrongPlayerTurn())
            return;
        if (!isForExpertGame) {
            view.displayErrorMessage(Translator.getCantUseCard());
            return;
        }
        connectionHandler.sendMessage(new UseCharacterCard(cardsType));
    }
}
