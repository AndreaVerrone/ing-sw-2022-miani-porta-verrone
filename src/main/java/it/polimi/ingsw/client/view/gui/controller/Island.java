package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.listeners.LocationListern;
import it.polimi.ingsw.client.view.gui.utils.image_getters.IslandBanImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.MotherNatureImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.TowerImageType;
import it.polimi.ingsw.client.view.gui.utils.position_getters.IslandPosition;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


/**
 * Class that represents an island on the table of the game
 */
public class Island {

    /**
     * Grid of the view used to place islands
     */
    private final GridPane gridIsland;

    /**
     * {@code ImageView} of the island handled
     */
    private final ImageView islandView;

    /**
     * ID of the island
     */
    private final int islandID;

    /**
     * ImageView of the tower eventually located on the island
     */
    private ImageView towerView;

    /**
     * ImageView of mother nature eventually located on the island
     */
    private ImageView motherNatureView;

    /**
     * All the students on the island
     */
    private final StudentOnIslandHandler studentOnIslandHandler;


    /**
     * {@code ImageView} of the ban on the island
     */
    private ImageView banView;

    /**
     * Movement of the island on the x-axis
     */
    private double XTranslation = 0;

    /**
     * Movement of the island on the y-axis
     */
    private double YTranslation = 0;

    /**
     * Column of the grid where the island is initially placed
     */
    private final int column;

    /**
     * Row of the grid where the island is initially placed
     */
    private final int row;

    /**
     * Island unified to this one moving clockwise
     */
    private Island islandUnitedClockwise = null;

    /**
     * Island unified to this one moving counterclockwise
     */
    private Island islandUnitedCounterClockWise = null;

    /**
     * X coordinate of the position available next to the island moving clockwise
     */
    private double clockWiseXCoordinate;

    /**
     * X coordinate of the position available next to the island moving counterclockwise
     */
    private double counterClockWiseXCoordinate;

    /**
     * Y coordinate of the position available next to the island moving clockwise
     */
    private double clockWiseYCoordinate;

    /**
     * Y coordinate of the position available next to the island moving counterclockwise
     */
    private double counterClockWiseYCoordinate;

    /**
     * Allows to move the island using an animation
     */
    private TranslateTransition translateAnimationIsland;

    /**
     * Allows to move the tower on the island using an animation
     */
    private TranslateTransition translateAnimationMotherNature;

    /**
     * Allows to move mother nature on the island using an animation
     */
    private TranslateTransition translateAnimationTower;
    /**
     * This class allows to handle the image of an island on the view of the table, allowing to add a tower, mother nature and students
     * @param gridIsland Grid of the view used to place islands
     * @param islandView {@code ImageView} of the island handled
     * @param islandID ID of the island handled
     */
    public Island(GridPane gridIsland, ImageView islandView, int islandID){
        this.gridIsland = gridIsland;
        this.islandView = islandView;
        this.islandID = islandID;

         column = IslandPosition.values()[islandID].getColumn();
         row = IslandPosition.values()[islandID].getRow();


        studentOnIslandHandler = new StudentOnIslandHandler(gridIsland, column, row);
        islandView.setOnMouseClicked(new LocationListern(Location.ISLAND));
        islandView.setOnMouseEntered(studentOnIslandHandler);
        islandView.setOnMouseExited(studentOnIslandHandler);

        setBan();

    }

    /**
     * Method to show the number of bans on the island
     */
    public void makeBansVisible(){
        studentOnIslandHandler.setExpertMode();
    }

    public int getIslandID() {
        return islandID;
    }

    /**
     * Get the position of the island on x-axis of the table
     * @return position of the island on x-axis of the table
     */
    public double getXPosition(){
        return (gridIsland.getCellBounds(column, row).getCenterX() + XTranslation);
    }

    /**
     * Get the position of the island on y-axis of the table
     * @return position of the island on y-axis of the table
     */
    public double getYPosition(){
        return (gridIsland.getCellBounds(column, row).getCenterY() + YTranslation);
    }

    public double getClockWiseXCoordinate() {
        return clockWiseXCoordinate;
    }

    public double getClockWiseYCoordinate() {
        return clockWiseYCoordinate;
    }

    public double getCounterClockWiseXCoordinate() {
        return counterClockWiseXCoordinate;
    }

