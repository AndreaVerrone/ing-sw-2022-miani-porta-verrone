package it.polimi.ingsw.network.messages.clienttoserver.launcher;

import it.polimi.ingsw.network.User;
import it.polimi.ingsw.network.messages.NetworkMessage;
import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;

/**
 * A message sent from client to server to send the unique identifier of a user.
 */
public class SendUserIdentifier extends ClientCommandNetMsg {

    /**
     * The unique identifier of a user
     */
    private final String identifier;

    /**
     * Creates a new message containing the unique identifier of a user
     * @param identifier the identifier of a user
     */
    public SendUserIdentifier(String identifier){
        this.identifier = identifier;
    }

    /**
     * Processes this message in the server.
     * <p>
     * More formally, uses the identifier in this message to bound a user with that identifier
     * to the client that sent this message.
     * @param clientInServer the client in the server
     */
    @Override
    public void normalProcess(ClientHandler clientInServer){
        clientInServer.setUser(new User(identifier));
        System.out.println("The secret is:\t" + identifier);
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
    }
}
