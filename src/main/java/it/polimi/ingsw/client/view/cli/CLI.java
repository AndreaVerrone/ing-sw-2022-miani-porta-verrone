package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.reduced_model.ReducedModel;
import it.polimi.ingsw.client.reduced_model.ReducedPlayerLoginInfo;
import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.Validator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.ConsoleCli;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.Table;
import it.polimi.ingsw.client.view.cli.matchmaking.widgets.MatchmakingView;
import it.polimi.ingsw.client.view.cli.waiting.IdleScreen;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;
import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.EnumCompleter;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/**
 * A class to handle the client ui in the console
 */
public class CLI extends ClientView {

    /**
     * The current screen that must be shown to the client
     */
    private CliScreen currentScreen = new IdleScreen(this);

    /**
     * The next screen that must be shown to the client
     */
    private CliScreen nextScreen;

    /**
     * the table of the game
     */
    Table table;

    private Collection<TowerType> towersAvailable = new ArrayList<>();
    private Collection<Wizard> wizardsAvailable = new ArrayList<>();

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

    public CLI() {
        setScreenBuilder(new CliScreenBuilder(this));
    }

    public Table getTable() {
        return table;
    }

    public Collection<TowerType> getTowersAvailable() {
        return towersAvailable;
    }

    public Collection<Wizard> getWizardsAvailable() {
        return wizardsAvailable;
    }

    public MatchmakingView getMatchmakingView(){
        return matchmakingView;
    }

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

    public void setNextScreen(CliScreen screen){
        nextScreen = screen;
        currentScreen.setStop();
    }

