package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Match;
import it.polimi.ingsw.controller.NotValidArgumentException;
import it.polimi.ingsw.controller.NotValidOperationException;
import it.polimi.ingsw.controller.Position;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.TowerType;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.network.NetworkSender;
import it.polimi.ingsw.network.User;
import it.polimi.ingsw.network.VirtualView;
import it.polimi.ingsw.network.messages.responses.ErrorCode;

import java.util.Collection;

/**
 * A class to handle session changes and action in game
 */
public class SessionController {

    /**
     * The virtual view associated with the client represented by this session controller.
     */
    private final VirtualView view;

    /**
     * The user associated with this session.
     *
     * @implNote This is initialized as a new user to handle the case were
     * the client does not succeed to send his identifier
     */
    private User user = new User();

    /**
     * The game associated with this session, if any.
     */
    private Match match;

    /**
     * The nickname chosen by the user to be used in the game.
     */
    private String nickname;

    /**
     * Creates a new session controller associated with the provided {@code NetworkSender}.
     *
     * @param sender the sender associated with this
     */
    protected SessionController(NetworkSender sender) {
        view = new NetworkView(sender);
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Unsubscribe the view of the client to not receive any more update the game he was in.
     */
    protected void detachFromGame(){
        match.removeClient(view);
    }

    /**
     * Checks if the player can be added to the chosen game, and if so it adds a new player.
     *
     * @param nickname the nickname chosen
     * @param gameID   the ID of the game chosen
     * @throws NotValidArgumentException  if there is no game with that ID or if the nickname is already taken.
     * @throws NotValidOperationException if the game has started or if a new player can't be added.
     * @apiNote Possible error codes:
     * <ul>
     *     <li>
     *         {@link ErrorCode#GAME_NOT_EXIST}: if there is no game with that ID
     *     </li>
     *     <li>
     *         {@link ErrorCode#NICKNAME_TAKEN}: if there is already a player with that nickname
     *     </li>
     *     <li>
     *         {@link ErrorCode#GAME_IS_FULL}: if the game already reached the maximum number of players supported
     *     </li>
     *     <li>
     *         {@link ErrorCode#GENERIC_INVALID_OPERATION}: if for any other reason the game can't be joined
     *     </li>
     * </ul>
     */
    public void enterGame(String nickname, String gameID)
            throws NotValidOperationException, NotValidArgumentException {
        Match match;
        try {
            match = Server.getInstance().getGame(gameID);
        } catch (NotValidArgumentException e) {
            throw new NotValidArgumentException(ErrorCode.GAME_NOT_EXIST);
        }
        match.addPlayer(nickname);
        match.addClient(view);

        this.match = match;
        this.nickname = nickname;

        Server.getInstance().addPlayer(user, match);
    }

    /**
     * Removes the player from the game. This is used in the matchmaking state to gracefully exit from a game.
     *
     * @param nickname the nickname of the player to remove
     * @throws NotValidArgumentException  if there is no player with the provided nickname
     * @throws NotValidOperationException if there is no game associated to this or if a player can't leave the game
     * @apiNote Possible error codes:
     * <ul>
     *      <li>
     *          {@link ErrorCode#GAME_NOT_EXIST}: if the client making the request is not in any game
     *      </li>
     *      <li>
     *          {@link ErrorCode#GENERIC_INVALID_ARGUMENT}: if there is no player with the nickname provided in the game
     *      </li>
     *      <li>
     *          {@link ErrorCode#GENERIC_INVALID_OPERATION}: if the player can't leave the game now
     *      </li>
     *  </ul>
     */
    public void exitFromGame(String nickname)
            throws NotValidOperationException, NotValidArgumentException {
        if (match == null)
            throw new NotValidOperationException(ErrorCode.GAME_NOT_EXIST);
        match.removePlayer(nickname);
        detachFromGame();
        Server.getInstance().removePlayer(user);
    }

    /**
     * Gets the game that the user was playing. This is used when a player disconnects
     * from a game and wants to resume it.
     *
     * @throws NotValidArgumentException if there is no game associated to that player
     * @apiNote Possible error codes:
     * <ul>
     *     <li>
     *         {@link ErrorCode#GENERIC_INVALID_ARGUMENT}: if there is no game associated to the user
     *     </li>
     * </ul>
     */
    public void resumeGame() throws NotValidArgumentException {
        match = Server.getInstance().resumeGame(user);
    }

    /**
     * Creates a new game for the selected number of players using the expert rules
     * if {@code wantExpert} is {@code true}.
     *
     * @param wantExpert   {@code true} if the client want to use expert rules, {@code false} otherwise
     * @param numOfPlayers the number of players chosen for the game
     * @return the unique ID of the created game
     */
    public String createNewGame(int numOfPlayers, boolean wantExpert) {
        Match newMatch = new Match(numOfPlayers, wantExpert);
        return Server.getInstance().addNewGame(newMatch);
    }

    /**
     * Gets an unmodifiable view of all the games present in the server that can be joined.
     *
     * @return the games ID in the server
     */
    public Collection<String> getGames() {
        return Server.getInstance().getGames();
    }

    /**
     * Forces the player to exit the game.
     */
    public void quitGame() {
        detachFromGame();
        Server.getInstance().removePlayer(user);
        match = null;
    }

    private void checkIfCanDo() throws NotValidOperationException {
        if (match == null)
            throw new NotValidOperationException(ErrorCode.GAME_NOT_EXIST);
        if (!match.getCurrentPlayerNickname().equals(nickname))
            throw new NotValidOperationException(ErrorCode.PLAYER_NOT_IN_TURN);
    }

    /**
     * Sets if the game need to use expert rules.
     *
     * @param isHardMode true if expert rules are required, false otherwise
     * @throws NotValidOperationException if the game has started
     * @apiNote Possible error codes:
     * <ul>
     *     <li>
     *         {@link ErrorCode#GAME_NOT_EXIST}: if the client making the request is not in any game
     *      </li>
     *      <li>
     *         {@link ErrorCode#GENERIC_INVALID_OPERATION}: if the rules can't be changed now
     *      </li>
     *  </ul>
     */
    public void setHardMode(boolean isHardMode) throws NotValidOperationException {
        if (match == null)
            throw new NotValidOperationException(ErrorCode.GAME_NOT_EXIST);
        match.setHardMode(isHardMode);
    }

    /**
     * Change the number of players needed in this game.
     * This cannot be less than the player already present in this lobby.
     *
     * @param value the new number of players
     * @throws NotValidOperationException if there is no game associated to this client or
     *                                    if the number of players can't be changed in the current state
     * @throws NotValidArgumentException  if the selected number of players is not valid
     *                                    (i.e. if it's not one of the value supported or
     *                                    less than the player already present in this lobby)
     * @apiNote Possible error codes:
     * <ul>
     *     <li>
     *          {@link ErrorCode#GAME_NOT_EXIST}: if the client making the request is not in any game
     *      </li>
     *      <li>
     *          {@link ErrorCode#NUMBER_PLAYERS_NOT_SUPPORTED}: if the selected number of players is not one of the
     *          supported options
     *      </li>
     *      <li>
     *          {@link ErrorCode#GENERIC_INVALID_ARGUMENT}: if the selected number of players is less than the
     *          actual players present in the game
     *      </li>
     *      <li>
     *          {@link ErrorCode#GENERIC_INVALID_OPERATION}: if the number of players can't be changed now
     *      </li>
     *  </ul>
     */
    public void changeNumOfPlayers(int value) throws NotValidOperationException, NotValidArgumentException {
        if (match == null)
            throw new NotValidOperationException(ErrorCode.GAME_NOT_EXIST);
        match.changeNumOfPlayers(value);
    }


    /**
     * Change the tower type of the player making the request.
     *
     * @param towerType the type of tower to assign
     * @throws NotValidOperationException if there is no game associated to this client,
     *                                    if it's not the turn of this client to choose the tower or
     *                                    if the tower of the players can't be changed now
     * @throws NotValidArgumentException  if the tower selected is not available
     * @apiNote Possible error codes:
     * <ul>
     *     <li>
     *          {@link ErrorCode#GAME_NOT_EXIST}: if the client making the request is not in any game
     *      </li>
     *      <li>
     *          {@link ErrorCode#GENERIC_INVALID_ARGUMENT}: if the selected tower is not available to be chosen
     *      </li>
     *      <li>
     *          {@link ErrorCode#PLAYER_NOT_IN_TURN}: if it's not the turn of this client to choose the tower
     *      </li>
     *      <li>
     *          {@link ErrorCode#GENERIC_INVALID_OPERATION}: if the tower of players can't be changed now
     *      </li>
     *  </ul>
     */
    public void setTowerOfPlayer(TowerType towerType) throws NotValidOperationException, NotValidArgumentException {
        checkIfCanDo();
        match.setTowerOfPlayer(towerType);
    }

    /**
     * Change the wizard of the player making the request.
     *
     * @param wizard the wizard to assign
     * @throws NotValidOperationException if there is no game associated to this client,
     *                                    if it's not the turn of this client to choose the wizard or
     *                                    if the wizard of the players can't be changed now
     * @throws NotValidArgumentException  if the wizard selected is not available
     * @apiNote Possible error codes:
     * <ul>
     *     <li>
     *          {@link ErrorCode#GAME_NOT_EXIST}: if the client making the request is not in any game
     *      </li>
     *      <li>
     *          {@link ErrorCode#GENERIC_INVALID_ARGUMENT}: if the selected wizard is not available to be chosen
     *      </li>
     *      <li>
     *          {@link ErrorCode#PLAYER_NOT_IN_TURN}: if it's not the turn of this client to choose the wizard
     *      </li>
     *      <li>
     *          {@link ErrorCode#GENERIC_INVALID_OPERATION}: if the wizard of players can't be changed now
     *      </li>
     *  </ul>
     */
    public void setWizardOfPlayer(Wizard wizard) throws NotValidOperationException, NotValidArgumentException {
        checkIfCanDo();
        match.setWizardOfPlayer(wizard);
    }

    /**
     * Moves the match making to the next state.
     *
     * @throws NotValidOperationException if there is no game associated to this client,
     *                                    if it's not the turn of this client to take a decision or
     *                                    if the matchmaking can't be moved to the next state
     *                                    (i.e. not all the expected operations of the current state were done)
     * @apiNote Possible error codes:
     * <ul>
     *     <li>
     *          {@link ErrorCode#GAME_NOT_EXIST}: if the client making the request is not in any game
     *      </li>
     *      <li>
     *          {@link ErrorCode#PLAYER_NOT_IN_TURN}: if it's not the turn of this client to take a decision
     *      </li>
     *      <li>
     *          {@link ErrorCode#GENERIC_INVALID_OPERATION}: if the matchmaking can't be moved to the next state
     *      </li>
     *  </ul>
     */
    public void next() throws NotValidOperationException {
        checkIfCanDo();
        match.next();
    }

    /**
     * Uses the selected assistant from the client.
     *
     * @param assistant the assistant to use
     * @throws NotValidOperationException if there is no game associated to this client,
     *                                    if it's not the turn of this client to choose the assistant or
     *                                    if the players can't use an assistant now
     * @throws NotValidArgumentException  if the assistant cannot be used, or it is not present in the player's deck
     * @apiNote Possible error codes:
     * <ul>
     *     <li>
     *          {@link ErrorCode#GAME_NOT_EXIST}: if the client making the request is not in any game
     *      </li>
     *      <li>
     *          {@link ErrorCode#ASSISTANT_NOT_EXIST}: if the selected assistant is not in the player's deck
     *      </li>
     *      <li>
     *          {@link ErrorCode#ASSISTANT_NOT_USABLE}: if the selected assistant can't be used
     *      </li>
     *      <li>
     *          {@link ErrorCode#PLAYER_NOT_IN_TURN}: if it's not the turn of this client to choose the wizard
     *      </li>
     *      <li>
     *          {@link ErrorCode#GENERIC_INVALID_OPERATION}: if the players can't use an assistant now
     *      </li>
     *  </ul>
     */
    public void useAssistant(Assistant assistant) throws NotValidOperationException, NotValidArgumentException {
        checkIfCanDo();
        match.useAssistant(assistant);
    }

    /**
     * Moves the passed student from the choosen location.
     *
     * @param student  the student to move
     * @param originPosition location from where the student has been taken
     * @throws NotValidOperationException if there is no game associated to this client,
     *                                    if it's not the turn of this client to move a student or
     *                                    if the players can't move a student now
     * @throws NotValidArgumentException  if the student to move is not present in the location
     * @apiNote Possible error codes:
     * <ul>
     *     <li>
     *          {@link ErrorCode#GAME_NOT_EXIST}: if the client making the request is not in any game
     *      </li>
     *      <li>
     *          {@link ErrorCode#STUDENT_NOT_PRESENT}: if the selected student is not in location choosen
     *      </li>
     *      <li>
     *          {@link ErrorCode#PLAYER_NOT_IN_TURN}: if it's not the turn of this client to move a student
     *      </li>
     *      <li>
     *          {@link ErrorCode#GENERIC_INVALID_OPERATION}: if the players can't move a student now
     *      </li>
     *  </ul>
     */
    public void chooseStudentFromLocation(PawnType student, Position originPosition) throws NotValidOperationException, NotValidArgumentException {
        checkIfCanDo();
        match.choseStudentFromLocation(student, originPosition);
    }

    /**
     * Choose a location on which operate
     *
     * @param destination location
     * @throws NotValidOperationException if there is no game associated to this client,
     *                                    if it's not the turn of this client to choose a destination or
     *                                    if the players can't move a student now
     * @throws NotValidArgumentException  if the destination doesn't exist or if the destination is already full of students
     * @apiNote Possible error codes:
     * <ul>
     *     <li>
     *          {@link ErrorCode#GAME_NOT_EXIST}: if the client making the request is not in any game
     *      </li>
     *      <li>
     *          {@link ErrorCode#DININGROOM_FULL}: if the location is a dining room full
     *      </li>
     *      <li>
     *          {@link ErrorCode#ISLAND_NOT_EXIST}: if the location is not existing island
     *      </li>
     *      <li>
     *          {@link ErrorCode#PLAYER_NOT_IN_TURN}: if it's not the turn of this client to move a student
     *      </li>
     *      <li>
     *          {@link ErrorCode#GENERIC_INVALID_OPERATION}: if the players can't move a student now
     *      </li>
     *  </ul>
     */
    public void chooseDestination(Position destination) throws NotValidOperationException, NotValidArgumentException {
        checkIfCanDo();
        match.chooseDestination(destination);
    }

    /**
     * Moves mother nature of the specified number of islands.
     *
     * @param positions the number of islands to move mother nature
     * @throws NotValidOperationException if there is no game associated to this client,
     *                                    if it's not the turn of this client to move mother nature or
     *                                    if the players can't move mother nature now
     * @throws NotValidArgumentException  if the passed value is not positive or
     *                                    if it exceeds the maximum value
     * @apiNote Possible error codes:
     * <ul>
     *     <li>
     *          {@link ErrorCode#GAME_NOT_EXIST}: if the client making the request is not in any game
     *      </li>
     *      <li>
     *          {@link ErrorCode#GENERIC_INVALID_ARGUMENT}: if the passed value is not correct
     *      </li>
     *      <li>
     *          {@link ErrorCode#PLAYER_NOT_IN_TURN}: if it's not the turn of this client to move mother nature
     *      </li>
     *      <li>
     *          {@link ErrorCode#GENERIC_INVALID_OPERATION}: if the players can't move mother nature now
     *      </li>
     *  </ul>
     */
    public void moveMotherNature(int positions) throws NotValidOperationException, NotValidArgumentException {
        checkIfCanDo();
        match.moveMotherNature(positions);
    }

    /**
     * Takes the student in the specified cloud and put them in the entrance of this client
     *
     * @param cloudID the ID of the chosen cloud
     * @throws NotValidOperationException if there is no game associated to this client,
     *                                    if it's not the turn of this client to take students from a cloud or
     *                                    if the players can't take students from a cloud now
     * @throws NotValidArgumentException  if the passed ID does not correspond to an existing cloud or
     *                                    if the selected cloud is empty
     * @apiNote Possible error codes:
     * <ul>
     *     <li>
     *          {@link ErrorCode#GAME_NOT_EXIST}: if the client making the request is not in any game
     *      </li>
     *      <li>
     *          {@link ErrorCode#CLOUD_EMPTY}: if the selected cloud is empty
     *      </li>
     *      <li>
     *          {@link ErrorCode#CLOUD_NOT_EXIST}: if the passed ID does not correspond to an existing cloud
     *      </li>
     *      <li>
     *          {@link ErrorCode#PLAYER_NOT_IN_TURN}: if it's not the turn of this client to take students from a cloud
     *      </li>
     *      <li>
     *          {@link ErrorCode#GENERIC_INVALID_OPERATION}: if the players can't take students from a cloud now
     *      </li>
     *  </ul>
     */
    public void takeFromCloud(int cloudID) throws NotValidOperationException, NotValidArgumentException {
        checkIfCanDo();
        match.takeFromCloud(cloudID);
    }
}
