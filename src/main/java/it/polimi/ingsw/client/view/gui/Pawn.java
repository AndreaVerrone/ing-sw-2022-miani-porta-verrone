package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.server.model.utils.PawnType;
import javafx.scene.image.ImageView;

public class Pawn {

    private final ImageView imageView;

    private final PawnType type;

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
