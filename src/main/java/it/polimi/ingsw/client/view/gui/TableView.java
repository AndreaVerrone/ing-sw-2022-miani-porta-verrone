package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.view.VirtualView;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.TowerType;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.*;

public class TableView extends VirtualView implements Initializable {

    @FXML
    private GridPane table;

    @FXML
    private GridPane islandGrid;

    @FXML
    private GridPane gridEntrance;

    @FXML
    private GridPane gridDiningRoom;

    @FXML
    private GridPane gridTowers;

    @FXML
    private ScrollPane  scrollPane;

    private final List<ImageView> schoolboards = new ArrayList<>();

    private final Collection<ImageView> islands = new ArrayList<>();

    private final Collection<ImageView> clouds = new ArrayList<>();

    private final EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            System.out.println(event.getX() + "," + event.getY());
        }
    };


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        createSchoolBoard(3);
        createIslands();
        createClouds(3);
        table.toBack();
        scrollPane.toBack();
        //table.setGridLinesVisible(true);
        SchoolBoard schoolBoard = new SchoolBoard(gridEntrance, gridDiningRoom, gridTowers, TowerType.BLACK);
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
        schoolBoard.removeProfessor(PawnType.BLUE_UNICORNS);
        schoolBoard.removeTower();


        Image cardImage= new Image("/assets/characterCards/CarteTOT_front.jpg", 300, 300, true, false);
        ImageView card = new ImageView(cardImage);

        FlowPane grid = new FlowPane(Orientation.HORIZONTAL, 300, 50);
        grid.toFront();
        table.add(grid, 2, 2);
        grid.setTranslateX(300);
        GridPane.setHalignment(grid, HPos.RIGHT);
        grid.getChildren().add(card);
        card = new ImageView(cardImage);
        grid.getChildren().add(card);
        card = new ImageView(cardImage);
        grid.getChildren().add(card);









        //gridEntrance.toFront();
        //gridEntrance.setOnMouseClicked(e -> System.out.println("SCELTO"));

    }

    private void createSchoolBoard(int numberOfPlayers){
        Image schoolBoardImage = new Image("/assets/schoolboard/Plancia_DEF2.png", 1500, 420, true, false);
        for(int i =0; i<numberOfPlayers; i++){
            if(schoolboards.size() == 0){
                ImageView schoolBoardPlayer = new ImageView(schoolBoardImage);
                schoolBoardPlayer.setOnMouseClicked(handler);
                schoolboards.add(schoolBoardPlayer);
                table.add(schoolBoardPlayer, 1,4);
                schoolBoardPlayer.toBack();
                GridPane.setValignment(schoolBoardPlayer, VPos.BOTTOM);
                continue;
            }
            if(schoolboards.size() == 1){
                ImageView schoolBoardPlayer = new ImageView(schoolBoardImage);
                schoolboards.add(schoolBoardPlayer);
                table.add(schoolBoardPlayer, 1, 0);
                schoolBoardPlayer.toBack();
                GridPane.setValignment(schoolBoardPlayer, VPos.TOP);
                continue;
            }
            ImageView schoolBoardPlayer = new ImageView(schoolBoardImage);
            schoolBoardPlayer.setRotate(-90);
            schoolboards.add(schoolBoardPlayer);
            table.add(schoolBoardPlayer, 0,2);
            GridPane.setHalignment(schoolBoardPlayer, HPos.LEFT);
        }
    }

    private void createIslands(){
        //islandGrid.setGridLinesVisible(true);
        int row;
        int column;
        for(int i=0; i<12; i++){
            int random = new Random().nextInt(Islands.values().length);
            Image islandImage = Islands.values()[random].getImage();
            ImageView island = new ImageView(islandImage);
            column=IslandPosition.values()[i].getColumn();
            row=IslandPosition.values()[i].getRow();
            islandGrid.add(island, column, row);
            GridPane.setValignment(island, VPos.CENTER);
            islands.add(island);
            island.setOnMouseClicked(e -> System.out.println("SCELTO"));
            island.toFront();
            //GridPane.setValignment(island, VPos.TOP);
        }
    }

    private void createClouds(int numberOfPlayers){
        int row;
        int column;
        for(int i=0;i<numberOfPlayers; i++){
            Image cloudImage = Clouds.values()[i].getImage();
            ImageView cloud = new ImageView(cloudImage);
            column = CloudPosition.values()[i].getColumn();
            row = CloudPosition.values()[i].getRow();
            islandGrid.add(cloud, column, row);
            clouds.add(cloud);
        }

    }



}
