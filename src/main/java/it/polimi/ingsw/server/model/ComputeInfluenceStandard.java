package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.gametable.Island;
import it.polimi.ingsw.server.model.player.Player;

/**
 * Implementation of the strategy for the standard computation of the influence on a given island
 */
public class ComputeInfluenceStandard implements ComputeInfluenceStrategy {

    @Override
    public int computeInfluence(Player player, Island island){
        int influence = 0;
        if (player.getTowerType() == island.getTower())
            influence += island.getSize();
        for (PawnType professor : player.getProfessors()){
            influence += island.numStudentsOf(professor);
        }
        return influence;
    }

}
