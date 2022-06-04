package it.polimi.ingsw.client.view.cli.game;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.Table;
import it.polimi.ingsw.server.model.player.Assistant;
import org.jline.reader.Completer;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlanningPhaseScreen extends CliScreen {

    /**
     * the name of the phase
     */
    private final String phase= Translator.getPlanningPhaseName();

    /**
     * the table of the game
     */
    private final Table table;

    /**
     * The constructor od the class
     * @param cli the cli of the user
     */
    public PlanningPhaseScreen(CLI cli) {
        super(cli);
        this.table=cli.getTable();
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

        // INPUT VALIDATOR AND COMPLETER
        // allowed and suggested inputs:
        // 1. list of player's deck
        List<Assistant> assistants = table.getAssistantsList();
        Collection<Completer> completers = new ArrayList<>();
        for (Assistant assistant : assistants) {
            completers.add(new StringsCompleter(String.valueOf(assistant.getValue()))); // completer
            inputReader.addCommandValidator(String.valueOf(assistant.getValue())); // validator
        }
        // 2. string to exit
        completers.add(new StringsCompleter(Translator.getMessageToExit()));
        inputReader.addCompleter(new AggregateCompleter(completers)); // completer
        inputReader.addCommandValidator(Translator.getMessageToExit()); // validator

        // prompt the user to enter something and reads the input
        String[] inputs = inputReader.readInput(Translator.getMessagePlanningPhase());

        if (inputs[0].equals(Translator.getMessageToExit())) {
            System.out.println("exiting from game ..."); // todo: for testing only
            // change screen
            //getCli().confirmExit(); // todo: actual code
        } else {
            String assistantValue = inputs[0];
            String assistantNamePrefix = "CARD_";
            System.out.println("sending to server: " + Assistant.valueOf(assistantNamePrefix + assistantValue)); // todo: for testing only
            // send message to server
            //getCli().getClientController().useAssistant(Assistant.valueOf(assistantNamePrefix + assistantValue)); // todo: actual code
        }

        getCli().setNextScreen(new MoveMotherNatureScreen(getCli())); // todo: testing only

    }
}
