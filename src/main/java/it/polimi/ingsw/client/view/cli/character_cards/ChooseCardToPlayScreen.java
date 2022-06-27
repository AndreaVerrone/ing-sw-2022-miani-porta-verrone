package it.polimi.ingsw.client.view.cli.character_cards;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A screen to choose which character card to play
 */
public class ChooseCardToPlayScreen extends CliScreen {

    /**
     * The screen from which the client is coming from
     */
    private final ScreenBuilder.Screen prevScreen;

    /**
     * Creates a new screen that enable the client to choose a character card to use
     * @param cli the cli of the client
     * @param prevScreen the screen he was previously in
     */
    public ChooseCardToPlayScreen(CLI cli, ScreenBuilder.Screen prevScreen) {
        super(cli);
        this.prevScreen = prevScreen;
    }

    @Override
    protected void show() {
        InputReader inputReader = new InputReader();
        Collection<CharacterCardsType> cardsTypes = getCli().getTable().getCards();
        Collection<String> completer = new ArrayList<>();
        for (CharacterCardsType cardsType : cardsTypes) {
            String label = Translator.getCardLabel() + cardsType.name().substring(4);
            completer.add(label);
            inputReader.addCommandValidator(label);
        }
        completer.add(Translator.getBack());
        inputReader.addCommandValidator(Translator.getBack());
        inputReader.addCompleter(new StringsCompleter(completer));
        String input = inputReader.readInput(Translator.getChooseCard())[0];

        if (input.equals(Translator.getBack())) {
            getCli().getScreenBuilder().build(prevScreen);
            return;
        }
        int cardNumber = Integer.parseInt(input.split("#")[1]);
        CharacterCardsType cardsType = CharacterCardsType.valueOf("CARD"+cardNumber);
        getCli().getClientController().useCharacterCard(cardsType);
    }
}
