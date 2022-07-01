package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.view.gui.listeners.LocationListener;
import it.polimi.ingsw.client.view.gui.listeners.StudentListener;
import it.polimi.ingsw.client.view.gui.utils.image_getters.ProfessorImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.StudentImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.TowerImageType;
import it.polimi.ingsw.client.view.gui.utils.position_getters.EntrancePosition;
import it.polimi.ingsw.client.view.gui.utils.position_getters.TowerPosition;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import javafx.geometry.HPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.*;
/**
 * Class that represents a school board on the table of the game
 */
public class SchoolBoard {

    /**
     * True if this is the school board of the client
     */
    boolean isFirstPlayer;

    /**
     * Grid used tp place the students on the entrance of the school board
     */
    private final GridPane gridEntrance;

    /**
     * Grid used to place the students on the dining room of the school board
     */
    private final GridPane gridDiningRoom;

    /**
     * Grid used to place the towers on the tower hall
     */
    private final GridPane gridTowers;

    /**
     * Color of the tower used by the player
     */
    private final TowerImageType towerType;

    /**
     * Map to match to every color of student all the pawns placed on the table of that color
     */
    private final Map<PawnType, List<Pawn>> tables = new HashMap<>(4,1);

    /**
     * List of professors place on the school board
     */
    private final List<Pawn> professors = new ArrayList<>();

    /**
     * List of students place on the entrance
     */
    private final List<Pawn> entrance = new ArrayList<>();

    /**
     * List of towers place on the tower hall
     */
    private final List<ImageView> towers = new ArrayList<>();

    private LocationListener entranceListener;

    private LocationListener diningRoomListener;

    private final Set<StudentListener> entranceListeners = new HashSet<>();

    private final Set<StudentListener> diningRoomListeners = new HashSet<>();

    private final GUI gui;

    /**
     * This class allows to add and remove students and towers on the school board
     * @param isFirstPlayer true if this is the school board of the client
     * @param gridEntrance Grid used tp place the students on the entrance of the school board
     * @param gridDiningRoom Grid used to place the students on the dining room of the school board
     * @param gridTowers Grid used to place the towers on the tower hall
     * @param towerType Color of the tower used by the player
     * @param gui the considered gui
     */
    public SchoolBoard(GUI gui, boolean isFirstPlayer, GridPane gridEntrance, GridPane gridDiningRoom, GridPane gridTowers, TowerType towerType){
        this.gui = gui;
        this.isFirstPlayer = isFirstPlayer;
        this.towerType = TowerImageType.typeConverter(towerType);
        this.gridEntrance = gridEntrance;
        addListenerToLocation(this.gridEntrance, Location.ENTRANCE);
        this.gridDiningRoom = gridDiningRoom;
        addListenerToLocation(gridDiningRoom, Location.DINING_ROOM);
        this.gridTowers = gridTowers;
        for(PawnType type : PawnType.values()) {
            tables.put(type, new ArrayList<>());
        }
    }

    /**
     * Allows to add a student of the given color to the dining room
     * @param type color of the student added
     */
    public void addStudentToDiningRoom(PawnType type){
        if (tables.get(type).size() == 10) return; //If the dining room is full of student of the given color do nothing
        StudentImageType studentType = StudentImageType.typeConverter(type);
        ImageView studentView = new ImageView(studentType.getImage());
        Pawn student = new Pawn(studentView, type);
        tables.get(type).add(student);
        addListenerToPawn(student, type, Location.DINING_ROOM);
        gridDiningRoom.add(studentView, tables.get(type).size(), studentType.getTablePosition());
    }

    /**
     * Allows to add a student of the given color to the entrance
     * @param type color of the student added
     */
    public void addStudentToEntrance(PawnType type){
        StudentImageType studentType = StudentImageType.typeConverter(type);
        ImageView studentView = new ImageView(studentType.getImage());
        int emptySpot = searchEmptySpotInEntrance();
        if (emptySpot == EntrancePosition.values().length) return;//If the entrance is full do nothing
        Pawn student = new Pawn(studentView, type);
        if (emptySpot == entrance.size()){
            entrance.add(student);
        }
        else{
            entrance.set(emptySpot, student);
        }
        addListenerToPawn(student, type, Location.ENTRANCE);
        int row = EntrancePosition.values()[emptySpot].getRow();
        int column = EntrancePosition.values()[emptySpot].getColumn();
        gridEntrance.add(studentView, column, row);
        GridPane.setHalignment(studentView, HPos.RIGHT);
    }

    /**
     * Searches for an empty spot in the entrance where to place a student
     * @return the index of {@code entrance} where there is an empty spot
     */
    private int searchEmptySpotInEntrance(){
        for(Pawn studentEmpty: entrance){
            if(studentEmpty == null) return entrance.indexOf(studentEmpty);
        }
        return entrance.size();
    }

