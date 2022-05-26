package it.polimi.ingsw.client.view.cli.launcher;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;

/**
 * Asks the user to enter a nickname in order to enter a game
 */
public class RequestNicknameScreen extends CliScreen {

    /**
     * The ID of the game the user want to join
     */
    private final String gameID;

    /**
     * Creates a new screen that asks the user to enter a nickname in order to enter a game
     * @param cli the cli of the user
     * @param gameID the ID of the game he wants to join
     */
    public RequestNicknameScreen(CLI cli, String gameID) {
        super(cli);
        this.gameID = gameID;
    }

    @Override
    protected void show() {
        InputReader nicknameReader = new InputReader();
        nicknameReader.addCommandValidator("[\\H\\S]+");
        String nickname = nicknameReader.readInput(Translator.getNickname())[0];
        getCli().getClientController().enterGame(nickname, gameID);
    }
}
