package it.polimi.ingsw.server;

import it.polimi.ingsw.network.NetworkSender;
import it.polimi.ingsw.network.VirtualView;

/**
 * A class simulating the view server side, sending every update requested to a real view over the network
 */
public class NetworkView implements VirtualView {

    /**
     * The sender that need to be used to forward every request that this view receives
     */
    private final NetworkSender sender;

    /**
     * Creates a new view that simulates the behaviour of a real view server side,
     * but in fact it forwards every request over the network using the provided {@code NetworkSender}.
     * @param sender the sender by which forward the requests made
     */
    public NetworkView(NetworkSender sender) {
        this.sender = sender;
    }
}
