package it.polimi.ingsw.client;

import it.polimi.ingsw.client.reduced_model.ReducedIsland;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.game.MoveMotherNatureScreen;
import it.polimi.ingsw.client.view.cli.game.MoveStudentsPhaseScreen;
import it.polimi.ingsw.client.view.cli.game.PlanningPhaseScreen;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.AssistantCard;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.AssistantCardUsed;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.Table;
import it.polimi.ingsw.client.view.cli.launcher.*;
import it.polimi.ingsw.network.messages.clienttoserver.game.*;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.CreateNewGame;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.EnterGame;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.GetGames;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.ResumeGame;
import it.polimi.ingsw.network.messages.clienttoserver.matchmaking.*;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;

import java.io.IOException;
import java.util.*;

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

        // todo: ACTUAL CODE
        /*
        //cli.setNextScreen(new LauncherScreen(cli));
        //cli.run();
         */

        // todo:testing code
        //  <--- from here
        StudentList stud = new StudentList();
        try {
            stud.changeNumOf(PawnType.BLUE_UNICORNS,3);
            stud.changeNumOf(PawnType.GREEN_FROGS,2);
            stud.changeNumOf(PawnType.PINK_FAIRIES,2);
        } catch (NotEnoughStudentException e) {
            throw new RuntimeException(e);
        }

        StudentList stud2 = new StudentList();
        try {
            stud2.changeNumOf(PawnType.BLUE_UNICORNS,1);
            stud2.changeNumOf(PawnType.GREEN_FROGS,1);
            stud2.changeNumOf(PawnType.RED_DRAGONS,1);
            stud2.changeNumOf(PawnType.PINK_FAIRIES,1);
        } catch (NotEnoughStudentException e) {
            throw new RuntimeException(e);
        }

        Map<String, Assistant> map0 = new HashMap<>();
        map0.put("player 1", Assistant.CARD_9);
        map0.put("player 2", Assistant.CARD_1);
        // map0.put("player 3", Assistant.CARD_3);

        Map<String,StudentList> map = new HashMap<>();
        map.put("player 1",stud);
        map.put("player 2",stud);
        map.put("player 3",stud);

        Map<String, Collection<PawnType>> map2 = new HashMap<>();
        map2.put("player 1", List.of(PawnType.BLUE_UNICORNS));
        map2.put("player 2", List.of(PawnType.GREEN_FROGS));
        map2.put("player 3", List.of());

        Map<String, TowerType> map3 = new HashMap<>();
        map3.put("player 1", TowerType.BLACK);
        map3.put("player 2", TowerType.WHITE);
        map3.put("player 3", TowerType.GREY);

        Map<String, Integer> map4 = new HashMap<>();
        map4.put("player 1", 8);
        map4.put("player 2", 7);
        map4.put("player 3", 6);

        Map<Integer,StudentList> map5 = new HashMap<>();
        map5.put(1,stud);
        map5.put(2,stud2);
        map5.put(3,stud2);

        ReducedIsland r1 = new ReducedIsland(1,stud,TowerType.BLACK,1,0);
        ReducedIsland r2 = new ReducedIsland(2,stud2,TowerType.BLACK,1,2);
        ReducedIsland r3 = new ReducedIsland(3,stud2,null,1,1);
        ReducedIsland r4 = new ReducedIsland(4,stud,TowerType.GREY,1,1);
        ReducedIsland r5 = new ReducedIsland(5,stud2,TowerType.WHITE,1,1);
        ReducedIsland r6 = new ReducedIsland(6,stud2,TowerType.WHITE,1,1);
        ReducedIsland r7 = new ReducedIsland(7,stud2,TowerType.WHITE,1,1);
        ReducedIsland r8 = new ReducedIsland(8,stud2,TowerType.WHITE,1,1);
        ReducedIsland r9 = new ReducedIsland(9,stud2,TowerType.WHITE,1,1);
        ReducedIsland r10 = new ReducedIsland(10,stud2,TowerType.WHITE,1,1);
        ReducedIsland r11 = new ReducedIsland(11,stud2,TowerType.WHITE,1,1);
        ReducedIsland r12 = new ReducedIsland(12,stud2,TowerType.WHITE,1,1);
        Collection<ReducedIsland> reducedIslands = new ArrayList<>(List.of(r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12));

        Table t = new Table(
                List.of(Assistant.CARD_1, Assistant.CARD_9),
                map0,
                //List.of(Assistant.CARD_1, Assistant.CARD_9),
                //List.of(Assistant.values()),
                map5,
                map,
                map,
                map2,
                map3,
                map4,
                map4,
                List.of("player 1", "player 2", "player 3"),
                reducedIslands
        );
        cli.setNextScreen(new PlanningPhaseScreen(cli,t));
        cli.run();
        //  <--- to here
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
            cli.setNextScreen(new HomeScreen(cli));
        } catch (IOException e) {
            System.out.println("Can't connect to server. Try again\n");
            cli.setNextScreen(new AskServerSpecificationScreen(cli));
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
     * Prompt the user to provide a nickname in order to enter a specified game
     * @param gameID the ID of the game he wants to enter
     */
    public void askNicknameToEnter(String gameID){
        cli.setNextScreen(new RequestNicknameScreen(cli, gameID));
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
     * Shows the ID of the games passed as a parameter.
     * @param gameIDs the listo of game ID to show
     */
    public void displayGames(Collection<String> gameIDs){
        cli.setNextScreen(new GamesListScreen(cli, gameIDs));
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
     * this method will display the planning phase screen
     * @param table the table to display
     */
    public void displayPlanningPhaseScreen(Table table){
        cli.setNextScreen(new PlanningPhaseScreen(cli,table));
    }

    /**
     * this method will display the move student phase screen
     * @param table the table to display
     */
    public void displayMoveStudentsScreen(Table table){
        cli.setNextScreen(new MoveStudentsPhaseScreen(cli,table));
    }

    /**
     * this method will display the move mother nature phase screen
     * @param table the table to display
     */
    public void displayMoveMotherNatureScreen(Table table){
        cli.setNextScreen(new MoveMotherNatureScreen(cli,table));
    }

    /**
     * this method will display the choose cloud phase screen
     * @param table the table to display
     */
    public void displayChooseCloudScreen(Table table){
        cli.setNextScreen(new MoveMotherNatureScreen(cli,table));
    }

    /**
     * this method will display the end game screen
     * @param table the table to display
     */
    public void displayEndGameScreen(Table table){
        cli.setNextScreen(new MoveMotherNatureScreen(cli,table));
    }

}
