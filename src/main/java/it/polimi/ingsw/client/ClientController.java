package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.launcher.Launcher;
import it.polimi.ingsw.network.messages.clienttoserver.game.MoveMotherNature;
import it.polimi.ingsw.network.messages.clienttoserver.game.QuitGame;
import it.polimi.ingsw.network.messages.clienttoserver.game.TakeStudentsFromCloud;
import it.polimi.ingsw.network.messages.clienttoserver.game.UseAssistant;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.CreateNewGame;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.EnterGame;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.GetGames;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.ResumeGame;
import it.polimi.ingsw.network.messages.clienttoserver.matchmaking.*;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.io.IOException;

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
     * Virtual match played by the client
     */
    private ConnectionHandler connectionHandler;

    /**
     * The cli of the client
     */
    private final CLI cli;

    /**
     * Creates a new controller that handles the connection between client and server and
     * check if the inputs are correct.
     * @param cli the cli of the client
     */
    public ClientController(CLI cli){
        this.cli = cli;
        cli.attachTo(this);
        cli.setCurrentScreen(new Launcher(cli));
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
        } catch (IOException e) {
            System.out.println("Can't connect to server");
        }
    }

    /**
     * Closes all the current tasks and terminates the application
     */
    public void closeApplication(){
        connectionHandler.closeApplication();
    }

    /**
     * Method to set the nickname of the current player of the match played by the client
     * @param nickNameCurrentPlayer nickname of the current player of the match played by the client
     */
    public void setNickNameCurrentPlayer(String nickNameCurrentPlayer) {
        this.nickNameCurrentPlayer = nickNameCurrentPlayer;
    }

    /**
     * Controls if the client is not the current player
     * @return true if the client is not the current player in the match which is playing
     */
    private boolean wrongPlayerTurn(){
        if(!nickNameOwner.equals(nickNameCurrentPlayer)){
            //TODO: Update view, not your turn!
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
            //TODO: wrong input
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
    public void ChangeNumPlayers(int newNumberPlayers){
        if(wrongPlayerTurn()) return;
        //Control it is a valid number of players
        if(newNumberPlayers < 2 || newNumberPlayers > 3){
            //TODO: wrong input
            return;
        }
        connectionHandler.sendMessage(new ChangeNumPlayers(newNumberPlayers));
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
     * Sends a message to the server to move mother nature of a given number of movements and controls the input given is right
     * @param movements movements of mother nature given
     */
    public void moveMotherNature(int movements){
        if(wrongPlayerTurn()) return;
        //Control the number of movements given is positive
        if(movements<=0){
            //TODO: wrong input
            return;
        }
        connectionHandler.sendMessage(new MoveMotherNature(movements));
    }

    /**
     * Sends a message to the server to quit the game during the creation of the match
     */
    public void quitGame(){
        connectionHandler.sendMessage(new QuitGame());
    }

    /**
     * Sends a message to the server to take all the student from the cloud given and controls the input is right
     * @param cloudId ID of the cloud from where the client wants to take all the students
     */
    public void takeStudentFromCloud(int cloudId){
        if(wrongPlayerTurn()) return;
        //Control it is a valid ID for a cloud
        if(cloudId<0 || cloudId>3){
            //TODO: wrongInput
            return;
        }
        connectionHandler.sendMessage(new TakeStudentsFromCloud(cloudId));
    }

    /**
     * Sends a message to the server to use the assistant card given by the client and controls the input is right
     * @param assistant assistant card chosen by the client given
     */
    public void useAssistant(Assistant assistant){
        if(wrongPlayerTurn()) return;
        connectionHandler.sendMessage(new UseAssistant(assistant));
    }
}
