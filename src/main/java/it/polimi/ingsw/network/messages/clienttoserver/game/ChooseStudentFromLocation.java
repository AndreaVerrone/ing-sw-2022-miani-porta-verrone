package it.polimi.ingsw.network.messages.clienttoserver.game;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.model.utils.PawnType;

/**
 * message sent from client to server to choose a student to move
 */
public class ChooseStudentFromLocation extends ClientCommandNetMsg {

    /**
     * Student chosen
     */
    private final PawnType student;

    /**
     * Position from where the student must be taken
     */
    private final Position originPosition;

    /**
     * Creates a new message to communicate the students that must be moved from the chosen location
     * @param student student to me moved
     * @param originPosition position from where move the student
     */
    public ChooseStudentFromLocation(PawnType student, Position originPosition){
        this.student = student;
        this.originPosition = originPosition;
    }

    @Override
    protected void normalProcess(ClientHandler clientInServer) throws NotValidArgumentException, NotValidOperationException {
        clientInServer.getSessionController().chooseStudentFromLocation(student, originPosition);
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
    }

    @Override
    public void processResponse(ResponseMessage response, ClientController clientController) {
        //TODO: 19/05/2022 handle response in client
    }
}
