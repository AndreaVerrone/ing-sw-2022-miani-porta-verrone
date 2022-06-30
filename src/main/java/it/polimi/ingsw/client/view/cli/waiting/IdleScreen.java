package it.polimi.ingsw.client.view.cli.waiting;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.waiting.WaitingScreen;

/**
 * A screen for displaying a generic waiting message
 */
public class IdleScreen extends WaitingScreen {

    public IdleScreen(CLI cli){
        super(cli, Translator.getWaitMessage());
    }
}
