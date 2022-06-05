package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.VirtualView;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.game.Game;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.controller.matchmaking.MatchMaking;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * A class used as a common interface for the Matchmaking and Game
 */
public class Match implements ObserversCommonInterface{

    /**
     * The Matchmaking of this match. After the game has started this will be null.
     */
    private MatchMaking matchMaking;

    /**
     * The game of this match. Before the game starts, this is null.
     */
    private Game game;

    /**
     * The views of the player in this match. All of this should be notified
     * when something in the match changes
     */
    private final Collection<VirtualView> playersView = new ArrayList<>();

    /**
     * Creates a new Match for the number of player specified using the expert rules if {@code wantExpert}
     * is {@code true}, or using the normal rules otherwise.
     *
     * @param numOfPlayers the number of players requested in this match
     * @param wantExpert   {@code true} if the expert rules must be used, {@code false} otherwise
     */
    public Match(int numOfPlayers, boolean wantExpert) {
        matchMaking = new MatchMaking(numOfPlayers, wantExpert);
    }

    /**
     * Subscribe the view of a client to be notified of changes in the game.
     * If the client already was subscribed to this, the method actually do nothing.
     * @param client the view of the client to add
     */
    public void addClient(VirtualView client){
        synchronized (playersView) {
            if (playersView.contains(client))
                return;
            playersView.add(client);
        }
    }

    /**
     * Unsubscribe the view of a client to not receive any more update from this game.
     * @param client the view of the client to remove
     */
    public void removeClient(VirtualView client){
        synchronized (playersView) {
            playersView.remove(client);
        }
    }

    /**
     * Gets the nickname of the player that need to play now.
     * @return the nickname of the current player
     */
    public String getCurrentPlayerNickname(){
        if (matchMaking != null)
            return matchMaking.getCurrentPlayer().getNickname();
        return game.getCurrentPlayerNickname();
    }

