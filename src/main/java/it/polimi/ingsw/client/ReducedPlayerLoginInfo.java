package it.polimi.ingsw.client;

import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.io.Serializable;

/**
 * A reduced version of {@link it.polimi.ingsw.server.controller.PlayerLoginInfo}
 * that can be sent over the network
 * @param nickname the nickname of the player
 * @param towerType the tower of the player
 * @param wizard the wizard of the player
 */
public record ReducedPlayerLoginInfo(String nickname, TowerType towerType, Wizard wizard) implements Serializable {
}
