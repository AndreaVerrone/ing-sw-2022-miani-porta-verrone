package it.polimi.ingsw.client.view.cli.matchmaking;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.Validator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;
import org.jline.builtins.Completers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A screen used to prompt the player to choose his initial parameters for the game
 */
public class ChooseParametersScreen extends CliScreen {

    /**
     * A list of towers available to be chosen
     */
    private final Collection<TowerType> towersAvailable;

    /**
     * A list of wizards available to be chosen
     */
    private final Collection<Wizard> wizardsAvailable;

    /**
     * Creates a new screen with the provided towers and wizards available
     * @param cli the cli of the client
     */
    public ChooseParametersScreen(CLI cli) {
        super(cli);
        this.towersAvailable = cli.getTowersAvailable();
        this.wizardsAvailable = cli.getWizardsAvailable();
    }

    @Override
    protected void show() {
        Canvas canvas = getCli().getBaseCanvas();
        canvas.setSubtitle("Matchmaking");
        Widget content = getCli().getMatchmakingView();
        if (getCli().getClientController().isInTurn()) {
            Collection<Widget> towers = new ArrayList<>();
            Widget spacer = new SizedBox(2f, 2f);
            for (TowerType towerType : towersAvailable) {
                towers.add(createTower(towerType));
                towers.add(spacer);
            }
            Collection<Widget> wizards = new ArrayList<>();
            for (Wizard wizard : wizardsAvailable) {
                wizards.add(createWizard(wizard));
                wizards.add(spacer);
            }
            content = new Column(List.of(
                    content,
                    new SizedBox(0, 2),
                    new Text(Translator.getTowerLabel()+Translator.getAvailableSuffix()),
                    new Row(towers),
                    new SizedBox(0, 1),
                    new Text(Translator.getWizardLabel()+Translator.getAvailableSuffix()),
                    new Row(wizards)
            ));
        }
        canvas.setContent(content);
        canvas.show();
    }

    private Widget createTower(TowerType towerType){
        Text baseText = new Text(Translator.getTowerName(towerType)).setBackgroundColor(Color.BRIGHT_GREY);
        return switch (towerType){
            case BLACK -> baseText.setForegroundColor(Color.BLACK);
            case WHITE -> baseText.setForegroundColor(Color.WHITE);
            case GREY -> baseText.setForegroundColor(Color.GREY);
        };
    }

    private Widget createWizard(Wizard wizard){
        Text baseText = new Text(Translator.getWizardName(wizard)).setBackgroundColor(Color.BRIGHT_GREY);
        return switch (wizard){
            case W1 -> baseText.setForegroundColor(Color.BRIGHT_YELLOW);
            case W2 -> baseText.setForegroundColor(Color.BRIGHT_GREEN);
            case W3 -> baseText.setForegroundColor(Color.BRIGHT_MAGENTA);
            case W4 -> baseText.setForegroundColor(Color.BRIGHT_RED);
        };
    }
    @Override
    protected void askAction() {
        InputReader inputReader = new InputReader();
        Validator<String> towerValidator = Validator.endsWith(Validator.MATCH_ANYTHING).reverse();
        for (TowerType towerType : towersAvailable)
            towerValidator = towerValidator.or(Validator.endsWith(Translator.getTowerName(towerType)));
        inputReader.addCommandValidator(Validator.beginsWith(Translator.getTowerLabel()).and(towerValidator));

        Validator<String> wizardValidator = Validator.endsWith(Validator.MATCH_ANYTHING).reverse();
        for (Wizard wizard : wizardsAvailable) {
            wizardValidator = wizardValidator.or(Validator.endsWith(getLast(wizard.name())));
        }
        inputReader.addCommandValidator(Validator.beginsWith(Translator.getWizardLabel()).and(wizardValidator));

        inputReader.addCommandValidator(Translator.getMessageToExit());
        inputReader.addCommandValidator(Translator.getNextPhase());
        inputReader.addCompleter(new Completers.TreeCompleter(
                Completers.TreeCompleter.node(Translator.getNextPhase()),
                Completers.TreeCompleter.node(Translator.getMessageToExit()),
                Completers.TreeCompleter.node(Translator.getTowerLabel(),
                        Completers.TreeCompleter.node(
                                towersAvailable.stream().map(Translator::getTowerName).toArray())),
                Completers.TreeCompleter.node(Translator.getWizardLabel(),
                        Completers.TreeCompleter.node(wizardsAvailable.stream().map(w -> getLast(w.name())).toArray()))
        ));

            String[] input = inputReader.readInput(Translator.getAskForInitialParameters());
            String command = input[0];

            if (Translator.getMessageToExit().equals(command)) {
                getCli().confirmExit();
            } else if (Translator.getTowerLabel().equals(command)) {
                getCli().getClientController().setTower(Translator.parseTowerType(input[1]));
            } else if (Translator.getWizardLabel().equals(command)) {
                getCli().getClientController().setWizard(Wizard.valueOf("W" + input[1]));
            } else if (Translator.getNextPhase().equals(command)) {
                getCli().getClientController().nextPhase();
            }
    }

    private String getLast(String s){
        return s.substring(s.length()-1);
    }
}
