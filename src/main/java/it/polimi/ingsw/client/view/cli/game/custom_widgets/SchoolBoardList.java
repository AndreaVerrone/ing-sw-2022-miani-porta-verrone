package it.polimi.ingsw.client.view.cli.game.custom_widgets;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.*;

public class SchoolBoardList extends StatefulWidget {

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
    private List<String> players;


    public Map<String, StudentList> getEntranceList() {
        return new HashMap<>(entranceList);
    }

    public Map<String, StudentList> getDiningRoomList() {
        return new HashMap<>(diningRoomList);
    }

    public Map<String, Collection<PawnType>> getProfTableList() {
        return new HashMap<>(profTableList);
    }

    public Map<String, TowerType> getTowerColorList() {
        return new HashMap<>(towerColorList);
    }

    public Map<String, Integer> getTowerNumberList() {
        return new HashMap<>(towerNumberList);
    }

    public Map<String, Integer> getCoinNumberList() {
        return new HashMap<>(coinNumberList);
    }

    public List<String> getPlayers() {
        return new ArrayList<>(players);
    }


    /**
     * The constructor of the class
     * @param entranceList map owner-entrance
     * @param diningRoomList map owner-dining Room
     * @param profTableList map owner-professors
     * @param towerColorList map owner-tower type
     * @param towerNumberList map owner-tower number
     * @param coinNumberList map owner-coin number
     */
    public SchoolBoardList(
            Map<String, StudentList> entranceList,
            Map<String, StudentList> diningRoomList,
            Map<String, Collection<PawnType>> profTableList,
            Map<String, TowerType> towerColorList,
            Map<String, Integer> towerNumberList,
            Map<String, Integer> coinNumberList,
            List<String> players) {
        this.entranceList = entranceList;
        this.diningRoomList = diningRoomList;
        this.profTableList = profTableList;
        this.towerColorList = towerColorList;
        this.towerNumberList = towerNumberList;
        this.coinNumberList = coinNumberList;
        this.players = players;

        create();
    }


    /* SETTERS NOT NEEDED
    public void setEntranceList(String owner, StudentList studentsInEntrance) {

        Map<String, StudentList> newEntranceMap = getEntranceList();
        newEntranceMap.put(owner,studentsInEntrance);

        setState(()-> entranceList = newEntranceMap);

    }

    public void setDiningRoomList(String owner, StudentList studentsInDiningRoom) {

        Map<String, StudentList> newDiningRoomMap = getDiningRoomList();
        newDiningRoomMap.put(owner,studentsInDiningRoom);

        setState(()-> diningRoomList=newDiningRoomMap);

    }

    public void setProfTableList(String owner, Collection<PawnType> professors) {
        Map<String, Collection<PawnType>> newProfTableMap = getProfTableList();
        newProfTableMap.put(owner,professors);

        setState(()->profTableList=newProfTableMap);

    }

    public void setTowerColorList(String owner, TowerType towerType) {
        Map<String, TowerType> newTowerColorMap = getTowerColorList();
        towerColorList.put(owner,towerType);

        setState(()->towerColorList=newTowerColorMap);
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
     */

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
        Text header = new Text(Translator.getSchoolBoardListHeader());

        // column containing all the school boards
        Column schoolBoardColumn = new Column();

        for (String nickname : players) {
            schoolBoardColumn.addChild(
                    new SchoolBoardView(
                            entranceList.get(nickname),
                            diningRoomList.get(nickname),
                            profTableList.get(nickname),
                            towerNumberList.get(nickname),
                            towerColorList.get(nickname),
                            coinNumberList.get(nickname),
                            nickname
                    )
            );
        }

        return schoolBoardColumn;
    }
}
