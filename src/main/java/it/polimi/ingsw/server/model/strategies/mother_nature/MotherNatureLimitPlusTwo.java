package it.polimi.ingsw.server.model.strategies.mother_nature;

import it.polimi.ingsw.server.model.player.Player;

/**
 * Implementation of the strategy to get mother nature standard range of motion plus two additional movements;
 */
public class MotherNatureLimitPlusTwo implements MotherNatureLimitStrategy{

    @Override
    public int getMNMovementLimit(Player currentPlayer) {
        return currentPlayer.getLastAssistant().getRangeOfMotion() + 2;
    }
}
