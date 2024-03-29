package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.client.reduced_model.ReducedModel;
import it.polimi.ingsw.client.reduced_model.ReducedPlayerLoginInfo;
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
import it.polimi.ingsw.server.observers.game.GameObserver;
import it.polimi.ingsw.server.observers.matchmaking.MatchmakingObserver;

import java.util.*;

/**
 * A class used as a common interface for the Matchmaking and Game
 */
public class Match implements GameObserver, MatchmakingObserver {

    /**
     * The Matchmaking of this match. After the game has started this will be null.
     */
    private MatchMaking matchMaking;

    /**
     * The game of this match. Before the game starts, this is null.
     */
    private Game game;

    /**
     * Represent if this match is for 2 or 3 players
     */
    private int playersInGame;

    /**
     * The views of the player in this match. All of this should be notified
     * when something in the match changes
     */
    private final Map<String, VirtualView> playersView = new HashMap<>();

    /**
     * A list of all players that are offline at the moment
     */
    private final Set<String> offlinePlayers = new HashSet<>();

    /**
     * Creates a new Match for the number of player specified using the expert rules if {@code wantExpert}
     * is {@code true}, or using the normal rules otherwise.
     *
     * @param numOfPlayers the number of players requested in this match
     * @param wantExpert   {@code true} if the expert rules must be used, {@code false} otherwise
     */
    public Match(int numOfPlayers, boolean wantExpert) {
        matchMaking = new MatchMaking(numOfPlayers, wantExpert);
        playersInGame = numOfPlayers;
        //ADD OBSERVER TO MATCHMAKING
        matchMaking.addMatchmakingObserver(this);
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

    public void setAsOffline(String nickname) {
        synchronized (offlinePlayers) {
            offlinePlayers.add(nickname);
        }
        if (nickname.equals(getCurrentPlayerNickname()))
            skipTurn();
    }

    public void setAsOnline(String nickname) {
        synchronized (offlinePlayers) {
            offlinePlayers.remove(nickname);
        }
    }

    /**
     * Skips the turn of the current player, doing random choices when necessary
     */
    private void skipTurn() {
        if (game != null) {
            game.skipTurn();
            return;
        }
        Optional<Game> possibleGame = matchMaking.skipTurn();
        possibleGame.ifPresent(this::setGame);
    }

    private void setGame(Game game) {
        matchMaking = null;
        this.game = game;
        game.addObservers(this);
        synchronized (playersView) {
            for (String nickname : playersView.keySet())
                game.askGameUpdate(nickname);
        }
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
        playersInGame = value;
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
     * Removes the player with the provided nickname from this game.
     * @param nickname the nickname of the player to remove
     */
    public void removePlayer(String nickname) {
        boolean exitedGracefully = false;
        synchronized (this) {
            if (matchMaking != null)
                try {
                    matchMaking.removePlayer(nickname);
                    exitedGracefully = true;
                } catch (NotValidArgumentException | NotValidOperationException ignore) {}
        }
        synchronized (playersView) {
            playersView.remove(nickname);
            if (playersView.isEmpty()) {
                Server.getInstance().deleteGame(this);
                return;
            }
            if (exitedGracefully)
                return;
            for (VirtualView view : playersView.values())
                view.notifyPlayerLeftGame(nickname);
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
        Server.getInstance().makeGameUnavailable(this);
        possibleGame.ifPresent(this::setGame);
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
        boolean isOffline;
        synchronized (offlinePlayers) {
            if (offlinePlayers.size() == playersInGame)
                return;
            isOffline = offlinePlayers.contains(getCurrentPlayerNickname());
        }
        if (isOffline) {
            skipTurn();
            return;
        }
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.currentPlayerOrStateChanged(stateType, getCurrentPlayerNickname());
            }
        }
    }

    @Override
    public void requestChoosePlayerParameter(
            Collection<TowerType> towersAvailable, Collection<Wizard> wizardsAvailable) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values())
                playerView.choosePlayerParameter(towersAvailable, wizardsAvailable);
        }
    }

    @Override
    public void coinOnCardObserverUpdate(CharacterCardsType characterCardsType) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.coinOnCardAdded(characterCardsType);
            }
        }
    }

    @Override
    public void studentsOnCardObserverUpdate(CharacterCardsType characterCardType, StudentList actualStudents) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.studentsOnCardChanged(characterCardType, actualStudents);
            }
        }
    }

    @Override
    public void numberOfPlayersObserverUpdate(int numberOfPlayers) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.numberOfPlayersChanged(numberOfPlayers);
            }
        }
    }

    @Override
    public void playersChangedObserverUpdate(Collection<PlayerLoginInfo> players) {
        Collection<ReducedPlayerLoginInfo> reducedPlayers = players.stream()
                .map(PlayerLoginInfo::reduce).toList();
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.playersChanged(reducedPlayers);
            }
        }
    }

    @Override
    public void towerSelectedObserverUpdate(String player, TowerType tower) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.towerSelected(player, tower);
            }
        }
    }

    @Override
    public void wizardSelectedObserverUpdate(String player, Wizard wizard) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.wizardSelected(player, wizard);
            }
        }
    }

    @Override
    public void banOnIslandObserverUpdate(int islandIDWithBan, int actualNumOfBans) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.numberOfBansOnIslandChanged(islandIDWithBan, actualNumOfBans);
            }
        }
    }

    @Override
    public void changeAssistantDeckObserverUpdate(String nickName, Collection<Assistant> actualDeck) {
        synchronized (playersView) {
            try {
                playersView.get(nickName).assistantDeckChanged(nickName, actualDeck);
            }catch (NullPointerException ignore) {}
        }
    }

    @Override
    public void changeCoinNumberInBagObserverUpdate(int actualNumOfCoins) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.coinNumberInBagChanged(actualNumOfCoins);
            }
        }
    }

    @Override
    public void changeCoinNumberObserverUpdate(String nickNameOfPlayer, int actualNumOfCoins) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.coinNumberOfPlayerChanged(nickNameOfPlayer, actualNumOfCoins);
            }
        }
    }

    @Override
    public void changeCurrentPlayerObserverUpdate(String actualCurrentPlayerNickname) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.currentPlayerOrStateChanged(getCurrentState(), actualCurrentPlayerNickname);
            }
        }
    }

    @Override
    public void changeTowerNumberUpdate(String nickName, int numOfActualTowers) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.towerNumberOfPlayerChanged(nickName, numOfActualTowers);
            }
        }
    }

    @Override
    public void islandUnificationObserverUpdate(int islandID, int islandRemovedID, int sizeIslandRemoved) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.islandsUnified(islandID, islandRemovedID, sizeIslandRemoved);
            }
        }
    }

    @Override
    public void lastAssistantUsedObserverUpdate(String nickName, Assistant actualLastAssistant) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.lastAssistantUsedChanged(nickName, actualLastAssistant);
            }
        }
    }

    @Override
    public void motherNaturePositionObserverUpdate(int actualMotherNaturePosition) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.motherNaturePositionChanged(actualMotherNaturePosition);
            }
        }
    }

    @Override
    public void professorObserverUpdate(String nickname, Collection<PawnType> actualProfessors) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.professorsOfPlayerChanged(nickname, actualProfessors);
            }
        }
    }

    @Override
    public void studentsInDiningRoomObserverUpdate(String nickname, StudentList actualStudents) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.studentsInDiningRoomChanged(nickname, actualStudents);
            }
        }
    }

    @Override
    public void studentsOnCloudObserverUpdate(int cloudID, StudentList actualStudentList) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.studentsOnCloudChanged(cloudID, actualStudentList);
            }
        }
    }

    @Override
    public void studentsOnEntranceObserverUpdate(String nickname, StudentList actualStudents) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.studentsOnEntranceChanged(nickname, actualStudents);
            }
        }
    }

    @Override
    public void studentsOnIslandObserverUpdate(int islandID, StudentList actualStudents) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.studentsOnIslandChanged(islandID, actualStudents);
            }
        }
    }

    @Override
    public void towerOnIslandObserverUpdate(int islandIDWithChange, TowerType actualTower) {
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.towerOnIslandChanged(islandIDWithChange, actualTower);
            }
        }
    }

    @Override
    public void endOfGameObserverUpdate(Collection<String> winners){
        synchronized (playersView) {
            for (VirtualView playerView : playersView.values()) {
                playerView.gameEnded(winners);
            }
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
        synchronized (playersView) {
            playersView.get(nickname).gameCreated(table);
        }
    }

    @Override
    public void notifyLastRound() {
        synchronized (playersView) {
            for (VirtualView view : playersView.values())
                view.notifyLastRound();
        }
    }
}