    /**
     * Sets if the game it's been creating need to use expert rules.
     * @param isHardMode {@code true} if expert rules are required, {@code false} otherwise
     * @throws NotValidOperationException if the game has started
     */
    public void setHardMode(boolean isHardMode) throws NotValidOperationException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        matchMaking.setHardMode(isHardMode);
    }

    /**
     * Change the number of players needed in this game. This cannot be less than the player already
     * present in this lobby.
     * @param value the new number of players
     * @throws NotValidArgumentException if the selected number of players is not valid (i.e. if it's not
     * one of the value supported or less than the player already present in this lobby)
     * @throws NotValidOperationException if the number of player can't be changed in the current state
     */
    public void changeNumOfPlayers(int value) throws NotValidOperationException, NotValidArgumentException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        matchMaking.changeNumOfPlayers(value);
    }

    /**
     * Adds a player with the provided nickname to this lobby. The nickname must be unique.
     * @param nickname the nickname chosen by the player
     * @throws NotValidArgumentException if the nickname is already taken
     * @throws NotValidOperationException if a new player can't be added in the current state
     */
    public void addPlayer(String nickname) throws NotValidOperationException, NotValidArgumentException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        synchronized (this) {
            matchMaking.addPlayer(nickname);
        }
    }

    /**
     * Removes the player with the provided nickname from this lobby.
     * @param nickname the nickname of the player to remove
     * @throws NotValidArgumentException if there is no player with the provided nickname
     * @throws NotValidOperationException if a player can't leave the game in the current state
     */
    public void removePlayer(String nickname) throws NotValidOperationException, NotValidArgumentException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        synchronized (this) {
            matchMaking.removePlayer(nickname);
            if (matchMaking.getPlayers().isEmpty())
                Server.getInstance().deleteGame(this);
        }
    }

    /**
     * Sets the tower type of the current player in the queue.
     * @param towerType the type of tower to assign
     * @throws NotValidArgumentException if the tower selected is not available
     * @throws NotValidOperationException if the tower of the player can't be changed in the current state
     */
    public void setTowerOfPlayer(TowerType towerType) throws NotValidOperationException, NotValidArgumentException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        matchMaking.setTowerOfPlayer(towerType);
    }

    /**
     * Sets the wizard of the current player in the queue.
     * @param wizard the wizard to assign
     * @throws NotValidArgumentException if the wizard selected is not available
     * @throws NotValidOperationException if the wizard of the player can't be changed in the current state
     */
    public void setWizardOfPlayer(Wizard wizard) throws NotValidOperationException, NotValidArgumentException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        matchMaking.setWizardOfPlayer(wizard);
    }

    /**
     * Moves the match making to the next state. It also returns the new game created, if any.
     * @throws NotValidOperationException if the state can't be changed (i.e. not all the expected operations
     * of the current state were done)
     */
    public void next() throws NotValidOperationException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        Optional<Game> possibleGame = matchMaking.next();
        if (possibleGame.isPresent()){
            matchMaking = null;
            game = possibleGame.get();
        }
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
        if (game == null)
            throw new NotValidOperationException();
        game.useAssistant(assistant);
    }

    /**
     * This method allows to select a student (of the PawnType specified in the parameter) that comes from the position
     * (also specified in the parameters).
     * @param color the {@code PawnType} of the student
     * @param originPosition the {@code Position} from where take the student
     * @throws NotValidOperationException if the position is not the one that was supposed to be in the considered state
     * @throws NotValidArgumentException if the student is not present in the specified location
     */
    public void chooseStudentFromLocation(PawnType color, Position originPosition) throws NotValidOperationException, NotValidArgumentException {
        if (game == null)
            throw new NotValidOperationException();
        game.chooseStudentFromLocation(color, originPosition);
    }

    /**
     * This method allows to choose a destination on which operate based on the state.
     * @param destination the Position
     * @throws NotValidOperationException if the position is not the one that was supposed to be in the considered state
     * @throws NotValidArgumentException if the
     */
    public void chooseDestination(Position destination) throws NotValidOperationException, NotValidArgumentException {
        if (game == null)
            throw new NotValidOperationException();
        game.chooseDestination(destination);
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
        if (game == null)
            throw new NotValidOperationException();
        game.moveMotherNature(positions);
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
        if (game == null)
            throw new NotValidOperationException();
        game.takeFromCloud(cloudID);
    }

    /**
     * Skips the turn of the current player, doing random choices when necessary
     */
    public void skipTurn() {
        if (game != null)
            game.skipTurn();
    }

    /**
     * Method to use a character card of the specified type
     * @param cardType type of the character card to use
     * @throws NotValidOperationException if the card is used in basic mode or the players hasn't
     *                                    enough money to use it or the current player has already used a card
     * @throws NotValidArgumentException if the card doesn't exist
     */
    public void useCharacterCard(CharacterCardsType cardType) throws NotValidOperationException, NotValidArgumentException {
        if (game == null)
            throw new NotValidOperationException();
        game.useCharacterCard(cardType);
    }

    @Override
    public void changeCurrentStateObserverUpdate(StateType stateType) {

    }

    @Override
    public void coinOnCardObserverUpdate(CharacterCardsType characterCardsType, boolean coinOnCard) {

    }

    @Override
    public void studentsOnCardObserverUpdate(CharacterCardsType characterCardType, StudentList actualStudents) {

    }

    @Override
    public void numberOfPlayersUpdate(int numberOfPlayers) {

    }

    @Override
    public void playersChangedObserverUpdate(Collection<PlayerLoginInfo> players) {

    }

    @Override
    public void towerSelectedObserverUpdate(String player, TowerType tower) {

    }

    @Override
    public void wizardSelectedObserverUpdate(String player, Wizard wizard) {

    }

    @Override
    public void banOnIslandObserverUpdate(int islandIDWithBan, int actualNumOfBans) {

    }

    @Override
    public void changeAssistantDeckObserverUpdate(String nickName, Collection<Assistant> actualDeck) {

    }

    @Override
    public void changeCoinNumberInBagObserverUpdate(int actualNumOfCoins) {

    }

    @Override
    public void changeCoinNumberObserverUpdate(String nickNameOfPlayer, int actualNumOfCoins) {

    }

    @Override
    public void changeCurrentPlayerObserverUpdate(String actualCurrentPlayerNickname) {

    }

    @Override
    public void changeTowerNumberUpdate(String nickName, int numOfActualTowers) {

    }

    @Override
    public void conquerIslandObserverUpdate() {

    }

    @Override
    public void emptyStudentBagObserverUpdate() {

    }

    @Override
    public void islandNumberObserverUpdate(int actualNumOfIslands) {

    }

    @Override
    public void islandUnificationObserverUpdate(int islandID, int islandRemovedID, int finalSize) {

    }

    @Override
    public void lastAssistantUsedObserverUpdate(String nickName, Assistant actualLastAssistant) {

    }

    @Override
    public void motherNaturePositionObserverUpdate(int actualMotherNaturePosition) {

    }

    @Override
    public void professorObserverUpdate(String nickName, Collection<PawnType> actualProfessors) {

    }

    @Override
    public void studentsInDiningRoomObserverUpdate(String nickname, StudentList actualStudents) {

    }

    @Override
    public void studentsOnCloudObserverUpdate(int cloudID, StudentList actualStudentList) {

    }

    @Override
    public void studentsOnEntranceObserverUpdate(String nickname, StudentList actualStudents) {

    }

    @Override
    public void studentsOnIslandObserverUpdate(int islandID, StudentList actualStudents) {

    }

    @Override
    public void towerOnIslandObserverUpdate(int islandIDWithChange, TowerType actualTower) {

    }
}
