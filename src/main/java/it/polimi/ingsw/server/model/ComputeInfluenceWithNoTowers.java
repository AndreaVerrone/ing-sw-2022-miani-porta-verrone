package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.gametable.Island;
import it.polimi.ingsw.server.model.player.Player;

/**
 * Implementation of the strategy where all the towers on the given island are not being considered for the computation of the influence
 */
public class ComputeInfluenceWithNoTowers implements ComputeInfluenceStrategy{

    @Override
    public int computeInfluence(Player player, Island island) {
        int influence = 0;
        for (PawnType professor : player.getProfessors()){
            influence += island.numStudentsOf(professor);
        }
        return influence;
    }
}
