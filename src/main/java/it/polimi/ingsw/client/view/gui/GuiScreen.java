package it.polimi.ingsw.client.view.gui;


public abstract class GuiScreen {

    /**
     * The GUI of the client
     */
    private final GUI gui;

    protected GuiScreen(GUI gui) {
        this.gui = gui;
    }

    public GUI getGui() {
        return gui;
    }

    // todo: adapt this for GUI
    /**
     * A method to show this screen on the command line
     */
    // abstract protected void show();

    /**
     * A method used to ask the user to enter some input, if necessary
     */
    // protected void askAction() {}

    /*final void run() {
        show();
        if (gui.getClientController().isInTurn())
            askAction();
    }*/

}
