package it.polimi.ingsw.server.observers.matchmaking;

import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;
import it.polimi.ingsw.server.observers.ChangeCurrentPlayerObserver;
import it.polimi.ingsw.server.observers.ChangeCurrentStateObserver;

import java.util.Collection;

public interface MatchmakingObserver extends
        PlayerLoginInfoObserver, NumberOfPlayersObserver, PlayersChangedObserver, ChangeCurrentPlayerObserver,
        ChangeCurrentStateObserver {

    void requestChoosePlayerParameter(Collection<TowerType> towersAvailable, Collection<Wizard> wizardsAvailable);
}
