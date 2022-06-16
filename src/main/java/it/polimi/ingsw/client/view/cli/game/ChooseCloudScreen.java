package it.polimi.ingsw.client.view.cli.game;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.Table;
import org.jline.reader.Completer;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * this screen needs to be displayed during the "choose cloud" stage of the action phase.
 */
public class ChooseCloudScreen extends CliScreen {

    /**
     * the name of the phase
     */
    private final String phase = Translator.getMessageChooseCloudPhase();

    /**
     * the table of the game
     */
    private final Table table;

    /**
     * The constructor od the class
     * @param cli the cli of the user
     */
    public ChooseCloudScreen(CLI cli) {
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

        canvas.setSubtitle(Translator.getMessageCurrentPlayer()+": "+currentPlayerNickname);
        canvas.show();

        askForAction();

    }

    /**
     * this method will ask the player to choose a cloud.
     */
    private void askForAction() {

        InputReader inputReader = new InputReader();

        // INPUT VALIDATOR AND INPUT COMPLETER
        // allowed and suggested inputs:

        // 1. the ID of the clouds that are on the table
        Collection<Completer> completers = new ArrayList<>();
        for (Integer i : table.getIdOfClouds()){
            inputReader.addCommandValidator(String.valueOf(i)); // validator
            completers.add(new StringsCompleter(String.valueOf(i))); // completer
        }

        // 2. the string to exit
        inputReader.addCommandValidator(Translator.getMessageToExit()); // validator
        completers.add(new StringsCompleter(Translator.getMessageToExit())); // completer
        inputReader.addCompleter(new AggregateCompleter(completers));

        //prompt the user to enter something and reads the input
        String[] inputs = inputReader.readInput(Translator.getMessageChooseCloudPhase());

        if (inputs[0].equals(Translator.getMessageToExit())) {
            // change screen
            getCli().confirmExit();
        }else {
            int cloudID;
             cloudID=Integer.parseInt(inputs[0]);
            // send message to server
            getCli().getClientController().takeStudentFromCloud(cloudID);
        }
    }
}
