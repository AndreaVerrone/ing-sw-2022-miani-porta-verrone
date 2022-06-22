package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.utils.image_getters.*;
import it.polimi.ingsw.client.view.gui.utils.position_getters.CloudPosition;
import it.polimi.ingsw.client.view.gui.utils.position_getters.IslandPosition;
import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Class to handle the view of the table of the game
 */
public class TableView implements Initializable {

    /**
     * Pane of the view used to scroll
     */
    @FXML
    private ScrollPane  scrollPane;

    /**
     * Grid of the table used to place the other grids
     */
    @FXML
    private GridPane table;

    /**
     * Grid of the table used to place the islands
     */
    @FXML
    private GridPane islandGrid;

    /**
     * Grid of the entrance of the client
     */
    @FXML
    private GridPane gridEntrancePlayer1;

    /**
     * Grid of the dining room of the client
     */
    @FXML
    private GridPane gridDiningRoomPlayer1;

    /**
     * Grid of the tower hall of the client
     */
    @FXML
    private GridPane gridTowersPlayer1;

    /**
     * Grid of the entrance of the player placed in front of the client
     */
    @FXML
    private GridPane gridEntrancePlayer2;

    /**
     * Grid of the dining room of the player placed in front of the client
     */
    @FXML
    private GridPane gridDiningRoomPlayer2;

    /**
     * Grid of the tower hall of the player placed in front of the client
     */
    @FXML
    private GridPane gridTowersPlayer2;

    /**
     * Grid of the entrance of the player placed on the left of the client
     */
    @FXML
    private GridPane gridEntrancePlayer3;

    /**
     * Grid of the dining room of the player placed on the left of the client
     */
    @FXML
    private GridPane gridDiningRoomPlayer3;

    /**
     * Grid of the tower hall of the player placed on the left of the client
     */
    @FXML
    private GridPane gridTowersPlayer3;

    /**
     * Pane containing the assistant cards of the client
     */
    @FXML
    private Pane assistantCardPanePlayer1;

    /**
     * Pane containing the assistant cards of the player placed in front of the client
     */
    @FXML
    private Pane assistantCardPanePlayer2;

    /**
     * Pane containing the assistant cards of the player placed on the left of the client
     */
    @FXML
    private Pane assistantCardPanePlayer3;

    /**
     * Label containing the nickname of the client
     */
    @FXML
    private Label nickNameLabelPlayer1;

    /**
     * Label containing the nickname of player in front of the client
     */
    @FXML
    private Label nickNameLabelPlayer2;

    /**
     * Label containing the nickname of player on the left of the client
     */
    @FXML
    private Label nickNameLabelPlayer3;

    /**
     * Pane containing the coins of the client
     */
    @FXML
    private FlowPane coinsPlayer1;

    /**
     * Pane containing the coins of player in front of the client
     */
    @FXML
    private FlowPane coinsPlayer2;

    /**
     * Pane containing the coins of player on the left of the client
     */
    @FXML
    private FlowPane coinsPlayer3;

    /**
     * Label showing the current state of the game
     */
    @FXML
    private Label stateLabel;

    /**
     * Label used to show a message
     */
    @FXML
    private Label messageLabel;

    /**
     * Image used to show the message
      */
    @FXML
    private ImageView messageImage;

    /**
     * Map with the schoolboards associated to every student
     */
    private final HashMap<String, SchoolBoard> schoolboards = new HashMap<>(3,1);

    /**
     * List of island of the game
     */
    private final List<Island> islands = new ArrayList<>();

    /**
     * List of cloud of the game
     */
    private final List<Cloud> clouds = new ArrayList<>();

    /**
     * list of character cards of the game
     */
    private final Collection<CharacterCard> characterCards = new ArrayList<>();

    /**
     * Map of the deck associated to the player
     */
    private final HashMap<String, AssistantCardDeck> decks = new HashMap<>(3,1);

    /**
     * Map of the nicknames' labels associated to every player
     */
    private final HashMap<String, Label> playersLabel = new HashMap<>(3,1);

