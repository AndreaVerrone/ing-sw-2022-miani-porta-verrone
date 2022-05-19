package it.polimi.ingsw.server.model.strategies.influence;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.PawnType;
import it.polimi.ingsw.server.model.gametable.Island;
import it.polimi.ingsw.server.model.player.Player;

/**
 * Implementation of the strategy where are added two more points to the influence of the current player
 */
public class ComputeInfluenceWithTwoAdditional implements ComputeInfluenceStrategy{

    /**
     * Model of the game
     */
    private final GameModel model;

    /**
     * Constructor of the class. It saves the model of the game
     * @param model model of the game
     */
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
