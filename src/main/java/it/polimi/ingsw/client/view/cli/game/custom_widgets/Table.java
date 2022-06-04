package it.polimi.ingsw.client.view.cli.game.custom_widgets;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.reduced_model.ReducedIsland;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.clouds.CloudsSet;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.islands.IslandsSet;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.schoolboard.SchoolBoardList;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.schoolboard.SchoolBoardView;
import it.polimi.ingsw.network.messages.servertoclient.game.AssistantUsed;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.*;
import java.util.stream.Collectors;

public class Table extends StatefulWidget {

    /**
     * the list of the Assistant card that are in the deck.
     */
    private List<Assistant> assistantsList;

    /**
     * map owner - assistant used.
     */
    private Map<String, Assistant> assistantsUsed;

    /**
     * a map containing the IDs of the clouds and the corresponding student list.
     */
    Map<Integer, StudentList> clouds;

    /**
     * map owner-entrance.
     */
    private Map<String, StudentList> entranceList;

    /**
     * map owner-dining Room.
     */
    private Map<String, StudentList> diningRoomList;

    /**
     * map owner-professors.
     */
    private Map<String, Collection<PawnType>> profTableList;

    /**
     * map owner-tower type.
     */
    private Map<String, TowerType> towerColorList;

    /**
     * map owner-tower number.
     */
    private Map<String, Integer> towerNumberList;

    /**
     * map owner-coin number.
     */
    private Map<String, Integer> coinNumberList;

    /**
     * The list of the nickname of the players.
     */
    private final List<String> players;

    /**
     * the list of reduced islands composing the island set
     */
    Collection<ReducedIsland> reducedIslands;

    /**
     * the island set that are on the table.
     */
    private IslandsSet islandsSet;


    // GETTER
    public List<Assistant> getAssistantsList() {
        return new ArrayList<>(this.assistantsList);
    }

    //public List<Assistant> getAssistantsUsed() {
        //return assistantsUsed;
    //}

    public Map<String, Assistant> getAssistantsUsed() {
        return new HashMap<>(this.assistantsUsed);
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
     * this method will return the collection of the ID of the islands that are on the table
     * @return the collection of the ID of the islands that are on the table
     */
    public Collection<Integer> getReducedIslands() {
        return reducedIslands.stream().map(ReducedIsland::ID).collect(Collectors.toList());
    }

    /**
     * The constructor of the class
     * @param assistantsList the list of the assistant cards of the player that are in the deck
     * @param assistantsUsed the list of the assistant cards that has been used
     * @param clouds a map containing the IDs of the clouds and the corresponding student list.
     * @param entranceList map owner-entrance
     * @param diningRoomList map owner-dining Room
     * @param profTableList map owner-professors
     * @param towerColorList map owner-tower type
     * @param towerNumberList map owner-tower number
     * @param coinNumberList map owner-coin number
     * @param players The list of the nickname of the players
     */
    public Table(List<Assistant> assistantsList, Map<String, Assistant> assistantsUsed, Map<Integer, StudentList> clouds, Map<String, StudentList> entranceList, Map<String, StudentList> diningRoomList, Map<String, Collection<PawnType>> profTableList, Map<String, TowerType> towerColorList, Map<String, Integer> towerNumberList, Map<String, Integer> coinNumberList, List<String> players, Collection<ReducedIsland> reducedIslands) {
        this.assistantsList = assistantsList;
        //this.assistantsUsed = assistantsUsed;
        this.assistantsUsed = assistantsUsed;
        this.clouds = clouds;
        this.entranceList = entranceList;
        this.diningRoomList = diningRoomList;
        this.profTableList = profTableList;
        this.towerColorList = towerColorList;
        this.towerNumberList = towerNumberList;
        this.coinNumberList = coinNumberList;
        this.players = players;
        this.reducedIslands=reducedIslands;

        create();
    }


    public void setAssistantsList(List<Assistant> assistantsList) {
        setState(()->this.assistantsList = assistantsList);
    }

    public void setAssistantsUsed(String owner, Assistant assistantUsed) {
        Map <String,Assistant> newAssistantUsedMap = getAssistantsUsed();
        newAssistantUsedMap.put(owner,assistantUsed);

        setState(()-> this.assistantsUsed = newAssistantUsedMap);
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
     * this method will update the number of the ban on the island with the ID specified
     * in the parameters.
     * @param ID the island on which the change has been happened
     * @param actualNumOfBan the actual number of bans on the specified island
     */
    public void updateBanOnIsland(int ID, int actualNumOfBan){
        islandsSet.bansChanged(ID,actualNumOfBan);
    }

    /**
     * this method will update the color of the tower on the island with the ID specified
     * in the parameters.
     * @param ID the island on which the change has been happened
     * @param actualTowerColor the actual color of the tower of the island (null if the tower is not present)
     */
    public void updateTowerType(int ID, TowerType actualTowerColor){
        islandsSet.towerChanged(ID, actualTowerColor);
    }

    /**
     * This method will update the students that are on the island with the ID specified
     * in the parameter.
     * @param ID the island on which the change has been happened
     * @param actualStudentsOnIsland the actual students on the island
     */
    public void updateStudents(int ID, StudentList actualStudentsOnIsland){
        islandsSet.studentsChanged(ID, actualStudentsOnIsland);
    }

    /**
     * this method will update the position of mother nature
     * @param ID the ID of the island on which mother nature should be moved
     */
    public void updateMotherNaturePosition(int ID){
        islandsSet.motherNatureMoved(ID);
    }

    public void islandUnification(){
        // todo: the observer send the ID of the island removed and the size of the island that remains
        //  while the unifyIsland method of the island sets requires
        //  @param islandID          the ID of the island kept
        //  @param removedIslandID   the ID of the island removed
        //  @param removedIslandSize the size of the island remover
        // islandsSet.unifyIslands();
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
        Text header = new Text(Translator.getHeaderOfTable());

        // 1. deck
        Deck deck = new Deck(assistantsList, Translator.getPlayerDeckName());

        // 2. school boards and corresponding assistant card used

        Column schoolBoardColumn = new Column();

        for (String nickname : players) {

            SchoolBoardView schoolBoardView = new SchoolBoardView(
                    entranceList.get(nickname),
                    diningRoomList.get(nickname),
                    profTableList.get(nickname),
                    towerNumberList.get(nickname),
                    towerColorList.get(nickname),
                    coinNumberList.get(nickname),
                    nickname
            );

            Widget assistantCardUsed;
            if(assistantsUsed.containsKey(nickname)) {
                assistantCardUsed = new AssistantCardUsed(assistantsUsed.get(nickname));
            }else{
                assistantCardUsed = new Text("");
            }

            Row row = new Row(List.of(schoolBoardView,assistantCardUsed));
            schoolBoardColumn.addChild(row);

        }

        // 3. islands
        islandsSet = new IslandsSet(reducedIslands);

        // 4. clouds
        CloudsSet cloudsOnTable = new CloudsSet(clouds);

        return new Column(List.of(
                header,
                deck,
                schoolBoardColumn,
                islandsSet,
                cloudsOnTable)
        );

    }
}
