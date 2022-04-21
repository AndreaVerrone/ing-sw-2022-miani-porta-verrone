package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gametable.GameTable;
import it.polimi.ingsw.model.gametable.Island;
import it.polimi.ingsw.model.gametable.exceptions.IslandNotFoundException;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;

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
     * The game table associated to this game.
     */
    private final GameTable gameTable;

    /**
     * The wizards available to be chosen by a player.
     */
    private final Set<Wizard> wizardsAvailable = new HashSet<>(List.of(Wizard.values()));

    /**
     * The color of tower available to be chosen by a player.
     */
    private final Set<TowerType> towersAvailable = new HashSet<>(List.of(TowerType.values()));

    /**
     * Constructs a new game model with the {@code players} passed as a parameter.
     * The game is supported for 2, 3, 4 players.
     * @param players the player playing this game
     */
    public GameModel(Collection<Player> players){
        assert players.size() >= 2 && players.size() <= 4 : "Number of players not supported";

        this.players.addAll(players);
        int numPlayers = players.size();
        gameTable = new GameTable(numPlayers);
        currentPlayer = this.players.get(0);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameTable getGameTable() {
        return gameTable;
    }

    /**
     * The wizards available to be chosen by a player.
     * <p>
     * Note: this should be used only to display the possibilities.
     * To modify the content use the appropriate methods.
     * @return the wizards available
     * @see #removeWizard(Wizard) 
     */
    public Set<Wizard> getWizardsAvailable() {
        return new HashSet<>(wizardsAvailable);
    }

    /**
     * The color of tower available to be chosen by a player.
     * <p>
     * Note: this should be used only to display the possibilities.
     * To modify the content use the appropriate methods.
     * @return the color of tower available
     * @see #removeTower(TowerType) 
     */
    public Set<TowerType> getTowersAvailable() {
        return new HashSet<>(towersAvailable);
    }

    public void removeWizard(Wizard wizard){
        assert wizardsAvailable.contains(wizard) : "The wizard is not available";

        wizardsAvailable.remove(wizard);
    }

    public void removeTower(TowerType tower){
        assert towersAvailable.contains(tower);

        towersAvailable.remove(tower);
    }

    /**
     * Calculates the order of the players based on their last assistant card played, in ascending order.
     * After this call, the current player will be the first player calculated as before.
     */
    public void calculatePlayersOrder(){
        players.sort(Comparator.comparingInt(o -> o.getLastAssistant().getValue()));
        currentPlayer = players.get(0);
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
     * For how the player's order is calculated, see {@link #calculatePlayersOrder()}.
     */
    public void nextPlayerTurn(){
        int currentPlayerPos = players.indexOf(currentPlayer);
        currentPlayer = players.get(currentPlayerPos + 1);
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
        int influence = 0;
        if (player.getTowerType() == island.getTower())
            influence += island.getSize();
        for (PawnType professor : player.getProfessors()){
            influence += island.numStudentsOf(professor);
        }
        return influence;
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
}
