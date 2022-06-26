package it.polimi.ingsw.client.view.cli.common_screens;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;

/**
 * A screen to display that a player in the game left
 */
public class PlayerLeftScreen extends CliScreen {

    /**
     * The nickname of the player that left the game
     */
    private final String nicknameLeft;


    /**
     * Creates a new screen to show that a player left the game
     * @param cli the cli of the client
     * @param nicknameLeft the nickname of the player
     */
    public PlayerLeftScreen(CLI cli, String nicknameLeft) {
        super(cli);
        this.nicknameLeft = nicknameLeft;
    }

    @Override
    protected void show() {
        System.out.println(nicknameLeft+ Translator.getLeftGameMessage());
        new InputReader().readInput(Translator.getPressEnterToExit());
        getCli().getClientController().quitGame();
        getCli().getScreenBuilder().build(ScreenBuilder.Screen.HOME);
    }
}
