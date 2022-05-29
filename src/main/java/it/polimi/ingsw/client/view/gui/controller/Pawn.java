package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.server.model.utils.PawnType;
import javafx.scene.image.ImageView;

/**
 * Class that represents a pawn on the table of the game
 */
public class Pawn {

    /**
     * {@code ImageView} of the pawn handled
     */
    private final ImageView imageView;

    /**
     * Color of the pawn handled
     */
    private final PawnType type;

    /**
     * This class allows to get the type and the view of a pawn on the table
     * @param imageView {@code ImageView} of the pawn handled
     * @param type Color of the pawn handled
     */
    public Pawn(ImageView imageView, PawnType type){
        this.imageView = imageView;
        this.type = type;
    }

    public PawnType getType() {
        return type;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
