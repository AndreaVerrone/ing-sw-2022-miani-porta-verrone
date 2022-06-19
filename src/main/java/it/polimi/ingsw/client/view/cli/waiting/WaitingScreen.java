package it.polimi.ingsw.client.view.cli.waiting;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.CliScreen;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.ConsoleCli;
import org.fusesource.jansi.AnsiConsole;

import java.util.List;

/**
 * A screen to display a generic waiting message on the cli
 */
public class WaitingScreen extends CliScreen {

    private final List<String> progress = List.of("...", "·..", ".·.", "..·");
    private final String message;

    WaitingScreen(CLI cli, String message) {
        super(cli);
        this.message = message;
    }

    @Override
    protected void show() {
        AnsiConsole.systemInstall();
        int index = 0;
        while (!shouldStop()) {
            System.out.print(message);
            System.out.print(progress.get(index));
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}

            ConsoleCli.moveToColumn(0);
            index = (index + 1) % progress.size();

        }
        for (int i=0; i<message.length()+3;i++)
            System.out.print("\010");
        AnsiConsole.systemUninstall();
    }
}
