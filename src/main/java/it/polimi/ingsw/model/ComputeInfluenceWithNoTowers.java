package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gametable.Island;
import it.polimi.ingsw.model.player.Player;

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
