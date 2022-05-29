package it.polimi.ingsw.server.controller.matchmaking;

import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.IGame;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.Optional;

/**
 * An interface representing the various state of the match making phase
 */
public interface MatchMakingState {

    /**
     * Adds a player with the provided nickname to the lobby. The nickname must be unique.
     * @param nickname the nickname chosen by the player
     * @throws NotValidArgumentException if the nickname is already taken
     * @throws NotValidOperationException if a new player can't be added in the current state
     */
    default void addPlayer(String nickname) throws NotValidArgumentException, NotValidOperationException{
        throw new NotValidOperationException();
    }

    /**
     * Removes the player with the provided nickname from the lobby.
     * @param nickname the nickname of the player to remove
     * @throws NotValidArgumentException if there is no player with the provided nickname
     * @throws NotValidOperationException if a player can't leave the game in the current state
     */
    default void removePlayer(String nickname) throws NotValidArgumentException, NotValidOperationException{
        throw new NotValidOperationException();
    }

    /**
     * Sets the tower type of the current player in the queue.
     * @param tower the type of tower to assign
     * @throws NotValidArgumentException if the tower selected is not available
     * @throws NotValidOperationException if the tower of the player can't be changed in the current state
     */
    default void setTowerOfPlayer(TowerType tower) throws NotValidArgumentException, NotValidOperationException{
        throw new NotValidOperationException();
    }

    /**
     * Sets the wizard of the current player in the queue.
     * @param wizard the wizard to assign
     * @throws NotValidArgumentException if the wizard selected is not available
     * @throws NotValidOperationException if the wizard of the player can't be changed in the current state
     */
    default void setWizardOfPlayer(Wizard wizard) throws NotValidArgumentException, NotValidOperationException{
        throw new NotValidOperationException();
    }

    /**
     * Change the number of players needed in this game. This cannot be less than the player already
     * present in the lobby.
     * @param value the new number of players
     * @throws NotValidArgumentException if the selected number of players is not valid (i.e. if it's not
     * one of the value supported or less than the player already present in the lobby)
     * @throws NotValidOperationException if the number of player can't be changed in the current state
     */
    default void changeNumOfPlayers(int value) throws NotValidArgumentException, NotValidOperationException{
        throw new NotValidOperationException();
    }

    /**
     * Moves the match making to the next state. It also returns the new game created, if any.
     * @throws NotValidOperationException if the state can't be changed (i.e. not all the expected operations
     * of the current state were done)
     * @return {@link Optional#empty()} if no game was meant to be created, or an {@code Optional} containing
     *      the game created
     */
    Optional<IGame> next() throws NotValidOperationException;

    /**
     * Returns the type of the state
     * @return the type of the state
     */
     StateType getType();
}
