package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.NetworkMessage;

/**
 * An interface describing the ability to send messages over the network
 */
public interface NetworkSender {

    /**
     * Send the specified message over the network to the other side of a connection.
     * @param message the message to send
     */
    void sendMessage(NetworkMessage message);
}
