package it.polimi.ingsw.client.view.cli.game;

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
    private Table table;

    /**
     * The constructor od the class
     * @param cli the cli of the user
     * @param table the table of the game
     */
    protected PlanningPhaseScreen(CLI cli, Table table) {
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
        inputReader.addCommandValidator("play CARD_\\d|exit");

        List<Assistant> assistants = table.getAssistantsList();
        Collection<Completer> completers = new ArrayList<>();
        for (Assistant assistant : assistants) {
            completers.add(new StringsCompleter("play" + " " + assistant.name()));
        }
        completers.add(new StringsCompleter("exit"));
        inputReader.addCompleter(new AggregateCompleter(completers));

        while (true) {
            //prompt the user to enter something and reads the input
            String[] inputs = inputReader.readInput("enter \"play\" followed by assistant card name to use");

            if (inputs[0].equals("exit")) {
                // System.out.println("exiting from game");
                // change screen
                getCli().confirmExit();
            }else {
                String assistantToPlay = inputs[1];
                // System.out.println("sending to server: " + Assistant.valueOf(assistantToPlay));
                // send message to server
                getCli().getClientController().useAssistant(Assistant.valueOf(assistantToPlay));
            }
            return;
        }
    }
}

// ********** COME ERA PRIMA
        /*InputReader inputReader = new InputReader();
        inputReader.addCommandValidator("play \\d\\d?");

        List<Assistant> assistants = table.getAssistantsList();
        Collection<Completer> completers = new ArrayList<>();
        for (Assistant assistant:  assistants){
            completers.add(new StringsCompleter("play" + " " + String.valueOf(assistant.getValue())));
        }
        inputReader.addCompleter(new AggregateCompleter(completers));

        while (true) {
            //try {
            //prompt the user to enter something and reads the input
            String[] inputs = inputReader.readInput("enter \"play\" followed by assistant number to use");
            String s = inputs[1];

            //if the argument of the command is "exit" closes the program
            if (Objects.equals(s, "exit")) {
                return;
            }

            int numOfAssistant=0;

            try {
                numOfAssistant=Integer.parseInt(s);
            }catch (NumberFormatException e){
                continue;
            }

            // to eliminate switch
            // Assistant.valueOf("CARD_1");


            switch (numOfAssistant) {
                case 1 -> {
                    // send message
                    // notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_1);
                    System.out.println("Sending to server ... card 1");
                    return;
                }
                case 2 -> {
                    // send message
                    // notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_2);
                    System.out.println("Sending to server ... card 2");
                    return;
                }
                case 3 -> {
                    //notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_3);
                    System.out.println("Sending to server ... card 3");
                    return;
                }
                case 4 -> {
                    // notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_4);
                    System.out.println("Sending to server ... card 4");
                    return;
                }
                case 5 -> {
                    // notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_5);
                    System.out.println("Sending to server ... card 5");
                    return;
                }
                case 6 -> {
                    // notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_6);
                    System.out.println("Sending to server ... card 6");
                    return;
                }
                case 7 -> {
                    // notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_7);
                    System.out.println("Sending to server ... card 7");
                    return;
                }
                case 8 -> {
                    // notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_8);
                    System.out.println("Sending to server ... card 8");
                    return;
                }
                case 9 -> {
                    // notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_9);
                    System.out.println("Sending to server ... card 9");
                    return;
                }
                case 10 -> {
                    // notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_10);
                    System.out.println("Sending to server ... card 10");
                    return;
                }
                default -> inputReader.showErrorMessage("card not in your deck");
            }
            //} catch (UserRequestExitException e) {
            //return;
            //}
            */

