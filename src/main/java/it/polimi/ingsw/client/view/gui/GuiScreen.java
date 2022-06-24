package it.polimi.ingsw.client.view.gui;


import it.polimi.ingsw.client.view.gui.controller.*;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;
import javafx.scene.control.Alert;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public abstract class GuiScreen {

    /**
     * The GUI of the client
     */
    private GUI gui;


    public GUI getGui() {
        return gui;
    }

    public void attachTo(GUI gui){
        this.gui = gui;
    }

    // todo: adapt this for GUI
    /**
     * A method to show this screen on the command line
     */
    //abstract protected void show();

    /**
     * A method used to ask the user to enter some input, if necessary
     */
    // protected void askAction() {}

    /*final void run() {
        show();
        if (gui.getClientController().isInTurn())
            askAction();
    }*/

    //METHOD USED BY LOBBY SCREEN
    public void setUp(String gameID, int totalNumOfPlayers, boolean isExpert, List<PlayerView> playerViewList){

    }

    public void updateTowerType(String nickname, TowerType newTower){
    }

    public void updateWizard(String nickname, Wizard newWizard){
    }

    public boolean updatePlayerList(Collection<PlayerView> playerViews){
        return false;
    }

    public void setUp(List<Wizard>wizards, List<TowerType>towerTypes){

    }

    //METHODS USED BY TABLE

    /**
     * Allows to save the nickname of the current player and sets the label of the current player as yellow
     * @param currentPlayer nickname of the current player
     */
    public void setCurrentPlayer(String currentPlayer){
    }

    /**
     * Method to move mother nature
     * @param movements new island where mother nature is moved
     */
    public void moveMotherNature(int movements){
    }

    /**
     * Method to change the number of bans on an island
     * @param islandID island selected
     * @param numberOfBans new number of bans on the island
     */
    public void changeBansOnIsland(int islandID, int numberOfBans){
    }

    /**
     * Method to change the number of coins of a player
     * @param player player with the number of coins changed
     * @param numberOfCoins new number of coins
     */
    public void changeNumberOfCoinsPlayer(String player, int numberOfCoins){
    }

    /**
     * Method to update the professor of a player
     * @param player player with the porfessor changed
     * @param professors new professors
     */
    public void updateProfessorsToPlayer(String player, HashSet<PawnType> professors){
    }

    /**
     * Method to update the students on the dining room of a player
     * @param player player with the dining room changed
     * @param students new students on the dining room
     */
    public void updateDiningRoomToPlayer(String player, StudentList students){

    }

    /**
     * Method to update the students on the entrance of a player
     * @param player player with the entrance changed
     * @param students new students on the entrance
     */
    public void updateEntranceToPlayer(String player, StudentList students){

    }

    /**
     * Method to update the students on an island
     * @param islandID ID of the island with the students changed
     * @param students new students on the island
     */
    public void updateStudentsOnIsland(int islandID, StudentList students){

    }

    public void updateTowersOnSchoolBoard(String player, int numberOfTowers){

    }

    /**
     * Method to update the tower on an island
     * @param islandID ID of the island with the tower changed
     * @param newTower type of the new tower
     */
    public void updateTowerOnIsland(int islandID, TowerType newTower){

    }

    /**
     * Method to update the students on an cloud
     * @param cloudID ID of the cloud with the students changed
     * @param students new students on the cloud
     */
    public void updateStudentOnCloud(int cloudID, StudentList students){
    }

    /**
     * Method to update the assistant card used by a player
     * @param player player with the assistant changed
     * @param assistant new assistant used by the player
     */
    public void useAssistantCard(String player, Assistant assistant){

    }

    /**
     * Method to add a coin on the card
     * @param cardType type of the card with the coin changed
     */
    public void addCoinOnCard(CharacterCardsType cardType){
    }

    /**
     * Method to update the students on the card
     * @param cardType type of the card with the students changed
     * @param students new students on the card
     */
    public void updateStudentsOnCard(CharacterCardsType cardType, StudentList students){
    }

    /**
     * Method to show on the table that this is the last round
     */
    public void showLastRound(){
    }

    /**
     * Method to update the state of the game
     */
    public void updateState(){
    }

    /**
     * Method to show a message to the player
     * @param message message shown to the player
     */
    public void showMessage(String message){
    }

    /**
     * Method to unify two group of islands
     * @param IDIslandToKeep ID of the islands remaining still
     * @param IDIslandToRemove ID of the island moving towards the other one
     * @param sizeIslandRemoved number of islands in the group containing the island moving
     */
    public void unifyIslands(int IDIslandToKeep, int IDIslandToRemove, int sizeIslandRemoved){

    }

    public void setGameID(String gameID) {
    }

    public void showErrorMessage(String message){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }
}
