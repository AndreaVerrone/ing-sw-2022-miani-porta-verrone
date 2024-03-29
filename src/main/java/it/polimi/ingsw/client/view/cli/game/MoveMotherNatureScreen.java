package it.polimi.ingsw.client.view.cli.game;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.Table;
import org.jline.reader.impl.completer.StringsCompleter;

/**
 * this screen needs to be displayed during the "move mother nature" stage of the action phase.
 */
public class MoveMotherNatureScreen extends CliScreen {

    /**
     * the name of the phase
     */
    private final String phase= Translator.getMoveMotherNaturePhaseName();

    /**
     * the table of the game
     */
    private final Table table;

    /**
     * The constructor od the class
     * @param cli the cli of the user
     */
    public MoveMotherNatureScreen(CLI cli) {
        super(cli);
        this.table=cli.getTable();
    }

    /**
     * A method to show this screen on the command line
     */
    @Override
    protected void show() {

        Canvas canvas = new Canvas(true, false);
        canvas.setContent(table);
        canvas.setTitle(phase);

        String currentPlayerNickname = getCli().getClientController().getNickNameCurrentPlayer();

        canvas.setSubtitle(Translator.getMessageCurrentPlayer()+": "+currentPlayerNickname);
        canvas.show();
    }

    /**
     * this method will ask the player to move mother nature.
     */
    @Override
    protected void askAction() {

        InputReader inputReader = new InputReader();

        // INPUT VALIDATOR
        // allowed inputs:
        // 1. the number of step of mother nature in the range [1-7]
        inputReader.addCommandValidator("[1-7]");
        // 2. the string to exit the game
        inputReader.addCommandValidator(Translator.getMessageToExit());
        inputReader.addCommandValidator(Translator.getUseCard());

        // INPUT COMPLETER
        // 1. the string to exit
        inputReader.addCompleter(new StringsCompleter(Translator.getMessageToExit(), Translator.getUseCard()));


        //prompt the user to enter something and reads the input
        String input = inputReader.readInput(Translator.getMessageMoveMotherNaturePhase())[0];

        if (input.equals(Translator.getMessageToExit())) {
            // change screen
            getCli().confirmExit();
            return;
        }
        if (input.equals(Translator.getUseCard())) {
            getCli().useCharacterCard();
            return;
        }
        int numOfMovements = Integer.parseInt(input);
        // send message to server
        getCli().getClientController().moveMotherNature(numOfMovements);
    }
}
