package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.UserRequestExitException;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.Validator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;
import it.polimi.ingsw.network.VirtualView;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.EnumCompleter;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.Locale;

/**
 * A class to handle the client ui in the console
 */
public class CLI implements VirtualView, Runnable {

    /**
     * The controller of the client of this view
     */
    private ClientController clientController;

    /**
     * The current screen that must be shown to the client
     */
    private CliScreen currentScreen = new IdleScreen();

    /**
     * The next screen that must be shown to the client
     */
    private CliScreen nextScreen;

    /**
     * The title of the application
     */
    public static final String APP_TITLE = """
            ███████╗██████╗ ██╗   ██╗ █████╗ ███╗   ██╗████████╗██╗███████╗
            ██╔════╝██╔══██╗╚██╗ ██╔╝██╔══██╗████╗  ██║╚══██╔══╝██║██╔════╝
            █████╗  ██████╔╝ ╚████╔╝ ███████║██╔██╗ ██║   ██║   ██║███████╗
            ██╔══╝  ██╔══██╗  ╚██╔╝  ██╔══██║██║╚██╗██║   ██║   ██║╚════██║
            ███████╗██║  ██║   ██║   ██║  ██║██║ ╚████║   ██║   ██║███████║
            ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝   ╚═╝╚══════╝
            """;

    private boolean shouldStop = false;

    @Override
    public void run() {
        while (!shouldStop){
            if (nextScreen == null){
                currentScreen = new IdleScreen();
            } else {
                currentScreen = nextScreen;
                nextScreen = null;
            }
            currentScreen.show();
        }
    }

    /**
     * Attach this view to the specified controller, if not already attached to one.
     * @param clientController the controller of the client
     */
    public void attachTo(ClientController clientController){
        if (this.clientController == null)
            this.clientController = clientController;
    }

    public ClientController getClientController(){
        return clientController;
    }

    public void setNextScreen(CliScreen screen){
//        currentScreen = screen;
//        currentScreen.show();
        nextScreen = screen;
        currentScreen.setStop();
    }

    public Canvas getBaseCanvas(){
        Canvas canvas = new Canvas();
        canvas.setTitle(APP_TITLE);
        canvas.setTitleColor(Color.BRIGHT_CYAN);
        canvas.setSubtitle(Translator.getGameSubtitle());
        return canvas;
    }

    /**
     * Prompt the user to choose in which language he wants to play.
     */
    public void chooseLanguage(){
        InputReader inputReader = new InputReader();
        for (Translator.Language language : Translator.Language.values()){
            for (String code : language.getCodes())
                inputReader.addCommandValidator(code);
        }
        inputReader.addCompleter(new EnumCompleter(Translator.Language.class));
        try{
            String language = inputReader.readInput(Translator.getChooseLanguage())[0];
            Translator.setLanguage(Translator.Language.fromCode(language));
        } catch (UserRequestExitException e){
        }
    }

    /**
     * Prompt the user to confirm that he want to close the application
     */
    public void confirmExit(){
        InputReader inputReader = new InputReader();
        inputReader.addCompleter(new AggregateCompleter(new StringsCompleter("yes"), new StringsCompleter("no")));
        inputReader.setNumOfArgsValidator(Validator.isOfNum(0));
        try {
            String input = inputReader.readInput(Translator.getConfirmExit())[0];
            if (parseBoolean(input)) {
                shouldStop = true;
                currentScreen.setStop();
                clientController.closeApplication();
            }
            else
                setNextScreen(currentScreen);
        } catch (UserRequestExitException e){
            shouldStop = true;
            currentScreen.setStop();
            clientController.closeApplication();
        }
    }

    private boolean parseBoolean(String bool){
        return switch (bool.toLowerCase(Locale.ROOT)){
            case "y", "yes", "s", "si" -> true;
            default -> false;
        };
    }
}
