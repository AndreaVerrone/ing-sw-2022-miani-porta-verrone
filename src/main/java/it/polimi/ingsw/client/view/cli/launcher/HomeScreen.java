package it.polimi.ingsw.client.view.cli.launcher;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.UserRequestExitException;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import org.jline.reader.Completer;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The home screen of the game
 */
public class HomeScreen implements CliScreen {

    /**
     * The canvas that show the content
     */
    private final Canvas canvas;

    /**
     * The cli of the user
     */
    private final CLI cli;

    /**
     * Creates a new home screen showing the user the base options.
     * @param cli the cli of the user
     */
    public HomeScreen(CLI cli) {
        this.cli = cli;
        Widget spacer = new SizedBox(0, 1);
        Widget content = new Column(List.of(
                new Text(Translator.getCreateGame()),
                spacer,
                new Text(Translator.getJoinGame()),
                spacer,
                new Text(Translator.getResumeGame()),
                spacer,
                new Text(Translator.getExit())
        ));
        canvas = cli.getBaseCanvas();
        canvas.setContent(content);
    }

    @Override
    public void show() {
        canvas.show();

        askForAction();
    }

    private void askForAction(){
        InputReader inputReader = new InputReader();
        Collection<String> commands = List.of("create", "join", "resume", "exit");
        Collection<Completer> completers = new ArrayList<>();
        for (String command : commands){
            inputReader.addCommandValidator(command);
            completers.add(new StringsCompleter(command));
        }
        inputReader.addCompleter(new AggregateCompleter(completers));
        try {
            String command = inputReader.readInput(Translator.getChooseHomeAction())[0];
            switch (command){
                case "create" -> {
                    int numPlayers = askNumOfPlayers();
                    if (numPlayers == -1) {
                        show();
                        return;
                    }
                    boolean wantExpertMode = askDifficulty();
                    cli.getClientController().createGame(numPlayers, wantExpertMode);
                }
                case "join" -> cli.getClientController().getGames();
                case "resume" -> cli.getClientController().resumeGame();
                case "exit" -> cli.confirmExit();
            }
        } catch (UserRequestExitException e){
            cli.confirmExit();
        }
    }

    private int askNumOfPlayers(){
        InputReader inputReader = new InputReader();
        inputReader.addCompleter(new AggregateCompleter(new StringsCompleter("2"), new StringsCompleter("3")));
        inputReader.addCommandValidator("(2|3)");
        try {
            String input = inputReader.readInput(Translator.getChooseNumPlayers())[0];
            return Integer.parseInt(input);
        } catch (UserRequestExitException e){
            return -1;
        }
    }

    private boolean askDifficulty(){
        InputReader inputReader = new InputReader();
        inputReader.addCompleter(new AggregateCompleter(
                new StringsCompleter("normal"),
                new StringsCompleter("expert")));
        inputReader.addCommandValidator("normal");
        inputReader.addCommandValidator("expert");
        try {
            String input = inputReader.readInput(Translator.getChooseDifficulty())[0];
            return input.equals("expert");
        } catch (UserRequestExitException e){
            return false;
        }
    }
}
