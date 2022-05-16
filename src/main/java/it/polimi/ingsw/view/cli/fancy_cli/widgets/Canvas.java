package it.polimi.ingsw.view.cli.fancy_cli.widgets;

import it.polimi.ingsw.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.view.cli.fancy_cli.utils.ConsoleCli;
import it.polimi.ingsw.view.cli.fancy_cli.utils.TextStyle;

import java.util.List;

/**
 * A class used to draw content on the screen via terminal
 */
public class Canvas implements Drawable {

    /**
     * The title of this canvas
     */
    private String title;
    /**
     * The subtitle of this canvas
     */
    private String subtitle;
    /**
     * The content of this canvas
     */
    private Widget content;
    /**
     * {@code true} if the canvas should erase all the text on the screen before drawing new content
     */
    private final boolean eraseOnUpdate;

    /**
     * Creates a canvas used to draw content on the terminal. It can have a title, subtitle and a content.
     * It also deletes all the text in the terminal before drawing new content if {@code eraseOnUpdate} is {@code true}.
     * @param eraseOnUpdate if this canvas should delete all the text in the terminal before drawing new content
     */
    public Canvas(boolean eraseOnUpdate) {
        this.eraseOnUpdate = eraseOnUpdate;
    }

    /**
     * Creates a canvas used to draw content on the terminal. It can have a title, subtitle and a content.
     * It will draw the content below other text in the terminal.
     * This is equivalent of calling {@code  Canvas(true)}.
     */
    public Canvas(){
        this(false);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setContent(Widget content) {
        this.content = content;
    }

    @Override
    public void show() {
        if (eraseOnUpdate)
            ConsoleCli.deleteConsole();
        System.out.print("\n");
        Text title = null;
        if (this.title != null)
            title = new Text(this.title, Color.YELLOW, Color.DEFAULT)
                .addTextStyle(TextStyle.BOLD).addTextStyle(TextStyle.UNDERLINE);
        Text subtitle = null;
        if (this.subtitle != null)
            subtitle = new Text(this.subtitle, Color.DEFAULT, Color.DEFAULT).addTextStyle(TextStyle.ITALIC);
        Widget header = new SizedBox(new Header(title, subtitle), content.getWidth(), 0);
        SizedBox sizedBox = new SizedBox(1f,1f);
        Column column = new Column(List.of(
                header,
                sizedBox,
                content
        ));
        column.show();
        System.out.print("\n\n");
    }
}
