package it.polimi.ingsw.server;

import it.polimi.ingsw.network.User;
import it.polimi.ingsw.network.messages.responses.ErrorCode;
import it.polimi.ingsw.server.controller.Match;
import it.polimi.ingsw.server.controller.NotValidArgumentException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * This is the main server of the game.
 */
public class Server {


    /**
     * This Map matches each unique user of a player with the game he is currently in.
     */
    private final Map<User, Match> players = new HashMap<>();

    /**
     * This Map matches each unique identifier with the corresponding game.
     */
    private final Map<String, Match> gamesMap = new HashMap<>();

    /**
     * This Map matches each unique game with the corresponding id.
     */
    private final Map<Match, String> gamesID = new HashMap<>();

    /**
     * This Map matches each unique user of a client with the nickname he has chosen in the game he is currently in.
     */
    private final Map<User, String> users = new HashMap<>();

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

        Scanner scanner = new Scanner(System.in);

        int portNumber = 0;
        while (portNumber == 0) {
            System.out.println("Choose a server port");
            int port;
            try {
                port = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("The server port must be a number between 1024 and 65535");
                continue;
            }
            if (port < 1024 || port > 65535) {
                System.out.println("The port must be between 1024 and 65535");
                continue;
            }
            portNumber = port;
        }

        try (ServerSocket serverSocket = new ServerSocket(portNumber)){
            while (true){
                System.out.println("Waiting for clients...");
                try {
                    Socket socket = serverSocket.accept();
                    socket.setSoTimeout(Server.SOKET_TIME_OUT * 1000);
                    new Thread(new ClientHandler(socket)).start();
                } catch (IOException e) {
                    System.out.println("Connection dropped!");
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred when trying to open the server socket");
            System.exit(1);
        }

    }

    /**
     * Deletes a game from the list of existing games.
     * @param match the game to delete
     */
    public void deleteGame(Match match){
        makeGameUnavailable(match);
        synchronized (gamesID) {
            gamesID.remove(match);
        }
        synchronized (players){
            players.values().removeIf(game -> game.equals(match));
        }
    }

    /**
     * Makes a game unavailable to accept more players and remove from the list of games showed to join.
     * @param match the game to remove from view
     */
    public void makeGameUnavailable(Match match){
        synchronized (gamesMap) {
            gamesMap.values().removeIf(value -> value.equals(match));
        }
    }

    /**
     * Adds a new player to an existing game.
     * <p>
     * This is used to save which player is in which game.
     * @param user the user to add
     * @param nickname the nickname of the player
     * @param match the game chosen
     */
    public void addPlayer(User user, String nickname, Match match) {
        synchronized (players){
            players.put(user, match);
        }
        synchronized (users) {
            users.put(user, nickname);
        }
    }

    /**
     * Gets the id of a particular match
     * @param match the match of which to get the id
     * @return the id of the match
     */
    String getIDOf(Match match) {
        synchronized (gamesID) {
            return gamesID.get(match);
        }
    }

    /**
     * Gets the nickname a user was using in the game he was playing
     * @param user the user of whom to get the nickname
     * @return the nickname of the user
     */
    String getNicknameOf(User user) {
        synchronized (users) {
            return users.get(user);
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
        while (true) {
            String gameID = generateGameID();
            synchronized (gamesMap) {
                if (gamesMap.containsKey(gameID))
                    continue;
            }
            synchronized (gamesMap) {
                gamesMap.put(gameID, match);
            }
            synchronized (gamesID) {
                gamesID.put(match, gameID);
            }
            return gameID;
        }
    }

    /**
     * Gets the game associated with the provided gameID.
     * @param gameID the ID of the game
     * @return the game associated with that ID
     * @throws NotValidArgumentException if there is no game with that ID
     */
    public Match getGame(String gameID) throws NotValidArgumentException {
        Match match;
        synchronized (gamesMap){
            match = gamesMap.get(gameID);
        }
        if (match == null)
            throw new NotValidArgumentException(ErrorCode.GAME_NOT_EXIST);
        return match;
    }

    /**
     * Gets an unmodifiable view of the games present in this server.
     * @return the games ID in this server
     */
    public Collection<String> getGames(){
        Set<String> gameIDs;
        synchronized (gamesMap){
            gameIDs = gamesMap.keySet();
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
            throw new NotValidArgumentException(ErrorCode.GAME_NOT_EXIST);
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
