package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.TowerType;
import it.polimi.ingsw.model.player.Wizard;

/**
 * This class is used to store information about the player of a game before the creation of the game.
 */
public class PlayerLoginInfo {

    /**
     * the {@code nickname} identifies the player.
     */
    private final String nickname;

    /**
     * the {@code wizard} is the wizard that the player chooses to impersonate during the game.
     */
    private Wizard wizard;

    /**
     * the {@code towerType} is the color of the tower that the player is using.
     */
    private TowerType towerType;

    /**
     * The constructor of the class takes in input the nickname of the player
     * @param nickname of the player
     */
    PlayerLoginInfo(String nickname){
        this.nickname=nickname;
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

    /**
     * This method will set the wizard that the player chooses to impersonate during the game.
     * <p>
     * The wizard to set is passed as a parameter in input to this method.
     * @param wizard the wizard that the player chooses to impersonate
     */
    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }

    /**
     * This method will set the color of the tower the player will play with.
     * <p>
     * The color to set is passed as a parameter in input to this method.
     * @param towerType the type of the tower (i.e., the color) to associate to the player
     */
    public void setTowerType(TowerType towerType) {
        this.towerType = towerType;
    }
}
