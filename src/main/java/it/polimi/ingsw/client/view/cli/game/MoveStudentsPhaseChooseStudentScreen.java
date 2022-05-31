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

public class MoveStudentsPhaseChooseStudentScreen extends CliScreen {

    /**
     * the name of the phase
     */
    private final String phase= Translator.getMoveStudentsPhaseChooseStudentName();

    /**
     * the table of the game
     */
    private final Table table;

    /**
     * The constructor od the class
     * @param cli the cli of the user
     * @param table the table of the game
     */
    protected MoveStudentsPhaseChooseStudentScreen(CLI cli, Table table) {
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
            System.out.println("exiting from game"); // todo: for testing only
            // change screen
            // getCli().confirmExit(); // todo: actual code
        }else{
            if(inputs[0].equals(Translator.getColor().get(0))){
                System.out.println("move blue"); // todo: testing only
                getCli().setNextScreen(new MoveStudentsPhaseChooseDestinationScreen(getCli(),table)); // todo:  testing only
                // getCli().getClientController().chooseStudentFromLocation(PawnType.BLUE_UNICORNS, new Position(Location.ENTRANCE)); // todo: actual code
                return;
            }
            if(inputs[0].equals(Translator.getColor().get(1))){
                System.out.println("move green"); // todo: testing only
                getCli().setNextScreen(new MoveStudentsPhaseChooseDestinationScreen(getCli(),table)); // todo:  testing only
                // getCli().getClientController().chooseStudentFromLocation(PawnType.GREEN_FROGS, new Position(Location.ENTRANCE)); // todo: actual code
                return;
            }
            if(inputs[0].equals(Translator.getColor().get(2))){
                System.out.println("move yellow"); // todo: testing only
                getCli().setNextScreen(new MoveStudentsPhaseChooseDestinationScreen(getCli(),table)); // todo:  testing only
                // getCli().getClientController().chooseStudentFromLocation(PawnType.YELLOW_GNOMES, new Position(Location.ENTRANCE)); // todo: actual code
                return;
            }
            if(inputs[0].equals(Translator.getColor().get(3))){
                System.out.println("move red"); // todo: testing only
                getCli().setNextScreen(new MoveStudentsPhaseChooseDestinationScreen(getCli(),table)); // todo:  testing only
                // getCli().getClientController().chooseStudentFromLocation(PawnType.RED_DRAGONS, new Position(Location.ENTRANCE)); // todo: actual code
                return;
            }
            if(inputs[0].equals(Translator.getColor().get(4))){
                System.out.println("move pink"); // todo: testing only
                getCli().setNextScreen(new MoveStudentsPhaseChooseDestinationScreen(getCli(),table)); // todo:  testing only
                // getCli().getClientController().chooseStudentFromLocation(PawnType.PINK_FAIRIES, new Position(Location.ENTRANCE)); // todo: actual code
            }
        }
    }
}