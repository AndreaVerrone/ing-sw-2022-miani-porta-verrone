package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.player.Player;

/**
 * Implementation of the strategy to calculate the limit for mother nature movements from the last assistant card
 * used by the current player
 */
public class MotherNatureLimitStandard implements MotherNatureLimitStrategy{


    @Override
    public int getMNMovementLimit(Player currentPlayer) {
        return currentPlayer.getLastAssistant().getRangeOfMotion();
    }
}
