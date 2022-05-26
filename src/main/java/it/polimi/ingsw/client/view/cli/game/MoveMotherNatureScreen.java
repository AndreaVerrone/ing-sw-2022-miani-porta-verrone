package it.polimi.ingsw.client.view.cli.game;

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
    private final String phase= "ACTION PHASE: Move mother nature";

    /**
     * the table of the game
     */
    private Table table;

    /**
     * The constructor od the class
     * @param cli the cli of the user
     * @param table the table of the game
     */
    protected MoveMotherNatureScreen(CLI cli, Table table) {
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
        inputReader.addCommandValidator("\\d\\d?|exit");
        inputReader.addCompleter(new StringsCompleter("exit"));

        while (true) {
            //prompt the user to enter something and reads the input
            String[] inputs = inputReader.readInput("insert the number of step to move mother nature");

            if (inputs[0].equals("exit")) {
                // System.out.println("exiting from game");
                // change screen
                getCli().confirmExit();
            }else {
                int numOfMovements;
                try {
                    numOfMovements=Integer.parseInt(inputs[0]);
                }catch (NumberFormatException e){
                    continue;
                }
                // send message to server
                // System.out.println("sending to server to move MN of: "+ numOfMovements);
                getCli().getClientController().moveMotherNature(numOfMovements);
            }
            return;
        }
    }
}
