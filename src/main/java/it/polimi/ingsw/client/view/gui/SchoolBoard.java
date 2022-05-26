package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.TowerType;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.*;

public class SchoolBoard {

    private final GridPane gridEntrance;

    private final GridPane gridDiningRoom;

    private final GridPane gridTowers;

    private final Map<PawnType, List<Pawn>> tables = new HashMap<>(4,1);

    private final List<Pawn> professors = new ArrayList<>();

    private final List<Pawn> entrance = new ArrayList<>();

    private final List<ImageView> towers = new ArrayList<>();

    private final Towers towerType;

    public SchoolBoard(GridPane gridEntrance, GridPane gridDiningRoom, GridPane gridTowers, TowerType towerType){
        this.towerType = Towers.typeConverter(towerType);
        this.gridEntrance = gridEntrance;
        this.gridEntrance.setOnMouseClicked(new LocationListern(Location.ENTRANCE));
        this.gridDiningRoom = gridDiningRoom;
        this.gridDiningRoom.setOnMouseClicked(new LocationListern(Location.DINING_ROOM));
        this.gridTowers = gridTowers;
        this.gridTowers.setOnMouseClicked(new LocationListern(Location.TOWER_HALL));
        for(PawnType type : PawnType.values()) {
            tables.put(type, new ArrayList<>());
        }
        fillTowers(8);
    }

    public void addStudentToDiningRoom(PawnType type){
        Students studentType = Students.typeConverter(type);
        ImageView student = new ImageView(studentType.getImage());
        tables.get(type).add(new Pawn(student, type));
        student.setOnMouseClicked(new StudentListener(type, Location.DINING_ROOM));
        student.toFront();
        gridDiningRoom.add(student, tables.get(type).size(), studentType.getTablePosition());
    }

    public void addStudentToEntrance(PawnType type){
        Students studentType = Students.typeConverter(type);
        ImageView student = new ImageView(studentType.getImage());
        int emptySpot = searchEmptySpotInEntrance();
        if (emptySpot == entrance.size()){
            entrance.add(new Pawn(student, type));
        }
        else{
            entrance.set(emptySpot, new Pawn(student, type));
        }
        student.setOnMouseClicked(new StudentListener(type, Location.ENTRANCE));
        student.toFront();
        int row = EntrancePosition.values()[emptySpot].getRow();
        int column = EntrancePosition.values()[emptySpot].getColumn();
        gridEntrance.add(student, column, row);
        GridPane.setHalignment(student, HPos.RIGHT);
    }

    private int searchEmptySpotInEntrance(){
        for(Pawn studentEmpty: entrance){
            if(studentEmpty == null) return entrance.indexOf(studentEmpty);
        }
        return entrance.size();
    }

    public void addProfessor(PawnType type){
        Professors professorType = Professors.typeConverter(type);
        ImageView professor = new ImageView(professorType.getImage());
        professors.add(new Pawn(professor, type));
        professor.setOnMouseClicked(new StudentListener(type, Location.DINING_ROOM));
        professor.toFront();
        gridDiningRoom.add(professor, 12, professorType.getTablePosition());
        GridPane.setHalignment(professor, HPos.CENTER);
    }

    private void fillTowers(int numberOfTowers){
        for(int i=0; i<numberOfTowers; i++){
            addTower();
        }
    }

    public void addTower(){
        ImageView tower = new ImageView(this.towerType.getImage());
        towers.add(tower);
        int row = TowerPosition.values()[towers.size() - 1].getRow();
        int column = TowerPosition.values()[towers.size() - 1].getColumn();
        gridTowers.add(tower, column, row);
        GridPane.setHalignment(tower, HPos.CENTER);
    }

    public void removeStudentFromDiningRoom(PawnType type){
        ImageView studentRemoved = tables.get(type).remove(tables.get(type).size()-1).getImageView();
        gridDiningRoom.getChildren().remove(studentRemoved);
    }

    public void removeStudentFromEntrance(PawnType type){
        Pawn studentRemoved = null;
        for(Pawn student : entrance){
            if (student== null) continue;
            if(student.getType() == type){
                studentRemoved = student;
                break;
            }
        }
        entrance.set(entrance.indexOf(studentRemoved), null);
        gridEntrance.getChildren().remove(studentRemoved.getImageView());
    }

    public void removeProfessor(PawnType type){
        Pawn professorRemoved = null;
        for(Pawn professor: professors){
            if(professor.getType() == type){
                professorRemoved = professor;
                break;
            }
        }
        professors.remove(professorRemoved);
        gridDiningRoom.getChildren().remove(professorRemoved.getImageView());
    }

    public void removeTower(){
        ImageView towerRemoved = towers.remove(towers.size()-1);
        gridTowers.getChildren().remove(towerRemoved);
    }
}
