package it.polimi.ingsw.client.view.cli.character_cards;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.Collection;

/**
 *  A screen to use character card 4 or 5
 */
public class UseCard45Screen extends CliScreen {

    /**
     * Creates a new screen used to use character card 4
     * @param cli the cli of the client
     */
    public UseCard45Screen(CLI cli) {
        super(cli);
    }

    @Override
    protected void show() {
        InputReader inputReader = new InputReader();
        Collection<String> completer = new ArrayList<>();
        for(int island : getCli().getTable().getIdOfReducedIslands()){
            String destination = Translator.getIslandName() + island;
            inputReader.addCommandValidator(destination);
            completer.add(destination);
        }
        inputReader.addCompleter(new StringsCompleter(completer));
        String input = inputReader.readInput(Translator.getChooseIsland())[0];

        int islandID = Integer.parseInt(input.split("#")[1]);
        Position island = new Position(Location.ISLAND);
        island.setField(islandID);
        getCli().getClientController().chooseDestination(island);
    }
}
