package it.polimi.ingsw.client;

import it.polimi.ingsw.server.controller.StateType;

import java.util.Collection;

/**
 * A class used to build and show the content of the view of the client
 */
public abstract class ScreenBuilder {

    @SuppressWarnings("MissingJavadoc")
    public enum Screen {
        CONNECTION_ERROR,
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
        END_GAME;

        public static Screen parse(StateType stateType) {
            return switch (stateType){
                case CHANGE_PLAYER_STATE -> ScreenBuilder.Screen.MATCHMAKING_WAIT_PLAYERS;
                case SET_PLAYER_PARAMETER_STATE -> ScreenBuilder.Screen.MATCHMAKING_ASK_PARAMS;
                case PLAY_ASSISTANT_STATE -> ScreenBuilder.Screen.PLAY_ASSISTANT_CARD;
                case MOVE_STUDENT_STATE -> ScreenBuilder.Screen.MOVE_STUDENT;
                case MOVE_MOTHER_NATURE_STATE -> ScreenBuilder.Screen.MOVE_MOTHER_NATURE;
                case CHOOSE_CLOUD_STATE -> ScreenBuilder.Screen.CHOOSE_CLOUD;
                default -> null;
            /*
            // todo: implement
            case USE_CHARACTER_CARD1_STATE -> null;
            case USE_CHARACTER_CARD4_STATE -> null;
            case USE_CHARACTER_CARD5_STATE -> null;
            case USE_CHARACTER_CARD8_STATE -> null;
            case USE_CHARACTER_CARD9_STATE -> null;
            case USE_CHARACTER_CARD10_STATE -> null;
            case USE_CHARACTER_CARD11_STATE -> null;
            case USE_CHARACTER_CARD12_STATE -> null;
             */
            };
        }
    }

    /**
     * Builds and shows the screen corresponding to the specified input
     * @param screen the screen that need to be shown
     */
    abstract public void build(Screen screen);

    /**
     * Builds and shows a content to ask the client a nickname to enter a game
     * @param screen the content to show
     * @param gameID the id of the game the client wants to join
     */
    abstract public void build(Screen screen, String gameID);

    /**
     * Builds and shows a content to display possible games to join or to notify the ending of the game
     * @param screen the content to show
     * @param inputs the list of inputs
     */
    abstract public void build(Screen screen, Collection<String> inputs);

    /**
     * Reshow the last screen displayed to the client
     */
    abstract public void rebuild();
}
