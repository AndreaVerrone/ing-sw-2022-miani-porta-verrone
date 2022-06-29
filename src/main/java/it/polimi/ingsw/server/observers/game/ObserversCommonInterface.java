package it.polimi.ingsw.server.observers.game;

import it.polimi.ingsw.server.observers.ChangeCurrentPlayerObserver;
import it.polimi.ingsw.server.observers.ChangeCurrentStateObserver;
import it.polimi.ingsw.server.observers.game.card_observers.CoinOnCardObserver;
import it.polimi.ingsw.server.observers.game.card_observers.StudentsOnCardObserver;
import it.polimi.ingsw.server.observers.game.player.*;
import it.polimi.ingsw.server.observers.game.table.*;
import it.polimi.ingsw.server.observers.game.table.island.BanOnIslandObserver;
import it.polimi.ingsw.server.observers.game.table.island.IslandUnificationObserver;
import it.polimi.ingsw.server.observers.game.table.island.StudentsOnIslandObserver;
import it.polimi.ingsw.server.observers.game.table.island.TowerOnIslandObserver;
import it.polimi.ingsw.server.observers.matchmaking.NumberOfPlayersObserver;
import it.polimi.ingsw.server.observers.matchmaking.PlayersChangedObserver;
import it.polimi.ingsw.server.observers.matchmaking.TowerSelectedObserver;
import it.polimi.ingsw.server.observers.matchmaking.WizardSelectedObserver;

public interface ObserversCommonInterface extends NumberOfPlayersObserver, PlayersChangedObserver, TowerSelectedObserver, WizardSelectedObserver, ChangeCurrentStateObserver, BanOnIslandObserver, ChangeAssistantDeckObserver, ChangeCoinNumberInBagObserver, ChangeCoinNumberObserver, ChangeCurrentPlayerObserver, ChangeTowerNumberObserver, EmptyStudentBagObserver, IslandNumberObserver, IslandUnificationObserver, LastAssistantUsedObserver, MotherNaturePositionObserver, ProfessorObserver, StudentsInDiningRoomObserver, StudentsOnCloudObserver, StudentsOnEntranceObserver, StudentsOnIslandObserver, TowerOnIslandObserver, CoinOnCardObserver, StudentsOnCardObserver, EndOfGameObserver, GameCreatedObserver {

}