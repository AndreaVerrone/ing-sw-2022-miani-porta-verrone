package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.gametable.Island;
import it.polimi.ingsw.server.model.player.Player;

/**
 * Implementation of the strategy where a given student's color is not being considered in the computation of the island
 */
public class ComputeInfluenceWithoutStudentColor implements ComputeInfluenceStrategy{

    /**
     * Student that is not being considered for the calculation of the influence
     */
    private final PawnType notCountedType;

    /**
     * Constructor of the class
     * @param type student color that is not being considered in the computation
     */
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
