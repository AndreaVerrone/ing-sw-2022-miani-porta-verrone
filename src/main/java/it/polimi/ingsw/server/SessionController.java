package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Match;
import it.polimi.ingsw.controller.NotValidArgumentException;
import it.polimi.ingsw.controller.NotValidOperationException;
import it.polimi.ingsw.messages.responses.ErrorCode;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.TowerType;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Wizard;

import java.util.Collection;

/**
 * A class to handle session changes and action in game
 */
public class SessionController {

    /**
     * The user associated with this session.
     */
    private User user;

    /**
     * The game associated with this session, if any.
     */
    private Match match;

    /**
     * The nickname chosen by the user to be used in the game.
     */
    private String nickname;

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Checks if the player can be added to the chosen game, and if so it adds a new player.
     *
     * @param nickname the nickname chosen
     * @param gameID   the ID of the game chosen
     * @throws NotValidArgumentException  if there is no game with that ID or
     *                                    if the nickname is already taken
     * @throws NotValidOperationException if the game has started or
     *                                    if a new player can't be added
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

        this.match = match;
        this.nickname = nickname;

        Server.getInstance().addPlayer(user, match);
    }

    /**
     * Removes the player from the game.
     *
     * @param nickname the nickname of the player to remove
     * @throws NotValidArgumentException  if there is no player with the provided nickname
     * @throws NotValidOperationException if there is no game associated to this
     *                                    or if a player can't leave the game
     */
    public void exitFromGame(String nickname)
            throws NotValidOperationException, NotValidArgumentException {
        if (match == null)
            throw new NotValidOperationException(ErrorCode.GAME_NOT_EXIST);
        match.removePlayer(nickname);
        Server.getInstance().removePlayer(user);
    }

    /**
     * Gets the game that the user was playing. This is used when a player disconnects
     * from a game and wants to resume it.
     *
     * @throws NotValidArgumentException if there is no game associated to that player
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


    private void checkIfCanDo() throws NotValidOperationException {
        if (match == null)
            throw new NotValidOperationException();
        if (!match.getCurrentPlayerNickname().equals(nickname))
            throw new NotValidOperationException();
    }

    /**
     * Sets if the game need to use expert rules.
     *
     * @param isHardMode true if expert rules are required, false otherwise
     * @throws NotValidOperationException {@inheritDoc}
     */
    public void setHardMode(boolean isHardMode) throws NotValidOperationException {
        if (match == null)
            throw new NotValidOperationException();
        match.setHardMode(isHardMode);
    }

    /**
     * Change the number of players needed in this game.
     * This cannot be less than the player already present in this lobby.
     *
     * @param value the new number of players
     * @throws NotValidOperationException if the game has started or {@inheritDoc}
     * @throws NotValidArgumentException  {@inheritDoc}
     */
    public void changeNumOfPlayers(int value) throws NotValidOperationException, NotValidArgumentException {
        if (match == null)
            throw new NotValidOperationException();
        match.changeNumOfPlayers(value);
    }


    /**
     * @throws NotValidOperationException if the game has started or {@inheritDoc}
     * @throws NotValidArgumentException  {@inheritDoc}
     */
    public void setTowerOfPlayer(TowerType towerType) throws NotValidOperationException, NotValidArgumentException {
        checkIfCanDo();
        match.setTowerOfPlayer(towerType);
    }

    /**
     * @throws NotValidOperationException if the game has started or {@inheritDoc}
     * @throws NotValidArgumentException  {@inheritDoc}
     */
    public void setWizardOfPlayer(Wizard wizard) throws NotValidOperationException, NotValidArgumentException {
        checkIfCanDo();
        match.setWizardOfPlayer(wizard);
    }

    /**
     * @throws NotValidOperationException if the game has started or {@inheritDoc}
     */
    public void next() throws NotValidOperationException {
        checkIfCanDo();
        match.next();
    }

    /**
     * @param assistant
     * @throws NotValidOperationException if the game has not started yet or {@inheritDoc}
     * @throws NotValidArgumentException  {@inheritDoc}
     */
    public void useAssistant(Assistant assistant) throws NotValidOperationException, NotValidArgumentException {
        checkIfCanDo();
        match.useAssistant(assistant);
    }

    /**
     * @param student
     * @param islandID
     * @throws NotValidOperationException if the game has not started yet or {@inheritDoc}
     * @throws NotValidArgumentException  {@inheritDoc}
     */
    public void moveStudentToIsland(PawnType student, int islandID) throws NotValidOperationException, NotValidArgumentException {
        checkIfCanDo();
        match.moveStudentToIsland(student, islandID);
    }

    /**
     * @param student
     * @throws NotValidOperationException if the game has not started yet or {@inheritDoc}
     * @throws NotValidArgumentException  {@inheritDoc}
     */
    public void moveStudentToDiningRoom(PawnType student) throws NotValidOperationException, NotValidArgumentException {
        checkIfCanDo();
        match.moveStudentToDiningRoom(student);
    }

    /**
     * @param positions
     * @throws NotValidOperationException if the game has not started yet or {@inheritDoc}
     * @throws NotValidArgumentException  {@inheritDoc}
     */
    public void moveMotherNature(int positions) throws NotValidOperationException, NotValidArgumentException {
        checkIfCanDo();
        match.moveMotherNature(positions);
    }

    /**
     * @param cloudID
     * @throws NotValidOperationException if the game has not started yet or {@inheritDoc}
     * @throws NotValidArgumentException  {@inheritDoc}
     */
    public void takeFromCloud(int cloudID) throws NotValidOperationException, NotValidArgumentException {
        checkIfCanDo();
        match.takeFromCloud(cloudID);
    }
}
