package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.utils.image_getters.CharacterCardImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.CloudImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.CoinImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.IslandImageType;
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

    @FXML
    private Label stateLabel;

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
        table.toBack();
        scrollPane.toBack();
        //table.setGridLinesVisible(true);
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
        //gridEntrance.toFront();
        //gridEntrance.setOnMouseClicked(e -> System.out.println("SCELTO"));

        table.setBackground(Background.fill(Color.LIGHTBLUE));

        setCurrentPlayer("Giorgio");
    }


    /**
     * Allows to create the table
     * @param players List of players playing
     */
    public void createTable(List<PlayerLoginInfo> players){
        createSchoolBoard(players);
        createIslands();
        createClouds(players.size());
        createAssistantDeck(players);
        setStateLabelProperties();
        setNicknames(players);
        setCoins(players);
    }

    private void setStateLabelProperties(){
        stateLabel.setTextAlignment(TextAlignment.RIGHT);
        stateLabel.setPadding(new Insets(5));
        stateLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        stateLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.MEDIUM)));
        stateLabel.setBackground(Background.fill(Color.WHITESMOKE));
    }

    /**
     * Allows to save the nickname of the current player and sets the label of the current player as yellow
     * @param currentPlayer nickname of the current player
     */
    public void setCurrentPlayer(String currentPlayer){
        Label labelOldCurrentPlayer = playersLabel.get(this.currentPlayer);
        labelOldCurrentPlayer.setBackground(Background.fill(Color.WHITESMOKE));
        Label labelNewCurrentPlayer = playersLabel.get(currentPlayer);
        labelNewCurrentPlayer.setBackground(Background.fill(Color.YELLOW));
        this.currentPlayer = currentPlayer;
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
        label.setBackground(Background.fill(Color.WHITE));
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
            Island island = new Island(islandGrid, islandView, islandID);
            column= IslandPosition.values()[islandID].getColumn();
            row=IslandPosition.values()[islandID].getRow();
            islandGrid.add(islandView, column, row);
            islandView.toBack();
            GridPane.setValignment(islandView, VPos.CENTER);
            GridPane.setHalignment(islandView, HPos.CENTER);
            islands.add(island);
            island.addMotherNature();
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
        grid.setTranslateX(300);
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

    public void moveMotherNature(int movements){
        islands.get(motherNatureIsland).removeMotherNature();
        islands.get(movements).addMotherNature();
    }

    public void changeBansOnIsland(int islandID, int numberOfBans){
        islands.get(islandID).changeNumberOfBans(numberOfBans);
    }

    public void changeNumberOfCoinsPlayer(String player, int numberOfCoins){
        playersCoinLabels.get(player).setText(Integer.toString(numberOfCoins));
    }

    public void updateProfessorsToPlayer(String player, HashSet<PawnType> professors){
        SchoolBoard schoolBoardPlayer = schoolboards.get(player);
        schoolBoardPlayer.updateProfessors(professors);
    }

    public void updateDiningRoomToPlayer(String player, StudentList students){
        SchoolBoard schoolBoardPlayer = schoolboards.get(player);
        schoolBoardPlayer.updateDiningRoom(students);
    }

    public void updateEntranceToPlayer(String player, StudentList students){
        SchoolBoard schoolBoardPlayer = schoolboards.get(player);
        schoolBoardPlayer.updateEntrance(students);
    }

    public void updateStudentsOnIsland(int islandID, StudentList students){
        Island island = islands.get(islandID);
        island.updateStudentsOnIsland(students);
    }

    public void updateTowersOnSchoolBoard(String player, int numberOfTowers){
        SchoolBoard schoolBoardPlayer = schoolboards.get(player);
        schoolBoardPlayer.updateTowers(numberOfTowers);
    }

    public void updateTowerOnIsland(int islandID, TowerType newTower){
        Island island = islands.get(islandID);
        island.addTower(newTower);
    }

    public void updateStudentOnCloud(int cloudID, StudentList students){

    }

    public void useAssistantCard(String player, Assistant assistant){
        AssistantCardDeck playerDeck =decks.get(player);
        playerDeck.useAssistantCard(assistant);
    }

    public void addCoinOnCard(CharacterCardsType cardType, boolean isCoinOnCard){
        for(CharacterCard card: characterCards){
            if(card.getCardType().equals(cardType)){
                card.setCoinOnCard(isCoinOnCard);
            }
        }
    }

    public void updateStudentsOnCard(CharacterCardsType cardType, StudentList students){
        for(CharacterCard card: characterCards){
            if(card.getCardType().equals(cardType)){
                card.setStudents(students);
            }
        }
    }

    public void updateState(){
        stateLabel.setText("");
        //TODO: UPDATE STATE
    }


    public void tryUpdate(MouseEvent event){
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
    }


}
