package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.TowerType;
import it.polimi.ingsw.model.player.Wizard;

/**
 * An interface representing the various state of the match making phase
 */
public interface MatchMakingState {

    /**
     * Adds a player with the provided nickname to the lobby. The nickname must be unique.
     * @param nickname the nickname chosen by the player
     */
    void addPlayer(String nickname);

    /**
     * Sets the tower type of the current player in the queue.
     * @param towerType the type of tower to assign
     */
    void setTowerOfPlayer(TowerType towerType);

    /**
     * Sets the wizard of the current player in the queue.
     * @param wizard the wizard to assign
     */
    void setWizardOfPlayer(Wizard wizard);

    /**
     * Change the number of players needed in this game. This cannot be less than the player already
     * present in the lobby.
     * @param value the new number of players
     */
    void changeNumOfPlayers(int value);

    /**
     * Moves the match making to the next state
     */
    void next();
}
