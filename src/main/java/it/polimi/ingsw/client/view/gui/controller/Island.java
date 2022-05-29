package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.listeners.LocationListern;
import it.polimi.ingsw.client.view.gui.utils.image_getters.MotherNatureImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.TowerImageType;
import it.polimi.ingsw.client.view.gui.utils.position_getters.IslandPosition;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.model.utils.TowerType;
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
     * This class allows to handle the image of an island on the view of the table, allowing to add a tower, mother nature and students
     * @param gridIsland Grid of the view used to place islands
     * @param islandView {@code ImageView} of the island handled
     * @param islandID ID of the island handled
     */
    public Island(GridPane gridIsland, ImageView islandView, int islandID){
        this.gridIsland = gridIsland;
        this.islandView = islandView;
        this.islandID = islandID;
        islandView.setOnMouseClicked(new LocationListern(Location.ISLAND));
    }

    /**
     * Allows to place a tower of the given type on the island
     * @param towerType color of the tower placed
     */
    public void addTower(TowerType towerType){
        ImageView towerView = new ImageView(TowerImageType.typeConverter(towerType).getImage());
        int column= IslandPosition.values()[islandID].getColumn();
        int row=IslandPosition.values()[islandID].getRow();
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
}
