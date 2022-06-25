package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.network.VirtualView;
import it.polimi.ingsw.server.controller.StateType;

/**
 * An abstract representation of the view of the client
 */
public abstract class ClientView implements VirtualView, Runnable {

    /**
     * The controller of the client of this view
     */
    private ClientController clientController;
    /**
     * The builder for creating all the components of the view
     */
    private ScreenBuilder screenBuilder;

    public ClientController getClientController() {
        return clientController;
    }


    public ScreenBuilder getScreenBuilder() {
        return screenBuilder;
    }

    protected void setScreenBuilder(ScreenBuilder screenBuilder) {
        this.screenBuilder = screenBuilder;
    }

    /**
     * Attach this view to the specified controller, if not already attached to one.
     * @param clientController the controller of the client
     */
    public void attachTo(ClientController clientController) {
        this.clientController = clientController;
    }

    /**
     * Displays a message of error on the screen
     * @param message a string describing the error
     */
    abstract public void displayErrorMessage(String message);

    /**
     * Displays a generic message on the screen
     * @param message a string representing the message
     */
    abstract public void displayMessage(String message);

    @Override
    public void currentPlayerOrStateChanged(StateType stateType, String currentPlayer) {
        clientController.setNickNameCurrentPlayer(currentPlayer);
        screenBuilder.build(ScreenBuilder.Screen.parse(stateType));
    }

    @Override
    public final void updateNicknameGameID(String nickname, String gameID) {
        clientController.setNickNameOwner(nickname);
        clientController.setGameID(gameID);
    }
}
