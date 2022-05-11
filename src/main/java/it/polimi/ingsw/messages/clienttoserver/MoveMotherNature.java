package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.controller.NotValidArgumentException;
import it.polimi.ingsw.controller.NotValidOperationException;
import it.polimi.ingsw.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;

/**
 * A message sent from the client to the server to indicate the number of movements wanted for mother nature
 */
public class MoveMotherNature extends ClientCommandNetMsg{

    /**
     * The number of movement mother nature need to do
     */
    private final int movement;

    /**
     * Creates a new message to comunicate the number of movements that the client wants to move mother nature
     * @param movement the number of movement
     */
    public MoveMotherNature(int movement) {
        this.movement = movement;
    }


    @Override
    protected void normalProcess(ClientHandler clientInServer) throws NotValidArgumentException, NotValidOperationException {
        clientInServer.getSessionController().moveMotherNature(movement);
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
    }

    @Override
    public void processResponse(ResponseMessage response) {
        // TODO: 11/05/2022 show response to the client
    }
}
