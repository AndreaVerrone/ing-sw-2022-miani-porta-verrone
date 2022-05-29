package it.polimi.ingsw.client.view.cli.game;

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
    private final String phase= "ACTION PHASE: move students";

    /**
     * the table of the game
     */
    private Table table;

    private int state = INITIAL_STATE;

    private static final int INITIAL_STATE = 0;

    private static final int MOVE_STUDENT_TO_DININGROOM_STATE = 1;

    private static final int MOVE_STUDENT_TO_ISLAND_STATE = 2;

    /**
     * The constructor od the class
     * @param cli the cli of the user
     * @param table the table of the game
     */
    protected MoveStudentsPhaseScreen(CLI cli, Table table) {
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


    private void askStudentToMoveFromEntrance(){

        InputReader inputReader = new InputReader();
        inputReader.addCommandValidator("blue|green|yellow|red|pink|exit");

        Collection<String> commands = List.of("blue", "green", "yellow","red","pink","exit");
        Collection<Completer> completers = new ArrayList<>();
        for (String command : commands){
            completers.add(new StringsCompleter(command));
        }
        inputReader.addCompleter(new AggregateCompleter(completers));

        while (true) {
            //prompt the user to enter something and reads the input
            String[] inputs = inputReader.readInput("enter the color of the student to move");

            switch (inputs[0]) {
                case "blue" -> {
                    getCli().getClientController().chooseStudentFromLocation(PawnType.BLUE_UNICORNS, new Position(Location.ENTRANCE));
                }
                case "green" -> {
                    getCli().getClientController().chooseStudentFromLocation(PawnType.GREEN_FROGS, new Position(Location.ENTRANCE));

                }
                case "yellow" ->{
                    getCli().getClientController().chooseStudentFromLocation(PawnType.YELLOW_GNOMES, new Position(Location.ENTRANCE));

                }
                case "red" ->{
                    getCli().getClientController().chooseStudentFromLocation(PawnType.RED_DRAGONS, new Position(Location.ENTRANCE));
                }
                case "pink" ->{
                    getCli().getClientController().chooseStudentFromLocation(PawnType.PINK_FAIRIES, new Position(Location.ENTRANCE));
                }
            }
            state=INITIAL_STATE;
            return;

        }
    }

    public void askStudentToMoveToIsland(){

        InputReader inputReader = new InputReader();
        inputReader.addCommandValidator("\\d\\d?|exit");
        inputReader.addCompleter(new StringsCompleter("exit"));

        while (true) {
            //prompt the user to enter something and reads the input
            String[] inputs = inputReader.readInput("insert the id of the island to put the student");

            if (inputs[0].equals("exit")) {
                // System.out.println("exiting from game");
                // change screen
                getCli().confirmExit();
            }else {
                int islandID;
                try {
                    islandID=Integer.parseInt(inputs[0]);
                }catch (NumberFormatException e){
                    continue;
                }
                // send message to server
                // System.out.println("sending to server to move MN of: "+ numOfMovements);
                Position island = new Position(Location.ISLAND);
                island.setField(islandID);
                getCli().getClientController().chooseDestination(island);
            }
            return;
        }
    }

    private void askForAction() {

        InputReader inputReader = new InputReader();
        inputReader.addCommandValidator("1|2|exit");

        Collection<String> commands = List.of("1","2","exit");
        Collection<Completer> completers = new ArrayList<>();
        for (String command : commands){
            completers.add(new StringsCompleter(command));
        }
        inputReader.addCompleter(new AggregateCompleter(completers));


        //prompt the user to enter something and reads the input
        String[] inputs = inputReader.readInput("enter 1 to move student to dining room, 2 to move to island");

        if (inputs[0].equals("exit")) {
            // System.out.println("exiting from game");
            // change screen
            getCli().confirmExit();
        }else{
            state=Integer.parseInt(inputs[0]);
            switch (state) {
                case MOVE_STUDENT_TO_DININGROOM_STATE -> {
                    askStudentToMoveFromEntrance();
                    getCli().getClientController().chooseDestination(new Position(Location.DINING_ROOM));
                }
                case MOVE_STUDENT_TO_ISLAND_STATE -> {
                    askStudentToMoveFromEntrance();
                    askStudentToMoveToIsland();
                }
            }
        }
    }
}
