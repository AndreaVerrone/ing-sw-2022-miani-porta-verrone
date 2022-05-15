package it.polimi.ingsw.client;

import it.polimi.ingsw.model.TowerType;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.network.NetworkSender;
import it.polimi.ingsw.network.messages.clienttoserver.game.*;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.*;
import it.polimi.ingsw.network.messages.clienttoserver.matchmaking.*;

/**
 * Class to control the messages from client to server
 */
public class Controller {
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
    private final NetworkSender match;

    /**
     * Constructor of the class
     */
    public Controller(){
        this.match = ConnectionHandler.getInstance();
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
     * @param numberOfPlayersString number of players given in input as a string
     * @param wantExpertString input from the client to tell the server if the game created is in expert mode or not
     */
    public void createGame(String numberOfPlayersString, String wantExpertString){
        int numberOfPlayers;
        boolean wantExpert;
        //Control it's an integer
        try {
            numberOfPlayers = Integer.parseInt(numberOfPlayersString);
        }
        catch(NumberFormatException e){
            //TODO: wrong input
            return;
        }
        //Control is a valid number of players
        if(numberOfPlayers < 2 || numberOfPlayers > 4){
            //TODO: wrong input
            return;
        }
        //Control if it is a yes or a no
        if(wantExpertString.length() > 0 && wantExpertString.charAt(0) == 'Y'){
            wantExpert = true;
        }
        else {
            if (wantExpertString.length() > 0 && wantExpertString.charAt(0) == 'N') {
                wantExpert = false;
            } else {
                //TODO: wrong input
                return;
            }
        }
        match.sendMessage(new CreateNewGame(numberOfPlayers, wantExpert));
    }

    /**
     * Saves the nickname of the client and sends a message to the server to enter a game
     * @param nickName nickname of the client
     * @param gameId gameId of the match the client wants to enter
     */
    public void enterGame(String nickName, String gameId){
        nickNameOwner = nickName;
        match.sendMessage(new EnterGame(nickName, gameId));
    }

    /**
     * Sends a message to the server to get all the available games
     */
    public void getGames(){
        match.sendMessage(new GetGames());
    }

    /**
     * Sends a message to the server to resume the match
     */
    public void resumeGame(){
        match.sendMessage(new ResumeGame());
    }


    /**
     * Sends a message to the server to change the number of players and controls the input given is right
     * @param newNumberPlayersString new number of players in the game
     */
    public void ChangeNumPlayers(String newNumberPlayersString){
        if(wrongPlayerTurn()) return;
        int newNumberPlayers;
        //Control it is an integer
        try {
            newNumberPlayers = Integer.parseInt(newNumberPlayersString);
        }
        catch(NumberFormatException e){
            //TODO: wrong input
            return;
        }
        //Control it is a valid number of players
        if(newNumberPlayers < 2 || newNumberPlayers > 4){
            //TODO: wrong input
            return;
        }
        match.sendMessage(new ChangeNumPlayers(newNumberPlayers));
    }

    /**
     * Sends a message to the server to exit the game
     */
    public void exitFromGame(){
        match.sendMessage(new ExitFromGame(nickNameOwner));
    }

    /**
     * Sends a message to the server to go to the next phase of the match
     */
    public void nextPhase() {
        if(wrongPlayerTurn()) return;
        match.sendMessage(new NextPhase());
    }

    /**
     * Sends a message to the server to set the towerType of the client and controls the input given is right
     * @param tower tower color chosen by the client
     */
    public void setTower(String tower){
        if(wrongPlayerTurn()) return;
        TowerType towerType;
        //Control the tower given is valid
        try {
            towerType = TowerType.valueOf(tower);
        }
        catch(IllegalArgumentException e){
            //TODO: wrong input
            return;
        }
        match.sendMessage(new SetTower(towerType));
    }

    /**
     * Sends a message to the server to set the wizard of the client and controls the input give is right
     * @param wizard wizard type chosen by the client
     */
    public void setWizard(String wizard){
        if(wrongPlayerTurn()) return;
        Wizard wizardType;
        //Control the wizard given is valid
        try {
            wizardType = Wizard.valueOf(wizard);
        }
        catch(IllegalArgumentException e){
            //TODO: wrong input
            return;
        }
        match.sendMessage(new SetWizard(wizardType));
    }

    /**
     * Sends a message to the server to move mother nature of a given number of movements and controls the input given is right
     * @param movementsString movements of mother nature given as a String
     */
    public void moveMotherNature(String movementsString){
        if(wrongPlayerTurn()) return;
        int movements;
        //Control the input given is an integer
        try {
            movements = Integer.parseInt(movementsString);
        }
        catch(NumberFormatException e){
            //TODO: wrong input
            return;
        }
        //Control the number of movements given is positive
        if(movements<=0){
            //TODO: wrong input
            return;
        }
        match.sendMessage(new MoveMotherNature(movements));
    }

    /**
     * Sends a message to the server to quit the game during the creation of the match
     */
    public void quitGame(){
        match.sendMessage(new QuitGame());
    }

    /**
     * Sends a message to the server to take all the student from the cloud given and controls the input is right
     * @param cloudIdString ID of the cloud from where the client wants to take all the students
     */
    public void takeStudentFromCloud(String cloudIdString){
        if(wrongPlayerTurn()) return;
        int cloudId;
        //Control the input given is an integer
        try {
            cloudId = Integer.parseInt(cloudIdString);
        }
        catch(NumberFormatException e){
            //TODO: wrong input
            return;
        }
        //Control it is a valid ID for a cloud
        if(cloudId<0 || cloudId>3){
            //TODO: wrongInput
            return;
        }
        match.sendMessage(new TakeStudentsFromCloud(cloudId));
    }

    /**
     * Sends a message to the server to use the assistant card given by the client and controls the input is right
     * @param assistantString assistant card chosen by the client given as a String
     */
    public void useAssistant(String assistantString){
        if(wrongPlayerTurn()) return;
        Assistant assistant;
        //Control the assistant given is valid
        try {
            assistant = Assistant.valueOf(assistantString);
        }
        catch(IllegalArgumentException e){
            //TODO: wrong input
            return;
        }
        match.sendMessage(new UseAssistant(assistant));
    }
}
