package it.polimi.ingsw.client.view.cli.game;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.Table;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.model.utils.PawnType;
import org.jline.reader.Completer;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MoveStudentsPhaseScreen extends CliScreen {

    /**
     * the name of the phase
     */
    private final String phase= Translator.getMoveStudentsPhaseName();

    /**
     * the table of the game
     */
    private final Table table;

    private static final int MOVE_STUDENT_TO_DININGROOM = 1;

    private static final int MOVE_STUDENT_TO_ISLAND = 2;

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

        Canvas canvas = new Canvas();
        canvas.setContent(table);
        canvas.setTitle(phase);

        String currentPlayerNickname = getCli().getClientController().getNickNameCurrentPlayer();

        canvas.setSubtitle(currentPlayerNickname);
        canvas.show();

        askForAction();

    }

    private void askStudentToMove() {

        InputReader inputReader = new InputReader();

        // INPUT VALIDATOR AND INPUT COMPLETER
        // allowed and suggested inputs:
        // the colors and the string to exit
        Collection<String> commands = new ArrayList<>();
        commands.add((Translator.getMessageToExit()));
        commands.addAll(Translator.getColor());

        Collection<Completer> completers = new ArrayList<>();
        for (String command : commands){
            completers.add(new StringsCompleter(command));
            inputReader.addCommandValidator(command);
        }

        completers.add(new StringsCompleter(Translator.getMessageToExit()));
        inputReader.addCompleter(new AggregateCompleter(completers));


        //prompt the user to enter something and reads the input
        String[] inputs = inputReader.readInput(Translator.getMessageToAskToChooseAColor());

        if (inputs[0].equals(Translator.getMessageToExit())) {
            // System.out.println("exiting from game"); // todo: for testing only
            // change screen
            getCli().confirmExit(); // todo: actual code
        }else{
            if(inputs[0].equals(Translator.getColor().get(0))){
                // System.out.println("move blue"); // todo: testing only
                getCli().getClientController().chooseStudentFromLocation(PawnType.BLUE_UNICORNS, new Position(Location.ENTRANCE)); // todo: actual code
                return;
            }
            if(inputs[0].equals(Translator.getColor().get(1))){
                // System.out.println("move green"); // todo: testing only
                getCli().getClientController().chooseStudentFromLocation(PawnType.GREEN_FROGS, new Position(Location.ENTRANCE)); // todo: actual code
                return;
            }
            if(inputs[0].equals(Translator.getColor().get(2))){
                // System.out.println("move yellow"); // todo: testing only
                getCli().getClientController().chooseStudentFromLocation(PawnType.YELLOW_GNOMES, new Position(Location.ENTRANCE)); // todo: actual code
                return;
            }
            if(inputs[0].equals(Translator.getColor().get(3))){
                // System.out.println("move red"); // todo: testing only
                getCli().getClientController().chooseStudentFromLocation(PawnType.RED_DRAGONS, new Position(Location.ENTRANCE)); // todo: actual code
                return;
            }
            if(inputs[0].equals(Translator.getColor().get(4))){
                // System.out.println("move pink"); // todo: testing only
                getCli().getClientController().chooseStudentFromLocation(PawnType.PINK_FAIRIES, new Position(Location.ENTRANCE)); // todo: actual code
            }
        }
    }

    private void askIsland(){

        InputReader inputReader = new InputReader();

        // INPUT VALIDATOR AND INPUT COMPLETER
        // allowed and suggested inputs:

        // 1. the ID of the islands that are on the table
        Collection<Integer> islandsOnTable = table.getReducedIslands();
        Collection<Completer> completers = new ArrayList<>();
        for(Integer island : islandsOnTable){
            inputReader.addCommandValidator(String.valueOf(island));
            completers.add(new StringsCompleter(String.valueOf(island)));
        }
        completers.add(new StringsCompleter(Translator.getMessageToExit()));

        // 2. the string to exit
        inputReader.addCommandValidator(Translator.getMessageToExit());
        inputReader.addCompleter(new AggregateCompleter(completers));

        //prompt the user to enter something and reads the input
        String[] inputs = inputReader.readInput(Translator.getMessageToAskIslandID());

        if (inputs[0].equals(Translator.getMessageToExit())) {
            // System.out.println("exiting from game"); // todo: testing only
            // change screen
            getCli().confirmExit(); // todo: actual code
        }else {
            int islandID;
            islandID=Integer.parseInt(inputs[0]);
            // send message to server
            System.out.println("sending to server to move student to: "+ islandID);
            Position island = new Position(Location.ISLAND); // todo: actual code
            island.setField(islandID);
            getCli().getClientController().chooseDestination(island);
        }

    }

    public void askDestination() {

        InputReader inputReader = new InputReader();

        // INPUT VALIDATOR AND INPUT COMPLETER
        // allowed and suggested inputs:
        // 1. 1 or 2
        inputReader.addCommandValidator("1|2|"+Translator.getMessageToExit());
        // 2. the string to exit
        inputReader.addCompleter(new StringsCompleter(Translator.getMessageToExit()));

        //prompt the user to enter something and reads the input
        String[] inputs = inputReader.readInput(Translator.getMessageToAskToChooseADestination());

        if (inputs[0].equals(Translator.getMessageToExit())) {
            // System.out.println("exiting from game"); // todo: testing only
            // change screen
            getCli().confirmExit(); // todo: actual code
        }else {

            int choice;
            choice=Integer.parseInt(inputs[0]);

            if(choice==MOVE_STUDENT_TO_DININGROOM){
                System.out.println("move to dining room");
                // getCli().setNextScreen(new EndGameScreen(getCli(), List.of("player 1", "player 2"))); // todo: testing only
                getCli().getClientController().chooseDestination(new Position(Location.DINING_ROOM)); // todo: actual code
                return;
            }

            if(choice==MOVE_STUDENT_TO_ISLAND){
                askIsland();
            }
        }

        // getCli().setNextScreen(new EndGameScreen(getCli(), List.of("player 1", "player 2"))); // todo: testing only
    }

    private void askForAction() {
        // ask the student to move
        askStudentToMove();
        // ask the destination to move the selected student
        askDestination();
    }
}
