package it.polimi.ingsw.network;

import it.polimi.ingsw.client.reduced_model.TableRecord;
import it.polimi.ingsw.server.controller.PlayerLoginInfo;
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

    void changeCurrentPlayerOrState(StateType stateType, String currentPlayer);

    void addCoinOnCard(CharacterCardsType characterCardsType, boolean coinOnCard);

    void addStudentsOnCard(CharacterCardsType characterCardType, StudentList actualStudents);

    void changeNumberOfPlayers(int numberOfPlayers);

    void playersChanged(Collection<PlayerLoginInfo> players);

    void towerSelected(String player, TowerType tower);

    void wizardSelected(String player, Wizard wizard);

    void changeNumberOfBansOnIsland(int islandIDWithBan, int actualNumOfBans);

    void changeAssistantDeck(String nickName, Collection<Assistant> actualDeck);

    void changeCoinNumberInBag(int actualNumOfCoins);

    void changeCoinNumber(String nickNameOfPlayer, int actualNumOfCoins);

    void changeTowerNumber(String nickName, int numOfActualTowers);

    void notifyLastRound();

    void islandNumberChanged(int actualNumOfIslands);

    void islandUnification(int islandID, int islandRemovedID, int finalSize);

    void changeLastAssistantUsed(String nickName, Assistant actualLastAssistant);

    void changeMotherNaturePosition(int actualMotherNaturePosition);

    void changeProfessor(String nickName, Collection<PawnType> actualProfessors);

    void changeStudentsInDiningRoom(String nickname, StudentList actualStudents);

    void changeStudentsOnCloud(int cloudID, StudentList actualStudentList);

    void changeStudentsOnEntrance(String nickname, StudentList actualStudents);

    void changeStudentsOnIsland(int islandID, StudentList actualStudents);

    void changeTowerOnIsland(int islandIDWithChange, TowerType actualTower);

    void endGame(Collection<String> winners);

    void gameCreated(TableRecord tableRecord);
}
