package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

import java.util.Collection;
import java.util.HashSet;

/**
 * A message sent from the server to all clients connected to a game to indicate that the game ended.
 * This will also contain information about which player won the game.
 */
public class GameEnded extends ServerCommandNetMsg {

    /**
     * The players that won the game.
     * <p>
     * A single member indicates a winner, two or more member indicates a tie between those players.
     */
    private final HashSet<String> winners;

    /**
     * Creates a new message to indicate that the game ended and comunicate the winner (or winners in case of a tie).
     * @param winners the nickname of the player/s that won
     */
    public GameEnded(Collection<String> winners) {
        this.winners = new HashSet<>(winners);
    }

    @Override
    public void processMessage(ClientView client) {
        client.getScreenBuilder().build(ScreenBuilder.Screen.END_GAME, winners);
    }

}
