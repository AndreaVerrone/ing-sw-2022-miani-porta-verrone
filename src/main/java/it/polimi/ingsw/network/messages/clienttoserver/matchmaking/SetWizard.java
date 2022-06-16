package it.polimi.ingsw.network.messages.clienttoserver.matchmaking;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.model.player.Wizard;

/**
 * A message sent from the client to the server to communicate the chosen wizard.
 */
public class SetWizard extends ClientCommandNetMsg {

    /**
     * The wizard chosen by the player.
     */
    private final Wizard wizard;

    /**
     * Creates a new message to communicate to the server the chosen wizard of a player.
     *
     * @param wizard the wizard chosen
     */
    public SetWizard(Wizard wizard) {
        this.wizard = wizard;
    }
    @Override
    protected void normalProcess(ClientHandler clientInServer)
            throws NotValidArgumentException, NotValidOperationException {
        clientInServer.getSessionController().setWizardOfPlayer(wizard);
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
    }
}
