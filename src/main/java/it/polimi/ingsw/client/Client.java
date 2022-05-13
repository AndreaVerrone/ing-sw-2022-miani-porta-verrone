package it.polimi.ingsw.client;

import java.io.IOException;
import java.util.Scanner;

/**
 * The client that is running the game
 */
public class Client {


    public static void main(String[] args)
    {

        // TODO: 09/05/2022 initialize view and ask for server parameters

        //code used for initial debugging
        Scanner scanner = new Scanner(System.in);

        System.out.println("Server port?");
        int socketPort = Integer.parseInt(scanner.nextLine());
        //end of code used for initial debugging

        ConnectionHandler connectionHandler;
        try {
            connectionHandler = ConnectionHandler.getInstance();
            connectionHandler.SetIpAndPort("localhost", socketPort);
        } catch (IOException e){
            System.out.println("Can't reach the server!");
            return;
        }
        new Thread(connectionHandler).start();
    }


}
