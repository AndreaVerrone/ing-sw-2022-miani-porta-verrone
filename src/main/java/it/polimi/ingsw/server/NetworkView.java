package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ReducedPlayerLoginInfo;
import it.polimi.ingsw.network.NetworkSender;
import it.polimi.ingsw.network.VirtualView;
import it.polimi.ingsw.network.messages.servertoclient.CurrentPlayerChanged;
import it.polimi.ingsw.network.messages.servertoclient.CurrentStateChanged;
import it.polimi.ingsw.network.messages.servertoclient.game.*;
import it.polimi.ingsw.network.messages.servertoclient.matchmaking.*;
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
    public void createGameView(Collection<ReducedPlayerLoginInfo> playerLoginInfos, int numPlayers, boolean isExpert) {
        sender.sendMessage(new GameEntered(playerLoginInfos, numPlayers, isExpert));
    }

    @Override
    public void changeCurrentState(StateType stateType) {
        sender.sendMessage(new CurrentStateChanged(stateType));
    }

    @Override
    public void addCoinOnCard(CharacterCardsType characterCardsType, boolean coinOnCard) {
        sender.sendMessage(new CoinOnCardAdded(characterCardsType, coinOnCard));
    }

    @Override
    public void addStudentsOnCard(CharacterCardsType characterCardType, StudentList actualStudents) {
        sender.sendMessage(new StudentsOnCardAdded(characterCardType, actualStudents));
    }

    @Override
    public void changeNumberOfPlayers(int numberOfPlayers) {
        sender.sendMessage(new NumPlayersChanged(numberOfPlayers));
    }

    @Override
    public void playersChanged(Collection<ReducedPlayerLoginInfo> players) {
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
        //TODO: send message
    }

    @Override
    public void changeAssistantDeck(String nickName, Collection<Assistant> actualDeck) {
        //TODO: send message

    }

    @Override
    public void changeCoinNumberInBag(int actualNumOfCoins) {
        sender.sendMessage(new CoinNumberInBagChanged(actualNumOfCoins));
    }

    @Override
    public void changeCoinNumber(String nickNameOfPlayer, int actualNumOfCoins) {
        //TODO: send message
    }

    @Override
    public void changeCurrentPlayer(String actualCurrentPlayerNickname) {
        sender.sendMessage(new CurrentPlayerChanged(actualCurrentPlayerNickname));
    }

    @Override
    public void changeTowerNumber(String nickName, int numOfActualTowers) {
        //TODO: send message

    }

    @Override
    public void conquerIslandObserver() {
        //TODO: send message

    }

    @Override
    public void emptyStudentBag() {
        sender.sendMessage(new LastRound());
    }

    @Override
    public void islandNumberChanged(int actualNumOfIslands) {
        //TODO: send message

    }

    @Override
    public void islandUnification(int islandID, int islandRemovedID, int finalSize) {
        //TODO: send message

    }

    @Override
    public void changeLastAssistantUsed(String nickName, Assistant actualLastAssistant) {
        sender.sendMessage(new AssistantUsed(nickName, actualLastAssistant));
    }

    @Override
    public void changeMotherNaturePosition(int actualMotherNaturePosition) {
        sender.sendMessage(new MotherNatureMoved(actualMotherNaturePosition));
    }

    @Override
    public void changeProfessor(String nickName, Collection<PawnType> actualProfessors) {
        sender.sendMessage(new ProfessorChanged(nickName, actualProfessors));
    }

    @Override
    public void changeStudentsInDiningRoom(String nickname, StudentList actualStudents) {
        //TODO: send message

    }

    @Override
    public void changeStudentsOnCloud(int cloudID, StudentList actualStudentList) {
        //TODO: send message

    }

    @Override
    public void changeStudentsOnEntrance(String nickname, StudentList actualStudents) {
        //TODO: send message

    }

    @Override
    public void changeStudentsOnIsland(int islandID, StudentList actualStudents) {
        //TODO: send message

    }

    @Override
    public void changeTowerOnIsland(int islandIDWithChange, TowerType actualTower) {
        sender.sendMessage(new TowerOnIslandChanged(actualTower, islandIDWithChange));
    }

    @Override
    public void endGame(Collection<String> winners) {
        sender.sendMessage(new GameEnded(winners));
    }
}
