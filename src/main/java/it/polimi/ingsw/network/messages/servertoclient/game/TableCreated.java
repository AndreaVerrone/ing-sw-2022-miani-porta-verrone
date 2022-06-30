package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.client.reduced_model.ReducedModel;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

/**
 * A message sent from server to all the client connected to a game with all the data
 * needed to create a table.
 */
public class TableCreated extends ServerCommandNetMsg {

    private final ReducedModel table;

    /**
     * the constructor of the class, it will take as an
     * input the table record.
     * @param reducedModel the table record
     */
    public TableCreated(ReducedModel reducedModel) {
        table = reducedModel;
    }

    /**
     * A method used to process this message.
     * <p>
     * This method runs in the client.
     *
     * @param client the client that receives this message
     */
    @Override
    public void processMessage(ClientView client) {
        client.gameCreated(table);
    }
}
