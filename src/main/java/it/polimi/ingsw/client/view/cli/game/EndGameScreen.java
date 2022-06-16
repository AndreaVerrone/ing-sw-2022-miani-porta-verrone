package it.polimi.ingsw.client.view.cli.game;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Text;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.List;

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
    private final List<String> winners;

    /**
     * The constructor of the class
     * @param cli the cli of the user
     * @param winners the list of the winners of the game
     */
    public EndGameScreen(CLI cli, List<String> winners) {
        super(cli);
        this.winners = winners;
    }

    /**
     * A method to show this screen on the command line
     */
    @Override
    protected void show() {

        Canvas canvas = new Canvas();

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
                text = new Text(winners.get(0) + " " + Translator.getMessageForTheLosers());
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
    }

    /**
     * this method allow the player to close the game when he asks to do that.
     */
    @Override
    protected void askAction(){

        InputReader inputReader = new InputReader();

        // INPUT VALIDATOR
        // the string to exit
        inputReader.addCommandValidator(Translator.getMessageToExit());

        // INPUT COMPLETER
        // the string to exit
        inputReader.addCompleter(new StringsCompleter(Translator.getMessageToExit()));

        //prompt the user to enter something and reads the input
        String[] inputs = inputReader.readInput(Translator.getMessageChooseEndPhase());

        if (inputs[0].equals(Translator.getMessageToExit())) {
            // change screen
            getCli().confirmExit();
        }
    }
}
