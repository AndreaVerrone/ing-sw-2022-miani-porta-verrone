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
     * The IP address of the server
     */
    private String ipAddress = "localhost";
    /**
     * The port of the server
     */
    private int port;

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

        chooseIP();
        choosePort();
        getCli().getClientController().createConnection(ipAddress, port);

    }

    private void chooseIP(){
        InputReader inputReader = new InputReader();
        inputReader.addCommandValidator("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
        inputReader.addCommandValidator("localhost");
        inputReader.addCompleter(new StringsCompleter("localhost"));
        ipAddress = inputReader.readInput(Translator.getChooseIP())[0];
    }

    private void choosePort(){
        InputReader inputReader = new InputReader();
        inputReader.addCommandValidator("\\d{4,5}");
        while (port == 0) {
            try {
                String sPort = inputReader.readInput(Translator.getChoosePort())[0];
                port = Integer.parseInt(sPort);
            } catch (NumberFormatException e) {
                inputReader.showErrorMessage("The port number is not supported!");
            }
        }
    }
}
