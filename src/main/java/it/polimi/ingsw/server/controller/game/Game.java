package it.polimi.ingsw.server.controller.game;

import it.polimi.ingsw.client.reduced_model.ReducedCloud;
import it.polimi.ingsw.client.reduced_model.ReducedIsland;
import it.polimi.ingsw.client.reduced_model.ReducedModel;
import it.polimi.ingsw.client.reduced_model.ReducedPlayer;
import it.polimi.ingsw.server.controller.*;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.controller.game.expert.card_observers.CoinOnCardObserver;
import it.polimi.ingsw.server.controller.game.expert.card_observers.StudentsOnCardObserver;
import it.polimi.ingsw.server.controller.game.states.*;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.gametable.GameTable;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.exceptions.EmptyBagException;
import it.polimi.ingsw.server.model.utils.exceptions.IslandNotFoundException;
import it.polimi.ingsw.server.model.utils.exceptions.ReachedMaxStudentException;

import java.util.*;

/**
 *A class to handle the various states of the game.It can change the current state and can call operations on it.
 */
public class Game {
    /**
     * State in which the player is playing an assistant card
     */
    private final PlayAssistantState playAssistantState;
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
     * The number of turns that have been played in the current round
     */
    private int turnsPlayed = 0;
 
    /**
     * List of winners of the game. If the list has more than one player it is considered a draw
     */
    private Collection<String> winners = null;

    /**
     * The constructor of the class.
     * It takes in input a collection of players, and it will construct the class.
     * @param players the player in the game
     */
    public Game(Collection<PlayerLoginInfo> players) {

        // 1. CREATION OF THE GAME MODEL CLASS OF THE MODEL
        model = new GameModel(players);

        // 2. SET UP THE FSA OF THE CONTROLLER

        // 2.1 create the states of the game regarding the planning phase and the 3 step of the action phase
        playAssistantState = new PlayAssistantState(this);
        moveMotherNatureState = new MoveMotherNatureState(this);
        chooseCloudState = new ChooseCloudState(this);

        // 2.2 set the initial state of the game
        setState(playAssistantState);

        // 3. INITIALIZATION OF THE MODEL
        initializeModel(players.size());

    }

    /**
     * Creates a reduced version of the whole model providing only the information relevant
     * to a specific player
     * @param nickname the nickname of the player this model is for
     * @return a reduced version of the model
     */
    public ReducedModel getReducedModel(String nickname) {
        Collection<Assistant> deck =
                model.getPlayerList().stream().filter(player -> player.getNickname().equals(nickname))
                        .findFirst().map(Player::getHand).orElse(new ArrayList<>());
        Collection<Assistant> assistantsUsed = playAssistantState.getAssistantsPlayed();
        Collection<ReducedCloud> clouds = model.getGameTable().createReducedSetOfClouds();
        Collection<ReducedPlayer> players = model.createReducedPlayers();
        Collection<ReducedIsland> islands = model.getGameTable().createReducedSetOfIslands();
        return new ReducedModel(deck,assistantsUsed,clouds,players,
                islands, model.getGameTable().getMotherNaturePosition());
    }

