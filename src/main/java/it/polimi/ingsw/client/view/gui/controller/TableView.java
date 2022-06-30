package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.reduced_model.*;
import it.polimi.ingsw.client.view.gui.GuiScreen;
import it.polimi.ingsw.client.view.gui.utils.image_getters.*;
import it.polimi.ingsw.client.view.gui.utils.position_getters.CloudPosition;
import it.polimi.ingsw.client.view.gui.utils.position_getters.IslandPosition;
import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.Location;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Dialog;
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

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Class to handle the view of the table of the game
 */
public class TableView extends GuiScreen implements Initializable {

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

    private Collection<Assistant> deck = new ArrayList<>();

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


    /*public void setUp(Collection<Assistant> deck){
        getGui().getScreenBuilder().build(ScreenBuilder.Screen.PLAY_ASSISTANT_CARD);
        Platform.runLater(()->getGui().getCurrentScreen().setUp(deck));
    }*/

    public void askUseAssistant(){
        /*getGui().getScreenBuilder().build(ScreenBuilder.Screen.PLAY_ASSISTANT_CARD);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UseAssistanScreen.fxml"));
            Parent root = loader.load();
            UseAssistantView useAssistantView= loader.getController();
            Platform.runLater(()->useAssistantView.setUp(this.deck));
            getGui().getStage().setScene(getGui().getStage().getScene());
            getGui().getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    getGui().useAssistantCard();

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
        /**
        createTable(new ArrayList<>(List.of(player1,
                player2,
                player3)));*/

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
    @Override
    public void createTable(TableRecord tableRecord, boolean isExpertMode, List<ReducedPlayerLoginInfo> players){
        Platform.runLater(() -> {
                    table.setBackground(Background.fill(Color.LIGHTBLUE));
                    table.toBack();
                    scrollPane.toBack();
                });

        setStateLabelProperties();
        setUpMessageView(players.get(0).wizard());
        setNicknames(players);

        setCoins(isExpertMode, players);

        if (isExpertMode){
            //TODO Add card creation
        }

        createSchoolBoard(tableRecord, players);

        createIslands(tableRecord, isExpertMode);

        createClouds(tableRecord);

        createAssistantDeck(players);

        motherNatureIsland = tableRecord.motherNaturePosition();
        islands.get(tableRecord.motherNaturePosition()).addMotherNature();
        this.deck=tableRecord.assistantsList();

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
    private void setNicknames(List<ReducedPlayerLoginInfo> players){
        for(int playerNumber =0; playerNumber < players.size(); playerNumber++){
            if(playerNumber == 0){
                nickNameLabelPlayer1.setText(players.get(playerNumber).nickname());
                playersLabel.put(nickNameLabelPlayer1.getText(), nickNameLabelPlayer1);
                setNicknameLabelProperties(nickNameLabelPlayer1);
            }
            if(playerNumber == 1){
                nickNameLabelPlayer2.setText(players.get(playerNumber).nickname());
                playersLabel.put(nickNameLabelPlayer2.getText(), nickNameLabelPlayer2);
                setNicknameLabelProperties(nickNameLabelPlayer2);
            }
            if(playerNumber == 2){
                nickNameLabelPlayer3.setText(players.get(playerNumber).nickname());
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
    public void setCoins(boolean isExpertMode, List<ReducedPlayerLoginInfo> players){
        Image coinImage = CoinImageType.COIN.getImage();
        for(int playerNumber =0; playerNumber < players.size(); playerNumber++){
            if(playerNumber == 0){
                ImageView coinViewPlayer1 = new ImageView(coinImage);
                Label numberOfCoinsPlayer1 = new Label();
                numberOfCoinsPlayer1.setText("0");
                numberOfCoinsPlayer1.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
                coinsPlayer1.getChildren().add(numberOfCoinsPlayer1);
                coinsPlayer1.getChildren().add(coinViewPlayer1);
                playersCoinLabels.put(players.get(playerNumber).nickname(), numberOfCoinsPlayer1);
                coinViewPlayer1.setVisible(isExpertMode);
                numberOfCoinsPlayer1.setVisible(isExpertMode);
            }
            if(playerNumber == 1){
                ImageView coinViewPlayer2 = new ImageView(coinImage);
                Label numberOfCoinsPlayer2 = new Label();
                numberOfCoinsPlayer2.setText("0");
                numberOfCoinsPlayer2.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
                coinsPlayer2.getChildren().add(numberOfCoinsPlayer2);
                coinsPlayer2.getChildren().add(coinViewPlayer2);
                playersCoinLabels.put(players.get(playerNumber).nickname(), numberOfCoinsPlayer2);
                coinViewPlayer2.setVisible(isExpertMode);
                numberOfCoinsPlayer2.setVisible(isExpertMode);
            }
            if(playerNumber == 2){
                ImageView coinViewPlayer3 = new ImageView(coinImage);
                Label numberOfCoinsPlayer3 = new Label();
                numberOfCoinsPlayer3.setText("0");
                numberOfCoinsPlayer3.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
                coinsPlayer3.getChildren().add(numberOfCoinsPlayer3);
                coinsPlayer3.getChildren().add(coinViewPlayer3);
                playersCoinLabels.put(players.get(playerNumber).nickname(), numberOfCoinsPlayer3);
                coinViewPlayer3.setVisible(isExpertMode);
                numberOfCoinsPlayer3.setVisible(isExpertMode);
            }
        }
    }


    /**
     * ALlows to create and place the schoolboards on the table
     * @param tableRecord tableRecord table record of the game
     * @param players List of players playing
     */
    private void createSchoolBoard(TableRecord tableRecord, List<ReducedPlayerLoginInfo> players){
        Image schoolBoardImage;
        for (ReducedSchoolBoard reducedSchoolBoard: tableRecord.schoolBoardList()) {
            if (reducedSchoolBoard.getOwner().equals(players.get(0).nickname())) {
                schoolBoardImage = new Image("/assets/schoolboard/Plancia_DEF.png", 1500, 420, true, false);
                ImageView schoolBoardPlayer = new ImageView(schoolBoardImage);
                SchoolBoard schoolBoardPlayer1 = new SchoolBoard(getGui(), true, gridEntrancePlayer1, gridDiningRoomPlayer1, gridTowersPlayer1, reducedSchoolBoard.getTowerType());
                schoolboards.put(reducedSchoolBoard.getOwner(), schoolBoardPlayer1);
                table.add(schoolBoardPlayer, 1, 4);
                schoolBoardPlayer.toBack();
                GridPane.setValignment(schoolBoardPlayer, VPos.BOTTOM);
            } else if (reducedSchoolBoard.getOwner().equals(players.get(1).nickname())) {
                schoolBoardImage = new Image("/assets/schoolboard/Plancia_DEF_reversed.png", 1500, 420, true, false);
                ImageView schoolBoardPlayer = new ImageView(schoolBoardImage);
                SchoolBoard schoolBoardPlayer2 = new SchoolBoard(getGui(), false, gridEntrancePlayer2, gridDiningRoomPlayer2, gridTowersPlayer2, reducedSchoolBoard.getTowerType());
                schoolboards.put(reducedSchoolBoard.getOwner(), schoolBoardPlayer2);
                table.add(schoolBoardPlayer, 1, 0);
                schoolBoardPlayer.toBack();
                GridPane.setValignment(schoolBoardPlayer, VPos.TOP);
            }else {
                schoolBoardImage = new Image("/assets/schoolboard/Plancia_DEF_reversed.png", 1500, 420, true, false);
                ImageView schoolBoardPlayer = new ImageView(schoolBoardImage);
                schoolBoardPlayer.setRotate(-90);
                SchoolBoard schoolBoardPlayer3 = new SchoolBoard(getGui(),false, gridEntrancePlayer3, gridDiningRoomPlayer3, gridTowersPlayer3, reducedSchoolBoard.getTowerType());
                schoolboards.put(reducedSchoolBoard.getOwner(), schoolBoardPlayer3);
                table.add(schoolBoardPlayer, 0, 2);
                schoolBoardPlayer.toBack();
                GridPane.setHalignment(schoolBoardPlayer, HPos.CENTER);
                GridPane.setValignment(schoolBoardPlayer, VPos.CENTER);
                schoolBoardPlayer.setTranslateY(24);
            }
            updateSchoolboard(reducedSchoolBoard);
        }
    }

    private void updateSchoolboard(ReducedSchoolBoard reducedSchoolBoard){
        updateEntranceToPlayer(reducedSchoolBoard.getOwner(), reducedSchoolBoard.getStudentsInEntrance());
        updateDiningRoomToPlayer(reducedSchoolBoard.getOwner(), reducedSchoolBoard.getStudentsInDiningRoom());
        updateTowersOnSchoolBoard(reducedSchoolBoard.getOwner(), reducedSchoolBoard.getTowerNumber());
        updateProfessorsToPlayer(reducedSchoolBoard.getOwner(), reducedSchoolBoard.getProfessors());
        changeNumberOfCoinsPlayer(reducedSchoolBoard.getOwner(), reducedSchoolBoard.getCoinNumber());
    }

    /**
     * Allows to create and place the islands on the table
     */
    private void createIslands(TableRecord tableRecord, boolean isExpertMode){
        int row;
        int column;
        for(ReducedIsland islandReduced : tableRecord.reducedIslands()){
            int random = new Random().nextInt(IslandImageType.values().length);
            Image RandomIslandImage = IslandImageType.values()[random].getImage();
            ImageView islandView = new ImageView(RandomIslandImage);
            islandView.setCursor(Cursor.HAND);
            column= IslandPosition.values()[islandReduced.ID()].getColumn();
            row=IslandPosition.values()[islandReduced.ID()].getRow();
            islandGrid.add(islandView, column, row);
            islandView.toBack();
            GridPane.setValignment(islandView, VPos.CENTER);
            GridPane.setHalignment(islandView, HPos.CENTER);
            Island island = new Island(getGui(), islandGrid, islandView, islandReduced.ID(), isExpertMode);
            islands.add(island);
            island.updateStudentsOnIsland(islandReduced.studentList());
            updateTowerOnIsland(islandReduced.ID(), islandReduced.tower());
            island.changeNumberOfBans(islandReduced.ban());
        }
    }

    /**
     * Allows to create and place the clouds on the table
     * @param tableRecord table record of the game
     */
    private void createClouds(TableRecord tableRecord){
        int row;
        int column;
        for(ReducedCloud reducedCloud: tableRecord.clouds()){
            Image cloudImage = CloudImageType.values()[reducedCloud.ID()].getImage();
            ImageView cloudView = new ImageView(cloudImage);
            column = CloudPosition.values()[reducedCloud.ID()].getColumn();
            row = CloudPosition.values()[reducedCloud.ID()].getRow();
            islandGrid.add(cloudView, column, row);
            Cloud cloud = new Cloud(getGui(), reducedCloud.ID(), cloudView, islandGrid, column, row);
            clouds.add(cloud);
            cloudView.toBack();
            updateStudentOnCloud(reducedCloud.ID(), reducedCloud.students());
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
    private void createAssistantDeck(List<ReducedPlayerLoginInfo> players){
        for(ReducedPlayerLoginInfo player: players){
            String nicknamePlayer = player.nickname();
            Wizard wizardPlayer = player.wizard();
            AssistantCardDeck deck;
            if(players.indexOf(player) == 0){
                deck = new AssistantCardDeck(wizardPlayer, assistantCardPanePlayer1, 1);
            }
            else if(players.indexOf(player) == 1){
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

    // METHODS TO UPDATE THE TABLE

        //UPDATE CURRENT PLAYER AND STATE

    /**
     * Allows to save the nickname of the current player and sets the label of the current player as yellow
     * @param currentPlayer nickname of the current player
     */
    public void setCurrentPlayer(String currentPlayer){
        Platform.runLater(() -> {
            Label labelOldCurrentPlayer = playersLabel.get(this.currentPlayer);
            labelOldCurrentPlayer.setBackground(new Background(new BackgroundFill(Color.YELLOW, new CornerRadii(5), Insets.EMPTY)));
            Label labelNewCurrentPlayer = playersLabel.get(currentPlayer);
            labelNewCurrentPlayer.setBackground(new Background(new BackgroundFill(Color.YELLOW, new CornerRadii(5), Insets.EMPTY)));
        });
        this.currentPlayer = currentPlayer;
    }

    /**
     * Method to update the state of the game
     */
    public void updateState(StateType currentState){
        Platform.runLater(() -> stateLabel.setText(currentState.toString()));
        //TODO: UPDATE STATE
    }

        //UPDATE ISLANDS

    /**
     * Method to move mother nature
     * @param movements new island where mother nature is moved
     */
    public void moveMotherNature(int movements){
        Platform.runLater(() -> {
                    islands.get(motherNatureIsland).removeMotherNature();
                    islands.get(movements).addMotherNature();
                    motherNatureIsland = movements;
        });
    }

    /**
     * Method to change the number of bans on an island
     * @param islandID island selected
     * @param numberOfBans new number of bans on the island
     */
    public void changeBansOnIsland(int islandID, int numberOfBans){
        Platform.runLater(() -> islands.get(islandID).changeNumberOfBans(numberOfBans));
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

    /**
     * Method to update the tower on an island
     * @param islandID ID of the island with the tower changed
     * @param newTower type of the new tower
     */
    public void updateTowerOnIsland(int islandID, TowerType newTower){
        Island island = islands.get(islandID);
        Platform.runLater(() -> island.addTower(newTower));
    }

    /**
     * Method to update the students on an cloud
     * @param cloudID ID of the cloud with the students changed
     * @param students new students on the cloud
     */
    public void updateStudentOnCloud(int cloudID, StudentList students){
        //TODO update student son cloud one at a time
    }

        //UPDATE ASSISTANT CARD

    /**
     * Method to update the assistant card used by a player
     * @param player player with the assistant changed
     * @param assistant new assistant used by the player
     */
    public void useAssistantCard(String player, Assistant assistant){
        AssistantCardDeck playerDeck =decks.get(player);
        Platform.runLater(() -> playerDeck.useAssistantCard(assistant));
    }

    public void updateDeck(List<Assistant> newDeck){
        this.deck=new ArrayList<>(newDeck);
    }

        //UPDATE SCHOOLBOARD

    /**
     * Method to change the number of coins of a player
     * @param player player with the number of coins changed
     * @param numberOfCoins new number of coins
     */
    public void changeNumberOfCoinsPlayer(String player, int numberOfCoins){
        Platform.runLater(() -> playersCoinLabels.get(player).setText(Integer.toString(numberOfCoins)));
    }

    /**
     * Method to update the professor of a player
     * @param player player with the porfessor changed
     * @param professors new professors
     */
    public void updateProfessorsToPlayer(String player, Collection<PawnType> professors){
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
        Platform.runLater(() -> {
            SchoolBoard schoolBoardPlayer = schoolboards.get(player);
            schoolBoardPlayer.updateEntrance(students);
        });
    }

    public void updateTowersOnSchoolBoard(String player, int numberOfTowers){
        SchoolBoard schoolBoardPlayer = schoolboards.get(player);
        schoolBoardPlayer.updateTowers(numberOfTowers);
    }

        //UPDATE CHARACTER CARD

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

        //UPDATE LAST ROUND

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

        //UPDATE MESSAGE

    /**
     * Method to show a message to the player
     * @param message message shown to the player
     */
    public void showMessage(String message){
        Platform.runLater( () -> {
            messageLabel.setText(message);
            messageLabel.setTextFill(Color.BLACK);
        });
    }


    public void showErrorMessage(String message){
        Platform.runLater( () -> {
            messageLabel.setText(message);
            messageLabel.setTextFill(Color.RED);
        });
    }
        //UNIFY ISLANDS

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

       //Find the two nearest islands in the group
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

    //HANDLE LISTENERS

    public void disableAllListeners(){
        String nicknameOwner = getGui().getClientController().getNickNameOwner();
        schoolboards.get(nicknameOwner).disableLocationListener(Location.ENTRANCE);
        schoolboards.get(nicknameOwner).disableLocationListener(Location.DINING_ROOM);
        schoolboards.get(nicknameOwner).disableStudentListeners(Location.ENTRANCE);
        schoolboards.get(nicknameOwner).disableStudentListeners(Location.DINING_ROOM);
        for(Island island: islands){
            island.disableLocationListener();
        }
        for (Cloud cloud: clouds){
            cloud.disableLocationListener();
        }
    }

    public void enableEntranceListeners(){
        String nicknameOwner = getGui().getClientController().getNickNameOwner();
        schoolboards.get(nicknameOwner).enableLocationListener(Location.ENTRANCE);
        schoolboards.get(nicknameOwner).enableStudentListeners(Location.ENTRANCE);
    }

    public void enableDiningRoomListeners(){
        String nicknameOwner = getGui().getClientController().getNickNameOwner();
        schoolboards.get(nicknameOwner).enableLocationListener(Location.DINING_ROOM);
        schoolboards.get(nicknameOwner).enableStudentListeners(Location.DINING_ROOM);
    }

    public void enableIslandsListeners(){
        for(Island island: islands){
            island.enableLocationListener();
        }
    }
    public void enableCloudsListeners(){
        for (Cloud cloud: clouds){
            cloud.enableLocationListener();
        }
    }


        //DEBUGGING

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
