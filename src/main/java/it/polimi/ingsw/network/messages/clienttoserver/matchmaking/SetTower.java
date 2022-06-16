package it.polimi.ingsw.network.messages.clienttoserver.matchmaking;

import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.model.utils.TowerType;

/**
 * A message sent from the client to the server to communicate the chosen tower.
 */
public class SetTower extends ClientCommandNetMsg {

    /**
     * The tower chosen by the player.
     */
    private final TowerType tower;

    /**
     * Creates a new message to communicate to the server the chosen tower of a player.
     *
     * @param tower the tower chosen
     */
    public SetTower(TowerType tower) {
        this.tower = tower;
    }


    @Override
    protected void normalProcess(ClientHandler clientInServer)
            throws NotValidArgumentException, NotValidOperationException {
        clientInServer.getSessionController().setTowerOfPlayer(tower);
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
    }
}
