package it.polimi.ingsw.server.model.strategies.influence;

import it.polimi.ingsw.server.model.gametable.Island;
import it.polimi.ingsw.server.model.player.Player;

/**
 * Interface to implement the strategy pattern for the computation of the influence on an island
 */
public interface ComputeInfluenceStrategy {

    /**
     * Method to calculate the influence of a player on a given island based on the students and the towers on it.
     * @param player player that is calculating the influence
     * @param island island on which the influence is being calculated
     * @return the value of the influence
     */
    int computeInfluence(Player player, Island island);
}
