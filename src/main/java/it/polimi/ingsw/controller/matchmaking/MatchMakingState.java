package it.polimi.ingsw.controller.matchmaking;

import it.polimi.ingsw.controller.NotValidArgumentException;
import it.polimi.ingsw.controller.NotValidOperationException;
import it.polimi.ingsw.model.TowerType;
import it.polimi.ingsw.model.player.Wizard;

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
    void addPlayer(String nickname) throws NotValidArgumentException, NotValidOperationException;

    /**
     * Removes the player with the provided nickname from the lobby.
     * @param nickname the nickname of the player to remove
     * @throws NotValidArgumentException if there is no player with the provided nickname
     * @throws NotValidOperationException if a player can't leave the game in the current state
     */
    void removePlayer(String nickname) throws NotValidArgumentException, NotValidOperationException;

    /**
     * Sets the tower type of the current player in the queue.
     * @param towerType the type of tower to assign
     * @throws NotValidArgumentException if the tower selected is not available
     * @throws NotValidOperationException if the tower of the player can't be changed in the current state
     */
    void setTowerOfPlayer(TowerType towerType) throws NotValidArgumentException, NotValidOperationException;

    /**
     * Sets the wizard of the current player in the queue.
     * @param wizard the wizard to assign
     * @throws NotValidArgumentException if the wizard selected is not available
     * @throws NotValidOperationException if the wizard of the player can't be changed in the current state
     */
    void setWizardOfPlayer(Wizard wizard) throws NotValidArgumentException, NotValidOperationException;

    /**
     * Change the number of players needed in this game. This cannot be less than the player already
     * present in the lobby.
     * @param value the new number of players
     * @throws NotValidArgumentException if the selected number of players is not valid (i.e. if it's not
     * one of the value supported or less than the player already present in the lobby)
     * @throws NotValidOperationException if the number of player can't be changed in the current state
     */
    void changeNumOfPlayers(int value) throws NotValidArgumentException, NotValidOperationException;

    /**
     * Moves the match making to the next state.
     * @throws NotValidOperationException if the state can't be changed (i.e. not all the expected operations
     * of the current state were done)
     */
    void next() throws NotValidOperationException;
}
