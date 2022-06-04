package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.reduced_model.ReducedIsland;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A message sent from server to all the client connected to a game with all the data
 * needed to create a table.
 */
public class TableCreated extends ServerCommandNetMsg {

    /**
     * the list of the Assistant card that are in the deck.
     */
    private final List<Assistant> assistantsList;

    /**
     * map owner - assistant used.
     */
    private final Map<String, Assistant> assistantsUsed;

    /**
     * a map containing the IDs of the clouds and the corresponding student list.
     */
    private final Map<Integer, StudentList> clouds;

    /**
     * map owner-entrance.
     */
    private final Map<String, StudentList> entranceList;

    /**
     * map owner-dining Room.
     */
    private final Map<String, StudentList> diningRoomList;

    /**
     * map owner-professors.
     */
    private final Map<String, Collection<PawnType>> profTableList;

    /**
     * map owner-tower type.
     */
    private final Map<String, TowerType> towerColorList;

    /**
     * map owner-tower number.
     */
    private final Map<String, Integer> towerNumberList;

    /**
     * map owner-coin number.
     */
    private final Map<String, Integer> coinNumberList;

    /**
     * The list of the nickname of the players.
     */
    private final List<String> players;

    /**
     * the list of reduced islands composing the island set
     */
    private final Collection<ReducedIsland> reducedIslands;


    /**
     * the constructor of the class
     * @param assistantsList the list of the Assistant card that are in the deck
     * @param assistantsUsed map owner - assistant used
     * @param clouds a map containing the IDs of the clouds and the corresponding student list.
     * @param entranceList map owner-entrance.
     * @param diningRoomList map owner-dining Room.
     * @param profTableList map owner-professors.
     * @param towerColorList map owner-tower type.
     * @param towerNumberList map owner-tower number.
     * @param coinNumberList map owner-coin number.
     * @param players The list of the nickname of the players.
     * @param reducedIslands the list of reduced islands composing the island set
     */
    public TableCreated(List<Assistant> assistantsList, Map<String, Assistant> assistantsUsed, Map<Integer, StudentList> clouds, Map<String, StudentList> entranceList, Map<String, StudentList> diningRoomList, Map<String, Collection<PawnType>> profTableList, Map<String, TowerType> towerColorList, Map<String, Integer> towerNumberList, Map<String, Integer> coinNumberList, List<String> players, Collection<ReducedIsland> reducedIslands) {
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
        this.reducedIslands = reducedIslands;
    }

    /**
     * A method used to process this message.
     * <p>
     * This method runs in the client.
     *
     * @param client the client that receives this message
     */
    @Override
    public void processMessage(ClientController client) {
        client.initializeTable(
                assistantsList,
                assistantsUsed,
                clouds,
                entranceList,
                diningRoomList,
                profTableList,
                towerColorList,
                towerNumberList,
                coinNumberList,
                players,
                reducedIslands
        );
    }
}
