package it.polimi.ingsw.client.view.cli.launcher;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Column;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Text;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Widget;
import org.jline.reader.Completer;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A screen displaying all the possible games a user can join
 */
public class GamesListScreen extends CliScreen {

    /**
     * The IDs of the games
     */
    private final Collection<String> gameIds;

    /**
     * Creates a new screen that display all the possible games a user can join passed as an argument
     * @param cli the cli of the user
     * @param gameIds the IDs of the games the user can join
     */
    public GamesListScreen(CLI cli, Collection<String> gameIds){
        super(cli);
        this.gameIds = gameIds;

    }

    @Override
    public void show() {
        Collection<Widget> widgets = new ArrayList<>();
        for (String gameID : gameIds)
            widgets.add(new Text(gameID));
        Canvas canvas = getCli().getBaseCanvas();
        canvas.setSubtitle(Translator.getJoinGame());
        canvas.setContent(new Column(
                widgets.isEmpty() ?
                        List.of(new Text(Translator.getNoGamesToJoin())) : widgets));
        canvas.show();
        askGameToJoin();

    }

    private void askGameToJoin(){
        InputReader inputReader = new InputReader();
        Collection<Completer> completers = new ArrayList<>();
        for (String gameID:gameIds){
            inputReader.addCommandValidator(gameID);
            completers.add(new StringsCompleter(gameID));
        }
        completers.add(new StringsCompleter(Translator.getBack()));
        inputReader.addCompleter(new AggregateCompleter(completers));
        inputReader.addCommandValidator(Translator.getBack());
        String input = inputReader.readInput(Translator.getChooseGame())[0];
        if (input.equals(Translator.getBack())){
            getCli().setNextScreen(new HomeScreen(getCli()));
            return;
        }
        getCli().getScreenBuilder().build(ScreenBuilder.Screen.ASK_NICKNAME, input);
    }
}
