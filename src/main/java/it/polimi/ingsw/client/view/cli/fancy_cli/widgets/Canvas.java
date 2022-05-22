package it.polimi.ingsw.client.view.cli.fancy_cli.widgets;

import it.polimi.ingsw.client.view.cli.fancy_cli.utils.*;
import org.fusesource.jansi.AnsiConsole;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
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
     * The color of the title
     */
    private Color titleColor = Color.DEFAULT;
    /**
     * {@code true} if the canvas should erase all the text on the screen before drawing new content
     */
    private final boolean eraseOnUpdate;

    /**
     * {@code true} if the canvas should redraw itself when it's content changes
     */
    private final boolean autoUpdate;

    /**
     * Creates a canvas used to draw content on the terminal. It can have a title, subtitle and a content.
     * It also deletes all the text in the terminal before drawing new content if {@code eraseOnUpdate} is {@code true},
     * and redraws itself upon changing if {@code autoUpdate} is {@code true}.
     * @param eraseOnUpdate if this canvas should delete all the text in the terminal before drawing new content
     * @param autoUpdate if this canvas should redraw itself upon changing
     */
    public Canvas(boolean eraseOnUpdate, boolean autoUpdate) {
        this.eraseOnUpdate = eraseOnUpdate;
        this.autoUpdate = autoUpdate;
    }

    /**
     * Creates a canvas used to draw content on the terminal. It can have a title, subtitle and a content.
     * If the content changes, this canvas will redraw itself.
     * It also deletes all the text in the terminal before drawing new content if {@code eraseOnUpdate} is {@code true}.
     * @param eraseOnUpdate if this canvas should delete all the text in the terminal before drawing new content
     */
    public Canvas(boolean eraseOnUpdate) {
        this(eraseOnUpdate, true);
    }

    /**
     * Creates a canvas used to draw content on the terminal. It can have a title, subtitle and a content.
     * If the content changes, this canvas will redraw itself.
     * It also deletes all the text in the terminal before drawing new content
     * This is equivalent of calling {@code  Canvas(true)}.
     */
    public Canvas(){
        this(true, true);
    }

    protected boolean shouldUpdate(){
        return autoUpdate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleColor(Color color){
        titleColor = color;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setContent(Widget content) {
        if (this.content != null)
            this.content.setCanvas(null);
        this.content = content;
        this.content.setCanvas(this);
    }

    @Override
    public void show() {
        int width = 0;
        try(Terminal terminal = TerminalBuilder.terminal()){
            width = terminal.getWidth();
        }catch (IOException e){

        }
        AnsiConsole.systemInstall();


        if (eraseOnUpdate)
            ConsoleCli.deleteConsole();
        System.out.print("\n");
        Text title = null;
        if (this.title != null)
            title = new Text(this.title, titleColor, Color.DEFAULT).addTextStyle(TextStyle.BOLD);
        Text subtitle = null;
        if (this.subtitle != null)
            subtitle = new Text(this.subtitle, Color.DEFAULT, Color.DEFAULT).addTextStyle(TextStyle.ITALIC);
        Widget header = new Header(title, subtitle);
        Widget actualContent = header;
        if (content != null) {
            Widget centeredHeader = new SizedBox(header, content.getWidth(), 0);
            SizedBox sizedBox = new SizedBox(1f, 1f);
            actualContent = new Column(List.of(
                    header,
                    sizedBox,
                    new SizedBox(content, centeredHeader.getWidth(), 0)
            ));
        }
        Widget finalContent = new SizedBox(actualContent, width, 0);
        finalContent.show();
        System.out.print("\n\n");
        AnsiConsole.systemUninstall();
    }
}
