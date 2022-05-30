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
    private final String phase= "PLANNING PAHSE";

    /**
     * the table of the game
     */
    private final Table table;

    /**
     * The constructor od the class
     * @param cli the cli of the user
     * @param table the table of the game
     */
    public PlanningPhaseScreen(CLI cli, Table table) {
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
        // 1. the number of the card to play
        inputReader.addCommandValidator("[1-9]|10"); // one number in [1-10]
        // 2. the string ("exit" or "esci") to exit the game
        inputReader.addCommandValidator(Translator.getMessageToExit());

        // INPUT COMPLETER
        // 1. list of player's deck
        List<Assistant> assistants = table.getAssistantsList();
        Collection<Completer> completers = new ArrayList<>();
        for (Assistant assistant : assistants) {
            completers.add(new StringsCompleter(String.valueOf(assistant.getValue())));
        }
        // 2. string to exit
        completers.add(new StringsCompleter(Translator.getMessageToExit()));
        inputReader.addCompleter(new AggregateCompleter(completers));

        // prompt the user to enter something and reads the input
        String[] inputs = inputReader.readInput(Translator.getMessagePlanningPhase());

        if (inputs[0].equals(Translator.getMessageToExit())) {
            // todo: remove before merge
            System.out.println("exiting from game ...");
            // change screen
            //getCli().confirmExit();
        } else {
            String assistantValue = inputs[0];
            // todo: remove before merge
            String assistantNamePrefix = "CARD_";
            System.out.println("sending to server: " + Assistant.valueOf(assistantNamePrefix + assistantValue));
            // send message to server
            //getCli().getClientController().useAssistant(Assistant.valueOf(assistantNamePrefix + assistantValue));
        }

    }
}

