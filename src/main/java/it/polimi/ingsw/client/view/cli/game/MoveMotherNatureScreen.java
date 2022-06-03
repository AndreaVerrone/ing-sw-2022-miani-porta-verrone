package it.polimi.ingsw.client.view.cli.game;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.Table;
import org.jline.reader.impl.completer.StringsCompleter;

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
     * @param table the table of the game
     */
    public MoveMotherNatureScreen(CLI cli, Table table) {
        super(cli);
        this.table=table;
    }

    /**
     * A method to show this screen on the command line
     */
    @Override
    protected void show() {

        Canvas canvas = new Canvas();
        canvas.setContent(table);
        canvas.setTitle(phase);

        String currentPlayerNickname = getCli().getClientController().getNickNameCurrentPlayer();

        canvas.setSubtitle(currentPlayerNickname);
        canvas.show();

        askForAction();

    }

    private void askForAction() {

        InputReader inputReader = new InputReader();

        // INPUT VALIDATOR
        // allowed inputs:
        // 1. the number of step of mother nature in the range [1-7]
        inputReader.addCommandValidator("[1-7]");
        // 2. the string to exit the game
        inputReader.addCommandValidator(Translator.getMessageToExit());

        // INPUT COMPLETER
        // 1. the string to exit
        inputReader.addCompleter(new StringsCompleter(Translator.getMessageToExit()));


        //prompt the user to enter something and reads the input
        String[] inputs = inputReader.readInput(Translator.getMessageMoveMotherNaturePhase());

        if (inputs[0].equals(Translator.getMessageToExit())) {
            System.out.println("exiting from game"); // todo: for testing only
            // change screen
            // getCli().confirmExit(); // todo: this is the actual code
        }else {
            int numOfMovements;
            numOfMovements=Integer.parseInt(inputs[0]);
            // send message to server
            System.out.println("sending to server to move MN of: "+ numOfMovements); // todo: for testing only
            // getCli().getClientController().moveMotherNature(numOfMovements); // todo: actual code
        }
        getCli().setNextScreen(new ChooseCloudScreen(getCli(),table)); // todo: for testing only
    }
}
