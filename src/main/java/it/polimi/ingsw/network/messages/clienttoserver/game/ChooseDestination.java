package it.polimi.ingsw.network.messages.clienttoserver.game;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.responses.ErrorCode;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.game.Position;

/**
 * message sent from the client to the server to choose a destination on which operate
 */
public class ChooseDestination extends ClientCommandNetMsg {
    /**
     * Position on where do an operation
     */
    private final Position destination;

    /**
     * Creates a new message to communicate the position on which operate
     * @param destination position chosen
     */
    public ChooseDestination(Position destination){
        this.destination = destination;
    }

    @Override
    protected void normalProcess(ClientHandler clientInServer) throws NotValidArgumentException, NotValidOperationException {
        clientInServer.getSessionController().chooseDestination(destination);
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
    }

}
