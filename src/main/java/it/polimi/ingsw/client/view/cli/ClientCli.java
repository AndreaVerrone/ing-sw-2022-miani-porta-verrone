package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.ClientController;

/**
 * The client that is running the game
 */
public class ClientCli {

    public static void main(String[] args)
    {
        CLI cli = new CLI();
        new ClientController(cli);
    }
}
