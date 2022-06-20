package it.polimi.ingsw.client.view.cli.matchmaking;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;

/**
 * A screen used to display the lobby to the client while waiting for other players
 */
public class LobbyScreen extends CliScreen {


    /**
     * Creates a new screen that shows the lobby of a game
     * @param cli the cli of the user
     */
    public LobbyScreen(CLI cli) {
        super(cli);
    }

    @Override
    protected void show() {
        Canvas canvas = getCli().getBaseCanvas();
        canvas.setSubtitle("Matchmaking");
        canvas.setContent(getCli().getMatchmakingView());
        canvas.show();
    }
}
