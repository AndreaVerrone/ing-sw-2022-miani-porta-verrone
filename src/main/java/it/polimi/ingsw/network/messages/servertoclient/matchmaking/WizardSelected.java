package it.polimi.ingsw.network.messages.servertoclient.matchmaking;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;
import it.polimi.ingsw.server.model.player.Wizard;

/**
 * A message sent from the server to the client to notify that the player has selected a wizard.
 */
public class WizardSelected extends ServerCommandNetMsg {

    /**
     * Nickname of the player that selected the tower
     */
    private final String nickname;

    /**
     * Wizard selected by the player
     */
    private final Wizard wizard;

    /**
     * Creates a new message to inform all the players in a game that the player has selected a wizard.
     * @param nickname of the player that selected the tower
     * @param wizard wizard selected by the player
     */
    public WizardSelected(String nickname, Wizard wizard){
        this.nickname = nickname;
        this.wizard = wizard;
    }

    @Override
    public void processMessage(ClientController client) {
        client.wizardChanged(nickname, wizard);
    }
}
