package it.polimi.ingsw.client.view.cli.game.custom_widgets;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.reduced_model.ReducedCloud;
import it.polimi.ingsw.client.reduced_model.ReducedIsland;
import it.polimi.ingsw.client.reduced_model.ReducedSchoolBoard;
import it.polimi.ingsw.client.reduced_model.TableRecord;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.clouds.CloudsSet;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.islands.IslandsSet;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.schoolboard.SchoolBoardView;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.*;

/**
 * A class used to give a graphical representation the table of the game.
 */
public class Table extends StatefulWidget {

    /**
     * the list of the Assistant card that are in the deck.
     */
    private Collection<Assistant> assistantsList;

    /**
     * map owner - assistant used.
     */
    private final Map<String, Assistant> assistantsUsed;

    /**
     * a map containing the IDs of the clouds and the corresponding reduced cloud
     */
    private final Map<Integer, ReducedCloud> clouds = new HashMap<>();

    /**
     * a map containing the owner of the school board and the corresponding reduced school board
     */
    private final Map<String,ReducedSchoolBoard> schoolBoards = new HashMap<>();

    /**
     * the island set that are on the table.
     */
    private final IslandsSet islandsSet;

    /**
     * the constructor of the class
     * @param tableRecord the recordTable class
     */
    public Table(TableRecord tableRecord) {

        this.assistantsList = tableRecord.assistantsList();

        this.assistantsUsed = tableRecord.assistantsUsed();

        // create the map ID of clouds - student list
        for (ReducedCloud cloud : tableRecord.clouds()) {
            clouds.put(cloud.ID(), cloud);
        }

        // create the map between the owner and all the single elements of the school board
        for(ReducedSchoolBoard schoolBoard: tableRecord.schoolBoardList()){
            schoolBoards.put(schoolBoard.getOwner(),schoolBoard);
        }

        islandsSet = new IslandsSet(tableRecord.reducedIslands());
        islandsSet.motherNatureMoved(tableRecord.motherNaturePosition());

        create();
    }

    // GETTER
    /**
     * this method will return a copy of the list of the Assistant card that are in the deck.
     * @return the list of the Assistant card that are in the deck
     */
    public List<Assistant> getAssistantsList() {
        return new ArrayList<>(this.assistantsList);
    }

    /**
     * this method will return a copy of the map owner - assistant used.
     * @return map owner - assistant used
     */
    public Map<String, Assistant> getAssistantsUsed() {
        return new HashMap<>(this.assistantsUsed);
    }

    /**
     * this method will return a copy of a map containing the IDs of the clouds and the corresponding student list.
     * @return a map containing the IDs of the clouds and the corresponding student list
     */
    public Collection<Integer> getIdOfClouds() {
      return clouds.keySet();
    }

    /**
     * this method will return the collection of the ID of the islands that are on the table
     * @return the collection of the ID of the islands that are on the table
     */
    public Collection<Integer> getIdOfReducedIslands() {
        return islandsSet.getIslandsID();
    }

    // SETTERS

    /**
     * this method allow to update the assistant deck of the player.
     * @param assistantsList actual deck of the player
     */
    public void setAssistantsList(Collection<Assistant> assistantsList) {
        setState(()->this.assistantsList = assistantsList);
    }

    /**
     * this method allow to update the last assistant used of the player specified in the parameters
     * @param owner the player
     * @param assistantUsed the actual last assistant used
     */
    public void setAssistantsUsed(String owner, Assistant assistantUsed) {
        setState(()-> assistantsUsed.put(owner,assistantUsed));
    }

    /**
     * this method allow to update the students on the cloud specified in parameters
     * @param ID the id of the cloud
     * @param studentList the actual student list on cloud
     */
    public void setClouds(int ID, StudentList studentList) {
        setState(()->clouds.put(ID,new ReducedCloud(ID,studentList)));
    }

    /**
     * this method allow to update the student on entrance of the school board of the player specified in parameters
     * @param owner the player
     * @param studentsInEntrance the actual students on entrance
     */
    public void setEntranceList(String owner, StudentList studentsInEntrance) {
        setState(()-> schoolBoards.get(owner).setStudentsInEntrance(studentsInEntrance));
    }

    /**
     * this method allow to update the students in the dining room of the school board of the player
     * specified in the parameters.
     * @param owner the player
     * @param studentsInDiningRoom the actual students in dining room
     */
    public void setDiningRoomList(String owner, StudentList studentsInDiningRoom) {
        setState(()-> schoolBoards.get(owner).setStudentsInDiningRoom(studentsInDiningRoom));
    }

    /**
     * this method allow to update the professors in the school board of the player specified in the
     * parameters
     * @param owner the player
     * @param professors the actual collection of professors
     */
    public void setProfTableList(String owner, Collection<PawnType> professors) {
        setState(()-> schoolBoards.get(owner).setProfessors(professors));
    }

    /**
     * this method allow to update the number of the towers in the school board of the
     * player specified in the parameters
     * @param owner the player
     * @param numOfTowers the actual number of towers
     */
    public void setTowerNumberList(String owner, int numOfTowers) {
        setState(()-> schoolBoards.get(owner).setTowerNumber(numOfTowers));
    }

    /**
     * this method will allow to update the number of the coins in the school board
     * of the player specified in the parameters.
     * @param owner the player
     * @param numOfCoins the actual number of coins
     */
    public void setCoinNumberList(String owner, int numOfCoins) {
        setState(()-> schoolBoards.get(owner).setCoinNumber(numOfCoins));
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
    public void updateTowerTypeOnIsland(int ID, TowerType actualTowerColor){
        islandsSet.towerChanged(ID, actualTowerColor);
    }

    /**
     * This method will update the students that are on the island with the ID specified
     * in the parameter.
     * @param ID the island on which the change has been happened
     * @param actualStudentsOnIsland the actual students on the island
     */
    public void updateStudentsOnIsland(int ID, StudentList actualStudentsOnIsland){
        islandsSet.studentsChanged(ID, actualStudentsOnIsland);
    }

    /**
     * this method will update the position of mother nature
     * @param ID the ID of the island on which mother nature should be moved
     */
    public void updateMotherNaturePosition(int ID){
        islandsSet.motherNatureMoved(ID);
    }

    public void islandUnification(int ID, int IDIslandRemoved, int removedIslandSize){
        islandsSet.unifyIslands(ID,IDIslandRemoved,removedIslandSize);
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
        Deck deck = new Deck(assistantsList);

        // 2. school boards and corresponding assistant card used

        Column schoolBoardColumn = new Column();

        for (String nickname : schoolBoards.keySet()) {

            // create the school board
            SchoolBoardView schoolBoardView = new SchoolBoardView(schoolBoards.get(nickname));

            // create the card used (if present)
            Widget assistantCardUsed;
            if(assistantsUsed.containsKey(nickname)) {
                assistantCardUsed = new AssistantCard(assistantsUsed.get(nickname));
            }else{
                assistantCardUsed = new Text("");
            }

            // create the row with school board and the card used
            Row rowSchoolBoardCardUsed = new Row(List.of(schoolBoardView,assistantCardUsed));
            // add the row to the column
            schoolBoardColumn.addChild(rowSchoolBoardCardUsed);

        }

        // 3. islands
//        islandsSet = new IslandsSet(reducedIslands);

        // 4. clouds
        CloudsSet cloudsOnTable = new CloudsSet(clouds.values());

        return new Column(
                List.of(
                    header,
                    deck,
                    schoolBoardColumn,
                    islandsSet,
                    cloudsOnTable
            )
        );
    }
}
