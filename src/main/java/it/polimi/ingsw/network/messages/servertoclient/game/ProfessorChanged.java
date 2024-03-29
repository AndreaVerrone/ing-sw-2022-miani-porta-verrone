package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;
import it.polimi.ingsw.server.model.utils.PawnType;

import java.util.Collection;
import java.util.HashSet;

/**
 * A message sent from server to all clients connected to a game to indicate that something
 * in the professors list of a player changed
 */
public class ProfessorChanged extends ServerCommandNetMsg {

    /**
     * The nickname of the player
     */
    private final String playerNickname;

    /**
     * The professors list of the player
     */
    private final HashSet<PawnType> professors;

    /**
     * Creates a new message to indicate that something in the professors list of a player changed.
     * @param playerNickname the nickname of the player
     * @param professors the new list of professors of that player
     */
    public ProfessorChanged(String playerNickname, Collection<PawnType> professors) {
        this.playerNickname = playerNickname;
        this.professors = new HashSet<>(professors);
    }

    @Override
    public void processMessage(ClientView client) {
        client.professorsOfPlayerChanged(playerNickname,professors);
    }

}
