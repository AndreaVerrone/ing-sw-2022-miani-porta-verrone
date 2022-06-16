package it.polimi.ingsw.network.messages.clienttoserver.game;

import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;

/**
 * Message sent from the client to the server to use a character card
 */
public class UseCharacterCard extends ClientCommandNetMsg {

    /**
     * Character card selected
     */
    private final CharacterCardsType card;

    /**
     * Create a new message to communicate the character card selected
     * @param card character card selected
     */
    public UseCharacterCard(CharacterCardsType card){
        this.card = card;
    }

    @Override
    protected void normalProcess(ClientHandler clientInServer) throws NotValidArgumentException, NotValidOperationException {
        clientInServer.getSessionController().useCharacterCard(card);
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
    }

}
