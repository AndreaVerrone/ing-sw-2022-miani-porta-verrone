package it.polimi.ingsw.client;

import java.util.Collection;

/**
 * A class used to build and show the content of the view of the client
 */
public abstract class ScreenBuilder {

    @SuppressWarnings("MissingJavadoc")
    protected enum Screen {
        LAUNCHER,
        HOME,
        SERVER_SPECS,
        ASK_NICKNAME,
        GAMES_LIST,
        MATCHMAKING_WAIT_PLAYERS,
        MATCHMAKING_ASK_PARAMS,
        PLAY_ASSISTANT_CARD,
        MOVE_STUDENT,
        MOVE_MOTHER_NATURE,
        CHOOSE_CLOUD,
        END_GAME
    }

    /**
     * Builds and shows the screen corresponding to the specified input
     * @param screen the screen that need to be shown
     */
    abstract protected void build(Screen screen);

    /**
     * Builds and shows a content to ask the client a nickname to enter a game
     * @param screen the content to show
     * @param gameID the id of the game the client wants to join
     */
    abstract protected void build(Screen screen, String gameID);

    /**
     * Builds and shows a content to display possible games to join or to notify the ending of the game
     * @param screen the content to show
     * @param inputs the list of inputs
     */
    abstract protected void build(Screen screen, Collection<String> inputs);
}
