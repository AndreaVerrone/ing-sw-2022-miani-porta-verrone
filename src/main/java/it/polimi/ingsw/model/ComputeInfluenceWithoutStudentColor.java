package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gametable.Island;
import it.polimi.ingsw.model.player.Player;

public class ComputeInfluenceWithoutStudentColor implements ComputeInfluenceStrategy{

    private final PawnType notCountedType;

    public  ComputeInfluenceWithoutStudentColor(PawnType type){
        notCountedType = type;
    }

    @Override
    public int computeInfluence(Player player, Island island){
        int influence = 0;
        if (player.getTowerType() == island.getTower())
            influence += island.getSize();
        for (PawnType professor : player.getProfessors()){
            if(professor.equals(notCountedType)) continue;
            influence += island.numStudentsOf(professor);
        }
        return influence;
    }
}