    /**
     * This method will set up the model before starting the game.
     * @param numOfPlayers the number of players in the game
     */
    private void initializeModel(int numOfPlayers) {

        // *** 0. save in a variable frequently used game table class
        GameTable gameTable=model.getGameTable();

        // *** 1. set mother nature position in a random island
        int motherNaturePosition = new Random().nextInt(12);
        int idOppositeIsland = (motherNaturePosition + 6) % 12; // island opposite with respect to mother nature
        gameTable.moveMotherNature(motherNaturePosition);

        // *** 2. put 2 students of each color in the bag and put one of them (randomly taken from the bag)
        // on each island starting from the right of mother nature and proceeding clockwise
        // (do not place the student on the island in the opposite position to mother nature)

        // 2.1 put 2 students of each color in the bag
        StudentList initialStudents = new StudentList();
        initialStudents.setAllAs(2);
        gameTable.fillBag(initialStudents);

        // 2.2 put students on the islands.
        int numOfIteration=0;
        for(int i=motherNaturePosition; numOfIteration<12; i=(i+1)%12){
            numOfIteration++;
            if(i!=idOppositeIsland && i!=motherNaturePosition){
                try {
                    gameTable.addToIsland(gameTable.getStudentFromBag(),i);
                } catch (IslandNotFoundException | EmptyBagException e) {
                    e.printStackTrace(); // not possible
                }
            }
        }

        // *** 3.put the remaining students in the bag
        int MAX_NUM_OF_STUDENTS = 26;       // the max num of students in the game
        StudentList remainingStudents = new StudentList();
        remainingStudents.setAllAs(MAX_NUM_OF_STUDENTS - 2);
        gameTable.fillBag(remainingStudents);

        // *** 4. take a number of "maxStudentAtEntrance" random students from the bag and put them at the entrance of each player
        int maxStudentAtEntrance = numOfPlayers == 2 ? 7:9;
        for(Player player : model.getPlayerList()){
            for(int i=0;i<maxStudentAtEntrance;i++){
                try {
                    player.addStudentToEntrance(gameTable.getStudentFromBag());
                } catch (ReachedMaxStudentException | EmptyBagException e) {
                    e.printStackTrace(); // it is impossible
                }
            }
        }
        // *** 5. fill clouds 
        gameTable.fillClouds();
    }

    /**
     * Gets the nickname of the player that need to play now.
     * @return the nickname of the current player
     */
    public String getCurrentPlayerNickname() {
        return model.getCurrentPlayer().getNickname();
    }

    /**
     * Changes the current state of the game
     * @param newState new state of the game
     */
    public void setState(GameState newState){
        state = newState;
        notifyChangeCurrentStateObservers();
    }

    /**
     * Sets the {@code lastRoundFlag} to true
     */
    public void setLastRoundFlag(){ lastRoundFlag = true;}

    /**
     * Set the winners of the game
     * @param winners players that have won. If more than one is considered a draw
     */
    public void setWinner(Collection<String> winners){
        this.winners = winners;
        notifyEndOfGameObservers();
    }

    /**
     * Method to use an assistant card
     *
     * @param assistant is the assistant card to be played
     * @throws NotValidOperationException if this method has been invoked in a state in which
     *                                    this operation is not supported
     * @throws NotValidArgumentException  if has been passed an assistant card that cannot be used,
     *                                    or it is not present in the player's deck
     */
    public void useAssistant(Assistant assistant) throws NotValidOperationException, NotValidArgumentException {
        state.useAssistant(assistant);
    }


    /**
     * Method to move mother nature of a certain number of islands
     *
     * @param positions number of islands to move on mother nature
     * @throws NotValidOperationException if this method has been invoked in a state in which
     *                                    this operation is not supported
     * @throws NotValidArgumentException  if the position is not positive, or it is not
     *                                    compliant with the rules of the game
     */
    public void moveMotherNature(int positions) throws NotValidOperationException, NotValidArgumentException {
        state.moveMotherNature(positions);
    }

    /**
     * Method to get all the students from a chosen cloud and put them in the entrance
     *
     * @param cloudID ID of the cloud from which get the students
     * @throws NotValidOperationException if this method has been invoked in a state in which
     *                                    this operation is not supported
     * @throws NotValidArgumentException  if the cloud passed as a parameter is empty
     */
    public void takeFromCloud(int cloudID) throws NotValidOperationException, NotValidArgumentException {
        state.takeFromCloud(cloudID);
    }


    /**
     * This method allows to select a student (of the PawnType specified in the parameter) that comes from the position
     * (also specified in the parameters).
     * @param color the {@code PawnType} of the student
     * @param originPosition the {@code Position} from where take the student
     * @throws NotValidOperationException if the position is not the one that was supposed to be in the considered state
     * @throws NotValidArgumentException if the student is not present in the specified location
     */
    public void chooseStudentFromLocation(PawnType color, Position originPosition)throws NotValidOperationException, NotValidArgumentException{
        state.choseStudentFromLocation(color,originPosition);
    }

