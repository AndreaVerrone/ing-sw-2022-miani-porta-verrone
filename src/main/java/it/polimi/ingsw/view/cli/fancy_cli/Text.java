package it.polimi.ingsw.view.cli.fancy_cli;

import it.polimi.ingsw.view.cli.fancy_cli.base_components.Widget;
import it.polimi.ingsw.view.cli.fancy_cli.utils.Color;
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
    private Color foregroundColor;

    /**
     * The background color of the text
     */
    private Color backgroundColor;

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
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
        setWidth(text.length());
        setHeight(1);
    }

    public Text setForegroundColor(Color foregroundColor){
        this.foregroundColor = foregroundColor;
        return this;
    }

    public Text setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
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
        if (foregroundColor != Color.DEFAULT)
            System.out.print(foregroundColor.foreground);
        if (backgroundColor != Color.DEFAULT)
            System.out.print(backgroundColor.background);
        System.out.print(style+text);
        System.out.print(Color.DEFAULT);
    }
}
