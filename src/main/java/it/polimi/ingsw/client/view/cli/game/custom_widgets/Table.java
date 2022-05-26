package it.polimi.ingsw.client.view.cli.game.custom_widgets;

import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.*;

public class Table extends StatefulWidget {

    /**
     * the list of the Assistant card that are in the deck.
     */
    private List<Assistant> assistantsList;

    /**
     * the list of the Assistant card that are in the deck.
     */
    private List<Assistant> assistantsUsed;

    /**
     * a map containing the IDs of the clouds and the corresponding student list.
     */
    Map<Integer, StudentList> clouds;

    /**
     * map owner-entrance
     */
    private Map<String, StudentList> entranceList;

    /**
     * map owner-dining Room
     */
    private Map<String, StudentList> diningRoomList;

    /**
     * map owner-professors
     */
    private Map<String, Collection<PawnType>> profTableList;

    /**
     * map owner-tower type
     */
    private Map<String, TowerType> towerColorList;

    /**
     * map owner-tower number
     */
    private Map<String, Integer> towerNumberList;

    /**
     * map owner-coin number
     */
    private Map<String, Integer> coinNumberList;

    /**
     * The list of the nickname of the players
     */
    private final List<String> players;


    // GETTER
    public List<Assistant> getAssistantsList() {
        return new ArrayList<>(this.assistantsList);
    }

    public List<Assistant> getAssistantsUsed() {
        return assistantsUsed;
    }

    public Map<Integer, StudentList> getClouds() {
        return new HashMap<>(this.clouds);
    }

    public Map<String, StudentList> getEntranceList() {
        return new HashMap<>(this.entranceList);
    }

    public Map<String, StudentList> getDiningRoomList() {
        return new HashMap<>(this.diningRoomList);
    }

    public Map<String, Collection<PawnType>> getProfTableList() {
        return new HashMap<>(this.profTableList);
    }

    public Map<String, TowerType> getTowerColorList() {
        return new HashMap<>(this.towerColorList);
    }

    public Map<String, Integer> getTowerNumberList() {
        return new HashMap<>(this.towerNumberList);
    }

    public Map<String, Integer> getCoinNumberList() {
        return new HashMap<>(this.coinNumberList);
    }

    public List<String> getPlayers() {
        return new ArrayList<>(this.players);
    }

    /**
     * The constructor of the class
     * @param assistantsList
     * @param assistantsUsed
     * @param clouds
     * @param entranceList
     * @param diningRoomList
     * @param profTableList
     * @param towerColorList
     * @param towerNumberList
     * @param coinNumberList
     * @param players
     */
    public Table(List<Assistant> assistantsList, List<Assistant> assistantsUsed, Map<Integer, StudentList> clouds, Map<String, StudentList> entranceList, Map<String, StudentList> diningRoomList, Map<String, Collection<PawnType>> profTableList, Map<String, TowerType> towerColorList, Map<String, Integer> towerNumberList, Map<String, Integer> coinNumberList, List<String> players) {
        this.assistantsList = assistantsList;
        this.assistantsUsed = assistantsUsed;
        this.clouds = clouds;
        this.entranceList = entranceList;
        this.diningRoomList = diningRoomList;
        this.profTableList = profTableList;
        this.towerColorList = towerColorList;
        this.towerNumberList = towerNumberList;
        this.coinNumberList = coinNumberList;
        this.players = players;

        create();
    }


    public void setAssistantsList(List<Assistant> assistantsList) {
        setState(()->this.assistantsList = assistantsList);
    }

    public void setAssistantsUsed(Assistant assistantUsed) {
        List <Assistant> newAssistantList = getAssistantsList();
        newAssistantList.add(assistantUsed);

        setState(()-> this.assistantsList = newAssistantList);
    }

    public void setClouds(int ID, StudentList studentList) {
        Map<Integer, StudentList> newCloudStudentListMap = getClouds();
        newCloudStudentListMap.put(ID,studentList);
        setState(()->this.clouds = newCloudStudentListMap);
    }

    public void setEntranceList(String owner, StudentList studentsInEntrance) {
        Map<String, StudentList> newEntranceMap = getEntranceList();
        newEntranceMap.put(owner,studentsInEntrance);
        setState(()-> this.entranceList = newEntranceMap);

    }

    public void setDiningRoomList(String owner, StudentList studentsInDiningRoom) {
        Map<String, StudentList> newDiningRoomMap = getDiningRoomList();
        newDiningRoomMap.put(owner,studentsInDiningRoom);
        setState(()-> this.diningRoomList=newDiningRoomMap);

    }

    public void setProfTableList(String owner, Collection<PawnType> professors) {
        Map<String, Collection<PawnType>> newProfTableMap = getProfTableList();
        newProfTableMap.put(owner,professors);
        setState(()-> this.profTableList=newProfTableMap);

    }

    public void setTowerColorList(String owner, TowerType towerType) {
        Map<String, TowerType> newTowerColorMap = getTowerColorList();
        newTowerColorMap.put(owner,towerType);
        setState(()->this.towerColorList=newTowerColorMap);
    }

    public void setTowerNumberList(String owner, int numOfTowers) {
        Map<String, Integer> newTowerNumberMap = getTowerNumberList();
        newTowerNumberMap.put(owner,numOfTowers);
        setState(()->towerNumberList=newTowerNumberMap);
    }

    public void setCoinNumberList(String owner, int numOfCoins) {
        Map<String, Integer> newCoinNumberMap = getCoinNumberList();
        newCoinNumberMap.put(owner,numOfCoins);
        setState(()->coinNumberList=newCoinNumberMap);
    }


    /**
     * A method used to define by which Widgets this StatefulWidget is composed.
     * This method is run every time something in the content change or when it should be
     * displayed, so no heavy processes should be done inside it.
     *
     * @return a Widget describing how this should be drawn on screen
     */
    @Override
    protected Widget build() {

        // header
        Text header = new Text("TABLE");

        // 1. card used
        Deck cardUsed = new Deck(assistantsUsed,"ASSISTANTS USED");

        // 2. deck
        Deck deck = new Deck(assistantsList, "DECK");

        // 3. character card deck

        // 4. school board list
        SchoolBoardList schoolBoardList = new SchoolBoardList(
                entranceList,
                diningRoomList,
                profTableList,
                towerColorList,
                towerNumberList,
                coinNumberList,
                players
        );

        // 5. islands

        // 6. clouds
        CloudsSet cloudsOnTable = new CloudsSet(clouds);

        return new Column(List.of(
                header,
                cardUsed,
                deck,
                schoolBoardList,
                cloudsOnTable)
        );

    }
}
