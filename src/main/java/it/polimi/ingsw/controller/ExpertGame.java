package it.polimi.ingsw.controller;

import java.util.Collection;

/**
 * A class to handle the game when the user requested to use the expert rules.
 * This allows the character cards to be used, as well as coins.
 */
public class ExpertGame extends Game{

    /**
     * Creates a new game that uses the expert rules.
     * @param players the players in the game
     */
    public ExpertGame(Collection<PlayerLoginInfo> players) {
        super(players);
        // TODO: 12/05/2022 add stuff related to character cards
    }
}
