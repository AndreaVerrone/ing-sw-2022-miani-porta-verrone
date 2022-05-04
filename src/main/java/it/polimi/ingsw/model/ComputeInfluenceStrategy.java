package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gametable.Island;
import it.polimi.ingsw.model.gametable.exceptions.IslandNotFoundException;
import it.polimi.ingsw.model.player.Player;

public interface ComputeInfluenceStrategy {
    int computeInfluence(Player player, Island island) throws IslandNotFoundException;
}
