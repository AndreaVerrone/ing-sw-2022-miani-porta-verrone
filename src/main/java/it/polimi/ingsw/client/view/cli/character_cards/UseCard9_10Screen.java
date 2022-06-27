package it.polimi.ingsw.client.view.cli.character_cards;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.Validator;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.Collection;

/**
 *  A screen to use character card 9 or 10
 */
public class UseCard9_10Screen extends CliScreen {

    /**
     * The card for which this screen is for
     */
    private final CharacterCardsType cardUsed;

    /**
     * Creates a new screen used to use character card 9 or 10
     * @param cli the cli of the client
     * @param cardUsed the card that showed this screen
     */
    public UseCard9_10Screen(CLI cli, CharacterCardsType cardUsed) {
        super(cli);
        this.cardUsed = cardUsed;
    }

    @Override
    protected void show() {
        Canvas canvas = new Canvas(true, false);
        canvas.setContent(getCli().getTable());

        String currentPlayerNickname = getCli().getClientController().getNickNameCurrentPlayer();

        canvas.setSubtitle(Translator.getMessageCurrentPlayer()+": "+currentPlayerNickname);
        canvas.show();
    }

    @Override
    protected void askAction() {
        InputReader inputReader = new InputReader();
        for(String color : Translator.getColor()){
            inputReader.addCommandValidator(Validator.beginsWith(color + " "));
            inputReader.addCommandValidator(Validator.endsWith(color));
        }
        inputReader.addCommandValidator(Translator.getBack());
        Collection<String> firstCompleter = new ArrayList<>(Translator.getColor());
        firstCompleter.add(Translator.getBack());
        inputReader.addCompleter(new ArgumentCompleter(
                new StringsCompleter(firstCompleter),
                new StringsCompleter(Translator.getColor())
        ));

        String message = Translator.getChooseCoupleStudents() +
                (cardUsed == CharacterCardsType.CARD9 ? Translator.getFirstCardSecondEntrance()
                        : Translator.getFirstDiningRoomSecondEntrance());

        String[] inputs = inputReader.readInput(message);
        if (inputs[0].equals(Translator.getBack())) {
            getCli().getClientController().chooseStudentFromLocation(null, new Position(Location.NONE));
            return;
        }

        Position firstPosition = cardUsed == CharacterCardsType.CARD9 ? new Position(Location.CHARACTER_CARD_9)
                : new Position(Location.DINING_ROOM);
        Position secondPosition = new Position(Location.ENTRANCE);
        getCli().getClientController().chooseStudentFromLocation(Translator.parseColor(inputs[0]), firstPosition);
        getCli().getClientController().chooseStudentFromLocation(Translator.parseColor(inputs[1]), secondPosition);
    }
}
