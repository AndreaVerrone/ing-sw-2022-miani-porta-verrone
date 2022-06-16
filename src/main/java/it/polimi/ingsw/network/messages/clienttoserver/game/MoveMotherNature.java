package it.polimi.ingsw.network.messages.clienttoserver.game;

import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;

/**
 * A message sent from the client to the server to indicate the number of movements wanted for mother nature
 */
public class MoveMotherNature extends ClientCommandNetMsg {

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

}
