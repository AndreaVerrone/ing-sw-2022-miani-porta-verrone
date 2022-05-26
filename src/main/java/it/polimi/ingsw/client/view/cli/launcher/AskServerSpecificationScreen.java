package it.polimi.ingsw.client.view.cli.launcher;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import org.jline.reader.impl.completer.StringsCompleter;

/**
 * A screen to ask the server attributes to create a connection
 */
public class AskServerSpecificationScreen extends CliScreen {

    /**
     * The IP address of the server
     */
    private String ipAddress = "localhost";
    /**
     * The port of the server
     */
    private int port;

    public AskServerSpecificationScreen(CLI cli) {
        super(cli);
    }

    @Override
    protected void show() {
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
            String sPort = inputReader.readInput(Translator.getChoosePort())[0];
            port = Integer.parseInt(sPort);
        }
    }
}
