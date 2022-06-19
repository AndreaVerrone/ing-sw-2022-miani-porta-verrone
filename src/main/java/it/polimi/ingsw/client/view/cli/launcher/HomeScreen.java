package it.polimi.ingsw.client.view.cli.launcher;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
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
public class HomeScreen extends CliScreen {

    /**
     * Creates a new home screen showing the user the base options.
     * @param cli the cli of the user
     */
    public HomeScreen(CLI cli) {
        super(cli);
    }

    @Override
    public void show() {

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
        Canvas canvas = getCli().getBaseCanvas();
        canvas.setContent(content);
        canvas.show();

        askForAction();
    }

    private void askForAction(){
        InputReader inputReader = new InputReader();
        Collection<String> commands = List.of(
                Translator.getCreate(), Translator.getJoin(), Translator.getResume(), Translator.getExit());
        Collection<Completer> completers = new ArrayList<>();
        for (String command : commands){
            inputReader.addCommandValidator(command);
            completers.add(new StringsCompleter(command));
        }
        inputReader.addCompleter(new AggregateCompleter(completers));
        String command = inputReader.readInput(Translator.getChooseHomeAction())[0];
        if (command.equals(Translator.getCreate())) {
                int numPlayers = askNumOfPlayers();
                boolean wantExpertMode = askDifficulty();
                getCli().getClientController().createGame(numPlayers, wantExpertMode);

        } else if (command.equals(Translator.getJoin())) {
            getCli().getClientController().getGames();
        } else if (command.equals(Translator.getResume())) {
            getCli().getClientController().resumeGame();
        } else if (command.equals(Translator.getExit())) {
            getCli().confirmExit();
        }
    }

    private int askNumOfPlayers(){
        InputReader inputReader = new InputReader();
        inputReader.addCompleter(new AggregateCompleter(new StringsCompleter("2"), new StringsCompleter("3")));
        inputReader.addCommandValidator("(2|3)");
        String input = inputReader.readInput(Translator.getChooseNumPlayers())[0];
        return Integer.parseInt(input);
    }

    private boolean askDifficulty(){
        InputReader inputReader = new InputReader();
        inputReader.addCompleter(new AggregateCompleter(
                new StringsCompleter(Translator.getDifficulty(true)),
                new StringsCompleter(Translator.getDifficulty(false))));
        inputReader.addCommandValidator(Translator.getDifficulty(true));
        inputReader.addCommandValidator(Translator.getDifficulty(false));
        String input = inputReader.readInput(Translator.getChooseDifficulty())[0];
        return input.equals(Translator.getDifficulty(true));
    }
}
