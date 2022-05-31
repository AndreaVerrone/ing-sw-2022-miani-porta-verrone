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
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MoveStudentsPhaseChooseDestinationScreen extends CliScreen {

    /**
     * the name of the phase
     */
    private final String phase= Translator.getMoveStudentsPhaseChooseDestinationName();

    /**
     * the table of the game
     */
    private final Table table;

    private static final int MOVE_STUDENT_TO_DININGROOM = 1;

    private static final int MOVE_STUDENT_TO_ISLAND = 2;

    /**
     * The constructor od the class
     * @param cli the cli of the user
     * @param table the table of the game
     */
    protected MoveStudentsPhaseChooseDestinationScreen(CLI cli, Table table) {
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

    private void askIsland(){

        InputReader inputReader = new InputReader();
        inputReader.addCommandValidator("\\d\\d?|"+Translator.getMessageToExit());
        inputReader.addCompleter(new StringsCompleter(Translator.getMessageToExit()));


        //prompt the user to enter something and reads the input
        String[] inputs = inputReader.readInput(Translator.getMessageToAskIslandID());

        if (inputs[0].equals(Translator.getMessageToExit())) {
            System.out.println("exiting from game"); // todo: testing only
            // change screen
            // getCli().confirmExit(); // todo: actual code
        }else {
            int islandID;
            islandID=Integer.parseInt(inputs[0]);
            // send message to server
            System.out.println("sending to server to move student to: "+ islandID);
            // Position island = new Position(Location.ISLAND); // todo: actual code
            // island.setField(islandID);
            // getCli().getClientController().chooseDestination(island);
        }

    }

    public void askForAction() {

        InputReader inputReader = new InputReader();
        inputReader.addCommandValidator("1|2|"+Translator.getMessageToExit());
        inputReader.addCompleter(new StringsCompleter(Translator.getMessageToExit()));


        //prompt the user to enter something and reads the input
        String[] inputs = inputReader.readInput(Translator.getMessageToAskToChooseADestination());

        if (inputs[0].equals(Translator.getMessageToExit())) {
            System.out.println("exiting from game"); // todo: testing only
            // change screen
            // getCli().confirmExit(); // todo: actual code
        }else {

            int choice;
            choice=Integer.parseInt(inputs[0]);

            if(choice==MOVE_STUDENT_TO_DININGROOM){
                System.out.println("move to dining room");
                getCli().setNextScreen(new EndGameScreen(getCli(), List.of("player 1", "player 2"))); // todo: testing only
                // getCli().getClientController().chooseDestination(new Position(Location.DINING_ROOM)); // todo: actual code
                return;
            }

            if(choice==MOVE_STUDENT_TO_ISLAND){
                askIsland();
            }
        }

        getCli().setNextScreen(new EndGameScreen(getCli(), List.of("player 1", "player 2"))); // todo: testing only
    }
}

