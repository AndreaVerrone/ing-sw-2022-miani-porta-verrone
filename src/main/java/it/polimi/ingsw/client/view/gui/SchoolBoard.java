package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.model.utils.PawnType;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.*;

public class SchoolBoard {

    private final GridPane gridEntrance;

    private final GridPane gridDiningRoom;

    private final GridPane gridTowers;

    private final Map<PawnType, List<ImageView>> tables = new HashMap<>(4,1);

    private final Map<PawnType, Integer> tablesPositions = new HashMap<>(4,1);

    private final List<ImageView> professors = new ArrayList<>();

    private final List<ImageView> entrance = new ArrayList<>();


    private final EventHandler<MouseEvent> handler = event -> System.out.println("SCElto");

    public SchoolBoard(GridPane gridEntrance, GridPane gridDiningRoom, GridPane gridTowers){
        this.gridEntrance = gridEntrance;
        this.gridEntrance.setOnMouseClicked(new LocationListern(Location.ENTRANCE));
        this.gridDiningRoom = gridDiningRoom;
        this.gridDiningRoom.setOnMouseClicked(new LocationListern(Location.DINING_ROOM));
        this.gridTowers = gridTowers;
        this.gridTowers.setOnMouseClicked(new LocationListern(Location.TOWER_HALL));
        int rowCounter = 1;
        for(PawnType type : PawnType.values()) {
            tables.put(type, new ArrayList<>());
            tablesPositions.put(type, rowCounter);
            rowCounter ++;
        }
    }

    public void addStudentToDiningRoom(PawnType type){
        Students studentType = Students.typeConverter(type);
        ImageView student = new ImageView(studentType.getImage());
        tables.get(type).add(student);
        student.setOnMouseClicked(new StudentListener(type, Location.DINING_ROOM));
        student.toFront();
        gridDiningRoom.add(student, tables.get(type).size(), tablesPositions.get(type));
    }

    public void addStudentToEntrance(PawnType type){
        Students studentType = Students.typeConverter(type);
        ImageView student = new ImageView(studentType.getImage());
        entrance.add(student);
        student.setOnMouseClicked(new StudentListener(type, Location.ENTRANCE));
        student.toFront();
        int row = EntrancePosition.values()[entrance.size()-1].getRow();
        int column = EntrancePosition.values()[entrance.size()-1].getColumn();
        gridEntrance.add(student, column, row);
        GridPane.setHalignment(student, HPos.RIGHT);
    }

    public void addProfessor(PawnType type){
        Professors professorType = Professors.typeConverter(type);
        ImageView professor = new ImageView(professorType.getImage());
        professors.add(professor);
        professor.setOnMouseClicked(new StudentListener(type, Location.DINING_ROOM));
        professor.toFront();
        gridDiningRoom.add(professor, 12, tablesPositions.get(type));
        //professor.setRotate(90);
        GridPane.setHalignment(professor, HPos.CENTER);
    }
}
