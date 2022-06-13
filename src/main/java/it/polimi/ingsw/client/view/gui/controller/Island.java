package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.listeners.LocationListern;
import it.polimi.ingsw.client.view.gui.utils.image_getters.IslandBanImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.MotherNatureImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.StudentImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.TowerImageType;
import it.polimi.ingsw.client.view.gui.utils.position_getters.IslandPosition;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

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
     * This class allows to handle the image of an island on the view of the table, allowing to add a tower, mother nature and students
     * @param gridIsland Grid of the view used to place islands
     * @param islandView {@code ImageView} of the island handled
     * @param islandID ID of the island handled
     */
    public Island(GridPane gridIsland, ImageView islandView, int islandID){
        this.gridIsland = gridIsland;
        this.islandView = islandView;
        this.islandID = islandID;

        int column = IslandPosition.values()[islandID].getColumn();
        int row = IslandPosition.values()[islandID].getRow();

        studentOnIslandHandler = new StudentOnIslandHandler(gridIsland, column, row);
        islandView.setOnMouseClicked(new LocationListern(Location.ISLAND));
        islandView.setOnMouseEntered(studentOnIslandHandler);
        islandView.setOnMouseExited(studentOnIslandHandler);

        setBan();
        studentOnIslandHandler.setExpertMode();
        if(islandID == 3) {
            changeNumberOfBans(4);
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
        ImageView towerView = new ImageView(TowerImageType.typeConverter(towerType).getImage());
        int column= IslandPosition.values()[islandID].getColumn();
        int row=IslandPosition.values()[islandID].getRow();
        towerView.setMouseTransparent(true);
        gridIsland.add(towerView, column, row);
        GridPane.setValignment(towerView, VPos.TOP);
        GridPane.setHalignment(towerView, HPos.CENTER);
        this.towerView = towerView;
        if(motherNatureView != null){
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
        if(newNumberOfBans == 0) banView.setVisible(false);
        if(newNumberOfBans > 0) banView.setVisible(true);
    }
}
