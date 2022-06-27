package it.polimi.ingsw.client.view.cli.game;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.Table;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import org.jline.reader.Completer;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * this screen needs to be displayed during the "move student" stage of the action phase.
 */
public class MoveStudentsPhaseScreen extends CliScreen {

    /**
     * the name of the phase
     */
    private final String phase= Translator.getMoveStudentsPhaseName();

    /**
     * the table of the game
     */
    private final Table table;

    /**
     * The constructor od the class
     * @param cli the cli of the user
     */
    public MoveStudentsPhaseScreen(CLI cli) {
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
     * this method will ask the player to move a student from the entrance of
     * the school board to a destination (island or dining room)
     */
    @Override
    protected void askAction() {

        InputReader inputReader = new InputReader();

        /*
         * the input requires 2 things: the color of the student to move and
         * the destination (i.e., where to move the student), therefore the
         * commands to use for the completer are divided in 2
         */

        // COMMANDS

        // commands for the first string of the input
        Collection<String> commandsFirstInputString = new ArrayList<>();
        // 1. the colors of the students to move
        commandsFirstInputString.add((Translator.getMessageToExit()));
        // 2. the message to exit
        // OSS: the player can decide also to exit, so the input can be only 1 word if it is the
        // string to exit the game
        commandsFirstInputString.addAll(Translator.getColor());
        commandsFirstInputString.add(Translator.getUseCard());

        // commands for the second string of the input
        Collection<String> commandsSecondInputString = new ArrayList<>();
        // 1. destination: islands
        Collection<Integer> islandsOnTable = table.getIdOfReducedIslands();
        for(Integer island : islandsOnTable){
            commandsSecondInputString.add(Translator.getIslandName() + island);
        }
        // 2. destination: dining room
        commandsSecondInputString.add(Translator.getDiningRoomLocationName());

        // THE COMPLETER
        // add the commands to create the argument completer
        Completer completer = new ArgumentCompleter(
                new StringsCompleter(commandsFirstInputString),
                new StringsCompleter(commandsSecondInputString)
        );
        // add the completer to the input reader
        inputReader.addCompleter(completer);

        // THE COMMAND VALIDATOR

        // create the regex string to validate the 2 inputs
        String regexFirstInputString = regexBuilder(Translator.getColor()); // string to exit or color
        String regexSecondInputString = regexBuilder(commandsSecondInputString); // destination (island or dining room)

        // create the regex to validate the input
        /*
         * it is allowed
         * - the string to exit alone or
         * - a color followed by a destination
         * the regex string is:
         * "(<string to exit>|((<one color>) (<one destination>)))"
         */
        inputReader.addCommandValidator("((" + regexFirstInputString + ")" +  " " + "(" + regexSecondInputString + "))");
        inputReader.addCommandValidator(Translator.getMessageToExit());
        inputReader.addCommandValidator(Translator.getUseCard());

        // ASK INPUT TO PLAYER
        // prompt the user to enter something and reads the input
        String[] inputs = inputReader.readInput(Translator.getMessageMoveStudentsPhase());

        // 1. check the first string of the input
        String firstStringOfInput = inputs[0];

        // if it is the string to exit -- > go to confirm exit screen
        if (firstStringOfInput.equals(Translator.getMessageToExit())) {
            // change screen
            getCli().confirmExit();
            return;
        }
        if (firstStringOfInput.equals(Translator.getUseCard())) {
            getCli().useCharacterCard();
            return;
        }
        // if it is not exit, take the color of the student to move
        getCli().getClientController().chooseStudentFromLocation(
                Translator.parseColor(firstStringOfInput), new Position(Location.ENTRANCE));

        // 2. check the second string of the input
        String secondStringOfInput = inputs[1];
        // if the destination is dining room
        if (secondStringOfInput.equals(Translator.getDiningRoomLocationName())) {
            getCli().getClientController().chooseDestination(new Position(Location.DINING_ROOM));
            return;
        }

        // if the destination is an island
        // take the number of the island from the island name
        // which is for example Island#1 or Isola#10 (in italian Isola#1 or Isola#10),
        // so the number of the island is/are the characters after the "#"
        // the ID of the chosen island
        int islandID = Integer.parseInt(secondStringOfInput.split("#")[1]);
        Position island = new Position(Location.ISLAND);
        island.setField(islandID);
        getCli().getClientController().chooseDestination(island);

    }

    /**
     * this method will create a regex string such that all the elements passed
     * in the collection of strings in the parameter are put in OR
     * <p>
     * Example:
     * <ul>
     *      <li> input: {"a","b","c"} </li>
     *      <li> output: {"a|b|c"} </li>
     * </ul>
     * </p>
     * @param strings the collection of strings to separate with a "|"
     * @return a string with elements separated by "|"
     */
    private String regexBuilder(Collection<String> strings){
        return String.join("|", strings);
    }
}
