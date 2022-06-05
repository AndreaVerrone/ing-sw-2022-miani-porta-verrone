package it.polimi.ingsw.client.view.cli.matchmaking;

import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.controller.PlayerLoginInfo;

import java.util.List;

/**
 * A widget to display the infos of a player in the matchmaking
 */
public class PlayerView extends StatelessWidget {

    /**
     * The infos of the player
     */
    private final PlayerLoginInfo info;

    /**
     * Creates a widget used to display the info of a player in the matchmaking
     * @param info the info of the player
     */
    public PlayerView(PlayerLoginInfo info) {
        this.info = info;

        create();
    }

    @Override
    protected Widget build() {
        Widget spacer = new SizedBox(2f, 0f);
        Widget nickname = new Text(info.getNickname());
        Widget tower = info.getTowerType() == null ? spacer : new Text("Tower: "+info.getTowerType());
        Widget wizard = info.getWizard() == null ? spacer : new Text("Wizard: "+info.getWizard());
        return new Row(List.of(
                nickname,
                spacer,
                tower,
                spacer,
                wizard
        ));
    }
}