    public Canvas getBaseCanvas(){
        Canvas canvas = new Canvas(true, false);
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
     * Prompt the user to confirm that he want to close the application or exit the game
     * based on the parameter passed
     * @param needToCloseApp true if this method should close the entire application
     */
    public void confirmExit(boolean needToCloseApp){
        InputReader inputReader = new InputReader();
        inputReader.addCompleter(new AggregateCompleter(new StringsCompleter("yes"), new StringsCompleter("no")));
        inputReader.setNumOfArgsValidator(Validator.isOfNum(0));
        String input = inputReader.readInput(Translator.getConfirmExit())[0];
        if (isNegativeAnswer(input)) {
            getScreenBuilder().rebuild();
            return;
        }
        if (needToCloseApp) {
            shouldStop = true;
            currentScreen.setStop();
            getClientController().closeApplication();
            return;
        }
        getClientController().quitGame();
        getScreenBuilder().build(ScreenBuilder.Screen.HOME);
    }

    /**
     * Prompt the user to confirm that he want to exit the game
     */
    public void confirmExit() {
        confirmExit(false);
    }

    /**
     * Moves to the screen when the player can choose a character card if the game is expert
     */
    public void useCharacterCard() {
        if (getClientController().isForExpertGame())
            getScreenBuilder().build(ScreenBuilder.Screen.CHOOSE_CHARACTER_CARD);
        else
            displayErrorMessage(Translator.getCantUseCard());
    }

    private boolean isNegativeAnswer(String bool){
        return switch (bool.toLowerCase(Locale.ROOT)){
            case "y", "yes", "s", "si" -> false;
            default -> true;
        };
    }
    @Override
    public void displayErrorMessage(String message){
        // print the message in red
        printColorMessage(Color.RED,message);
        // emit a sound
        System.out.print("\u0007");
        try {
            Thread.sleep(2000); //waits a little to make the client see the error
        }catch (InterruptedException ignore){}
        getScreenBuilder().rebuild();
    }

    /**
     * this method will print in yellow the message passed in the parameters
     * @param message string containing the message to print
     */
    @Override
    public void displayMessage(String message){
        printColorMessage(Color.BRIGHT_YELLOW,message);
    }

    /**
     * This method allow to print a colored message
     * @param color the color to use to print the message
     * @param message the message to print
     */
    private void printColorMessage(Color color, String message){
        AnsiConsole.systemInstall();
        System.out.print(color + message);
        ConsoleCli.resetStyle();
        System.out.print("\n");
        AnsiConsole.systemUninstall();
    }

    @Override
    public void createMatchmakingView(Collection<ReducedPlayerLoginInfo> playerLoginInfos, int numPlayers,
                                      boolean isExpert, String currentPlayer) {
        getClientController().setNickNameCurrentPlayer(currentPlayer);
        matchmakingView = new MatchmakingView(playerLoginInfos, numPlayers, isExpert, getClientController().getGameID());
        getScreenBuilder().build(ScreenBuilder.Screen.MATCHMAKING_WAIT_PLAYERS);
    }

    @Override
    public void gameCreated(ReducedModel reducedModel) {
        this.table = new Table(reducedModel);
        matchmakingView = null;
        getClientController().setForExpertGame(reducedModel.isExpertGame());
    }

    @Override
    public void currentPlayerOrStateChanged(StateType currentState, String currentPlayer) {
        if (matchmakingView != null)
            matchmakingView.setSelected(currentPlayer);
        super.currentPlayerOrStateChanged(currentState, currentPlayer);
    }

    // MATCHMAKING UPDATES METHODS

    @Override
    public void numberOfPlayersChanged(int numberOfPlayers) {
        // can't be implemented in cli
    }

    @Override
    public void playersChanged(Collection<ReducedPlayerLoginInfo> players) {
        boolean lobbyFull = matchmakingView.update(players);
        if (lobbyFull && getClientController().isInTurn())
            getClientController().nextPhase();
    }

    @Override
    public void choosePlayerParameter(Collection<TowerType> towersAvailable, Collection<Wizard> wizardsAvailable) {
        this.towersAvailable = towersAvailable;
        this.wizardsAvailable = wizardsAvailable;
        getScreenBuilder().build(ScreenBuilder.Screen.MATCHMAKING_ASK_PARAMS);
    }

    @Override
    public void towerSelected(String player, TowerType tower) {
        matchmakingView.modify(player, tower);
        getScreenBuilder().build(ScreenBuilder.Screen.MATCHMAKING_ASK_PARAMS);
    }

    @Override
    public void wizardSelected(String player, Wizard wizard) {
        matchmakingView.modify(player, wizard);
        getScreenBuilder().build(ScreenBuilder.Screen.MATCHMAKING_ASK_PARAMS);
    }

    // PLANNING PHASE UPDATES METHODS

    @Override
    public void lastAssistantUsedChanged(String nickName, Assistant actualLastAssistant) {
        table.setAssistantsUsed(nickName, actualLastAssistant);
    }
    @Override
    public void assistantDeckChanged(String owner, Collection<Assistant> actualDeck) {
        if (owner.equals(getClientController().getNickNameOwner()))
            table.setAssistantsList(actualDeck);
    }

    // SCHOOLBOARD UPDATES METHODS

    @Override
    public void studentsOnEntranceChanged(String nickname, StudentList actualStudents) {
        table.setEntranceList(nickname,actualStudents);
    }

    @Override
    public void studentsInDiningRoomChanged(String nickname, StudentList actualStudents) {
        table.setDiningRoomList(nickname, actualStudents);
    }

    @Override
    public void professorsOfPlayerChanged(String nickName, Collection<PawnType> actualProfessors) {
        table.setProfTableList(nickName, actualProfessors);
    }

    @Override
    public void towerNumberOfPlayerChanged(String nickName, int numOfActualTowers) {
        table.setTowerNumberList(nickName, numOfActualTowers);
    }

    // ISLAND UPDATES METHODS

    @Override
    public void studentsOnIslandChanged(int islandID, StudentList actualStudents) {
        table.updateStudentsOnIsland(islandID, actualStudents);
    }

    @Override
    public void towerOnIslandChanged(int islandIDWithChange, TowerType actualTower) {
        table.updateTowerTypeOnIsland(islandIDWithChange,actualTower);
    }

    @Override
    public void islandNumberChanged(int actualNumOfIslands) {
    }

    @Override
    public void islandsUnified(int islandID, int islandRemovedID, int sizeIslandRemoved) {
        table.islandUnification(islandID, islandRemovedID, sizeIslandRemoved);
    }

    @Override
    public void motherNaturePositionChanged(int actualMotherNaturePosition) {
        table.updateMotherNaturePosition(actualMotherNaturePosition);
    }

    // CLOUD UPDATES METHOD

    @Override
    public void studentsOnCloudChanged(int cloudID, StudentList actualStudentList) {
        table.setClouds(cloudID, actualStudentList);
    }

    // EXPERT GAME UPDATES METHODS

    @Override
    public void coinNumberInBagChanged(int actualNumOfCoins) {
    }

    @Override
    public void coinNumberOfPlayerChanged(String nickNameOfPlayer, int actualNumOfCoins) {
        table.setCoinNumberList(nickNameOfPlayer,actualNumOfCoins);
    }

    @Override
    public void numberOfBansOnIslandChanged(int islandIDWithBan, int actualNumOfBans) {
        table.updateBanOnIsland(islandIDWithBan, actualNumOfBans);
    }

    @Override
    public void coinOnCardAdded(CharacterCardsType characterCardsType) {
        table.updateCardCost(characterCardsType);
    }

    @Override
    public void studentsOnCardChanged(CharacterCardsType characterCardType, StudentList actualStudents) {
        table.updateStudentOnCard(characterCardType, actualStudents);
    }

    // END GAME UPDATE METHODS
    @Override
    public void notifyLastRound() {
        displayMessage(Translator.getLastRoundMessage());
    }

    @Override
    public void gameEnded(Collection<String> winners) {
        getScreenBuilder().build(ScreenBuilder.Screen.END_GAME, winners);
    }
}
