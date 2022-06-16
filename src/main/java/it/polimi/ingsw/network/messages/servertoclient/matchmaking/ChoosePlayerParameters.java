package it.polimi.ingsw.network.messages.servertoclient.matchmaking;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A message sent from the server to all clients to indicate that an action is needed
 * in order to choose the parameters of the current player in the matchmaking
 */
public class ChoosePlayerParameters extends ServerCommandNetMsg {

    /**
     * The towers available from which the client can choose
     */
    private final Collection<TowerType> towersAvailable;

    /**
     * The wizards available from which the client can choose
     */
    private final Collection<Wizard> wizardsAvailable;

    /**
     * Creates a new message to indicate all the clients that the current player need to choose parameters
     * @param towersAvailable the towers available from which the client can choose
     * @param wizardsAvailable the wizards available from which the client can choose
     */
    public ChoosePlayerParameters(Collection<TowerType> towersAvailable, Collection<Wizard> wizardsAvailable) {
        this.towersAvailable = new ArrayList<>(towersAvailable);
        this.wizardsAvailable = new ArrayList<>(wizardsAvailable);
    }

    @Override
    public void processMessage(ClientController client) {
        client.requestChoosePlayerParameter(towersAvailable, wizardsAvailable);
    }
}
