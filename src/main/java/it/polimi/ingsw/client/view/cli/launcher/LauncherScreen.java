package it.polimi.ingsw.client.view.cli.launcher;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.UserRequestExitException;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;
import org.jline.reader.impl.completer.StringsCompleter;

/**
 * The launcher of the game in the cli
 */
public class LauncherScreen extends CliScreen {

    /**
     * Creates a new screen for the launcher of the application
     * @param cli the cli of the user
     */
    public LauncherScreen(CLI cli) {
        super(cli);
    }

    @Override
    public void show(){

        Canvas canvas = getCli().getBaseCanvas();

        canvas.show();

        getCli().chooseLanguage();

        canvas.setSubtitle(Translator.getGameSubtitle());
        canvas.show();

        getCli().setNextScreen(new AskServerSpecificationScreen(getCli()));

    }
}
