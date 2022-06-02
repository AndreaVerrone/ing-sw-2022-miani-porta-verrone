package it.polimi.ingsw.server.controller.matchmaking.observers;

import it.polimi.ingsw.server.controller.Match;
import it.polimi.ingsw.server.model.player.Wizard;

/**
 * Implementation of the observer for wizard selection
 */
public class WizardSelected implements  WizardSelectedObserver{

    /**
     * @see Match
     */
    private final Match match;

    /**
     * Constructor of the class
     * @param match Match of the game
     */
    public WizardSelected(Match match){
        this.match = match;
    }

    @Override
    public void wizardSelectedObserverUpdate(String player, Wizard wizard) {
        match.notifyWizardSelected(player, wizard);
    }
}
