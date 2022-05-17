package it.polimi.ingsw.view.cli.fancy_cli.widgets;

import it.polimi.ingsw.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.view.cli.fancy_cli.utils.ConsoleCli;
import it.polimi.ingsw.view.cli.fancy_cli.utils.TextStyle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Widget used to show formatted text on the screen
 */
public class Text extends Widget {

    /**
     * The text to display
     */
    private Collection<String> text;

    /**
     * The maximum number of characters per line
     */
    private final int maxCharsPerLine;

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
     * Creates a Text widget with the provided content that occupies at most the width specified
     * and uses the foreground and background colors chosen
     * @param text the content to display
     * @param foregroundColor the foreground color to use
     * @param backgroundColor the background color to use
     * @param maxWidth the maximum width that this text should occupy, in characters number
     */
    public Text(String text, Color foregroundColor, Color backgroundColor, int maxWidth){
        this.maxCharsPerLine = maxWidth;
        this.text = wrapText(text);
        this.foregroundColor = foregroundColor.foreground;
        this.backgroundColor = backgroundColor.background;
        calculateSize();
    }

    /**
     * Creates a Text widget with the provided content that occupies at most the width specified
     * and uses the foreground and background colors chosen
     * @param text the content to display
     * @param foregroundColor the foreground color to use
     * @param backgroundColor the background color to use
     * @param maxWidth the maximum width that this text should occupy, in points number
     */
    public Text(String text, Color foregroundColor, Color backgroundColor, float maxWidth){
        this(text, foregroundColor, backgroundColor, ConsoleCli.convertFromGeneralWidthToCharNumber(maxWidth));
    }

    /**
     * Creates a Text widget with the provided content using the default colors of the terminal.
     * This Text will be as wide as needed
     * @param text the content to display
     */
    public Text(String text) {
        this(text, Color.DEFAULT, Color.DEFAULT, -1);
    }

    /**
     * Creates a Text widget with the provided content that occupies at most the width specified
     * and uses the default colors of the terminal.
     * @param text the content to display
     * @param maxWidth the maximum width that this text should occupy
     */
    public Text(String text, float maxWidth){
        this(text, Color.DEFAULT, Color.DEFAULT, maxWidth);
    }

    /**
     * Creates a Text widget with the provided content using the foreground and background colors chosen.
     * This Text will be as wide as needed.
     * @param text the content to display
     * @param foregroundColor the foreground color to use
     * @param backgroundColor the background color to use
     */
    public Text(String text, Color foregroundColor, Color backgroundColor){
        this(text, foregroundColor, backgroundColor, -1);
    }

    private Collection<String> wrapText(String text){
        String[] lines = text.split("\n");
        if (maxCharsPerLine<1)
            return List.of(lines);
        Collection<String> wrappedLines = new ArrayList<>();
        for (String line:lines){
            String[] words = line.split(" ");
            StringBuilder wrapLine = new StringBuilder();
            for (String word : words){
                if (wrapLine.isEmpty()){
                    wrapLine.append(word);
                    continue;
                }
                if (wrapLine.length()+1+word.length() <= maxCharsPerLine){
                    wrapLine.append(" ").append(word);
                    continue;
                }
                wrappedLines.add(wrapLine.toString());
                wrapLine.delete(0, wrapLine.length());
                wrapLine.append(word);
            }
            wrappedLines.add(wrapLine.toString());
        }
        return wrappedLines;
    }

    private void calculateSize(){
        int width = this.text.stream().max(Comparator.comparingInt(String::length)).map(String::length).orElse(0);
        setWidth(width);
        setHeight(this.text.size());
    }
    public void setText(String text){
        this.text = wrapText(text);
        calculateSize();
        update();
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
    protected void display() {

        for (String line : text) {
            System.out.print(backgroundColor + foregroundColor + style + line);
            ConsoleCli.resetStyle();
            System.out.print("\n");
            ConsoleCli.moveToColumn(getStartingPoint());
        }
        ConsoleCli.moveCursorUp(1);
    }
}