    public double getCounterClockWiseYCoordinate() {
        return counterClockWiseYCoordinate;
    }

    public Island getIslandUnitedClockwise() {
        return islandUnitedClockwise;
    }

    public Island getIslandUnitedCounterClockWise() {
        return islandUnitedCounterClockWise;
    }

    /**
     * Method to save the island unified to his one moving clockwise
     * @param islandUnitedClockwise island unified moving clockwise
     */
    public void setIslandUnitedClockwise(Island islandUnitedClockwise) {
        this.islandUnitedClockwise = islandUnitedClockwise;
    }

    /**
     * Method to save the island unified to his one moving counterclockwise
     * @param islandUnitedCounterClockWise island unified moving counterclockwise
     */
    public void setIslandUnitedCounterClockWise(Island islandUnitedCounterClockWise) {
        this.islandUnitedCounterClockWise = islandUnitedCounterClockWise;
    }

    /**
     * Method to calculate the coordinates of the positions available next to the island, based on its position on the table
     */
    public void calculateCoordinates(){
        int SPACE_BETWEEN_ISLANDS_LINEAR = 100;
        int SPACE_BETWEEN_ISLANDS_DIAGONAL = 70;
        if(getYPosition() < 170) {
            if (getXPosition() >= 280 && getXPosition() <= 840) {
                clockWiseXCoordinate = SPACE_BETWEEN_ISLANDS_LINEAR;
                clockWiseYCoordinate = 0;
                counterClockWiseXCoordinate = -SPACE_BETWEEN_ISLANDS_LINEAR;
                counterClockWiseYCoordinate = 0;
            } else if (getXPosition() >= 840 && getXPosition() <= 910) {
                clockWiseXCoordinate = SPACE_BETWEEN_ISLANDS_DIAGONAL;
                clockWiseYCoordinate = SPACE_BETWEEN_ISLANDS_DIAGONAL;
                counterClockWiseXCoordinate = -SPACE_BETWEEN_ISLANDS_LINEAR;
                counterClockWiseYCoordinate = 0;
            } else if (getXPosition() >= 910 && getXPosition() <= 1020) {
                clockWiseXCoordinate = SPACE_BETWEEN_ISLANDS_DIAGONAL;
                clockWiseYCoordinate = SPACE_BETWEEN_ISLANDS_DIAGONAL;
                counterClockWiseXCoordinate = -SPACE_BETWEEN_ISLANDS_DIAGONAL;
                counterClockWiseYCoordinate = -SPACE_BETWEEN_ISLANDS_DIAGONAL;
            } else if (getXPosition() >= 80 && getXPosition() <= 200) {
                clockWiseXCoordinate = SPACE_BETWEEN_ISLANDS_DIAGONAL;
                clockWiseYCoordinate = -SPACE_BETWEEN_ISLANDS_DIAGONAL;
                counterClockWiseXCoordinate = -SPACE_BETWEEN_ISLANDS_DIAGONAL;
                counterClockWiseYCoordinate = SPACE_BETWEEN_ISLANDS_DIAGONAL;
            } else if (getXPosition() >= 200 && getXPosition() <= 280) {
                clockWiseXCoordinate = SPACE_BETWEEN_ISLANDS_LINEAR;
                clockWiseYCoordinate = 0;
                counterClockWiseXCoordinate = -SPACE_BETWEEN_ISLANDS_DIAGONAL;
                counterClockWiseYCoordinate = SPACE_BETWEEN_ISLANDS_DIAGONAL;
            }
        } else if (getYPosition() >= 170 && getYPosition() <= 360) {
            if(getXPosition() >= 900){
                if(getYPosition() < 200){
                    clockWiseXCoordinate = 0;
                    clockWiseYCoordinate = SPACE_BETWEEN_ISLANDS_LINEAR;
                    counterClockWiseXCoordinate = -SPACE_BETWEEN_ISLANDS_DIAGONAL;
                    counterClockWiseYCoordinate = -SPACE_BETWEEN_ISLANDS_DIAGONAL;
                } else if (getYPosition() > 320) {
                    clockWiseXCoordinate = -SPACE_BETWEEN_ISLANDS_DIAGONAL;
                    clockWiseYCoordinate = SPACE_BETWEEN_ISLANDS_DIAGONAL;
                    counterClockWiseXCoordinate = 0;
                    counterClockWiseYCoordinate = SPACE_BETWEEN_ISLANDS_LINEAR;
                }else {
                    clockWiseXCoordinate = 0;
                    clockWiseYCoordinate = SPACE_BETWEEN_ISLANDS_LINEAR;
                    counterClockWiseXCoordinate = 0;
                    counterClockWiseYCoordinate = -SPACE_BETWEEN_ISLANDS_LINEAR;
                }
            } else if (getXPosition() <= 120) {
                if(getYPosition() < 200){
                    clockWiseXCoordinate = SPACE_BETWEEN_ISLANDS_DIAGONAL;
                    clockWiseYCoordinate = -SPACE_BETWEEN_ISLANDS_DIAGONAL;
                    counterClockWiseXCoordinate = 0;
                    counterClockWiseYCoordinate = SPACE_BETWEEN_ISLANDS_LINEAR;
                } else if (getYPosition() > 330) {
                    clockWiseXCoordinate = 0;
                    clockWiseYCoordinate = -SPACE_BETWEEN_ISLANDS_LINEAR;
                    counterClockWiseXCoordinate = SPACE_BETWEEN_ISLANDS_DIAGONAL;
                    counterClockWiseYCoordinate = SPACE_BETWEEN_ISLANDS_DIAGONAL;
                }else {
                    clockWiseXCoordinate = 0;
                    clockWiseYCoordinate = -SPACE_BETWEEN_ISLANDS_LINEAR;
                    counterClockWiseXCoordinate = 0;
                    counterClockWiseYCoordinate = SPACE_BETWEEN_ISLANDS_LINEAR;
                }
            }
        } else if (getYPosition() > 360) {
            if (getXPosition() >= 280 && getXPosition() <= 840) {
                clockWiseXCoordinate = -SPACE_BETWEEN_ISLANDS_LINEAR;
                clockWiseYCoordinate = 0;
                counterClockWiseXCoordinate = SPACE_BETWEEN_ISLANDS_LINEAR;
                counterClockWiseYCoordinate = 0;
            } else if (getXPosition() >= 840 && getXPosition() <= 910) {
                clockWiseXCoordinate = -SPACE_BETWEEN_ISLANDS_LINEAR;
                clockWiseYCoordinate = 0;
                counterClockWiseXCoordinate = SPACE_BETWEEN_ISLANDS_DIAGONAL;
                counterClockWiseYCoordinate = -SPACE_BETWEEN_ISLANDS_DIAGONAL;
            } else if (getXPosition() >= 910 && getXPosition() <= 1020) {
                clockWiseXCoordinate = -SPACE_BETWEEN_ISLANDS_DIAGONAL;
                clockWiseYCoordinate = SPACE_BETWEEN_ISLANDS_DIAGONAL;
                counterClockWiseXCoordinate = SPACE_BETWEEN_ISLANDS_DIAGONAL;
                counterClockWiseYCoordinate = -SPACE_BETWEEN_ISLANDS_DIAGONAL;
            } else if (getXPosition() >= 80 && getXPosition() <= 200) {
                clockWiseXCoordinate = -SPACE_BETWEEN_ISLANDS_DIAGONAL;
                clockWiseYCoordinate = -SPACE_BETWEEN_ISLANDS_DIAGONAL;
                counterClockWiseXCoordinate = SPACE_BETWEEN_ISLANDS_DIAGONAL;
                counterClockWiseYCoordinate = SPACE_BETWEEN_ISLANDS_DIAGONAL;
            } else if (getXPosition() >= 200 && getXPosition() <= 280) {
                clockWiseXCoordinate = -SPACE_BETWEEN_ISLANDS_DIAGONAL;
                clockWiseYCoordinate = -SPACE_BETWEEN_ISLANDS_DIAGONAL;
                counterClockWiseXCoordinate = SPACE_BETWEEN_ISLANDS_LINEAR;
                counterClockWiseYCoordinate = 0;
            }
        }
    }

