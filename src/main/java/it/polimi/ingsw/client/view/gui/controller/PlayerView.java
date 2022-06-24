package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.Optional;

public class PlayerView {

    private String nickname = "";

    private Optional<Wizard> wizard;

    private Optional<TowerType> towerType;

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
