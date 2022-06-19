package it.polimi.ingsw.client.view.cli.waiting;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;

/**
 * Shows to the client that a connection error occurred and he has to wait for the connection to be reestablished
 */
public class ConnectionErrorScreen extends WaitingScreen {

    public ConnectionErrorScreen(CLI cli) {
        super(cli, Translator.getConnectionError());
    }
}
