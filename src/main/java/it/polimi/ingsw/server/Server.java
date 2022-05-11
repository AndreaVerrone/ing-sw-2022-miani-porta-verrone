package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Match;
import it.polimi.ingsw.controller.NotValidArgumentException;
import it.polimi.ingsw.network.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * This is the main server of the game.
 */
public class Server {


    /**
     * This Map matches each unique nickname of a player with the game he is currently in.
     */
    private final Map<User, Match> players = new HashMap<>();

    /**
     * This Map matches each unique identifier with the corresponding game.
     */
    private final Map<String, Match> games = new HashMap<>();

    /**
     * The instance of the server.
     */
    private static Server instance = null;

    /**
     * The time, in seconds, after that this socket will signal a time-out exception
     */
    private static final int SOKET_TIME_OUT = 15;

    /**
     * The time, in seconds, after that the socket client side will signal a time-out exception
     */
    protected static final int CLIENT_SOCKET_TIME_OUT = 10;

    private Server() {}

    /**
     * Gets the instance of the server.
     * @return the instance of the server
     */
    public static Server getInstance(){
        if (instance == null)
            instance = new Server();
        return instance;
    }


    public static void main(String[] args) {

        //code used for initial debugging
        Scanner scanner = new Scanner(System.in);

        System.out.println("Server port?");
        int portNumber = Integer.parseInt(scanner.nextLine());
        //end of code used for initial debugging

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println("An error occurred when trying to open the server socket");
            System.exit(1);
            return;
        }

        while (true){
            try {
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(Server.SOKET_TIME_OUT * 1000);
                new Thread(new ClientHandler(socket)).start();
            } catch (IOException e) {
                System.out.println("Connection dropped!");
            }
        }

    }


    /**
     * Adds a new player to an existing game.
     * <p>
     * This is used to save which player is in which game.
     * @param user the user to add
     * @param match the game chosen
     */
    public void addPlayer(User user, Match match) {
        synchronized (players){
            players.put(user, match);
        }
    }

    /**
     * Removes a player from a game.
     * <p>
     * More formally, this disassociates a player from the game he was previously in.
     * @param user the player to remove
     */
    public void removePlayer(User user) {
        synchronized (players){
            players.remove(user);
        }
    }

    /**
     * Adds a new game to the list of games in this server.
     * @param match the new game generated
     * @return the ID associated to the new game
     */
    public String addNewGame(Match match){
        String gameID = generateGameID();
        synchronized (games){
            if (!games.containsKey(gameID)) {
                games.put(gameID, match);
                return gameID;
            }
        }
        return addNewGame(match);
    }

    /**
     * Gets the game associated with the provided gameID.
     * @param gameID the ID of the game
     * @return the game associated with that ID
     * @throws NotValidArgumentException if there is no game with that ID
     */
    public Match getGame(String gameID) throws NotValidArgumentException {
        Match match;
        synchronized (games){
            match = games.get(gameID);
        }
        if (match == null)
            throw new NotValidArgumentException();
        return match;
    }

    /**
     * Gets an unmodifiable view of the games present in this server.
     * @return the games ID in this server
     */
    public Collection<String> getGames(){
        Set<String> gameIDs;
        synchronized (games){
            gameIDs = games.keySet();
        }
        return Collections.unmodifiableSet(gameIDs);
    }

    /**
     * Gets the game that the user was playing. This is used when a player disconnects from a game and
     * want to resume it.
     * @param user the player making the request
     * @return the game he was playing
     * @throws NotValidArgumentException if there is no game associated to that player
     */
    public Match resumeGame(User user) throws NotValidArgumentException {
        Match match;
        synchronized (players){
            match = players.get(user);
        }
        if (match == null)
            throw new NotValidArgumentException();
        return match;
    }

    private String generateGameID(){
        Random random = new Random();
        int length = 10;
        StringBuilder gameID = new StringBuilder(length);
        random.ints(length, 0, 10).forEach(gameID::append);
        return gameID.toString();
    }
}
