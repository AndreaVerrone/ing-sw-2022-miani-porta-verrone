package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.view.cli.character_cards.ChooseCardToPlayScreen;
import it.polimi.ingsw.client.view.cli.character_cards.UseCard1Screen;
import it.polimi.ingsw.client.view.cli.common_screens.PlayerLeftScreen;
import it.polimi.ingsw.client.view.cli.game.*;
import it.polimi.ingsw.client.view.cli.launcher.*;
import it.polimi.ingsw.client.view.cli.matchmaking.ChooseParametersScreen;
import it.polimi.ingsw.client.view.cli.matchmaking.LobbyScreen;
import it.polimi.ingsw.client.view.cli.waiting.ConnectionErrorScreen;

import java.util.Collection;

/**
 * The builder of the screens of the CLI
 */
public class CliScreenBuilder extends ScreenBuilder {

    /**
     * The cli this builder is created for
     */
    private final CLI cli;

    /**
     * The last screen shown on the client's console
     */
    private CliScreen currentScreen;

    /**
     * The enum representation of the current screen
     */
    private Screen screen;

    /**
     * Creates a new ScreenBuilder used to create and show the various screens of the passed cli
     * @param cli the cli this builder is created for
     */
    CliScreenBuilder(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void build(ScreenBuilder.Screen screen) {
        currentScreen = switch (screen){
            case CONNECTION_ERROR -> new ConnectionErrorScreen(cli);
            case LAUNCHER -> new LauncherScreen(cli);
            case HOME -> new HomeScreen(cli);
            case SERVER_SPECS -> new AskServerSpecificationScreen(cli);
            case MATCHMAKING_WAIT_PLAYERS -> new LobbyScreen(cli);
            case MATCHMAKING_ASK_PARAMS -> new ChooseParametersScreen(cli);
            case PLAY_ASSISTANT_CARD -> new PlanningPhaseScreen(cli);
            case MOVE_STUDENT -> new MoveStudentsPhaseScreen(cli);
            case MOVE_MOTHER_NATURE -> new MoveMotherNatureScreen(cli);
            case CHOOSE_CLOUD -> new ChooseCloudScreen(cli);
            case CHOOSE_CHARACTER_CARD -> new ChooseCardToPlayScreen(cli, this.screen);
            case USE_CHARACTER_CARD1 -> new UseCard1Screen(cli);
            case USE_CHARACTER_CARD4 -> null;
            case USE_CHARACTER_CARD5 -> null;
            case USE_CHARACTER_CARD8 -> null;
            case USE_CHARACTER_CARD9 -> null;
            case USE_CHARACTER_CARD10 -> null;
            case USE_CHARACTER_CARD11 -> null;
            case USE_CHARACTER_CARD12 -> null;
            default -> throw new IllegalArgumentException();
        };
        this.screen = screen;
        show();
    }

    @Override
    public void build(ScreenBuilder.Screen screen, String input) {
        switch (screen){
            case ASK_NICKNAME -> currentScreen = new RequestNicknameScreen(cli, input);
            case PLAYER_LEFT -> currentScreen = new PlayerLeftScreen(cli, input);
            default -> throw new IllegalArgumentException();
        }
        show();
    }

    @Override
    public void build(ScreenBuilder.Screen screen, Collection<String> inputs) {
        switch (screen){
            case GAMES_LIST -> currentScreen = new GamesListScreen(cli, inputs);
            case END_GAME -> currentScreen = new EndGameScreen(cli, inputs);
            default -> throw new IllegalArgumentException();
        }
        show();
    }

    @Override
    public void rebuild() {
        show();
    }

    private void show() {
        cli.setNextScreen(currentScreen);
    }
}