    /**
     * Allows to add a professor of the given color to the dining room.
     * @param type color of the professor added
     */
    public void addProfessor(PawnType type){
        if(professors.size() == ProfessorImageType.values().length) return; //If the dining room is full of professors do nothing
        ProfessorImageType professorType = ProfessorImageType.typeConverter(type);
        ImageView professor = new ImageView(professorType.getImage());
        professors.add(new Pawn(professor, type));
        gridDiningRoom.add(professor, 12, professorType.getTablePosition());
        GridPane.setHalignment(professor, HPos.CENTER);
    }

    /**
     * Fills the tower hall with towers
     * @param numberOfTowers total possible number of towers on the school board. Depends on the number of players
     */
    private void fillTowers(int numberOfTowers){
        for(int i=0; i<numberOfTowers; i++){
            addTower();
        }
    }

    /**
     * Allows to add a tower to the dining room.
     */
    public void addTower(){
        if (towers.size() == TowerPosition.values().length) return; //If the hall is full do nothing
        ImageView tower = new ImageView(this.towerType.getImage());
        towers.add(tower);
        int row = TowerPosition.values()[towers.size() - 1].getRow();
        int column = TowerPosition.values()[towers.size() - 1].getColumn();
        gridTowers.add(tower, column, row);
        GridPane.setHalignment(tower, HPos.CENTER);
    }

    /**
     * Allows to remove a student from the dining room.
     * @param type color of the student removed
     */
    public void removeStudentFromDiningRoom(PawnType type){
        if (tables.get(type).size() == 0) return;//If the dining room is empty do nothing
        Pawn studentRemoved = tables.get(type).remove(tables.get(type).size()-1);
        ImageView studentRemovedView = studentRemoved.getImageView();
        gridDiningRoom.getChildren().remove(studentRemovedView);
        removeListenerToPawn(studentRemoved, Location.DINING_ROOM);
    }

    /**
     * Allows to remove a student from the entrance.
     * @param type color of the student removed
     */
    public void removeStudentFromEntrance(PawnType type){
        Pawn studentRemoved = null;
        for(Pawn student : entrance){
            if (student== null) continue;
            if(student.getType() == type){
                studentRemoved = student;
                break;
            }
        }
        if (studentRemoved == null) return;//If the student  given is not present, do nothing
        removeListenerToPawn(studentRemoved, Location.ENTRANCE);
        entrance.set(entrance.indexOf(studentRemoved), null);
        gridEntrance.getChildren().remove(studentRemoved.getImageView());
    }

    /**
     * Allows to remove a professor from the dining room
     * @param type color of the professor removed
     */
    public void removeProfessor(PawnType type){
        Pawn professorRemoved = null;
        for(Pawn professor: professors){
            if(professor.getType() == type){
                professorRemoved = professor;
                break;
            }
        }
        if(professorRemoved == null) return;//If the professor is not present, do nothing
        professors.remove(professorRemoved);
        gridDiningRoom.getChildren().remove(professorRemoved.getImageView());
    }

    /**
     * Allows to remove a tower from the school board
     */
    public void removeTower(){
        if (towers.size() == 0) return; //If there are no towers do nothing
        ImageView towerRemoved = towers.remove(towers.size()-1);
        gridTowers.getChildren().remove(towerRemoved);
    }


    //METHODS TO HANDLE LISTENERS

    /**
     * method to add a listener to a pawn
     * @param pawn {@code Pawn} of the student where to add the listener
     * @param type {@code PawnType} of the pawn
     * @param location {@code Location} where the pawn is placed
     */
    private void addListenerToPawn(Pawn pawn, PawnType type, Location location){
        if(isFirstPlayer){
            StudentListener listener = new StudentListener(gui, type, location);
            pawn.getImageView().setOnMouseClicked(listener);
            if(location.equals(Location.ENTRANCE)){
                entranceListeners.add(listener);
            }else{
                diningRoomListeners.add(listener);
            }
            pawn.attachListener(listener);
        }
    }

    /**
     * method to remove a listener to a pawn
     * @param pawn {@code Pawn} of the student where to remove the listener
     * @param location {@code Location} where the pawn is placed
     */
    private void removeListenerToPawn(Pawn pawn, Location location){
        if(isFirstPlayer){
            StudentListener listener = pawn.getListener();
            if(location.equals(Location.ENTRANCE)){
                entranceListeners.remove(listener);
            }else{
                diningRoomListeners.remove(listener);
            }
        }
    }

