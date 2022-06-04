package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.game.expert.card_observers.CoinOnCardObserver;
import it.polimi.ingsw.server.controller.game.expert.card_observers.StudentsOnCardObserver;
import it.polimi.ingsw.server.controller.matchmaking.observers.NumberOfPlayersObserver;
import it.polimi.ingsw.server.controller.matchmaking.observers.PlayersChangedObserver;
import it.polimi.ingsw.server.controller.matchmaking.observers.TowerSelectedObserver;
import it.polimi.ingsw.server.controller.matchmaking.observers.WizardSelectedObserver;
import it.polimi.ingsw.server.observers.*;

public interface ObserversCommonInterface extends NumberOfPlayersObserver, PlayersChangedObserver, TowerSelectedObserver, WizardSelectedObserver, ChangeCurrentStateObserver, BanOnIslandObserver, ChangeAssistantDeckObserver, ChangeCoinNumberInBagObserver, ChangeCoinNumberObserver, ChangeCurrentPlayerObserver, ChangeTowerNumberObserver, ConquerIslandObserver, EmptyStudentBagObserver, IslandNumberObserver, IslandUnificationObserver, LastAssistantUsedObserver, MotherNaturePositionObserver, ProfessorObserver, StudentsInDiningRoomObserver, StudentsOnCloudObserver, StudentsOnEntranceObserver, StudentsOnIslandObserver, TowerOnIslandObserver, CoinOnCardObserver, StudentsOnCardObserver {
}
