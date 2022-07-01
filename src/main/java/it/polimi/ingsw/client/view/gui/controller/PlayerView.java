package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.Optional;

/**
 * This class is used to store information regarding a player
 */
public class PlayerView {

    /**
     * The nickname.
     */
    private String nickname = "";

    /**
     * The wizard.
     */
    private Optional<Wizard> wizard;

    /**
     * The tower.
     */
    private Optional<TowerType> towerType;

    /**
     * The constructor of the class.
     * It will create the class taking in input the nickname of the player
     * @param nickname the nickname of the player
     */
    public PlayerView(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public Optional<Wizard> getWizard() {
        return wizard;
    }

    public Optional<TowerType> getTowerType() {
        return towerType;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setWizard(Wizard wizard) {
        this.wizard = Optional.ofNullable(wizard);
    }

    public void setTowerType(TowerType towerType) {
        this.towerType = Optional.ofNullable(towerType);
    }
}
