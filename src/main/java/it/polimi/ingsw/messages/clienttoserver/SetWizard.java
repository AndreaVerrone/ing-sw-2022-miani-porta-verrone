package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.controller.NotValidArgumentException;
import it.polimi.ingsw.controller.NotValidOperationException;
import it.polimi.ingsw.messages.responses.ResponseMessage;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.server.ClientHandler;

/**
 * A message sent from the client to the server to communicate the chosen wizard.
 */
public class SetWizard extends ClientCommandNetMsg{

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

    @Override
    public void processResponse(ResponseMessage response) {
        // TODO: 11/05/2022 handle response in client
    }
}
