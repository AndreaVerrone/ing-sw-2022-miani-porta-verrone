package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.view.cli.game.*;
import it.polimi.ingsw.client.view.cli.launcher.*;
import it.polimi.ingsw.client.view.cli.matchmaking.ChooseParametersScreen;
import it.polimi.ingsw.client.view.cli.matchmaking.LobbyScreen;

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
     * Creates a new ScreenBuilder used to create and show the various screens of the passed cli
     * @param cli the cli this builder is created for
     */
    CliScreenBuilder(CLI cli) {
        this.cli = cli;
    }

    @Override
    protected void build(ScreenBuilder.Screen screen) {
        CliScreen nextScreen = switch (screen){
            case LAUNCHER -> new LauncherScreen(cli);
            case HOME -> new HomeScreen(cli);
            case SERVER_SPECS -> new AskServerSpecificationScreen(cli);
            case MATCHMAKING_WAIT_PLAYERS -> new LobbyScreen(cli);
            case MATCHMAKING_ASK_PARAMS -> new ChooseParametersScreen(cli);
            case PLAY_ASSISTANT_CARD -> new PlanningPhaseScreen(cli);
            case MOVE_STUDENT -> new MoveStudentsPhaseScreen(cli);
            case MOVE_MOTHER_NATURE -> new MoveMotherNatureScreen(cli);
            case CHOOSE_CLOUD -> new ChooseCloudScreen(cli);
            default -> throw new IllegalArgumentException();
        };
        cli.setNextScreen(nextScreen);
    }

    @Override
    protected void build(ScreenBuilder.Screen screen, String gameID) {
        if (screen != Screen.ASK_NICKNAME)
            throw new IllegalArgumentException();
        cli.setNextScreen(new RequestNicknameScreen(cli, gameID));
    }

    @Override
    protected void build(ScreenBuilder.Screen screen, Collection<String> inputs) {
        switch (screen){
            case GAMES_LIST -> cli.setNextScreen(new GamesListScreen(cli, inputs));
            case END_GAME -> cli.setNextScreen(new EndGameScreen(cli, inputs));
            default -> throw new IllegalArgumentException();
        }
    }
}
