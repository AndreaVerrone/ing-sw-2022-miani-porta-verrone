package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gametable.Island;
import it.polimi.ingsw.model.player.Player;

public class ComputeInfluenceWithTwoAdditional implements ComputeInfluenceStrategy{

    private final GameModel model;

    public  ComputeInfluenceWithTwoAdditional(GameModel model){
        this.model = model;
    }

    @Override
    public int computeInfluence(Player player, Island island){
        int influence = 0;
        if(model.getCurrentPlayer().equals(player)) influence = 2;
        if (player.getTowerType() == island.getTower())
            influence += island.getSize();
        for (PawnType professor : player.getProfessors()){
            influence += island.numStudentsOf(professor);
        }
        return influence;
    }
}
