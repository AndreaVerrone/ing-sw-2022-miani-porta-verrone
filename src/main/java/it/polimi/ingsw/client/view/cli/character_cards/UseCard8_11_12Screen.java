package it.polimi.ingsw.client.view.cli.character_cards;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import org.jline.reader.impl.completer.StringsCompleter;

/**
 *  A screen to use character card 8, 11 or 12
 */
public class UseCard8_11_12Screen extends CliScreen {

    /**
     * The card for which this screen is for
     */
    private final CharacterCardsType cardUsed;

    /**
     * Creates a new screen used to use character card 8, 11 or 12
     * @param cli the cli of the client
     * @param cardUsed the card that showed this screen
     */
    public UseCard8_11_12Screen(CLI cli, CharacterCardsType cardUsed) {
        super(cli);
        this.cardUsed = cardUsed;
    }

    @Override
    protected void show() {

    }

    @Override
    protected void askAction() {
        InputReader inputReader = new InputReader();
        for(String color : Translator.getColor()){
            inputReader.addCommandValidator(color);
        }
        inputReader.addCompleter(new StringsCompleter(Translator.getColor()));
        String input = inputReader.readInput(Translator.getChooseStudent())[0];

        Position position = cardUsed == CharacterCardsType.CARD11 ?
                new Position(Location.CHARACTER_CARD_11) : new Position(Location.NONE);
        getCli().getClientController().chooseStudentFromLocation(Translator.parseColor(input), position);
    }
}
