package it.polimi.ingsw.server;

import it.polimi.ingsw.network.NetworkSender;
import it.polimi.ingsw.network.VirtualView;
import it.polimi.ingsw.network.messages.clienttoserver.game.MoveMotherNature;
import it.polimi.ingsw.network.messages.servertoclient.CurrentPlayerChanged;
import it.polimi.ingsw.network.messages.servertoclient.CurrentStateChanged;
import it.polimi.ingsw.network.messages.servertoclient.game.*;
import it.polimi.ingsw.network.messages.servertoclient.matchmaking.NumPlayersChanged;
import it.polimi.ingsw.network.messages.servertoclient.matchmaking.PlayersChanged;
import it.polimi.ingsw.network.messages.servertoclient.matchmaking.TowerSelected;
import it.polimi.ingsw.network.messages.servertoclient.matchmaking.WizardSelected;
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
 * A class simulating the view server side, sending every update requested to a real view over the network
 */
public class NetworkView implements VirtualView {

    /**
     * The sender that need to be used to forward every request that this view receives
     */
    private final NetworkSender sender;

    /**
     * Creates a new view that simulates the behaviour of a real view server side,
     * but in fact it forwards every request over the network using the provided {@code NetworkSender}.
     * @param sender the sender by which forward the requests made
     */
    public NetworkView(NetworkSender sender) {
        this.sender = sender;
    }

    @Override
    public void changeCurrentState(StateType stateType) {
        sender.sendMessage(new CurrentStateChanged(stateType));
    }

    @Override
    public void addCoinOnCard(CharacterCardsType characterCardsType, boolean coinOnCard) {
    }

    @Override
    public void addStudentsOnCard(CharacterCardsType characterCardType, StudentList actualStudents) {

    }

    @Override
    public void changeNumberOfPlayers(int numberOfPlayers) {
        sender.sendMessage(new NumPlayersChanged(numberOfPlayers));
    }

    @Override
    public void playersChanged(Collection<PlayerLoginInfo> players) {
        sender.sendMessage(new PlayersChanged(players));
    }

    @Override
    public void towerSelected(String player, TowerType tower) {
        sender.sendMessage(new TowerSelected(player, tower));
    }

    @Override
    public void wizardSelected(String player, Wizard wizard) {
        sender.sendMessage(new WizardSelected(player, wizard));
    }

    @Override
    public void changeNumberOfBansOnIsland(int islandIDWithBan, int actualNumOfBans) {

    }

    @Override
    public void changeAssistantDeck(String nickName, Collection<Assistant> actualDeck) {

    }

    @Override
    public void changeCoinNumberInBag(int actualNumOfCoins) {
        sender.sendMessage(new CoinNumberInBagChanged(actualNumOfCoins));
    }

    @Override
    public void changeCoinNumber(String nickNameOfPlayer, int actualNumOfCoins) {
        sender.sendMessage(new CoinNumberOfPlayerChanged(nickNameOfPlayer, actualNumOfCoins));
    }

    @Override
    public void changeCurrentPlayer(String actualCurrentPlayerNickname) {
        sender.sendMessage(new CurrentPlayerChanged(actualCurrentPlayerNickname));
    }

    @Override
    public void changeTowerNumber(String nickName, int numOfActualTowers) {

    }

    @Override
    public void conquerIslandObserver() {

    }

    @Override
    public void emptyStudentBag() {
        sender.sendMessage(new LastRound());
    }

    @Override
    public void islandNumberChanged(int actualNumOfIslands) {

    }

    @Override
    public void islandUnification(int islandID, int islandRemovedID, int finalSize) {

    }

    @Override
    public void changeLastAssistantUsed(String nickName, Assistant actualLastAssistant) {
        sender.sendMessage(new AssistantUsed(nickName, actualLastAssistant));
    }

    @Override
    public void ChangeMotherNaturePosition(int actualMotherNaturePosition) {
        sender.sendMessage(new MotherNatureMoved(actualMotherNaturePosition));
    }

    @Override
    public void changeProfessor(String nickName, Collection<PawnType> actualProfessors) {
        sender.sendMessage(new ProfessorChanged(nickName, actualProfessors));
    }

    @Override
    public void changeStudentsInDiningRoom(String nickname, StudentList actualStudents) {

    }

    @Override
    public void changeStudentsOnCloud(int cloudID, StudentList actualStudentList) {

    }

    @Override
    public void changeStudentsOnEntrance(String nickname, StudentList actualStudents) {

    }

    @Override
    public void changeStudentsOnIsland(int islandID, StudentList actualStudents) {

    }

    @Override
    public void changeTowerOnIsland(int islandIDWithChange, TowerType actualTower) {
        sender.sendMessage(new TowerOnIslandChanged(actualTower, islandIDWithChange));
    }
}
