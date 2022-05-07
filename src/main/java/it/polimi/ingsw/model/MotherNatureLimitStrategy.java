package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;

/**
 * Interface for the implementation of the strategy pattern for the calculation of mother nature range of motion
 */
public interface MotherNatureLimitStrategy {

    /**
     * Calculates the maximum value of which mother nature can be moved.
     * @param currentPlayer Current player of the game
     * @return Mother nature range of motion for the current player
     */
    int getMNMovementLimit(Player currentPlayer);

}
