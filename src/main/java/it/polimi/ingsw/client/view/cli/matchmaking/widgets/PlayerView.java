package it.polimi.ingsw.client.view.cli.matchmaking.widgets;

import it.polimi.ingsw.client.reduced_model.ReducedPlayerLoginInfo;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.List;

/**
 * A widget to display the infos of a player in the matchmaking
 */
public class PlayerView extends StatefulWidget {

    /**
     * A widget to display the nickname of the player
     */
    private final Widget nickname;
    /**
     * A widget to display the tower of the player
     */
    private Widget tower;
    /**
     * A widget to display the wizard of the player
     */
    private Widget wizard;

    private final Widget spacer = new SizedBox(2f, 0f);

    /**
     * Creates a widget used to display the info of a player in the matchmaking
     * @param info the info of the player
     */
    public PlayerView(ReducedPlayerLoginInfo info) {
        nickname = new Text(info.nickname());
        tower = createTower(info.towerType());
        wizard = createWizard(info.wizard());

        create();
    }

    void setTower(TowerType tower){
        setState(() -> this.tower = createTower(tower));
    }

    void setWizard(Wizard wizard){
        setState(() -> this.wizard = createWizard(wizard));
    }

    private Widget createTower(TowerType towerType){
        return towerType == null ? spacer : new Text("Tower: "+towerType);
    }

    private Widget createWizard(Wizard wizard){
        return wizard == null ? spacer : new Text("Wizard: "+wizard);
    }

    @Override
    protected Widget build() {
        return new Row(List.of(
                nickname,
                spacer,
                tower,
                spacer,
                wizard
        ));
    }
}