    /**
     * Map of the abel containing the number of coins of a player associated to every player
     */
    private final HashMap<String, Label> playersCoinLabels = new HashMap<>(3 ,1);

    /**
     * Nickname of the current player
     */
    private String currentPlayer;

    /**
     * ID of the island where mother nature is placed
     */
    private int motherNatureIsland;


    /**
     *Initialization of the table where schoolboards, islands, clouds and cards are created
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void tryCreateTable(){
        PlayerLoginInfo player1 = new PlayerLoginInfo("Giorgio");
        this.currentPlayer = "Giorgio";
        player1.setTowerType(TowerType.BLACK);
        player1.setWizard(Wizard.W1);
        PlayerLoginInfo player2 = new PlayerLoginInfo("Andrea");
        player2.setTowerType(TowerType.WHITE);
        player2.setWizard(Wizard.W2);
        PlayerLoginInfo player3 = new PlayerLoginInfo("Alessia");
        player3.setTowerType(TowerType.GREY);
        player3.setWizard(Wizard.W3);
        createTable(new ArrayList<>(List.of(player1,
                player2,
                player3)));

        SchoolBoard schoolBoard = schoolboards.get("Giorgio");
        schoolBoard.addStudentToDiningRoom(PawnType.GREEN_FROGS);
        schoolBoard.addStudentToDiningRoom(PawnType.GREEN_FROGS);
        schoolBoard.addStudentToDiningRoom(PawnType.GREEN_FROGS);
        schoolBoard.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        schoolBoard.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        schoolBoard.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        schoolBoard.addStudentToEntrance(PawnType.RED_DRAGONS);
        schoolBoard.addStudentToEntrance(PawnType.PINK_FAIRIES);
        schoolBoard.addStudentToEntrance(PawnType.BLUE_UNICORNS);
        schoolBoard.addStudentToEntrance(PawnType.GREEN_FROGS);
        schoolBoard.addProfessor(PawnType.RED_DRAGONS);
        schoolBoard.addProfessor(PawnType.BLUE_UNICORNS);
        schoolBoard.addProfessor(PawnType.YELLOW_GNOMES);
        schoolBoard.removeStudentFromDiningRoom(PawnType.GREEN_FROGS);
        schoolBoard.removeStudentFromEntrance(PawnType.PINK_FAIRIES);
        schoolBoard.removeStudentFromEntrance(PawnType.BLUE_UNICORNS);
        schoolBoard.addStudentToEntrance(PawnType.YELLOW_GNOMES);
        schoolBoard.addStudentToEntrance(PawnType.YELLOW_GNOMES);
        schoolBoard.addStudentToEntrance(PawnType.YELLOW_GNOMES);
        schoolBoard.addStudentToEntrance(PawnType.PINK_FAIRIES);
        schoolBoard.removeProfessor(PawnType.BLUE_UNICORNS);
        schoolBoard.removeTower();
        schoolBoard.addProfessor(PawnType.GREEN_FROGS);

        islands.get(5).addTower(TowerType.BLACK);
        islands.get(10).addTower(TowerType.WHITE);
        clouds.get(1).addStudent(PawnType.YELLOW_GNOMES);
        clouds.get(1).addStudent(PawnType.RED_DRAGONS);
        clouds.get(1).addStudent(PawnType.BLUE_UNICORNS);

        islands.get(10).removeTower();
        islands.get(7).removeMotherNature();

        decks.get("Andrea").useAssistantCard(Assistant.CARD_10);
        decks.get("Alessia").useAssistantCard(Assistant.CARD_5);

        islands.get(3).addStudent(PawnType.YELLOW_GNOMES);
        islands.get(3).addStudent(PawnType.GREEN_FROGS);
        islands.get(3).addStudent(PawnType.YELLOW_GNOMES);
        islands.get(7).addStudent(PawnType.RED_DRAGONS);
        islands.get(9).addStudent(PawnType.PINK_FAIRIES);
        islands.get(1).addStudent(PawnType.BLUE_UNICORNS);

        addCards(new ArrayList<>((List.of(CharacterCardsType.CARD1,
                CharacterCardsType.CARD5,
                CharacterCardsType.CARD9))));

        setCurrentPlayer("Giorgio");
    }


    /**
     * Allows to create the table
     * @param players List of players playing
     */
    public void createTable(List<PlayerLoginInfo> players){
        table.setBackground(Background.fill(Color.LIGHTBLUE));
        table.toBack();
        scrollPane.toBack();
        createSchoolBoard(players);
        createIslands();
        createClouds(players.size());
        createAssistantDeck(players);
        setStateLabelProperties();
        setUpMessageView(players.get(0).getWizard());
        setNicknames(players);
        setCoins(players);
    }