    /**
     * Method to add a listener to a location  on the school board
     * @param locationView view where to add the listener
     * @param locationType type of location where to add the listener
     */
    private void addListenerToLocation(GridPane locationView,Location locationType){
        if(isFirstPlayer){
            LocationListener listener = new LocationListener(gui, locationType) ;
            locationView.setOnMouseClicked(listener);
            if(locationType.equals(Location.ENTRANCE)){
                entranceListener = listener;
            }else {
                diningRoomListener = listener;
            }
        }
    }

    /**
     * This method will allow to enable listeners on the considered location
     * @param location the considered location
     */
    public void enableLocationListener(Location location){
        if(location.equals(Location.ENTRANCE)){
            entranceListener.enableListener();
        }else {
            diningRoomListener.enableListener();
        }
    }

    /**
     * This method will allow to disable listeners on the considered location
     * @param location the considered location
     */
    public void disableLocationListener(Location location){
        if(location.equals(Location.ENTRANCE)){
            entranceListener.disableListener();
        }else{
            diningRoomListener.disableListener();
        }
    }

    /**
     * This method will allow to enable listeners on the students in the considered location
     * @param location the considered location
     */
    public void enableStudentListeners(Location location){
        if(location.equals(Location.ENTRANCE)){
            for(StudentListener listener: entranceListeners){
                listener.enableListener();
            }
        }else{
            for(StudentListener listener: diningRoomListeners){
                listener.enableListener();
            }
        }
    }

    /**
     * This method will allow to disable listeners on the students in the considered location
     * @param location the considered location
     */
    public void disableStudentListeners(Location location){
        if(location.equals(Location.ENTRANCE)){
            for(StudentListener listener: entranceListeners){
                listener.disableListener();
            }
        }else{
            for(StudentListener listener: diningRoomListeners){
                listener.disableListener();
            }
        }
    }

    //METHODS TO UPDATE PAWNS ON THE SCHOOLBOARD

    /**
     * Method to update the professors in the school board one at a time
     * @param newProfessors new professors on the school board
     */
    public void updateProfessors(Collection<PawnType> newProfessors){
        Collection<Pawn> professorsCopy = new ArrayList<>(professors);
       for(Pawn professorOnTable: professorsCopy) {
           if(!newProfessors.contains(professorOnTable.getType())) removeProfessor(professorOnTable.getType());
       }
       for(PawnType newProfessor: newProfessors){
           if(!((professorsCopy.stream().map(Pawn::getType).toList()).contains(newProfessor))) addProfessor(newProfessor);
       }
    }

    /**
     * Method to update the students in the dining room one at a time
     * @param newStudents new students in the dining room
     */
    public void updateDiningRoom(StudentList newStudents){
        for(PawnType student: PawnType.values()){
            int differenceOfStudents = tables.get(student).size() - newStudents.getNumOf(student);
            if(differenceOfStudents > 0){
                for(int numberOfStudents = 0; numberOfStudents < differenceOfStudents; numberOfStudents ++){
                    removeStudentFromDiningRoom(student);
                }
            } else if (differenceOfStudents < 0) {
                for(int numberOfStudents = 0; numberOfStudents < Math.abs(differenceOfStudents); numberOfStudents ++){
                    addStudentToDiningRoom(student);
                }
            }
        }
    }

    /**
     * Method to update the students in the entrance one at a time
     * @param newStudents new students in the entrance
     */
    public void updateEntrance(StudentList newStudents){
        StudentList studentsCopy = newStudents.clone();
        for (Pawn studentOnEntrance : entrance) {
            if (studentOnEntrance != null) {
                if (studentsCopy.getNumOf(studentOnEntrance.getType()) == 0) {
                    removeStudentFromEntrance(studentOnEntrance.getType());
                } else {
                    try {
                        studentsCopy.changeNumOf(studentOnEntrance.getType(), -1);
                    } catch (NotEnoughStudentException e) {
                        throw new RuntimeException(e); //Not possible theoretically
                    }
                }
            }
        }
        List<PawnType> entranceType = entrance.stream().filter(Objects::nonNull).map(Pawn::getType).collect(ArrayList::new,
                ArrayList::add,
                ArrayList::addAll);
        for(PawnType student: PawnType.values()){
            for(int i = 0; i < newStudents.getNumOf(student); i++) {
                if((entranceType.contains(student))){
                    entranceType.remove(student);
                }
                else {
                    addStudentToEntrance(student);
                }
            }
        }
    }

    //METHODS TO UPDATE TOWERS ON THE SCHOOLBOARD

    /**
     * Method to update the towers on the school board
     * @param numberOfTowers new number of towers on the school board
     */
    public void updateTowers(int numberOfTowers){
        int differenceNumberOfTowers = towers.size() - numberOfTowers;
        for(int i=0; i < Math.abs(differenceNumberOfTowers); i++){
            if (differenceNumberOfTowers > 0) removeTower();
            else addTower();
        }

    }

}
