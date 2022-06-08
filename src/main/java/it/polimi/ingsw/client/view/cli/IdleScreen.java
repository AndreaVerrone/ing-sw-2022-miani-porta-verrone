package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.ConsoleCli;
import org.fusesource.jansi.AnsiConsole;

import java.util.List;

/**
 * A screen for displaying a generic waiting message
 */
public class IdleScreen extends CliScreen {

    private final List<String> progress = List.of(".  ", ".. ", "...");

    public IdleScreen(CLI cli) {
        super(cli);
    }

    @Override
    protected void show() {
        AnsiConsole.systemInstall();
        String message = Translator.getWaitMessage();
        int index = 0;
        while (!shouldStop()) {
            System.out.print(message);
            System.out.print(progress.get(index));
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }

            ConsoleCli.moveToColumn(0);
            index = (index + 1) % progress.size();

        }
        for (int i=0; i<message.length()+3;i++)
            System.out.print("\010");
        AnsiConsole.systemUninstall();
    }
}