    /**
     * Method to set the properties of the label that shows the state of the game
     */
    private void setStateLabelProperties(){
        stateLabel.setTextAlignment(TextAlignment.RIGHT);
        stateLabel.setPadding(new Insets(5));
        stateLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        stateLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), BorderStroke.MEDIUM)));
        stateLabel.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, new CornerRadii(20), Insets.EMPTY)));
    }

    /**
     * Allows to set the nicknames of the players when the game is created
     * @param players list of players playing
     */
    private void setNicknames(List<PlayerLoginInfo> players){
        for(int playerNumber =0; playerNumber < players.size(); playerNumber++){
            if(playerNumber == 0){
                nickNameLabelPlayer1.setText(players.get(playerNumber).getNickname());
                playersLabel.put(nickNameLabelPlayer1.getText(), nickNameLabelPlayer1);
                setNicknameLabelProperties(nickNameLabelPlayer1);
            }
            if(playerNumber == 1){
                nickNameLabelPlayer2.setText(players.get(playerNumber).getNickname());
                playersLabel.put(nickNameLabelPlayer2.getText(), nickNameLabelPlayer2);
                setNicknameLabelProperties(nickNameLabelPlayer2);
            }
            if(playerNumber == 2){
                nickNameLabelPlayer3.setText(players.get(playerNumber).getNickname());
                playersLabel.put(nickNameLabelPlayer3.getText(), nickNameLabelPlayer3);
                setNicknameLabelProperties(nickNameLabelPlayer3);
            }
        }
    }

    /**
     * Allows to set the properties of the label used for the nicknames
     * @param label label with the nickname
     */
    private void setNicknameLabelProperties(Label label) {
        label.setTextAlignment(TextAlignment.RIGHT);
        label.setPadding(new Insets(5));
        label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        label.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.MEDIUM)));
        label.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, new CornerRadii(5), Insets.EMPTY)));
    }

    /**
     * Method used to set up the abel and the image to show a message to the player
     * @param wizard wizard chosen by the owner
     */
    private void setUpMessageView(Wizard wizard){
        messageLabel.setPadding(new Insets(5));
        messageLabel.setFont(Font.font("verdana", FontWeight.MEDIUM, FontPosture.REGULAR, 20));
        messageLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(40), BorderStroke.MEDIUM)));
        messageLabel.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, new CornerRadii(40), Insets.EMPTY)));
        messageImage.setImage(WizardImageType.typeConverter(wizard).getImageWithoutBackground());
    }

    /**
     * Allows to set the coins on the view of the table
     * @param players list of players playing
     */
    public void setCoins(List<PlayerLoginInfo> players){
        Image coinImage = CoinImageType.COIN.getImage();
        for(int playerNumber =0; playerNumber < players.size(); playerNumber++){
            if(playerNumber == 0){
                ImageView coinViewPlayer1 = new ImageView(coinImage);
                Label numberOfCoinsPlayer1 = new Label();
                numberOfCoinsPlayer1.setText("0");
                numberOfCoinsPlayer1.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
                coinsPlayer1.getChildren().add(numberOfCoinsPlayer1);
                coinsPlayer1.getChildren().add(coinViewPlayer1);
                playersCoinLabels.put(players.get(playerNumber).getNickname(), numberOfCoinsPlayer1);
            }
            if(playerNumber == 1){
                ImageView coinViewPlayer2 = new ImageView(coinImage);
                Label numberOfCoinsPlayer2 = new Label();
                numberOfCoinsPlayer2.setText("0");
                numberOfCoinsPlayer2.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
                coinsPlayer2.getChildren().add(numberOfCoinsPlayer2);
                coinsPlayer2.getChildren().add(coinViewPlayer2);
                playersCoinLabels.put(players.get(playerNumber).getNickname(), numberOfCoinsPlayer2);
            }
            if(playerNumber == 2){
                ImageView coinViewPlayer3 = new ImageView(coinImage);
                Label numberOfCoinsPlayer3 = new Label();
                numberOfCoinsPlayer3.setText("0");
                numberOfCoinsPlayer3.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
                coinsPlayer3.getChildren().add(numberOfCoinsPlayer3);
                coinsPlayer3.getChildren().add(coinViewPlayer3);
                playersCoinLabels.put(players.get(playerNumber).getNickname(), numberOfCoinsPlayer3);
            }
        }
    }


    /**
     * ALlows to create and place the schoolboards on the table
     * @param players List of players playing
     */
    private void createSchoolBoard(List<PlayerLoginInfo> players){
        Image schoolBoardImage;
        for (PlayerLoginInfo player : players) {
            if (schoolboards.size() == 0) {
                schoolBoardImage = new Image("/assets/schoolboard/Plancia_DEF.png", 1500, 420, true, false);
                ImageView schoolBoardPlayer = new ImageView(schoolBoardImage);
                SchoolBoard schoolBoardPlayer1 = new SchoolBoard(true, gridEntrancePlayer1, gridDiningRoomPlayer1, gridTowersPlayer1, players.get(0).getTowerType());
                schoolboards.put(player.getNickname(), schoolBoardPlayer1);
                table.add(schoolBoardPlayer, 1, 4);
                schoolBoardPlayer.toBack();
                GridPane.setValignment(schoolBoardPlayer, VPos.BOTTOM);
                continue;
            }
            if (schoolboards.size() == 1) {
                schoolBoardImage = new Image("/assets/schoolboard/Plancia_DEF_reversed.png", 1500, 420, true, false);
                ImageView schoolBoardPlayer = new ImageView(schoolBoardImage);
                SchoolBoard schoolBoardPlayer2 = new SchoolBoard(false, gridEntrancePlayer2, gridDiningRoomPlayer2, gridTowersPlayer2, players.get(1).getTowerType());
                schoolboards.put(player.getNickname(), schoolBoardPlayer2);
                table.add(schoolBoardPlayer, 1, 0);
                schoolBoardPlayer.toBack();
                GridPane.setValignment(schoolBoardPlayer, VPos.TOP);
                continue;
            }
            schoolBoardImage = new Image("/assets/schoolboard/Plancia_DEF_reversed.png", 1500, 420, true, false);
            ImageView schoolBoardPlayer = new ImageView(schoolBoardImage);
            schoolBoardPlayer.setRotate(-90);
            SchoolBoard schoolBoardPlayer3 = new SchoolBoard(false, gridEntrancePlayer3, gridDiningRoomPlayer3, gridTowersPlayer3, players.get(2).getTowerType());
            schoolboards.put(player.getNickname(), schoolBoardPlayer3);
            table.add(schoolBoardPlayer, 0, 2);
            schoolBoardPlayer.toBack();
            GridPane.setHalignment(schoolBoardPlayer, HPos.CENTER);
            GridPane.setValignment(schoolBoardPlayer, VPos.CENTER);
            schoolBoardPlayer.setTranslateY(24);
        }
    }

    /**
     * Allows to create and place the islands on the table
     */
    private void createIslands(){
        int row;
        int column;
        for(int islandID=0; islandID<12; islandID++){
            int random = new Random().nextInt(IslandImageType.values().length);
            Image RandomIslandImage = IslandImageType.values()[random].getImage();
            ImageView islandView = new ImageView(RandomIslandImage);
            islandView.setCursor(Cursor.HAND);
            column= IslandPosition.values()[islandID].getColumn();
            row=IslandPosition.values()[islandID].getRow();
            islandGrid.add(islandView, column, row);
            islandView.toBack();
            GridPane.setValignment(islandView, VPos.CENTER);
            GridPane.setHalignment(islandView, HPos.CENTER);
            Island island = new Island(islandGrid, islandView, islandID);
            islands.add(island);
        }
    }

    /**
     * Allows to create and place the clouds on the table
     * @param numberOfPlayers number of players playing
     */
    private void createClouds(int numberOfPlayers){
        int row;
        int column;
        for(int i=0;i<numberOfPlayers; i++){
            Image cloudImage = CloudImageType.values()[i].getImage();
            ImageView cloudView = new ImageView(cloudImage);
            column = CloudPosition.values()[i].getColumn();
            row = CloudPosition.values()[i].getRow();
            islandGrid.add(cloudView, column, row);
            Cloud cloud = new Cloud(cloudView, islandGrid, column, row);
            clouds.add(cloud);
            cloudView.toBack();
        }

    }

    /**
     * Allows to create and place the character cards on the table
     * @param cards List of {@code CharacterCardsType} randomly choosen to be used
     */
    private void addCards(Collection<CharacterCardsType> cards){
        FlowPane grid = new FlowPane(Orientation.HORIZONTAL, 300, 50);
        table.add(grid, 2, 2);
        grid.setTranslateX(500);
        for(CharacterCardsType card : cards){
            ImageView cardView = new ImageView(CharacterCardImageType.typeConverter(card).getImage());
            CharacterCard newCard = new CharacterCard(card, cardView);
            this.characterCards.add(newCard);
            grid.getChildren().add(cardView);
        }

    }

    /**
     * Allows to create and place an assistant deck for every player
     * @param players List of players playing
     */
    private void createAssistantDeck(List<PlayerLoginInfo> players){
        for(int numberOfPlayers =0 ; numberOfPlayers < players.size(); numberOfPlayers++){
            String nicknamePlayer = players.get(numberOfPlayers).getNickname();
            Wizard wizardPlayer = players.get(numberOfPlayers).getWizard();
            AssistantCardDeck deck;
            if(numberOfPlayers == 0){
                deck = new AssistantCardDeck(wizardPlayer, assistantCardPanePlayer1, 1);
            }
            else if(numberOfPlayers == 1){
                deck = new AssistantCardDeck(wizardPlayer, assistantCardPanePlayer2, 2);
            }
            else {
                deck = new AssistantCardDeck(wizardPlayer, assistantCardPanePlayer3, 3);
            }
            decks.put(nicknamePlayer, deck);
        }
    }

    /**
     * Method to set a closed hand when the cursor is pressed
     * @param event event triggered by the mouse when is pressed
     */
     public void setCursorOnMouseDragged(Event event){
        scrollPane.setCursor(Cursor.CLOSED_HAND);
     }

    /**
     * Method to set an open hand when the cursor is pressed
     * @param event event triggered by the mouse when is released
     */
    public void setCursorOnMouseReleased(Event event){
        scrollPane.setCursor(Cursor.OPEN_HAND);
    }

    // METHODS TO MODIFY THE TABLE

    /**
     * Allows to save the nickname of the current player and sets the label of the current player as yellow
     * @param currentPlayer nickname of the current player
     */
    public void setCurrentPlayer(String currentPlayer){
        Label labelOldCurrentPlayer = playersLabel.get(this.currentPlayer);
        labelOldCurrentPlayer.setBackground(new Background(new BackgroundFill(Color.YELLOW, new CornerRadii(5), Insets.EMPTY)));
        Label labelNewCurrentPlayer = playersLabel.get(currentPlayer);
        labelNewCurrentPlayer.setBackground(new Background(new BackgroundFill(Color.YELLOW, new CornerRadii(5), Insets.EMPTY)));
        this.currentPlayer = currentPlayer;
    }

    /**
     * Method to move mother nature
     * @param movements new island where mother nature is moved
     */
    public void moveMotherNature(int movements){
        islands.get(motherNatureIsland).removeMotherNature();
        islands.get(movements).addMotherNature();
        motherNatureIsland = movements;
    }

    /**
     * Method to change the number of bans on an island
     * @param islandID island selected
     * @param numberOfBans new number of bans on the island
     */
    public void changeBansOnIsland(int islandID, int numberOfBans){
        islands.get(islandID).changeNumberOfBans(numberOfBans);
    }

    /**
     * Method to change the number of coins of a player
     * @param player player with the number of coins changed
     * @param numberOfCoins new number of coins
     */
    public void changeNumberOfCoinsPlayer(String player, int numberOfCoins){
        playersCoinLabels.get(player).setText(Integer.toString(numberOfCoins));
    }

    /**
     * Method to update the professor of a player
     * @param player player with the porfessor changed
     * @param professors new professors
     */
    public void updateProfessorsToPlayer(String player, HashSet<PawnType> professors){
        SchoolBoard schoolBoardPlayer = schoolboards.get(player);
        schoolBoardPlayer.updateProfessors(professors);
    }

    /**
     * Method to update the students on the dining room of a player
     * @param player player with the dining room changed
     * @param students new students on the dining room
     */
    public void updateDiningRoomToPlayer(String player, StudentList students){
        SchoolBoard schoolBoardPlayer = schoolboards.get(player);
        schoolBoardPlayer.updateDiningRoom(students);
    }

    /**
     * Method to update the students on the entrance of a player
     * @param player player with the entrance changed
     * @param students new students on the entrance
     */
    public void updateEntranceToPlayer(String player, StudentList students){
        SchoolBoard schoolBoardPlayer = schoolboards.get(player);
        schoolBoardPlayer.updateEntrance(students);
    }

    /**
     * Method to update the students on an island
     * @param islandID ID of the island with the students changed
     * @param students new students on the island
     */
    public void updateStudentsOnIsland(int islandID, StudentList students){
        Island island = islands.get(islandID);
        island.updateStudentsOnIsland(students);
    }

    public void updateTowersOnSchoolBoard(String player, int numberOfTowers){
        SchoolBoard schoolBoardPlayer = schoolboards.get(player);
        schoolBoardPlayer.updateTowers(numberOfTowers);
    }

    /**
     * Method to update the tower on an island
     * @param islandID ID of the island with the tower changed
     * @param newTower type of the new tower
     */
    public void updateTowerOnIsland(int islandID, TowerType newTower){
        Island island = islands.get(islandID);
        island.addTower(newTower);
    }

    /**
     * Method to update the students on an cloud
     * @param cloudID ID of the cloud with the students changed
     * @param students new students on the cloud
     */
    public void updateStudentOnCloud(int cloudID, StudentList students){
        //TODO update student son cloud one at a time
    }

    /**
     * Method to update the assistant card used by a player
     * @param player player with the assistant changed
     * @param assistant new assistant used by the player
     */
    public void useAssistantCard(String player, Assistant assistant){
        AssistantCardDeck playerDeck =decks.get(player);
        playerDeck.useAssistantCard(assistant);
    }

    /**
     * Method to add a coin on the card
     * @param cardType type of the card with the coin changed
     */
    public void addCoinOnCard(CharacterCardsType cardType){
        for(CharacterCard card: characterCards){
            if(card.getCardType().equals(cardType)){
                card.incrementCost();
            }
        }
    }

    /**
     * Method to update the students on the card
     * @param cardType type of the card with the students changed
     * @param students new students on the card
     */
    public void updateStudentsOnCard(CharacterCardsType cardType, StudentList students){
        for(CharacterCard card: characterCards){
            if(card.getCardType().equals(cardType)){
                card.setStudents(students);
            }
        }
    }

    /**
     * Method to show on the table that this is the last round
     */
    public void showLastRound(){

        new Thread(() -> {
            String oldText = messageLabel.getText();
            Platform.runLater(() -> messageLabel.setText("LAST ROUND"));
            for(int blinks = 0; blinks < 3; blinks ++){
                Platform.runLater(() -> messageLabel.setBackground(new Background(new BackgroundFill(Color.YELLOW, new CornerRadii(40), Insets.EMPTY))));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Platform.runLater(() -> messageLabel.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, new CornerRadii(40), Insets.EMPTY))));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Platform.runLater(()-> messageLabel.setText(oldText));
        }).start();
    }

    /**
     * Method to update the state of the game
     */
    public void updateState(){
        stateLabel.setText("");
        //TODO: UPDATE STATE
    }

    /**
     * Method to show a message to the player
     * @param message message shown to the player
     */
    public void showMessage(String message){
        messageLabel.setText(message);
    }

    /**
     * Method to unify two group of islands
     * @param IDIslandToKeep ID of the islands remaining still
     * @param IDIslandToRemove ID of the island moving towards the other one
     * @param sizeIslandRemoved number of islands in the group containing the island moving
     */
    public void unifyIslands(int IDIslandToKeep, int IDIslandToRemove, int sizeIslandRemoved){
        Island islandToKeep = islands.get(IDIslandToKeep);
        Island islandToRemove = islands.get(IDIslandToRemove);

        //Get the last and the first island of each group
        int IDLastIslandClockWiseToKeep = getLastIslandClockWise(islandToKeep);
        int IDLastIslandCounterClockWiseToKeep= getLastIslandCounterClockWise(islandToKeep);
        int IDLastIslandClockWiseToRemove = getLastIslandClockWise(islandToRemove);
        int IDLastIslandCounterCLockWiseToRemove = getLastIslandCounterClockWise(islandToRemove);
        boolean roundClockWise;

       //Find the tow nearest islands in the group
        if(IDLastIslandCounterClockWiseToKeep - IDLastIslandClockWiseToRemove == 1 || IDLastIslandCounterClockWiseToKeep - IDLastIslandClockWiseToRemove == -11){
            IDIslandToKeep = IDLastIslandCounterClockWiseToKeep;
            IDIslandToRemove = IDLastIslandClockWiseToRemove;
            islandToKeep = islands.get(IDIslandToKeep);
            islandToRemove = islands.get(IDIslandToRemove);
            islandToKeep.setIslandUnitedCounterClockWise(islandToRemove);
            islandToRemove.setIslandUnitedClockwise(islandToKeep);
            roundClockWise = true;
        }
        else{
            IDIslandToKeep = IDLastIslandClockWiseToKeep;
            IDIslandToRemove = IDLastIslandCounterCLockWiseToRemove;
            islandToKeep.setIslandUnitedClockwise(islandToRemove);
            islandToRemove.setIslandUnitedCounterClockWise(islandToKeep);
            roundClockWise = false;
        }

        //Move each island of one group to the nearest island of the other one
        int IDKept = IDIslandToKeep;
        int IDRemoved;
        for(int i=0; i < sizeIslandRemoved; i++){
            if(!roundClockWise){
                IDRemoved = (IDIslandToRemove + i) % 12;
            }else {
                IDRemoved = (IDIslandToRemove + 12 - i) % 12;
            }
            moveIslands(IDKept, IDRemoved);
            IDKept = IDRemoved;
        }

    }

    /**
     * Get last island of a group of island moving clockwise
     * @param island one island of the group
     * @return ID of the last island of the group of island moving clockwise
     */
    private int getLastIslandClockWise(Island island){
        while (island.getIslandUnitedClockwise() != null){
            island = island.getIslandUnitedClockwise();
        }
        return island.getIslandID();
    }

    /**
     * Get last island of a group of island moving counterclockwise
     * @param island one island of the group
     * @return ID of the last island of the group of island moving counterclockwise
     */
    private int getLastIslandCounterClockWise(Island island){
        while (island.getIslandUnitedCounterClockWise() != null){
            island = island.getIslandUnitedCounterClockWise();
        }
        return island.getIslandID();
    }

    /**
     * Method to move two islands near each other.One island stay stills and the other one moves towards the first one
     * @param IDIslandToKeep ID of the island remaining still
     * @param IDIslandToRemove ID of the island moving
     */
    public void moveIslands(int IDIslandToKeep, int IDIslandToRemove){
        Island islandToKeep = islands.get(IDIslandToKeep);
        Island islandToRemove = islands.get(IDIslandToRemove);

        //FInd distance between islands
        double xTranslation = islandToKeep.getXPosition() - islandToRemove.getXPosition();
        double yTranslation = islandToKeep.getYPosition() - islandToRemove.getYPosition();
        islandToKeep.calculateCoordinates();

        //Find the the direction of the movement
        boolean roundClockWise = ((IDIslandToRemove < IDIslandToKeep) && !(IDIslandToRemove == 0 && IDIslandToKeep == 11)) || (IDIslandToRemove == 11 && IDIslandToKeep == 0);

        //Place the island based on the position on the map of the island remaining still
        if(roundClockWise){
            xTranslation += islandToKeep.getCounterClockWiseXCoordinate();
            yTranslation += islandToKeep.getCounterClockWiseYCoordinate();
        }else{
            xTranslation += islandToKeep.getClockWiseXCoordinate();
            yTranslation += islandToKeep.getClockWiseYCoordinate();
        }

        //Move the island
        islandToRemove.translateIsland(xTranslation, yTranslation);

    }


    /**
     * Method and attribute used only for debugging, remove after
     */
    int clicks = 0;
    public void tryUpdate(MouseEvent event){
        clicks ++;
        StudentList students1 = new StudentList();
        StudentList students2 = new StudentList();
        try {
            students1.changeNumOf(PawnType.RED_DRAGONS, 3);
            students1.changeNumOf(PawnType.GREEN_FROGS, 6);
            students1.changeNumOf(PawnType.YELLOW_GNOMES, 2);
            students2.changeNumOf(PawnType.GREEN_FROGS, 2);
            students2.changeNumOf(PawnType.PINK_FAIRIES, 1);
            students2.changeNumOf(PawnType.BLUE_UNICORNS, 1);
        } catch (NotEnoughStudentException e) {
            throw new RuntimeException(e);
        }

        updateDiningRoomToPlayer("Giorgio", students1);
        updateDiningRoomToPlayer("Andrea", students1);
        updateEntranceToPlayer("Giorgio", students2);
        //updateEntranceToPlayer("Andrea", students2);
        //updateEntranceToPlayer("Alessia", students2);

        updateTowersOnSchoolBoard("Giorgio", 3);

        showLastRound();

        if (clicks == 1) {
            motherNatureIsland = 6;
            moveMotherNature(11);
            updateTowerOnIsland(10, TowerType.WHITE);
            changeBansOnIsland(10, 1);
            unifyIslands(10, 11, 1);
        }
        if(clicks == 2){
            moveMotherNature(2);
            unifyIslands(10, 9, 1);
        }
        if(clicks == 3){
            unifyIslands(9, 8, 1);
            updateTowerOnIsland(10, TowerType.BLACK);
            moveMotherNature(11);
        }
        if(clicks == 4){
            unifyIslands(8, 7, 1);
            updateTowerOnIsland(10, TowerType.BLACK);
            moveMotherNature(11);
        }
        if(clicks == 5){
            unifyIslands(6, 11, 5);
            updateTowerOnIsland(10, TowerType.BLACK);
            moveMotherNature(11);
        }
        if(clicks == 6){
            unifyIslands(0, 8, 6);
            updateTowerOnIsland(10, TowerType.BLACK);
            moveMotherNature(11);
        }
        if(clicks == 7){
            unifyIslands(6, 5, 1);
            updateTowerOnIsland(10, TowerType.BLACK);
            moveMotherNature(11);
        }
        if(clicks == 8){
            unifyIslands(5, 4, 1);
            updateTowerOnIsland(10, TowerType.BLACK);
            moveMotherNature(11);
        }
        if(clicks == 9){
            unifyIslands(4, 3, 1);
            updateTowerOnIsland(10, TowerType.BLACK);
            moveMotherNature(11);
        }
    }


}
