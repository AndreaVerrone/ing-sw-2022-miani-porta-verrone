package it.polimi.ingsw.network;

import it.polimi.ingsw.client.ReducedPlayerLoginInfo;
import it.polimi.ingsw.client.reduced_model.TableRecord;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.Collection;

/**
 * An interface representing the view of the game
 */
public interface VirtualView {

    /**
     * Displays the home page of the game
     */
    void showHome();

    /**
     * Creates the initial view of the game (the matchmaking) using the parameter passed
     *
     * @param playerLoginInfos the list of players currently in the lobby
     * @param numPlayers       the number of players requested in the game
     * @param isExpert         if the game uses expert rules
     * @param currentPlayer the nickname of the current player
     */
    void createGameView(Collection<ReducedPlayerLoginInfo> playerLoginInfos, int numPlayers,
                        boolean isExpert, String currentPlayer);

    /**
     * Notifies that the towers and wizards available have been changed
     * @param towersAvailable the towers available
     * @param wizardsAvailable the wizards available
     */
    void choosePlayerParameter(Collection<TowerType> towersAvailable, Collection<Wizard> wizardsAvailable);

    /**
     * Notifies that the current player or the state of the game has changed
     * @param stateType the current state of the game
     * @param currentPlayer the current player
     */
    void currentPlayerOrStateChanged(StateType stateType, String currentPlayer);

    /**
     * Notifies that the passed card has been used and need to increase it's cost
     * @param characterCardsType the card used
     */
    void coinOnCardAdded(CharacterCardsType characterCardsType);

    /**
     * Notifies a change in the students on the specified card
     * @param characterCardType the card on which the changes happened
     * @param actualStudents the students on the card
     */
    void studentsOnCardChanged(CharacterCardsType characterCardType, StudentList actualStudents);

    /**
     * Notifies that the selected number of players for the game has changed
     * @param numberOfPlayers the new number of player requested
     */
    void numberOfPlayersChanged(int numberOfPlayers);

    /**
     * Notifies that the players in the matchmaking have been changed
     * @param players the actual players in the matchmaking
     */
    void playersChanged(Collection<ReducedPlayerLoginInfo> players);

    /**
     * Notifies that a player has selected the tower that he want to use in the game
     * @param player the player that made the decision
     * @param tower the tower selected
     */
    void towerSelected(String player, TowerType tower);

    /**
     * Notifies that a player has selected a wizard that he want to use in the game
     * @param player the player that made the decision
     * @param wizard the wizard selected
     */
    void wizardSelected(String player, Wizard wizard);

    /**
     * Notifies that the number of bans on the specified island has changed
     * @param islandIDWithBan the id of the island
     * @param actualNumOfBans the number of bans on that island
     */
    void numberOfBansOnIslandChanged(int islandIDWithBan, int actualNumOfBans);

    /**
     * Notifies that the deck of the specified player has changed
     * @param nickName the nickname of the player
     * @param actualDeck the deck of the player
     */
    void assistantDeckChanged(String nickName, Collection<Assistant> actualDeck);

    /**
     * Notifies that the number of coins in the bag has changed
     * @param actualNumOfCoins the new number of coins
     */
    void coinNumberInBagChanged(int actualNumOfCoins);

    /**
     * Notifies that the number of coins of a player has changed
     * @param nickNameOfPlayer the nickname of the player
     * @param actualNumOfCoins the number of coins
     */
    void coinNumberOfPlayerChanged(String nickNameOfPlayer, int actualNumOfCoins);

    /**
     * Notifies that the number of towers of a player has changed
     * @param nickName the nickname of the player
     * @param numOfActualTowers the number of towers
     */
    void towerNumberOfPlayerChanged(String nickName, int numOfActualTowers);

    /**
     * Notifies that the current round the clients are playing is the last of this game
     */
    void notifyLastRound();

    /**
     * Notifies that the total number of islands is changed
     * @param actualNumOfIslands the new number of islands
     */
    void islandNumberChanged(int actualNumOfIslands);

    /**
     * Notifies that two islands have been unified
     * @param islandID the id of the island that remained on the table
     * @param islandRemovedID the id of the island removed from the table
     * @param finalSize the size of the island removed
     */
    void islandsUnified(int islandID, int islandRemovedID, int finalSize);

    /**
     * Notifies that the last assistant played of a player has changed
     * @param nickName the nickname of the player
     * @param actualLastAssistant the last assistant that he used
     */
    void lastAssistantUsedChanged(String nickName, Assistant actualLastAssistant);

    /**
     * Notifies that the position of mother nature has changed
     * @param actualMotherNaturePosition the new position of mother nature
     */
    void motherNaturePositionChanged(int actualMotherNaturePosition);

    /**
     * Notifies that the professors of a player changed
     * @param nickName the nickname of the player
     * @param actualProfessors the professors he owns
     */
    void professorsOfPlayerChanged(String nickName, Collection<PawnType> actualProfessors);

    /**
     * Notifies that the students in the dining room of a player changed
     * @param nickname the nickname of the player
     * @param actualStudents the students in his dining room
     */
    void studentsInDiningRoomChanged(String nickname, StudentList actualStudents);

    /**
     * Notifies that the students on a cloud changed
     * @param cloudID the id of the cloud
     * @param actualStudentList the students on the cloud
     */
    void studentsOnCloudChanged(int cloudID, StudentList actualStudentList);

    /**
     * Notifies that the students in the entrance of a player changed
     * @param nickname the nickname of the player
     * @param actualStudents the students in his entrance
     */
    void studentsOnEntranceChanged(String nickname, StudentList actualStudents);

    /**
     * Notifies that the students on an island changed
     * @param islandID the id of the island
     * @param actualStudents the students on the island
     */
    void studentsOnIslandChanged(int islandID, StudentList actualStudents);

    /**
     * Notifies that the tower on an island changed
     * @param islandIDWithChange the id of the island
     * @param actualTower the new tower on that island
     */
    void towerOnIslandChanged(int islandIDWithChange, TowerType actualTower);

    /**
     * Notifies that the game has ended and who is the winner
     * @param winners a list containing the nicknames of the winners
     */
    void gameEnded(Collection<String> winners);

    /**
     * Notifies that the game has been created
     * @param tableRecord the state of the game at that moment
     */
    void gameCreated(TableRecord tableRecord);
}
