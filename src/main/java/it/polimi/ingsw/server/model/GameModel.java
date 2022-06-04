package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.model.gametable.GameTable;
import it.polimi.ingsw.server.model.gametable.Island;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.strategies.check_professor.CheckProfessorStandard;
import it.polimi.ingsw.server.model.strategies.check_professor.CheckProfessorStrategy;
import it.polimi.ingsw.server.model.strategies.influence.ComputeInfluenceStandard;
import it.polimi.ingsw.server.model.strategies.influence.ComputeInfluenceStrategy;
import it.polimi.ingsw.server.model.strategies.mother_nature.MotherNatureLimitStandard;
import it.polimi.ingsw.server.model.strategies.mother_nature.MotherNatureLimitStrategy;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.TowerType;
import it.polimi.ingsw.server.model.utils.exceptions.EmptyBagException;
import it.polimi.ingsw.server.model.utils.exceptions.IslandNotFoundException;
import it.polimi.ingsw.server.observers.ChangeCoinNumberInBagObserver;
import it.polimi.ingsw.server.observers.ChangeCurrentPlayerObserver;
import it.polimi.ingsw.server.observers.ConquerIslandObserver;
import it.polimi.ingsw.server.observers.EmptyStudentBagObserver;

import java.util.*;

public class GameModel {

    /**
     * The player that is currently playing this turn.
     */
    private Player currentPlayer;

    /**
     * The players in this game.
     */
    private final List<Player> players = new ArrayList<>();

    /**
     * This is the list of the players at the moment of the creation.
     * It is useful to handle the management of the turn of players.
     */
    private final List<Player> initialPlayerList;

    /**
     * The game table associated to this game.
     */
    private final GameTable gameTable;

    /**
     * Strategy to compute the influence on an island
     */
    private ComputeInfluenceStrategy computeInfluenceStrategy;

    /**
      * The strategy to use to compute {@code checkProfessor(studentColor)} method
     */
    private CheckProfessorStrategy checkProfessorStrategy;

    /**
     * Strategy to move mother nature
     */
    private MotherNatureLimitStrategy motherNatureLimitStrategy;

    /**
     * Add bag for coins
     */
    private final CoinsBag coinsBag;

