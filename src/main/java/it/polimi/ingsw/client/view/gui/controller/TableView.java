package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.VirtualView;
import it.polimi.ingsw.client.view.gui.utils.image_getters.CharacterCardImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.CloudImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.IslandImageType;
import it.polimi.ingsw.client.view.gui.utils.position_getters.CloudPosition;
import it.polimi.ingsw.client.view.gui.utils.position_getters.IslandPosition;
import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.TowerType;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.*;

/**
 * Class to handle the view of the table of the game
 */
public class TableView extends VirtualView implements Initializable {

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
     * List of schoolboards of the game
     */
    private final List<SchoolBoard> schoolboards = new ArrayList<>();

    /**
     * List of island of the game
     */
    private final List<Island> islands = new ArrayList<>();

    /**
     * List of cloud of the game
     */
    private final Collection<ImageView> clouds = new ArrayList<>();

    /**
     * list of character cards of the game
     */
    private final Collection<ImageView> characterCards = new ArrayList<>();

    /**
     * Map of the deck associated to the player
     */
    private final HashMap<String, AssistantCardDeck> decks = new HashMap<>(3,1);

    /**
     *Initialization of the table where schoolboards, islands, clouds and cards are created
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PlayerLoginInfo player1 = new PlayerLoginInfo("A");
        player1.setTowerType(TowerType.BLACK);
        player1.setWizard(Wizard.W1);
        PlayerLoginInfo player2 = new PlayerLoginInfo("B");
        player2.setTowerType(TowerType.WHITE);
        player2.setWizard(Wizard.W2);
        PlayerLoginInfo player3 = new PlayerLoginInfo("C");
        player3.setTowerType(TowerType.GREY);
        player3.setWizard(Wizard.W3);
        createTable(new ArrayList<>(List.of(player1,
                player2,
                player3)));
        table.toBack();
        scrollPane.toBack();
        //table.setGridLinesVisible(true);
        SchoolBoard schoolBoard = schoolboards.get(2);
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

        islands.get(5).addTower(TowerType.BLACK);
        islands.get(10).addTower(TowerType.WHITE);

        islands.get(10).removeTower();
        islands.get(7).removeMotherNature();

        decks.get("B").useAssistantCard(Assistant.CARD_10);
        decks.get("C").useAssistantCard(Assistant.CARD_5);

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
    }

    /**
     * ALlows to create and place the schoolboards on the table
     * @param players List of players playing
     */
    private void createSchoolBoard(List<PlayerLoginInfo> players){
        Image schoolBoardImage;
        for(int i =0; i< players.size(); i++){
            if(schoolboards.size() == 0){
                schoolBoardImage = new Image("/assets/schoolboard/Plancia_DEF.png", 1500, 420, true, false);
                ImageView schoolBoardPlayer = new ImageView(schoolBoardImage);
                SchoolBoard schoolBoardPlayer1 = new SchoolBoard(players.get(0).getNickname(), gridEntrancePlayer1, gridDiningRoomPlayer1, gridTowersPlayer1, players.get(0).getTowerType());
                schoolboards.add(schoolBoardPlayer1);
                table.add(schoolBoardPlayer, 1,4);
                schoolBoardPlayer.toBack();
                GridPane.setValignment(schoolBoardPlayer, VPos.BOTTOM);
                continue;
            }
            if(schoolboards.size() == 1){
                schoolBoardImage = new Image("/assets/schoolboard/Plancia_DEF_reversed.png", 1500, 420, true, false);
                ImageView schoolBoardPlayer = new ImageView(schoolBoardImage);
                SchoolBoard schoolBoardPlayer2 = new SchoolBoard(players.get(1).getNickname(), gridEntrancePlayer2, gridDiningRoomPlayer2, gridTowersPlayer2, players.get(1).getTowerType());
                schoolboards.add(schoolBoardPlayer2);
                table.add(schoolBoardPlayer, 1, 0);
                schoolBoardPlayer.toBack();
                GridPane.setValignment(schoolBoardPlayer, VPos.TOP);
                continue;
            }
            schoolBoardImage = new Image("/assets/schoolboard/Plancia_DEF_reversed.png", 1500, 420, true, false);
            ImageView schoolBoardPlayer = new ImageView(schoolBoardImage);
            schoolBoardPlayer.setRotate(-90);
            SchoolBoard schoolBoardPlayer3 = new SchoolBoard(players.get(2).getNickname(), gridEntrancePlayer3, gridDiningRoomPlayer3, gridTowersPlayer3, players.get(2).getTowerType());
            schoolboards.add(schoolBoardPlayer3);
            table.add(schoolBoardPlayer, 0,2);
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
            ImageView cloud = new ImageView(cloudImage);
            column = CloudPosition.values()[i].getColumn();
            row = CloudPosition.values()[i].getRow();
            islandGrid.add(cloud, column, row);
            clouds.add(cloud);
            cloud.toBack();
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
            this.characterCards.add(cardView);
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


}