    /**
     * Set di ban image on the island
     */
    private void setBan(){
        int column= IslandPosition.values()[islandID].getColumn();
        int row=IslandPosition.values()[islandID].getRow();
        banView = new ImageView(IslandBanImageType.BAN.getImage());
        gridIsland.add(banView, column, row);
        banView.toFront();
        banView.setVisible(false);
    }

    /**
     * Allows to place a tower of the given type on the island
     * @param towerType color of the tower placed
     */
    public void addTower(TowerType towerType){
        if(towerView != null) removeTower();
        ImageView towerView = new ImageView(TowerImageType.typeConverter(towerType).getImage());
        int column= IslandPosition.values()[islandID].getColumn();
        int row=IslandPosition.values()[islandID].getRow();
        towerView.setMouseTransparent(true);
        gridIsland.add(towerView, column, row);
        GridPane.setValignment(towerView, VPos.TOP);
        GridPane.setHalignment(towerView, HPos.CENTER);
        this.towerView = towerView;
        translateTower();
        if(motherNatureView != null){
            removeMotherNature();
            addMotherNature();
        }

    }

    /**
     * Allows to move mother nature to this island
     */
    public void addMotherNature(){
        ImageView motherNatureView = new ImageView(MotherNatureImageType.MOTHER_NATURE.getImage());
        int column=IslandPosition.values()[islandID].getColumn();
        int row=IslandPosition.values()[islandID].getRow();
        motherNatureView.setMouseTransparent(true);
        gridIsland.add(motherNatureView, column, row);
        GridPane.setValignment(motherNatureView, VPos.CENTER);
        GridPane.setHalignment(motherNatureView, HPos.CENTER);
        this.motherNatureView = motherNatureView;
        translateMotherNature();
    }

