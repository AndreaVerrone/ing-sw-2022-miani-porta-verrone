package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.UserRequestExitException;
import it.polimi.ingsw.network.VirtualView;
import org.jline.reader.impl.completer.EnumCompleter;

/**
 * A class to handle the client ui in the console
 */
public class CLI implements VirtualView {

    /**
     * The controller of the client of this view
     */
    private ClientController clientController;

    /**
     * The current screen that must be shown to the client
     */
    private CliScreen currentScreen;


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

    public void setCurrentScreen(CliScreen screen){
        currentScreen = screen;
        currentScreen.show();
    }
    /**
     * Prompt the user to choose in which language he wants to play.
     */
    public static void chooseLanguage(){
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
}
