package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.matchmaking.IMatchMaking;
import it.polimi.ingsw.controller.matchmaking.MatchMaking;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.TowerType;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Wizard;

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
     * Creates a new Match for the number of player specified using the expert rules if {@code wantExpert}
     * is {@code true}, or using the normal rules otherwise.
     *
     * @param numOfPlayers the number of players requested in this match
     * @param wantExpert   {@code true} if the expert rules must be used, {@code false} otherwise
     */
    public Match(int numOfPlayers, boolean wantExpert) {
        matchMaking = new MatchMaking(numOfPlayers, wantExpert);
    }


    @Override
    public void setHardMode(boolean isHardMode) throws NotValidOperationException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        matchMaking.setHardMode(isHardMode);
    }

    /**
     * @throws NotValidOperationException if the game has started or {@inheritDoc}
     */
    @Override
    public void changeNumOfPlayers(int value) throws NotValidOperationException, NotValidArgumentException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        matchMaking.changeNumOfPlayers(value);
    }

    /**
     * @throws NotValidOperationException if the game has started or {@inheritDoc}
     */
    @Override
    public void addPlayer(String nickname) throws NotValidOperationException, NotValidArgumentException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        matchMaking.addPlayer(nickname);
    }

    /**
     * @throws NotValidOperationException if the game has started or {@inheritDoc}
     */
    @Override
    public void removePlayer(String nickname) throws NotValidOperationException, NotValidArgumentException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        matchMaking.removePlayer(nickname);
    }

    /**
     * @throws NotValidOperationException if the game has started or {@inheritDoc}
     */
    @Override
    public void setTowerOfPlayer(TowerType towerType) throws NotValidOperationException, NotValidArgumentException {
        if (matchMaking == null)
            throw new NotValidOperationException();
        matchMaking.setTowerOfPlayer(towerType);
    }

    /**
     * @throws NotValidOperationException if the game has started or {@inheritDoc}
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
     */
    @Override
    public void useAssistant(Assistant assistant) throws NotValidOperationException, NotValidArgumentException {
        if (game == null)
            throw new NotValidOperationException();
        game.useAssistant(assistant);
    }

    /**
     * @throws NotValidOperationException if the game has not started yet or {@inheritDoc}
     */
    @Override
    public void moveStudentToIsland(PawnType student, int islandID) throws NotValidOperationException, NotValidArgumentException {
        if (game == null)
            throw new NotValidOperationException();
        game.moveStudentToIsland(student, islandID);
    }

    /**
     * @throws NotValidOperationException if the game has not started yet or {@inheritDoc}
     */
    @Override
    public void moveStudentToDiningRoom(PawnType student) throws NotValidOperationException, NotValidArgumentException {
        if (game == null)
            throw new NotValidOperationException();
        game.moveStudentToDiningRoom(student);
    }

    /**
     * @throws NotValidOperationException if the game has not started yet or {@inheritDoc}
     */
    @Override
    public void moveMotherNature(int positions) throws NotValidOperationException, NotValidArgumentException {
        if (game == null)
            throw new NotValidOperationException();
        game.moveMotherNature(positions);
    }

    /**
     * @throws NotValidOperationException if the game has not started yet or {@inheritDoc}
     */
    @Override
    public void takeFromCloud(int cloudID) throws NotValidOperationException, NotValidArgumentException {
        if (game == null)
            throw new NotValidOperationException();
        game.takeFromCloud(cloudID);
    }
}
