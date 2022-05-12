package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.matchmaking.IMatchMaking;
import it.polimi.ingsw.controller.matchmaking.MatchMaking;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.TowerType;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.network.VirtualView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A class used as a common interface for the Matchmaking and Game
 */
public class Match implements IMatchMaking, IGame {

    /**
     * The Matchmaking of this match. After the game has started this will be null.
     */
    private MatchMaking matchMaking;

    /**
     * The game of this match. Before the game starts, this is null.
     */
    private Game game;

    /**
     * The views of the player in this match. All of this should be notified
     * when something in the match changes
     */
    private final Collection<VirtualView> playersView = new ArrayList<>();

    /**
     * Creates a new Match for the number of player specified using the expert rules if {@code wantExpert}
     * is {@code true}, or using the normal rules otherwise.
     *
     * @param numOfPlayers the number of players requested in this match
     * @param wantExpert   {@code true} if the expert rules must be used, {@code false} otherwise
     */
    public Match(int numOfPlayers, boolean wantExpert) {
        matchMaking = new MatchMaking(numOfPlayers, wantExpert);
    }

    /**
     * Subscribe the view of a client to be notified of changes in the game.
     * If the client already was subscribed to this, the method actually do nothing.
     * @param client the view of the client to add
     */
    public void addClient(VirtualView client){
        synchronized (playersView) {
            if (playersView.contains(client))
                return;
            playersView.add(client);
        }
    }

    /**
     * Unsubscribe the view of a client to not receive any more update from this game.
     * @param client the view of the client to remove
     */
    public void removeClient(VirtualView client){
        synchronized (playersView) {
            playersView.remove(client);
        }
    }


    /**
     * Gets the nickname of the player that need to play now.
     * @return the nickname of the current player
     */
    public String getCurrentPlayerNickname(){
        if (matchMaking != null)
            return matchMaking.getCurrentPlayer().getNickname();
        return game.getModel().getCurrentPlayer().getNickname();
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     */
    @Override
    public void setHardMode(boolean isHardMode) throws NotValidOperationException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        matchMaking.setHardMode(isHardMode);
    }

    /**
     * @throws NotValidOperationException if the game has started or {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void changeNumOfPlayers(int value) throws NotValidOperationException, NotValidArgumentException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        matchMaking.changeNumOfPlayers(value);
    }

    /**
     * @throws NotValidOperationException if the game has started or {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void addPlayer(String nickname) throws NotValidOperationException, NotValidArgumentException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        synchronized (this) {
            matchMaking.addPlayer(nickname);
        }
    }

    /**
     * @throws NotValidOperationException if the game has started or {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void removePlayer(String nickname) throws NotValidOperationException, NotValidArgumentException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        synchronized (this) {
            matchMaking.removePlayer(nickname);
        }
    }

    /**
     * @throws NotValidOperationException if the game has started or {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void setTowerOfPlayer(TowerType towerType) throws NotValidOperationException, NotValidArgumentException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        matchMaking.setTowerOfPlayer(towerType);
    }

    /**
     * @throws NotValidOperationException if the game has started or {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void setWizardOfPlayer(Wizard wizard) throws NotValidOperationException, NotValidArgumentException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        matchMaking.setWizardOfPlayer(wizard);
    }

    /**
     * @throws NotValidOperationException if the game has started or {@inheritDoc}
     */
    @Override
    public void next() throws NotValidOperationException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        matchMaking.next();
    }

    /**
     * @throws NotValidOperationException if the game has not started yet or {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void useAssistant(Assistant assistant) throws NotValidOperationException, NotValidArgumentException {
        if (game == null)
            throw new NotValidOperationException();
        game.useAssistant(assistant);
    }

    /**
     * @throws NotValidOperationException if the game has not started yet or {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void moveStudentToIsland(PawnType student, int islandID) throws NotValidOperationException, NotValidArgumentException {
        if (game == null)
            throw new NotValidOperationException();
        game.moveStudentToIsland(student, islandID);
    }

    /**
     * @throws NotValidOperationException if the game has not started yet or {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void moveStudentToDiningRoom(PawnType student) throws NotValidOperationException, NotValidArgumentException {
        if (game == null)
            throw new NotValidOperationException();
        game.moveStudentToDiningRoom(student);
    }

    /**
     * @throws NotValidOperationException if the game has not started yet or {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void moveMotherNature(int positions) throws NotValidOperationException, NotValidArgumentException {
        if (game == null)
            throw new NotValidOperationException();
        game.moveMotherNature(positions);
    }

    /**
     * @throws NotValidOperationException if the game has not started yet or {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void takeFromCloud(int cloudID) throws NotValidOperationException, NotValidArgumentException {
        if (game == null)
            throw new NotValidOperationException();
        game.takeFromCloud(cloudID);
    }
}
