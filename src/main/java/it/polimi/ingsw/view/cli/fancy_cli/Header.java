package it.polimi.ingsw.view.cli.fancy_cli;

/**
 * A widget used for displaying a title and/or a subtitle.
 * This is meant to be a standalone widget to put on top of others
 * and should not be placed in {@code Row} or {@code GridView}.
 */
public class Header extends Widget{

    /**
     * The title to display in this header
     */
    private Text title;
    /**
     * The subtitle to display in this header
     */
    private Text subtitle;

    /**
     * Creates a header with the provided title and subtitle.
     * @param title the title to display
     * @param subtitle the subtitle to display
     */
    public Header(Text title, Text subtitle){
        this.title = title;
        this.subtitle = subtitle;
        calculateSize();
        attachTo(title);
        attachTo(subtitle);
    }

    /**
     * Creates a header with the provided title.
     * @param title the title to display
     */
    public Header(Text title){
        this(title, null);
    }

    /**
     * Creates an empty header.
     */
    public Header(){
        this(null, null);
    }

    private void calculateSize(){
        int width = 0;
        int height = 0;
        if (title != null){
            width = title.getWidth();
            height = title.getHeight();
        }
        if (subtitle != null){
            width = Math.max(subtitle.getWidth(), width);
            height += subtitle.getHeight();
        }
        setWidth(width);
        setHeight(height);
    }

    private void attachTo(Text text){
        if (text != null)
            text.onSizeChange(this::calculateSize);
    }

    public void setTitle(Text title) {
        if (this.title != null)
            this.title.detachListener();
        this.title = title;
        attachTo(title);
    }

    public void setSubtitle(Text subtitle) {
        if (this.subtitle != null)
            this.subtitle.detachListener();
        this.subtitle = subtitle;
        attachTo(subtitle);
    }

    @Override
    protected void display() {
        if (title != null){
            showContent(title);
        }
        if (subtitle != null){
            showContent(subtitle);
        }
    }

    private void showContent(Text content){
        SizedBox sizedBox = new SizedBox(content, getWidth(), 0);
        sizedBox.setStartingPoint(getStartingPoint());
        sizedBox.show();
        System.out.print("\n");
    }
}
