package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.cli.CLI;

import java.util.Objects;

/**
 * The client that is running the game
 */
public class Client {


    public static void main(String[] args)
    {
        boolean isCli;
        if (args.length == 0)
            isCli = false;
        else
            isCli = Objects.equals(args[0], "-c");

        CLI ui = new CLI();
        new ClientController(ui);

    }


}
