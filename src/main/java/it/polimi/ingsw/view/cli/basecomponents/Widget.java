package it.polimi.ingsw.view.cli.basecomponents;

/**
 * A class representing a generic component of the cli
 */
public abstract class Widget implements Drawable {



    @Override
    public String toString() {
        return show();
    }
}
