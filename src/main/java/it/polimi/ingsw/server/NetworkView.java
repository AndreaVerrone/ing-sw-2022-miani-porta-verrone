package it.polimi.ingsw.server;

import it.polimi.ingsw.client.reduced_model.ReducedPlayerLoginInfo;
import it.polimi.ingsw.client.reduced_model.TableRecord;
import it.polimi.ingsw.network.NetworkSender;
import it.polimi.ingsw.network.VirtualView;
import it.polimi.ingsw.network.messages.servertoclient.PlayerOrStateChanged;
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
    public void showHome() {
        // not used here
    }

    @Override
    public void createGameView(Collection<ReducedPlayerLoginInfo> playerLoginInfos, int numPlayers, boolean isExpert, String currentPlayer) {
        sender.sendMessage(new GameEntered(playerLoginInfos, numPlayers, isExpert, currentPlayer));
    }

    @Override
    public void currentPlayerOrStateChanged(StateType stateType, String currentPlayer) {
        sender.sendMessage(new PlayerOrStateChanged(currentPlayer, stateType));
    }

    @Override
    public void coinOnCardAdded(CharacterCardsType characterCardsType) {
        sender.sendMessage(new CoinOnCardAdded(characterCardsType));
    }

    @Override
    public void studentsOnCardChanged(CharacterCardsType characterCardType, StudentList actualStudents) {
        sender.sendMessage(new StudentsOnCardAdded(characterCardType, actualStudents));
    }

    @Override
    public void numberOfPlayersChanged(int numberOfPlayers) {
        sender.sendMessage(new NumPlayersChanged(numberOfPlayers));
    }

    @Override
    public void playersChanged(Collection<ReducedPlayerLoginInfo> players) {
        sender.sendMessage(new PlayersChanged(players));
    }

    @Override
    public void choosePlayerParameter(Collection<TowerType> towersAvailable, Collection<Wizard> wizardsAvailable) {
        sender.sendMessage(new ChoosePlayerParameters(towersAvailable, wizardsAvailable));
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
    public void numberOfBansOnIslandChanged(int islandIDWithBan, int actualNumOfBans) {
        sender.sendMessage(new BanOnIslandChanged(islandIDWithBan,actualNumOfBans));
    }

    @Override
    public void assistantDeckChanged(String nickName, Collection<Assistant> actualDeck) {
        sender.sendMessage(new DeckChanged(nickName,actualDeck));
    }

    @Override
    public void coinNumberInBagChanged(int actualNumOfCoins) {
        sender.sendMessage(new CoinNumberInBagChanged(actualNumOfCoins));
    }

    @Override
    public void coinNumberOfPlayerChanged(String nickNameOfPlayer, int actualNumOfCoins) {
        sender.sendMessage(new CoinInSchoolBoardChanged(nickNameOfPlayer,actualNumOfCoins));
    }

    @Override
    public void towerNumberOfPlayerChanged(String nickName, int numOfActualTowers) {
        sender.sendMessage(new TowerNumberChanged(nickName,numOfActualTowers));
    }

    @Override
    public void notifyLastRound() {
        sender.sendMessage(new LastRound());
    }

    @Override
    public void islandNumberChanged(int actualNumOfIslands) {
        // TODO: send message
        //  there is not a message since this observer is used to check
        //  the end of game, it maybe could be useful for the GUI ?
    }

    @Override
    public void islandsUnified(int islandID, int islandRemovedID, int finalSize) {
        sender.sendMessage(new IslandUnified(islandID,islandRemovedID,finalSize));
    }

    @Override
    public void lastAssistantUsedChanged(String nickName, Assistant actualLastAssistant) {
        sender.sendMessage(new AssistantUsed(nickName, actualLastAssistant));
    }

    @Override
    public void motherNaturePositionChanged(int actualMotherNaturePosition) {
        sender.sendMessage(new MotherNatureMoved(actualMotherNaturePosition));
    }

    @Override
    public void professorsOfPlayerChanged(String nickName, Collection<PawnType> actualProfessors) {
        sender.sendMessage(new ProfessorChanged(nickName, actualProfessors));
    }

    @Override
    public void studentsInDiningRoomChanged(String nickname, StudentList actualStudents) {
        sender.sendMessage(new StudentsInDiningRoomChanged(nickname,actualStudents));
    }

    @Override
    public void studentsOnCloudChanged(int cloudID, StudentList actualStudentList) {
        sender.sendMessage(new StudentsOnCloudChanged(cloudID,actualStudentList));
    }

    @Override
    public void studentsOnEntranceChanged(String nickname, StudentList actualStudents) {
        sender.sendMessage(new StudentsOnEntranceChanged(nickname,actualStudents));
    }

    @Override
    public void studentsOnIslandChanged(int islandID, StudentList actualStudents) {
        sender.sendMessage(new StudentsOnIslandChanged(islandID,actualStudents));
    }

    @Override
    public void towerOnIslandChanged(int islandIDWithChange, TowerType actualTower) {
        sender.sendMessage(new TowerOnIslandChanged(actualTower, islandIDWithChange));
    }

    @Override
    public void gameEnded(Collection<String> winners) {
        sender.sendMessage(new GameEnded(winners));
    }

    @Override
    public void gameCreated(TableRecord tableRecord) {
        sender.sendMessage(new TableCreated(tableRecord));
    }
}
