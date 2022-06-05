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

/**
 * this is the screen that needs to be displayed during the planning phase of the game
 */
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

    /**
     * this method will ask the player to use an assistant card.
     */
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
            // System.out.println("exiting from game ..."); // todo: for testing only
            // change screen
            getCli().confirmExit(); // todo: actual code
        } else {
            String assistantValue = inputs[0];
            String assistantNamePrefix = "CARD_";
            // System.out.println("sending to server: " + Assistant.valueOf(assistantNamePrefix + assistantValue)); // todo: for testing only
            // send message to server
            getCli().getClientController().useAssistant(Assistant.valueOf(assistantNamePrefix + assistantValue)); // todo: actual code
        }

        // todo: code for testing only
        //  <-- from here
        // getCli().getClientController().setAssistantsList(List.of(Assistant.CARD_9));

        // getCli().getClientController().setAssistantsUsed("player 1", Assistant.CARD_7);

        /*StudentList stud2 = new StudentList();
        try {
            stud2.changeNumOf(PawnType.BLUE_UNICORNS,1);
            stud2.changeNumOf(PawnType.GREEN_FROGS,1);
            stud2.changeNumOf(PawnType.RED_DRAGONS,1);
            stud2.changeNumOf(PawnType.PINK_FAIRIES,1);
        } catch (NotEnoughStudentException e) {
            throw new RuntimeException(e);
        }*/
        //getCli().getClientController().setClouds(1,stud2);

        // getCli().getClientController().setEntranceList("player 2", stud2);

        // getCli().getClientController().setDiningRoomList("player 2", stud2);

        // getCli().getClientController().setProfTableList("player 3", new ArrayList<>(List.of(PawnType.values())));

        // getCli().getClientController().setTowerColorList("player 1", TowerType.WHITE);

        // getCli().getClientController().setTowerNumberList("player 1", 30);

        // getCli().getClientController().setCoinNumberList("player 2", 999);

        // getCli().getClientController().updateBanOnIsland(1,2); // todo: there was a problem now fixed

        // getCli().getClientController().updateTowerType(1, TowerType.WHITE);

        // getCli().getClientController().updateStudents(1,stud2); // todo: there was a problem now fixed

        // getCli().getClientController().updateMotherNaturePosition(1); // todo: there was a problem now fixed

        // getCli().getClientController().islandUnification(1,2,1);

        //getCli().setNextScreen(new MoveMotherNatureScreen(getCli()));

        // getCli().displayMessage("yellow warning message");
        // getCli().displayErrorMessage("RED warning message");
        // <-- to here

    }
}

