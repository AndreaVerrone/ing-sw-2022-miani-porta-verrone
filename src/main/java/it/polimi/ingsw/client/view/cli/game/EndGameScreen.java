package it.polimi.ingsw.client.view.cli.game;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Text;

import java.util.Collection;

/**
 * this class represent the screen that must be displayed at the end of the game.
 */
public class EndGameScreen extends CliScreen {

    /**
     * The name of the phase
     */
    private final String phase = Translator.getEndGamePhaseName();

    /**
     * The list of the winners.
     */
    private final Collection<String> winners;

    /**
     * The constructor of the class
     * @param cli the cli of the user
     * @param winners the list of the winners of the game
     */
    public EndGameScreen(CLI cli, Collection<String> winners) {
        super(cli);
        this.winners = winners;
    }

    /**
     * A method to show this screen on the command line
     */
    @Override
    protected void show() {

        Canvas canvas = new Canvas(true, false);

        Text text;

        String ownerPlayer = getCli().getClientController().getNickNameOwner();

        int numOfWinners = winners.size();

        // two different situations are possible:

        // *** 1. there is only 1 winner
        if (numOfWinners == 1) {

            // the winner is the owner
            if (winners.contains(ownerPlayer)) {
                text = new Text(Translator.getMessageForTheWinner());
            } else {
                // the winner is not the owner
                text = new Text(winners.toArray()[0] + " " + Translator.getMessageForTheLosers());
            }

        } else {
        // *** 2. there is more than 1 winner --> parity situation
            StringBuilder message = new StringBuilder(Translator.getMessageForParity() +": \n");

            for (String winner : winners) {
                message.append(winner).append("\n");
            }
            text = new Text(message.toString());
        }

        canvas.setContent(text);
        canvas.setTitle(phase);

        canvas.show();

        new InputReader().readInput(Translator.getPressEnterToExit());
        getCli().getClientController().quitGame();
        getCli().getScreenBuilder().build(ScreenBuilder.Screen.HOME);
    }
}
