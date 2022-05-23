package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.ConsoleCli;
import org.fusesource.jansi.AnsiConsole;

import java.util.List;

public class IdleScreen extends CliScreen {

    private final List<String> progress = List.of(".  ", ".. ", "...");

    @Override
    protected void show() {
        AnsiConsole.systemInstall();
        System.out.print(Translator.getWaitMessage());
        int index = 0;
        while (!shouldStop()) {
            System.out.print(progress.get(index));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }

            ConsoleCli.moveCursorLeft(3);
            index = (index + 1) % progress.size();

        }
        AnsiConsole.systemUninstall();
    }
}
