package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;


/**
 * This class is used to store information regarding a player
 */
public class PlayerView {

    /**
     * The nickname.
     */
    private String nickname;

    /**
     * The wizard.
     */
    private Wizard wizard;

    /**
     * The tower.
     */
    private TowerType towerType;

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

    public Wizard getWizard() {
        return wizard;
    }

    public TowerType getTowerType() {
        return towerType;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }

    public void setTowerType(TowerType towerType) {
        this.towerType = towerType;
    }
}
