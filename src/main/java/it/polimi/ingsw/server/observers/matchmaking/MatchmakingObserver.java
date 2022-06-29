package it.polimi.ingsw.server.observers.matchmaking;

import it.polimi.ingsw.server.observers.ChangeCurrentPlayerObserver;
import it.polimi.ingsw.server.observers.ChangeCurrentStateObserver;

public interface MatchmakingObserver extends
        PlayerLoginInfoObserver, NumberOfPlayersObserver, PlayersChangedObserver, ChangeCurrentPlayerObserver,
        ChangeCurrentStateObserver {
}
