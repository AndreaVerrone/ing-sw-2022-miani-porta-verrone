package it.polimi.ingsw.client;

import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.expert.cards.CharacterCard5;

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
        CHOOSE_CHARACTER_CARD,
        USE_CHARACTER_CARD1,
        USE_CHARACTER_CARD4,
        USE_CHARACTER_CARD5,
        USE_CHARACTER_CARD8,
        USE_CHARACTER_CARD9,
        USE_CHARACTER_CARD10,
        USE_CHARACTER_CARD11,
        USE_CHARACTER_CARD12,
        END_GAME,
        CHOOSE_LANGUAGE,
        CHOOSE_GAME_PARAMETERS,
        IDLE,
        PLAYER_LEFT;

        public static Screen parse(StateType stateType) {
            return switch (stateType) {
                case CHANGE_PLAYER_STATE -> ScreenBuilder.Screen.MATCHMAKING_WAIT_PLAYERS;
                case SET_PLAYER_PARAMETER_STATE -> ScreenBuilder.Screen.MATCHMAKING_ASK_PARAMS;
                case PLAY_ASSISTANT_STATE -> ScreenBuilder.Screen.PLAY_ASSISTANT_CARD;
                case MOVE_STUDENT_STATE -> ScreenBuilder.Screen.MOVE_STUDENT;
                case MOVE_MOTHER_NATURE_STATE -> ScreenBuilder.Screen.MOVE_MOTHER_NATURE;
                case CHOOSE_CLOUD_STATE -> ScreenBuilder.Screen.CHOOSE_CLOUD;
                case USE_CHARACTER_CARD1_STATE -> USE_CHARACTER_CARD1;
                case USE_CHARACTER_CARD4_STATE -> USE_CHARACTER_CARD4;
                case USE_CHARACTER_CARD5_STATE -> USE_CHARACTER_CARD5;
                case USE_CHARACTER_CARD8_STATE -> USE_CHARACTER_CARD8;
                case USE_CHARACTER_CARD9_STATE -> USE_CHARACTER_CARD9;
                case USE_CHARACTER_CARD10_STATE -> USE_CHARACTER_CARD10;
                case USE_CHARACTER_CARD11_STATE -> USE_CHARACTER_CARD11;
                case USE_CHARACTER_CARD12_STATE -> USE_CHARACTER_CARD12;
                default -> null;
            };
        }
    }

    /**
     * Builds and shows the screen corresponding to the specified input
     * @param screen the screen that need to be shown
     */
    abstract public void build(Screen screen);

    /**
     * Builds and shows a content to ask the client a nickname to enter a game or
     * notifies that a player left the game
     * @param screen the content to show
     * @param input the input
     */
    abstract public void build(Screen screen, String input);

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
