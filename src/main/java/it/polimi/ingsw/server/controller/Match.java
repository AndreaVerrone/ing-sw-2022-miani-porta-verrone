package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.client.reduced_model.ReducedPlayerLoginInfo;
import it.polimi.ingsw.client.reduced_model.ReducedModel;
import it.polimi.ingsw.network.VirtualView;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.game.Game;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.controller.game.states.EndState;
import it.polimi.ingsw.server.controller.matchmaking.MatchMaking;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.gametable.GameTable;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.*;

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
    private final Map<String, VirtualView> playersView = new HashMap<>();

    /**
     * Creates a new Match for the number of player specified using the expert rules if {@code wantExpert}
     * is {@code true}, or using the normal rules otherwise.
     *
     * @param numOfPlayers the number of players requested in this match
     * @param wantExpert   {@code true} if the expert rules must be used, {@code false} otherwise
     */
    public Match(int numOfPlayers, boolean wantExpert) {
        matchMaking = new MatchMaking(numOfPlayers, wantExpert);

        //ADD OBSERVER TO MATCHMAKING
        matchMaking.addChangeCurrentPlayerObserver(this);
        matchMaking.addNumberOfPlayersObserver(this);
        matchMaking.addPlayersChangedObserver(this);
        matchMaking.addChangeCurrentStateObserver(this);
    }

    /**
     * Subscribe the view of a client to be notified of changes in the game.
     * If the client already was subscribed to this, the method actually do nothing.
     * @param nickname the nickname of the user
     * @param client the view of the client to add
     */
    public void addClient(String nickname, VirtualView client){
        synchronized (playersView) {
            playersView.put(nickname, client);
        }
    }

    /**
     * Unsubscribe the view of a client to not receive any more update from this game.
     * @param client the view of the client to remove
     */
    public void removeClient(String client){
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
     * This method will return the current state.
     * It can be either a state of the game or a state of the creation of it.
     * @return the current state
     */
    private StateType getCurrentState(){
        if(game!=null){
            return game.getState().getType();
        }
        return matchMaking.getState().getType();
    }

    /**
     * A method used to send all the information of this match.
     * This is useful when a user resumes a game he was playing
     * @param nickname the nickname of the user to notify
     */
    public void sendResumeInformation(String nickname) {
        VirtualView view;
        synchronized (playersView) {
            view = playersView.get(nickname);
        }
        synchronized (this) {
            if (matchMaking != null)
                notifyGameEntered(view);
            else
                view.gameCreated(game.getReducedModel(nickname));
            view.currentPlayerOrStateChanged(getCurrentState(), getCurrentPlayerNickname());
        }
    }


    /**
     * Notifies the requested client that he entered this match
     * @param view the view associated to the client to notify
     */
    public void notifyGameEntered(VirtualView view) {
        Collection<ReducedPlayerLoginInfo> players;
        int numPlayers;
        boolean isExpert;
        synchronized (this) {
            players = matchMaking.getPlayers().stream()
                    .map(PlayerLoginInfo::reduce).toList();
            numPlayers = matchMaking.getNumPlayers();
            isExpert = matchMaking.isHardMode();
        }
        view.createMatchmakingView(players, numPlayers, isExpert, getCurrentPlayerNickname());
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
            addObserversToPlayer(nickname);
        }
    }

    /**
     * Removes the player with the provided nickname from this game.
     * @param nickname the nickname of the player to remove
     */
    public void removePlayer(String nickname) {
        synchronized (this) {
            if (matchMaking != null)
                try {
                    matchMaking.removePlayer(nickname);
                    if (matchMaking.getPlayers().isEmpty())
                        Server.getInstance().deleteGame(this);
                    return;
                } catch (NotValidArgumentException | NotValidOperationException ignore) {}
        }
        synchronized (playersView) {
            playersView.remove(nickname);
            for (VirtualView view : playersView.values())
                view.notifyPlayerLeftGame(nickname);
        }
    }

    /**
     * Method to add observers to a player just added
     * @param playerNickname nickname of the player just added
     */
    private void addObserversToPlayer(String playerNickname){
        for(PlayerLoginInfo player: matchMaking.getPlayers()){
            if(player.getNickname().equals(playerNickname)){
                player.addTowerSelectedObserver(this);
                player.addWizardSelectedObserver(this);
            }
        }
    }

    /**
     * Method to remove the observers from a player removed from the game
     * @param playerNickname nickname of the player removed
     */
    private void removeObserversFromPlayer(String playerNickname){
        for(PlayerLoginInfo player: matchMaking.getPlayers()){
            if(player.getNickname().equals(playerNickname)){
                player.removeTowerSelectedObserver(this);
                player.removeWizardSelectedObserver(this);
            }
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
            addObserverToGame();
            for (String nickname : playersView.keySet())
                game.askGameUpdate(nickname);
        }
    }

    /**
     * Method to add all the observers to game, both in basic and expert mode
     */
    private void addObserverToGame(){
        game.addChangeCurrentStateObserver(this);
        game.addGameCreatedObserver(this);
        game.addStudentsOnCardObserver(this);
        game.addCoinOnCardObserver(this);
        game.addEndOfGameObserver(this);

        GameModel model = game.getModel();
        model.addChangeCurrentPlayerObserver(this);
        model.addEmptyStudentBagObserver(this);
        model.addChangeCoinNumberInBagObserver(this);

        GameTable gameTable = model.getGameTable();
        gameTable.addMotherNaturePositionObserver(this);
        gameTable.addStudentsOnCloudObserver(this);
        gameTable.addIslandNumberObserver(this);
        gameTable.addStudentsOnIslandObserver(this);
        gameTable.addTowerOnIslandObserver(this);
        gameTable.addBanOnIslandObserver(this);
        gameTable.addUnificationIslandObserver(this);

        for(Player player: model.getPlayerList()){
            player.addChangeAssistantDeckObserver(this);
            player.addChangeCoinNumberObserver(this);
            player.addProfessorObserver(this);
            player.addChangeTowerNumberObserver(this);
            player.addLastAssistantUsedObserver(this);
            player.addStudentsInDiningRoomObserver(this);
            player.addStudentsOnEntranceObserver(this);
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
        for(VirtualView playerView: playersView.values()){
            playerView.currentPlayerOrStateChanged(stateType, getCurrentPlayerNickname());
        }
    }

    @Override
    public void requestChoosePlayerParameter(
            Collection<TowerType> towersAvailable, Collection<Wizard> wizardsAvailable) {
        for (VirtualView playerView : playersView.values())
            playerView.choosePlayerParameter(towersAvailable, wizardsAvailable);
    }

    @Override
    public void coinOnCardObserverUpdate(CharacterCardsType characterCardsType) {
        for(VirtualView playerView: playersView.values()){
            playerView.coinOnCardAdded(characterCardsType);
        }
    }

    @Override
    public void studentsOnCardObserverUpdate(CharacterCardsType characterCardType, StudentList actualStudents) {
        for(VirtualView playerView: playersView.values()){
            playerView.studentsOnCardChanged(characterCardType, actualStudents);
        }
    }

    @Override
    public void numberOfPlayersObserverUpdate(int numberOfPlayers) {
        for(VirtualView playerView: playersView.values()){
            playerView.numberOfPlayersChanged(numberOfPlayers);
        }
    }

    @Override
    public void playersChangedObserverUpdate(Collection<PlayerLoginInfo> players) {
        Collection<ReducedPlayerLoginInfo> reducedPlayers = players.stream()
                .map(PlayerLoginInfo::reduce).toList();
        for(VirtualView playerView: playersView.values()){
            playerView.playersChanged(reducedPlayers);
        }
    }

    @Override
    public void towerSelectedObserverUpdate(String player, TowerType tower) {
        for(VirtualView playerView: playersView.values()){
            playerView.towerSelected(player, tower);
        }
    }

    @Override
    public void wizardSelectedObserverUpdate(String player, Wizard wizard) {
        for(VirtualView playerView: playersView.values()){
            playerView.wizardSelected(player, wizard);
        }
    }

    @Override
    public void banOnIslandObserverUpdate(int islandIDWithBan, int actualNumOfBans) {
        for(VirtualView playerView: playersView.values()){
            playerView.numberOfBansOnIslandChanged(islandIDWithBan, actualNumOfBans);
        }
    }

    @Override
    public void changeAssistantDeckObserverUpdate(String nickName, Collection<Assistant> actualDeck) {

        playersView.get(nickName).assistantDeckChanged(nickName, actualDeck);


        // check condition of last round : if the player finishes the card, then set last round flag
        if(actualDeck.isEmpty()){
            game.setLastRoundFlag();
            for(VirtualView playerView: playersView.values()){
                playerView.notifyLastRound();
            }
        }
    }

    @Override
    public void changeCoinNumberInBagObserverUpdate(int actualNumOfCoins) {
        for(VirtualView playerView: playersView.values()){
            playerView.coinNumberInBagChanged(actualNumOfCoins);
        }
    }

    @Override
    public void changeCoinNumberObserverUpdate(String nickNameOfPlayer, int actualNumOfCoins) {
        for(VirtualView playerView: playersView.values()){
            playerView.coinNumberOfPlayerChanged(nickNameOfPlayer, actualNumOfCoins);
        }
    }

    @Override
    public void changeCurrentPlayerObserverUpdate(String actualCurrentPlayerNickname) {
        for(VirtualView playerView: playersView.values()){
            playerView.currentPlayerOrStateChanged(getCurrentState(),actualCurrentPlayerNickname);
        }
    }

    @Override
    public void changeTowerNumberUpdate(String nickName, int numOfActualTowers) {
        for(VirtualView playerView: playersView.values()){
            playerView.towerNumberOfPlayerChanged(nickName, numOfActualTowers);
        }

        // check condition of end of the game
        if(numOfActualTowers==0){
            game.setState(new EndState(game));
        }
    }

    @Override
    public void emptyStudentBagObserverUpdate() {
        // set the last round flag
        game.setLastRoundFlag();

        for(VirtualView playerView: playersView.values()){
            playerView.notifyLastRound();
        }
    }

    @Override
    public void islandNumberObserverUpdate(int actualNumOfIslands) {

        // check condition of end of the game
        if(actualNumOfIslands==3){
            game.setState(new EndState(game));
        }

        // todo: maybe this is not needed
        for(VirtualView playerView: playersView.values()){
            playerView.islandNumberChanged(actualNumOfIslands);
        }
    }

    @Override
    public void islandUnificationObserverUpdate(int islandID, int islandRemovedID, int finalSize) {
        for(VirtualView playerView: playersView.values()){
            playerView.islandsUnified(islandID, islandRemovedID, finalSize);
        }
    }

    @Override
    public void lastAssistantUsedObserverUpdate(String nickName, Assistant actualLastAssistant) {
        for(VirtualView playerView: playersView.values()){
            playerView.lastAssistantUsedChanged(nickName, actualLastAssistant);
        }
    }

    @Override
    public void motherNaturePositionObserverUpdate(int actualMotherNaturePosition) {
        for(VirtualView playerView: playersView.values()){
            playerView.motherNaturePositionChanged(actualMotherNaturePosition);
        }
    }

    @Override
    public void professorObserverUpdate(String nickName, Collection<PawnType> actualProfessors) {
        for(VirtualView playerView: playersView.values()){
            playerView.professorsOfPlayerChanged(nickName, actualProfessors);
        }
    }

    @Override
    public void studentsInDiningRoomObserverUpdate(String nickname, StudentList actualStudents) {
        for(VirtualView playerView: playersView.values()){
            playerView.studentsInDiningRoomChanged(nickname, actualStudents);
        }
    }

    @Override
    public void studentsOnCloudObserverUpdate(int cloudID, StudentList actualStudentList) {
        for(VirtualView playerView: playersView.values()){
            playerView.studentsOnCloudChanged(cloudID, actualStudentList);
        }
    }

    @Override
    public void studentsOnEntranceObserverUpdate(String nickname, StudentList actualStudents) {
        for(VirtualView playerView: playersView.values()){
            playerView.studentsOnEntranceChanged(nickname, actualStudents);
        }
    }

    @Override
    public void studentsOnIslandObserverUpdate(int islandID, StudentList actualStudents) {
        for(VirtualView playerView: playersView.values()){
            playerView.studentsOnIslandChanged(islandID, actualStudents);
        }
    }

    @Override
    public void towerOnIslandObserverUpdate(int islandIDWithChange, TowerType actualTower) {
        for(VirtualView playerView: playersView.values()){
            playerView.towerOnIslandChanged(islandIDWithChange, actualTower);
        }
    }

    @Override
    public void endOfGameObserverUpdate(Collection<String> winners){
        for(VirtualView playerView: playersView.values()){
            playerView.gameEnded(winners);
        }
    }

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param table the table of the game
     */
    @Override
    public void gameCreatedObserverUpdate(String nickname, ReducedModel table) {
        playersView.get(nickname).gameCreated(table);
    }
}
