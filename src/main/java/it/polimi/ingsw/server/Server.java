package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Match;
import it.polimi.ingsw.controller.NotValidArgumentException;

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

        int portNumber = Integer.parseInt(args[1]);

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
                //Todo: add client handler
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
     */
    public void addNewGame(Match match){
        String gameID = generateGameID();
        synchronized (games){
            if (!games.containsKey(gameID)) {
                games.put(gameID, match);
                return;
            }
        }
        addNewGame(match);
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
        for (int i=0;i<length;i++){
            gameID.append(random.nextInt(10));
        }
        return gameID.toString();
    }
}