    /**
     * Allows to remove the tower on the island if present
     */
    public void removeTower(){
        if(towerView != null) {
            gridIsland.getChildren().remove(towerView);
            towerView = null;
        }
    }

    /**
     * Allows to remove mother nature on the island if present
     */
    public void removeMotherNature(){
        if(motherNatureView != null) {
            gridIsland.getChildren().remove(motherNatureView);
            motherNatureView = null;
        }
    }

    /**
     * Add student to the island
     * @param color color of the student added
     */
    public void addStudent(PawnType color){
        try {
            studentOnIslandHandler.getStudents().changeNumOf(color, 1);
        } catch (NotEnoughStudentException e) {
            e.printStackTrace(); //Not Possible
        }
    }

    /**
     * Remove a student from the island
     * @param color of the student removed
     */
    public void removeStudent(PawnType color){
        try {
            studentOnIslandHandler.getStudents().changeNumOf(color, -1);
        } catch (NotEnoughStudentException e) {
            //Simply do nothing
        }
    }

    /**
     * Change number of bans on the island
     * @param newNumberOfBans new number of bans on the island
     */
    public void changeNumberOfBans(int newNumberOfBans){
        studentOnIslandHandler.changeNumberOfBans(newNumberOfBans);
        if (newNumberOfBans == 0) banView.setVisible(false);
        if (newNumberOfBans > 0) banView.setVisible(true);
    }

    /**
     * Method to update the students on the island
     * @param students new student son the island
     */
    public void updateStudentsOnIsland(StudentList students){
        for(PawnType student: PawnType.values()){
            removeStudent(student);
        }
        for(PawnType student: PawnType.values()){
            for(int i = 0; i < students.getNumOf(student); i++) {
                addStudent(student);
            }
        }
    }

    /**
     * Method to move the island of a fixed position
     * @param x movement on the x-axis
     * @param y movement on the y-axis
     */
    public void translateIsland(double x, double y){
        XTranslation += x;
        YTranslation += y;

        islandView.setTranslateX(XTranslation);
        islandView.setTranslateY(YTranslation);

        /*
        translateAnimationIsland = new TranslateTransition(Duration.millis(1000), islandView);
        translateAnimationIsland.setByX(XTranslation);
        translateAnimationIsland.setByY(YTranslation);
        translateAnimationIsland.play(); *///ANIMATION NOT WORKING
        //TODO: FIX ANIMATION

        translateMotherNature();

        translateTower();

        if(banView != null){
            banView.setVisible(false);
        }

        studentOnIslandHandler.showStudentsView(false);

    }

    /**
     * Method to move mother nature if present on the island
     */
    private void translateMotherNature(){
        if(motherNatureView != null) {
            motherNatureView.setTranslateX(XTranslation);
            motherNatureView.setTranslateY(YTranslation);
        }
    }

    /**
     * Method to move the tower if present on the island
     */
    private void translateTower(){
        if(towerView != null) {
            towerView.setTranslateX(XTranslation);
            towerView.setTranslateY(YTranslation);
        }
    }

}
