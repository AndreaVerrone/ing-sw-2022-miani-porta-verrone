package it.polimi.ingsw.client.view.cli;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * An interface for the screen that composes the CLI
 */
public abstract class CliScreen {

    /**
     * The CLI of the client
     */
    private final CLI cli;

    /**
     * If this screen need to stop
     */
    private final AtomicBoolean stop = new AtomicBoolean();

    protected CliScreen(CLI cli) {
        this.cli = cli;
    }

    public CLI getCli() {
        return cli;
    }

    /**
     * @return if this view should stop its execution
     */
    protected boolean shouldStop(){
        return stop.get();
    }

    /**
     * Tells this view to stop the execution as soon as possible
     */
    protected void setStop(){
        stop.set(true);
    }

    /**
     * A method to show this screen on the command line
     */
    abstract protected void show();

    /**
     * A method used to ask the user to enter some input, if necessary
     */
    protected void askAction() {}

    final void run() {
        show();
        if (cli.getClientController().isInTurn())
            askAction();
    }
}
