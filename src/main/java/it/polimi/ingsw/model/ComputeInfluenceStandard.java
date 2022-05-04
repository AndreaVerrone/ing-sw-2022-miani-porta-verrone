package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gametable.GameTable;
import it.polimi.ingsw.model.gametable.Island;
import it.polimi.ingsw.model.gametable.exceptions.IslandNotFoundException;
import it.polimi.ingsw.model.player.Player;

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
