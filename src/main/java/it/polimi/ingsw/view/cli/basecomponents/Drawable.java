package it.polimi.ingsw.view.cli.basecomponents;

/**
 * An interface representing the ability to be drawn on a cli
 */
interface Drawable {

    /**
     * Defines in which way this should be represented on the screen.
     * @return a String to show on screen representing this
     */
    String show();
}
