package it.polimi.ingsw.client.view.cli.character_cards;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.Validator;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A screen to use character card 1
 */
public class UseCard1Screen extends CliScreen {

    /**
     * Creates a new screen used to use character card 1
     * @param cli the cli of the client
     */
    public UseCard1Screen(CLI cli) {
        super(cli);
    }

    @Override
    protected void show() {
        InputReader reader = new InputReader();
        Validator<String> studentValidator = Validator.contains(Validator.MATCH_ANYTHING).reverse();
        for (String color : Translator.getColor())
            studentValidator.or(Validator.beginsWith(color));
        Validator<String> destinationValidator = Validator.contains(Validator.MATCH_ANYTHING).reverse();
        Collection<String> destinations = new ArrayList<>();
        for(int island : getCli().getTable().getIdOfReducedIslands()){
            String destination = Translator.getIslandName() + island;
            destinationValidator.or(Validator.endsWith(destination));
            destinations.add(Translator.getIslandName() + island);
        }
        reader.addStrictCommandValidator(studentValidator);
        reader.addStrictCommandValidator(destinationValidator);

        reader.addCompleter(new ArgumentCompleter(
                new StringsCompleter(Translator.getColor()),
                new StringsCompleter(destinations)
        ));

        String[] inputs = reader.readInput(Translator.getChooseStudentIsland());
        getCli().getClientController().chooseStudentFromLocation(
                Translator.parseColor(inputs[0]), new Position(Location.CHARACTER_CARD_1));

        int islandID = Integer.parseInt(inputs[1].split("#")[1]);
        Position island = new Position(Location.ISLAND);
        island.setField(islandID);
        getCli().getClientController().chooseDestination(island);
    }

}
