package it.polimi.ingsw.network.messages.servertoclient;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.server.controller.StateType;

/**
 * A message sent from the server to all clients connected to a game to indicate that t
 * he current player or the state has changed.
 */
public class PlayerOrStateChanged extends ServerCommandNetMsg{

    /**
     * the actual current player
     */
    private final String currentPlayerNickname;

    /**
     * the actual state
     */
    private final StateType currentState;

    /**
     * the constructor of the class.
     * It will create the class using the actual current player
     * and the actual state.
     * @param currentPlayerNickname the actual current player
     * @param currentState the current state
     */
    public PlayerOrStateChanged(String currentPlayerNickname, StateType currentState) {
        this.currentPlayerNickname = currentPlayerNickname;
        this.currentState = currentState;
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

        client.setNickNameCurrentPlayer(currentPlayerNickname);
        switch (currentState){
            case PLAY_ASSISTANT_STATE -> client.displayPlanningPhaseScreen();
            case MOVE_STUDENT_STATE -> client.displayMoveStudentsScreen();
            case MOVE_MOTHER_NATURE_STATE -> client.displayMoveMotherNatureScreen();
            case CHOOSE_CLOUD_STATE -> client.displayChooseCloudScreen();
        }

    }
}
