package it.polimi.ingsw.client.view.cli.game;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Text;

import java.util.List;
import java.util.stream.Collectors;


public class EndGameScreen extends CliScreen {

    /**
     * The name of the phase
     */
    private final String phase = Translator.getEndGamePhaseName();

    /**
     * The list of the winners.
     */
    private final List<String> winners;

    /**
     * The constructor of the class
     * @param cli the cli of the user
     * @param winners the list of the winners of the game
     */
    protected EndGameScreen(CLI cli, List<String> winners) {
        super(cli);
        this.winners = winners;
    }

    /**
     * A method to show this screen on the command line
     */
    @Override
    protected void show() {

        Canvas canvas = new Canvas();

        Text text;
        String ownerPlayer = getCli().getClientController().getNickNameOwner();

        int numOfWinners = winners.size();
        int numOfPlayers = 3; // todo: how to get this info? maybe it can be sent by the observer ?

        // three different situations are possible:
        // *** 1. if the number of players is equal to the number of winner --> parity
        if(numOfPlayers==numOfWinners){
            text = new Text("parity");
        // *** 2. if the winner is 1 --> there is only one winner
        }else if(numOfWinners==1){
            // the winner is the owner
            if(winners.contains(ownerPlayer)){
                text = new Text("congratulation you have won the game");
            }else{
                // the winner is not the owner
                text = new Text(winners.get(0) + "has won the game");
            }
        // *** 3. there are 3 players and the winners are 2
        }else{
            // the owner is one of the winner
            if(winners.contains(ownerPlayer)){
                String otherWinner = winners
                        .stream()
                        .filter(nickname -> !winners.contains(nickname))
                        .toList()
                        .get(0);
                text = new Text("you and " + otherWinner + "have won the game");
            }else{
                // the owner is not one of the winners
                text = new Text(winners.get(0) + " and " + winners.get(1) + "have won the game");
            }
        }

        canvas.setContent(text);
        canvas.setTitle(phase);

        canvas.show();

        //askForAction(); // todo: maybe ask to exit from the game ?

    }
}
