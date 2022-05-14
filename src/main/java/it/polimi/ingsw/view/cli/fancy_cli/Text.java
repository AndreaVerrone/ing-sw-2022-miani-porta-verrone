package it.polimi.ingsw.view.cli.fancy_cli;

import it.polimi.ingsw.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.view.cli.fancy_cli.utils.ConsoleCli;
import it.polimi.ingsw.view.cli.fancy_cli.utils.TextStyle;

/**
 * Widget used to show formatted text on the screen
 */
public class Text extends Widget {

    /**
     * The text to display
     */
    private final String text;

    /**
     * The foreground color of the text
     */
    private String foregroundColor;

    /**
     * The background color of the text
     */
    private String backgroundColor;

    /**
     * All the styles to apply to the text.
     */
    private String style = "";

    /**
     * Creates a Text widget with the provided content using the default colors of the terminal.
     * @param text the content to display
     */
    public Text(String text) {
        this(text, Color.DEFAULT, Color.DEFAULT);
    }

    /**
     * Creates a Text widget with the provided content using the foreground and background colors chosen
     * @param text the content to display
     * @param foregroundColor the foreground color to use
     * @param backgroundColor the background color to use
     */
    public Text(String text, Color foregroundColor, Color backgroundColor){
        this.text = text;
        this.foregroundColor = foregroundColor.foreground;
        this.backgroundColor = backgroundColor.background;
        setWidth(text.length());
        setHeight(1);
    }

    public Text setForegroundColor(Color foregroundColor){
        this.foregroundColor = foregroundColor.foreground;
        return this;
    }

    public Text setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor.background;
        return this;
    }

    public Text addTextStyle(TextStyle textStyle){
        style = style + textStyle;
        return this;
    }

    public void removeAllTextStyles(){
        style = "";
    }

    @Override
    public void show() {
        ConsoleCli.moveToColumn(getStartingPoint());
        System.out.print(backgroundColor+foregroundColor+style+text);
        ConsoleCli.resetStyle();
    }
}
