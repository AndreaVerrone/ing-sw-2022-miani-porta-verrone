package it.polimi.ingsw.client.view.cli.launcher;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.UserRequestExitException;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.SizedBox;
import org.jline.reader.impl.completer.StringsCompleter;

/**
 * The launcher of the game in the cli
 */
public class Launcher implements CliScreen {

    /**
     * The IP address of the server
     */
    private String ipAddress = "localhost";
    /**
     * The port of the server
     */
    private int port;

    private final CLI cli;

    /**
     * Creates a new screen for the launcher of the application
     * @param cli the observer fo the IP and port of the server
     */
    public Launcher(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void show(){

        Canvas canvas = new Canvas();

        canvas.setTitle(CLI.APP_TITLE);
        canvas.setTitleColor(Color.BRIGHT_CYAN);
        canvas.setSubtitle(Translator.getGameSubtitle());
        canvas.setContent(new SizedBox(0, 10));
        canvas.show();

        try{
            Thread.sleep(500);
        }catch (InterruptedException e){

        }

        cli.chooseLanguage();

        canvas.setSubtitle(Translator.getGameSubtitle());
        canvas.show();

        chooseIP();
        choosePort();
        cli.getClientController().createConnection(ipAddress, port);


    }

    private void chooseIP(){
        InputReader inputReader = new InputReader();
        inputReader.addCommandValidator("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
        inputReader.addCommandValidator("localhost");
        inputReader.addCompleter(new StringsCompleter("localhost"));
        try {
            ipAddress = inputReader.readInput(Translator.getChooseIP())[0];
        } catch (UserRequestExitException e){
            // TODO: 22/05/2022 handle quit game
        }
    }

    private void choosePort(){
        InputReader inputReader = new InputReader();
        inputReader.addCommandValidator("\\d{4,5}");
        while (port == 0) {
            try {
                String sPort = inputReader.readInput(Translator.getChoosePort())[0];
                port = Integer.parseInt(sPort);
            } catch (UserRequestExitException e) {
                // TODO: 22/05/2022 handle quit game
            } catch (NumberFormatException e) {
                inputReader.showErrorMessage("The port number is not supported!");
            }
        }
    }
}
