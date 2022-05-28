package it.polimi.ingsw.server.controller.game;

import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.controller.game.states.*;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.gametable.GameTable;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.exceptions.EmptyBagException;
import it.polimi.ingsw.server.model.utils.exceptions.IslandNotFoundException;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import it.polimi.ingsw.server.model.utils.exceptions.ReachedMaxStudentException;

import java.util.*;


/**
 *A class to handle the various states of the game.It can change the current state and can call operations on it.
 */
public class Game implements IGame {
    /**
     * State in which the player is playing an assistant card
     */
    private final GameState playAssistantState;
    /**
     * State in which the player moves a student from his entrance to an island or his dining room
     */
    private final GameState moveStudentState;
    /**
     * State in which the player is moving mother nature
     */
    private final GameState moveMotherNatureState;
    /**
     * State in which the player is choosing the island from where gets the students
     */
    private final GameState chooseCloudState;
    /**
     * Current state of the game
     */
    private GameState state;
    /**
     * Model of the game
     */
    private final GameModel model;
    /**
     * If this flag is true the game is in its last round
     */
    private boolean lastRoundFlag = false;
 
    /**
     * List of winners of the game.If the list has more than one player it is considered a draw
     */
    private Collection<Player> winners = null;

    /**
     * This is the max number of students of each color
     */
    private final int MAX_NUM_OF_STUDENTS = 26;

    private final int maxStudentAtEntrance;

    /**
     * The constructor of the class.
     * It takes in input a collection of players and it will construct the class.
     * @param players the player in the game
     */
    public Game(Collection<PlayerLoginInfo> players){

        // CREATION OF THE GAME MODEL CLASS OF THE MODEL
        model = new GameModel(players);

        // SET UP THE FSA OF THE CONTROLLER

        // create the states of the game regarding the planning pahse
        // and the 3 step of the action phase
        playAssistantState = new PlayAssistantState(this);
        moveStudentState = new MoveStudentState(this);
        moveMotherNatureState = new MoveMotherNatureState(this);
        chooseCloudState = new ChooseCloudState(this);

        // set the initial state of the game
        state = playAssistantState;

        // INITIALIZATION OF THE MODEL

        // save in a variable frequently used classes
        // the game model
        GameModel gameModel = getModel();
        // the game table
        GameTable gameTable=gameModel.getGameTable();

        // 0. set up the number of max students at entrance
        int numOfPlayers = gameModel.getPlayerList().size();
        maxStudentAtEntrance = numOfPlayers == 2 ? 7:9;

        // *** 1. set mother nature position in a random island
        int numOfIslands = gameTable.getNumberOfIslands();
        Random random = new Random();
        // generate a random number between 0 (included) and numOfIsland (excluded)
        gameTable.moveMotherNature(random.nextInt(numOfIslands));

        // *** 2. put 2 students of each color in the bag and put one of them (randomly taken from the bag)
        // on each island starting from the right of mother nature and proceeding clockwise
        // (do not place the student on the island in the opposite position to mother nature)

        //  put 2 students of each color in the bag
        StudentList initialStudents = new StudentList();
        for (int i=0;i<PawnType.values().length;i++) {
            try {
                initialStudents.changeNumOf(PawnType.values()[i], 2);
            } catch (NotEnoughStudentException e) {
                // not possible
                e.printStackTrace();
            }
        }
        gameTable.fillBag(initialStudents);

        // put students on the islands.
        int motherNaturePosition = gameTable.getMotherNaturePosition();
        int idOppositeIsland = (motherNaturePosition + 6) % 12; // island opposite with respect to mother nature

        int numOfIteration=0;
        for(int i=motherNaturePosition; numOfIteration<12; i=(i+1)%12){
            numOfIteration++;
            if(i!=idOppositeIsland && i!=motherNaturePosition){
                try {
                    gameTable.addToIsland(gameTable.getStudentFromBag(),i);
                } catch (IslandNotFoundException | EmptyBagException e) {
                    e.printStackTrace();
                    // not possible
                    // todo: how to manage
                }
            }
        }

        // *** 3.put the remaining students in the bag

        int remainingStudentsOfEachColor = MAX_NUM_OF_STUDENTS - 2;

        StudentList remainingStudents = new StudentList();
        for (int i=0;i<PawnType.values().length;i++) {
            try {
                remainingStudents.changeNumOf(PawnType.values()[i], remainingStudentsOfEachColor);
            } catch (NotEnoughStudentException e) {
                // not possible
                e.printStackTrace();
            }
        }
        gameTable.fillBag(remainingStudents);

        // *** 4. take a number of "maxStudentAtEntrance" random students from the bag and put them at the entrance of each player

        for(Player player : gameModel.getPlayerList()){
            for(int i=0;i<maxStudentAtEntrance;i++){
                try {
                    player.addStudentToEntrance(gameTable.getStudentFromBag());
                } catch (ReachedMaxStudentException | EmptyBagException e) {
                    // todo: how to manage ?
                    // it is impossible
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public String getCurrentPlayerNickname() {
        return model.getCurrentPlayer().getNickname();
    }

    /**
     * Changes the current state of the game
     * @param newState new state of the game
     */
    public void setState(GameState newState){
        state = newState;
    }

    /**
     * Sets the {@code lastRoundFlag} to true
     */
    public void setLastRoundFlag(){ lastRoundFlag = true;}

    /**
     * Set the winners of the game
     * @param winners players that have won. If more than one is considered a draw
     */
    public void setWinner(Collection<Player> winners){
        this.winners = winners;
        //TODO: update observer
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void useAssistant(Assistant assistant) throws NotValidOperationException, NotValidArgumentException {
        state.useAssistant(assistant);
    }


    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void moveMotherNature(int positions) throws NotValidOperationException, NotValidArgumentException {
        state.moveMotherNature(positions);
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void takeFromCloud(int cloudID) throws NotValidOperationException, NotValidArgumentException {
        state.takeFromCloud(cloudID);
    }


    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void chooseStudentFromLocation(PawnType color, Position originPosition)throws NotValidOperationException, NotValidArgumentException{
        state.choseStudentFromLocation(color,originPosition);
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void chooseDestination(Position destination)throws NotValidOperationException,NotValidArgumentException{
        state.chooseDestination(destination);
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void useCharacterCard(CharacterCardsType cardType) throws NotValidOperationException, NotValidArgumentException {
        throw new NotValidOperationException("Cannot use cards in basic mode!");
    }


    /**
     * Does the reset operations at the end of every turn
     */
    public void endOfTurn(){
        //Nothing to do in basic mode
    }

    public GameModel getModel() {
        return model;
    }

    public boolean getLastRoundFlag(){ return lastRoundFlag;}

    public GameState getState() {
        return state;
    }

    public GameState getPlayAssistantState() {
        return playAssistantState;
    }

    public GameState getMoveStudentState() {
        return moveStudentState;
    }

    public GameState getMoveMotherNatureState() {
        return moveMotherNatureState;
    }

    public GameState getChooseCloudState() {
        return chooseCloudState;
    }

    public Collection<Player> getWinner(){return Collections.unmodifiableCollection(winners);}

}
