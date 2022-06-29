package it.polimi.ingsw.server.observers.game;

import it.polimi.ingsw.server.observers.ChangeCurrentPlayerObserver;
import it.polimi.ingsw.server.observers.ChangeCurrentStateObserver;
import it.polimi.ingsw.server.observers.game.card_observers.CoinOnCardObserver;
import it.polimi.ingsw.server.observers.game.card_observers.StudentsOnCardObserver;
import it.polimi.ingsw.server.observers.game.player.ChangeCoinNumberObserver;
import it.polimi.ingsw.server.observers.game.player.PlayerObserver;
import it.polimi.ingsw.server.observers.game.table.ChangeCoinNumberInBagObserver;
import it.polimi.ingsw.server.observers.game.table.TableObserver;
import it.polimi.ingsw.server.observers.game.table.island.BanOnIslandObserver;

public interface GameObserver extends
        ChangeCurrentStateObserver, ChangeCurrentPlayerObserver, GameCreatedObserver,
        PlayerObserver, TableObserver, EndOfGameObserver, BanOnIslandObserver,
        ChangeCoinNumberInBagObserver, ChangeCoinNumberObserver,
        CoinOnCardObserver, StudentsOnCardObserver {

    /**
     * Notifies that the game entered the last round
     */
    void notifyLastRound();
}
