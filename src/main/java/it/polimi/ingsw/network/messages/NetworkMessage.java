package it.polimi.ingsw.network.messages;

import java.io.Serializable;
import java.util.UUID;

/**
 * A class representing a generic message that can be sent over a network link.
 */
abstract public class NetworkMessage implements Serializable {

    /**
     * The unique identifier of this message
     */
    private final UUID identifier = UUID.randomUUID();

    public UUID getIdentifier(){
        return identifier;
    }
}
