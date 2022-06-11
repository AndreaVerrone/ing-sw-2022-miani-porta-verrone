package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.reduced_model.ReducedIsland;
import it.polimi.ingsw.client.reduced_model.TableRecord;
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

    private final TableRecord table;

    /**
     * the constructor of the class, it will take as an
     * input the table record.
     * @param tableRecord the table record
     */
    public TableCreated(TableRecord tableRecord) {
        table = tableRecord;
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
        client.initializeTable(table);
    }
}