    /**
     * Constructs a new game model with the {@code players} passed as a parameter.
     * The game is supported for 2, 3, 4 players.
     * @param playersLoginInfo the player playing this game
     */
    public GameModel(Collection<PlayerLoginInfo> playersLoginInfo){

        assert playersLoginInfo.size() >= 2 && playersLoginInfo.size() <= 4 : "Number of players not supported";

        coinsBag = new CoinsBag();

        int numPlayers = playersLoginInfo.size();

        boolean isThreePlayerGame = numPlayers == 3;

        for(PlayerLoginInfo playerInfo :playersLoginInfo){
            this.players.add(new Player(playerInfo,isThreePlayerGame,coinsBag));
        }

        // the initial player list is equal to the list of the player at the moment of the creation.
        // and it will not be modified
        initialPlayerList=new ArrayList<>(players);

        gameTable = new GameTable(numPlayers);

        currentPlayer = this.players.get(0);

        computeInfluenceStrategy = new ComputeInfluenceStandard();

        this.checkProfessorStrategy = new CheckProfessorStandard(this);

        this.motherNatureLimitStrategy = new MotherNatureLimitStandard();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameTable getGameTable() {
        return gameTable;
    }

    /**
     * This method will return an unmodifiable view of the list of player.
     * @return the list of players
     */
    public Collection<Player> getPlayerList(){
        return Collections.unmodifiableList(players);
    }

    /**
     * This method allow to take one student from the bag and removing it.
     * @return the PawnType of the student extracted
     * @throws EmptyBagException if the bag is empty
     */
    public PawnType getStudentFromBag() throws EmptyBagException {
        return gameTable.getStudentFromBag();
    }

    /**
     * Change the strategy to calculate mother nature range of motion
     * @param strategy strategy to calculate mother nature range of motion
     */
    public void setMotherNatureLimitStrategy(MotherNatureLimitStrategy strategy) {
        this.motherNatureLimitStrategy = strategy;
    }

    /**
     * The maximum value of which mother nature can be moved.
     * This usually corresponds to the value on the last assistant card the player used.
     * @return The maximum value of which mother nature can be moved.
     */
    public int getMNMovementLimit(){
        return motherNatureLimitStrategy.getMNMovementLimit(currentPlayer);
    }

    public void setCheckProfessorStrategy(CheckProfessorStrategy checkProfessorStrategy){
        this.checkProfessorStrategy=checkProfessorStrategy;
    }

    /**
     * This method check if after the adding of a student, of the {@code PawnType} specified in the parameter,
     * in the table of the dining room of the current player, the professor must be given to the player.
     * If it is yes the professor will be given to the current player and removed from the previous owner
     * if there was one, otherwise the professor will be just added to the current player.
     *
     * @param studentColor the {@code PawnType} of the student that has been added in the dining room
     */
    public void checkProfessor(PawnType studentColor){
        checkProfessorStrategy.checkProfessor(studentColor);
    }

    /**
     * Set the strategy to calculate the influence on an island
     * @param strategy strategy to use for the calculation of the influence
     */
    public void setComputeInfluenceStrategy(ComputeInfluenceStrategy strategy) {
        computeInfluenceStrategy = strategy;
    }

    /**
     * Calculates the order of the players based on their last assistant card played, in ascending order.
     * After this call, the current player will be the first player calculated as before.
     * This method will compute the order of players to play the planning phase.
     */
    public void calculatePlanningPhaseOrder(){

        // this is the index of the first player of the action phase in the initial list of players
        int index=initialPlayerList.indexOf(players.get(0));

        int numOfIteration=0;
        for(int i=1;i<players.size();i++){
            players.set(i,nextPlayerInInitialList(index+numOfIteration));
            numOfIteration ++;
        }
        setCurrentPlayer(players.get(0));
    }

    /**
     * this method return the next player considering the initial list of player and
     * clockwise rotation.
     * @param index this is the index from which consider the next player
     * @return the next player in the list
     */
    private Player nextPlayerInInitialList(int index){
        return initialPlayerList.get((index+1)%(initialPlayerList.size()));
    }

    /**
     * Calculates the order of the players to play the action phase
     */
    public void calculateActionPhaseOrder(){
        players.sort(Comparator.comparingInt(o -> o.getLastAssistant().getValue()));
        setCurrentPlayer(players.get(0));
    }

    /**
     * Sets as current player the next in the player's order.
     * <p>
     * For example, if the player's order is
     * <pre>
     *     Player1 -> Player2 -> Player3
     * </pre>
     * and the current player is Player1, after this call the current player would be Player2.
     * <p>
     * For how the player's order is calculated, see {@link #calculateActionPhaseOrder()}.
     */
    public void nextPlayerTurn(){
        int currentPlayerPos = players.indexOf(currentPlayer);
        setCurrentPlayer(players.get(currentPlayerPos + 1));
    }

    /**
     * This method will set the player, passed as a parameter, as the current player.
     * @param newCurrentPlayer the player that will be the current player after the invocation of this method
     */
    private void setCurrentPlayer(Player newCurrentPlayer){
        currentPlayer=newCurrentPlayer;
        notifyChangeCurrentPlayerObservers(currentPlayer.getNickname());
    }

    /**
     * Calculates the influence of each player in order to possibly change the tower on the island
     * passed as a parameter. The tower is changed if there is no tower on the island or the tower on the island
     * is of different type from the one of the player with the highest influence.
     * <p>
     * If the tower changes, this will also check if the island must be unified with the ones nearby.
     * @param islandID the ID of the island
     * @throws IslandNotFoundException if the ID does not correspond to an existing island
     */
    public void conquerIsland(int islandID) throws IslandNotFoundException {
        Island island = gameTable.getIsland(islandID);

        if (island.getBan()>0){
            island.removeBan();
            // notify the observers that the method has been invoked
            // when there was a ban on the island
            notifyConquerIslandObserver();

            return;
        }
        Player maxInfluencePlayer = computeMaxPlayerInfluence(island);
        boolean towerHasChanged = changeTowerOn(island, maxInfluencePlayer);
        if(towerHasChanged)
            gameTable.checkForUnify(island);
    }

    /**
     * Calculates the influence of each player on the specified island and return the player
     * with the highest influence.
     * @param island the island on which to calculate the influence
     * @return the player with the highest influence
     */
    private Player computeMaxPlayerInfluence(Island island){
        Player maxInfluencePlayer = null;
        int maxInfluence = 0;

        for (Player player : players) {
            int influence = computeInfluence(player, island);
            if (influence < maxInfluence)
                continue;
            if (influence > maxInfluence) {
                maxInfluencePlayer = player;
                maxInfluence = influence;
                continue;
            }
            // player and max player have the same influence
            if (maxInfluencePlayer == null) // if there is no max player do nothing
                continue;
            if (player.getTowerType() == island.getTower()){ // if player controls the island
                maxInfluencePlayer = player;
                continue;
            }
            if (player.getTowerType() != island.getTower() &&
                    maxInfluencePlayer.getTowerType() != island.getTower()){ // if none of them control the island
                maxInfluencePlayer = null;
            }
        }

        return maxInfluencePlayer;
    }

    /**
     * Calculates the influence of a player on an island. The influence is based on the number of students
     * on the island for each type of professor the player controls, and the number of towers of the same color
     * of the player.
     * @param player the player that need the influence calculated
     * @param island the island on which to calculate the influence
     * @return the influence calculated
     */
    private int computeInfluence(Player player, Island island){
        return computeInfluenceStrategy.computeInfluence(player, island);
    }

    /**
     * Checks if the tower needs to be changed on the island based on the player that now has the highest
     * influence on that and return a boolean to indicate this. If the tower need to be changed,
     * this will change the tower and also move the right number of tower from the player on the island,
     * but also remove any tower that were previously there and return them to the right player.
     * @param island the island on which to check the change of tower
     * @param maxInfluencePlayer the player with the highest influence on that island
     * @return {@code true} if the tower has changed, {@code false}
     */
    private boolean changeTowerOn(Island island, Player maxInfluencePlayer){
        if (maxInfluencePlayer == null) //no predominant player
            return false;
        TowerType towerOnIsland = island.getTower();
        if (maxInfluencePlayer.getTowerType() == towerOnIsland) // the player already control the island
            return false;
        island.setTower(maxInfluencePlayer.getTowerType());
        maxInfluencePlayer.changeTowerNumber(-island.getSize());
        if (towerOnIsland == null) // there was no tower on the island
            return true;
        for (Player player : players){
            if (player.getTowerType() == towerOnIsland){
                player.changeTowerNumber(island.getSize());
                break;
            }
        }
        return true;
    }

    /**
     * This method will reset all the strategy to the standard
     */
    public void resetStrategy(){
        this.checkProfessorStrategy = new CheckProfessorStandard(this);
        this.computeInfluenceStrategy = new ComputeInfluenceStandard();
        this.motherNatureLimitStrategy = new MotherNatureLimitStandard();
    }
  
      // MANAGEMENT OF OBSERVERS ON CURRENT PLAYER
    /**
     * List of the observer on the current player
     */
    private final List<ChangeCurrentPlayerObserver> changeCurrentPlayerObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on current player.
     * @param observer the observer to be added
     */
    public void addChangeCurrentPlayerObserver(ChangeCurrentPlayerObserver observer){
        changeCurrentPlayerObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on current player.
     * @param observer the observer to be removed
     */
    public void removeChangeCurrentPlayerObserver(ChangeCurrentPlayerObserver observer){
        changeCurrentPlayerObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on current player.
     * @param actualCurrentPlayerNickname the actual current player's nickname
     */
    private void notifyChangeCurrentPlayerObservers(String actualCurrentPlayerNickname){
        for(ChangeCurrentPlayerObserver observer : changeCurrentPlayerObservers)
            observer.changeCurrentPlayerObserverUpdate(actualCurrentPlayerNickname);
    }

    // MANAGEMENT OF THE OBSERVERS ON CONQUER ISLAND
    /**
     * List of the observers on conquer island invocation when there is a ban on the island.
     */
    private final List<ConquerIslandObserver> conquerIslandObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on conquer island
     * invocation when there is a ban on the island.
     * @param observer the observer to be added
     */
    public void addConquerIslandObserver(ConquerIslandObserver observer){
        conquerIslandObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on conquer island
     * invocation when there is a ban on the island.
     * @param observer the observer to be removed
     */
    public void removeConquerIslandObserver(ConquerIslandObserver observer){
        conquerIslandObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that conquer island method has been invoked when there is
     * a ban on the island.
     */
    private void notifyConquerIslandObserver(){
        for(ConquerIslandObserver observer: conquerIslandObservers){
            observer.conquerIslandObserverUpdate();
        }
    }

    // METHODS TO ALLOW ATTACHING AND DETACHING OF OBSERVERS ON EMPTY STUDENT BAG

    /**
     * This method allows to add the observer, passed as a parameter, on empty student bag.
     * @param observer the observer to be added
     */
    public void addEmptyStudentBagObserver(EmptyStudentBagObserver observer){
        gameTable.addEmptyStudentBagObserver(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on empty student bag.
     * @param observer the observer to be removed
     */
    public void removeEmptyStudentBagObserver(EmptyStudentBagObserver observer){
        gameTable.removeEmptyStudentBagObserver(observer);
    }

    // METHODS TO ALLOW ATTACHING AND DETACHING OF OBSERVERS ON COINS BAG

    /**
     * This method allows to add the observer, passed as a parameter, on coins bag.
     * @param observer the observer to be added
     */
    public void addChangeCoinNumberInBagObserver(ChangeCoinNumberInBagObserver observer){
       coinsBag.addChangeCoinNumberInBagObserver(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on coins bag.
     * @param observer the observer to be removed
     */
    public void removeChangeCoinNumberInBagObserver(ChangeCoinNumberInBagObserver observer){
        coinsBag.removeChangeCoinNumberInBagObserver(observer);
    }
  
}
