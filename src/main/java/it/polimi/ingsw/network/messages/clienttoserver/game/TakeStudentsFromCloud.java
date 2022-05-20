package it.polimi.ingsw.network.messages.clienttoserver.game;

import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;

/**
 * A message sent from the client to the server to tell from which cloud take the students
 */
public class TakeStudentsFromCloud extends ClientCommandNetMsg {

    /**
     * The ID of the cloud chosen
     */
    private final int cloudID;

    /**
     * Creates a new message to indicate from which cloud the client wants to take the students
     * @param cloudID the ID of the cloud
     */
    public TakeStudentsFromCloud(int cloudID) {
        this.cloudID = cloudID;
    }

    @Override
    protected void normalProcess(ClientHandler clientInServer) throws NotValidArgumentException, NotValidOperationException {
        clientInServer.getSessionController().takeFromCloud(cloudID);
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
    }

    @Override
    public void processResponse(ResponseMessage response) {
        // TODO: 11/05/2022 handle responses in client
    }
}
