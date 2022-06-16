package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ReducedPlayerLoginInfo;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.Validator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;
import it.polimi.ingsw.client.view.cli.matchmaking.ChooseParametersScreen;
import it.polimi.ingsw.client.view.cli.matchmaking.LobbyScreen;
import it.polimi.ingsw.client.view.cli.matchmaking.widgets.MatchmakingView;
import it.polimi.ingsw.network.VirtualView;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.EnumCompleter;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.Collection;
import java.util.Locale;

/**
 * A class to handle the client ui in the console
 */
public class CLI implements VirtualView, Runnable {

    /**
     * The controller of the client of this view
     */
    private ClientController clientController;

    /**
     * The current screen that must be shown to the client
     */
    private CliScreen currentScreen = new IdleScreen(this);

    /**
     * The next screen that must be shown to the client
     */
    private CliScreen nextScreen;

    /**
     * The title of the application
     */
    public static final String APP_TITLE = """
            ███████╗██████╗ ██╗ █████╗ ███╗   ██╗████████╗██╗   ██╗███████╗
            ██╔════╝██╔══██╗██║██╔══██╗████╗  ██║╚══██╔══╝╚██╗ ██╔╝██╔════╝
            █████╗  ██████╔╝██║███████║██╔██╗ ██║   ██║    ╚████╔╝ ███████╗
            ██╔══╝  ██╔══██╗██║██╔══██║██║╚██╗██║   ██║     ╚██╔╝  ╚════██║
            ███████╗██║  ██║██║██║  ██║██║ ╚████║   ██║      ██║   ███████║
            ╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝      ╚═╝   ╚══════╝
            """;

    private boolean shouldStop = false;

    /**
     * A widget representing the matchmaking content
     */
    private MatchmakingView matchmakingView;

    @Override
    public void run() {
        while (!shouldStop){
            if (nextScreen == null){
                currentScreen = new IdleScreen(this);
            } else {
                currentScreen = nextScreen;
                nextScreen = null;
            }
            currentScreen.run();
        }
    }

    /**
     * Attach this view to the specified controller, if not already attached to one.
     * @param clientController the controller of the client
     */
    public void attachTo(ClientController clientController){
        if (this.clientController == null)
            this.clientController = clientController;
    }

    public ClientController getClientController(){
        return clientController;
    }

    public void setNextScreen(CliScreen screen){
        nextScreen = screen;
        currentScreen.setStop();
    }

    public Canvas getBaseCanvas(){
        Canvas canvas = new Canvas();
        canvas.setTitle(APP_TITLE);
        canvas.setTitleColor(Color.BRIGHT_CYAN);
        canvas.setSubtitle(Translator.getGameSubtitle());
        return canvas;
    }

    /**
     * Prompt the user to choose in which language he wants to play.
     */
    public void chooseLanguage(){
        InputReader inputReader = new InputReader();
        for (Translator.Language language : Translator.Language.values()){
            for (String code : language.getCodes())
                inputReader.addCommandValidator(code);
        }
        inputReader.addCompleter(new EnumCompleter(Translator.Language.class));
        String language = inputReader.readInput(Translator.getChooseLanguage())[0];
        Translator.setLanguage(Translator.Language.fromCode(language));
    }

    /**
     * Prompt the user to confirm that he want to close the application
     */
    public void confirmExit(){
        InputReader inputReader = new InputReader();
        inputReader.addCompleter(new AggregateCompleter(new StringsCompleter("yes"), new StringsCompleter("no")));
        inputReader.setNumOfArgsValidator(Validator.isOfNum(0));
        String input = inputReader.readInput(Translator.getConfirmExit())[0];
        if (parseBoolean(input)) {
            shouldStop = true;
            currentScreen.setStop();
            clientController.closeApplication();
            return;
        }
        setNextScreen(currentScreen);
    }

    private boolean parseBoolean(String bool){
        return switch (bool.toLowerCase(Locale.ROOT)){
            case "y", "yes", "s", "si" -> true;
            default -> false;
        };
    }

    public MatchmakingView getMatchmakingView(){
        return matchmakingView;
    }

    @Override
    public void createGameView(Collection<ReducedPlayerLoginInfo> playerLoginInfos, int numPlayers, boolean isExpert) {
        matchmakingView = new MatchmakingView(playerLoginInfos, numPlayers, isExpert, clientController.getGameID());
        setNextScreen(new LobbyScreen(this));
    }

    @Override
    public void changeCurrentState(StateType stateType) {

    }

    @Override
    public void addCoinOnCard(CharacterCardsType characterCardsType, boolean coinOnCard) {

    }

    @Override
    public void addStudentsOnCard(CharacterCardsType characterCardType, StudentList actualStudents) {

    }

    @Override
    public void changeNumberOfPlayers(int numberOfPlayers) {

    }

    @Override
    public void playersChanged(Collection<ReducedPlayerLoginInfo> players) {
        boolean lobbyFull = matchmakingView.update(players);
        if (lobbyFull)
            clientController.nextPhase();
    }

    @Override
    public void towerSelected(String player, TowerType tower) {

    }

    @Override
    public void wizardSelected(String player, Wizard wizard) {

    }

    @Override
    public void changeNumberOfBansOnIsland(int islandIDWithBan, int actualNumOfBans) {

    }

    @Override
    public void changeAssistantDeck(String nickName, Collection<Assistant> actualDeck) {

    }

    @Override
    public void changeCoinNumberInBag(int actualNumOfCoins) {

    }

    @Override
    public void changeCoinNumber(String nickNameOfPlayer, int actualNumOfCoins) {

    }

    @Override
    public void changeCurrentPlayer(String actualCurrentPlayerNickname) {

    }

    @Override
    public void changeTowerNumber(String nickName, int numOfActualTowers) {

    }

    @Override
    public void conquerIslandObserver() {

    }

    @Override
    public void emptyStudentBag() {

    }

    @Override
    public void islandNumberChanged(int actualNumOfIslands) {

    }

    @Override
    public void islandUnification(int islandID, int islandRemovedID, int finalSize) {

    }

    @Override
    public void changeLastAssistantUsed(String nickName, Assistant actualLastAssistant) {

    }

    @Override
    public void changeMotherNaturePosition(int actualMotherNaturePosition) {

    }

    @Override
    public void changeProfessor(String nickName, Collection<PawnType> actualProfessors) {

    }

    @Override
    public void changeStudentsInDiningRoom(String nickname, StudentList actualStudents) {

    }

    @Override
    public void changeStudentsOnCloud(int cloudID, StudentList actualStudentList) {

    }

    @Override
    public void changeStudentsOnEntrance(String nickname, StudentList actualStudents) {

    }

    @Override
    public void changeStudentsOnIsland(int islandID, StudentList actualStudents) {

    }

    @Override
    public void changeTowerOnIsland(int islandIDWithChange, TowerType actualTower) {

    }

    @Override
    public void endGame(Collection<String> winners) {

    }
}
