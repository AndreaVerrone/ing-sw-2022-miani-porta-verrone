package it.polimi.ingsw.client.view.cli.fancy_cli.utils;

/**
 * Class used to do common stuff on the console used to display the cli
 */
public class ConsoleCli {

    /**
     * Resets alle the applied colors and style to the text in the console.
     */
    static public void resetStyle(){
        System.out.print("\u001b[0m");
    }

    /**
     * Deletes all the content of the console and moves the cursor to the upper left corner
     */
    static public void deleteConsole(){
        System.out.print("\u001b[2J");
        moveToColumn(0);
        moveCursorUp(500);
    }

    /**
     * Converts the width given into character number so that
     * one point of the provided width have the same size as one line height.
     * @param width the width in general points
     * @return the number of characters required
     */
    static public int convertFromGeneralWidthToCharNumber(float width){
        return Math.round(width*2.8f);
    }

    /**
     * Moves the cursor to a specific column in the console.
     * @param column the column to move to
     */
    static public void moveToColumn(int column){
        System.out.print("\u001b["+column+"G");
    }

    static private void moveCursor(int value, String direction){
        if (value<1)
            return;
        System.out.print("\u001b["+value+direction);
    }

    /**
     * Moves the cursor n lines up. If the cursor is already at the edge
     * of the screen or the number of lines is less than 1, this has no effect.
     * @param lines the number of lines to move up
     */
    static public void moveCursorUp(int lines){
        moveCursor(lines, "A");
    }

    /**
     * Moves the cursor n lines down. If the cursor is already at the edge
     * of the screen or the number of lines is less than 1, this has no effect.
     * @param lines the number of lines to move down
     */
    static public void moveCursorDown(int lines){
        moveCursor(lines, "B");
    }

    /**
     * Moves the cursor n characters right. If the cursor is already at the edge
     * of the screen or the number of characters is less than 1, this has no effect.
     * @param characters the number of characters to move right
     */
    static public void moveCursorRight(int characters){
        moveCursor(characters, "C");
    }

    /**
     * Moves the cursor n characters left. If the cursor is already at the edge
     * of the screen or the number of characters is less than 1, this has no effect.
     * @param characters the number of characters to move left
     */
    static public void moveCursorLeft(int characters){
        moveCursor(characters, "D");
    }
}
