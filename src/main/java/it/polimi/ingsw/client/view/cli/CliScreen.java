package it.polimi.ingsw.client.view.cli;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * An interface for the screen that composes the CLI
 */
public abstract class CliScreen {

    private final AtomicBoolean stop = new AtomicBoolean();

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
}