    /**
     * This method allows to choose a destination on which operate based on the state.
     * @param destination the Position
     * @throws NotValidOperationException if the position is not the one that was supposed to be in the considered state
     * @throws NotValidArgumentException if the
     */
    public void chooseDestination(Position destination)throws NotValidOperationException,NotValidArgumentException{
        state.chooseDestination(destination);
    }

    /**
     * Method to use a character card of the specified type
     * @param cardType type of the character card to use
     * @throws NotValidOperationException if the card is used in basic mode or the players hasn't
     *                                    enough money to use it or the current player has already used a card
     * @throws NotValidArgumentException if the card doesn't exist
     */
    public void useCharacterCard(CharacterCardsType cardType) throws NotValidOperationException, NotValidArgumentException {
        throw new NotValidOperationException();
    }


    /**
     * Does the reset operations at the end of every turn
     */
    public void endOfTurn(){
        turnsPlayed++;
        if (turnsPlayed == model.getPlayerList().size()){ // end of round
            if (lastRoundFlag){
                setState(new EndState(this));
                return;
            }
            turnsPlayed = 0;
            model.getGameTable().fillClouds();
            model.calculatePlanningPhaseOrder();
            setState(playAssistantState);
            return;
        }
        model.nextPlayerTurn();
        setState(new MoveStudentState(this, 0));
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

    public GameState getMoveMotherNatureState() {
        return moveMotherNatureState;
    }

    public GameState getChooseCloudState() {
        return chooseCloudState;
    }

    public Collection<String> getWinner(){return Collections.unmodifiableCollection(winners);}

    /**
     * Skips the turn of the current player, doing random choices when necessary
     */
    public void skipTurn() {
        state.skipTurn();
    }

    // MANAGEMENT OF OBSERVERS FOR END OF THE GAME
    /**
     * List of the observer on the end of the game
     */
    private final List<EndOfGameObserver> endOfGameObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on the end of the game.
     * @param observer the observer to be added
     */
    public void addEndOfGameObserver(EndOfGameObserver observer){
        endOfGameObservers.add(observer);
    }

    /**
     * This method notify all the attached observers that the game has ended.
     */
    private void notifyEndOfGameObservers(){
        for(EndOfGameObserver observer : endOfGameObservers)
            observer.endOfGameObserverUpdate(getWinner());
    }

    // MANAGEMENT OF OBSERVERS FOR STATE SWITCH
    /**
     * List of the observer on the current state
     */
    private final List<ChangeCurrentStateObserver> changeCurrentStateObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on current state.
     * @param observer the observer to be added
     */
    public void addChangeCurrentStateObserver(ChangeCurrentStateObserver observer){
        changeCurrentStateObservers.add(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on current state.
     */
    private void notifyChangeCurrentStateObservers(){
        for(ChangeCurrentStateObserver observer : changeCurrentStateObservers)
            observer.changeCurrentStateObserverUpdate(this.state.getType());
    }

    // MANAGEMENT OF OBSERVERS FOR GAME CREATED
    /**
     * List of the observer on the game creation
     */
    private final List<GameCreatedObserver> gameCreatedObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on game creation.
     * @param observer the observer to be added
     */
    public void addGameCreatedObserver(GameCreatedObserver observer){
        gameCreatedObservers.add(observer);
    }

    public void askGameUpdate(String nickname){
        notifyGameCreatedObservers(nickname, getReducedModel(nickname));
        notifyChangeCurrentStateObservers();
    }
    /**
     * This method notify all the attached observers that a change has been happened on current state.
     * @param table the table of the game just created
     */
    private void notifyGameCreatedObservers(String nickname, ReducedModel table){
        for(GameCreatedObserver observer : gameCreatedObservers)
            observer.gameCreatedObserverUpdate(nickname, table);
    }

    // METHODS TO ALLOW ATTACHING AND DETACHING OF OBSERVERS ON CHARACTER CARDS IF ANY

    /**
     * Does nothing in basic mode since there are no character cards.
     * @param observer the observer to be added if it's expert mode
     */
    public void addStudentsOnCardObserver(StudentsOnCardObserver observer){}

    /**
     * Does nothing in basic mode since there are no character cards.
     * @param observer the observer to be added if it's expert mode
     */
    public void addCoinOnCardObserver(CoinOnCardObserver observer){}

}
