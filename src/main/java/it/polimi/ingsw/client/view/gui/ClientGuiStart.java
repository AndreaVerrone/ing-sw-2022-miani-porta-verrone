package it.polimi.ingsw.client.view.gui;

public class ClientGuiStart {

        public static void main(String[] args) {
            ClientGui.main(args);
        }
}


/*

        // TODO: 09/05/2022 initialize view and ask for server parameters

        //code used for initial debugging
        Scanner scanner = new Scanner(System.in);

        System.out.println("Server port?");
        int socketPort = Integer.parseInt(scanner.nextLine());
        //end of code used for initial debugging

        ConnectionHandler connectionHandler;
        try {
            connectionHandler = new ConnectionHandler("localhost", socketPort);
        } catch (IOException e){
            System.out.println("Can't reach the server!");
            return;
        }
        new Thread(connectionHandler).start();
*/


