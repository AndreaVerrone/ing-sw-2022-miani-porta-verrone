package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.client.reduced_model.ReducedPlayerLoginInfo;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;
import it.polimi.ingsw.server.observers.matchmaking.PlayerLoginInfoObserver;

import java.util.HashSet;
import java.util.Set;

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
     * List of the observers on this player
     */
    private final Set<PlayerLoginInfoObserver> playerObservers = new HashSet<>();

    /**
     * The constructor of the class takes in input the nickname of the player
     * @param nickname of the player
     */
    public PlayerLoginInfo(String nickname) {
        this.nickname = nickname;
    }

    /**
     * This method allows to add the observer, passed as a parameter, on this player.
     * @param observer the observer to be added
     */
    public void addObserver(PlayerLoginInfoObserver observer) {
        playerObservers.add(observer);
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
        notifyWizardSelectedObserver();
    }

    /**
     * This method will set the color of the tower the player will play with.
     * <p>
     * The color to set is passed as a parameter in input to this method.
     * @param towerType the type of the tower (i.e., the color) to associate to the player
     */
    public void setTowerType(TowerType towerType) {
        this.towerType = towerType;
        notifyTowerSelectedObserver();
    }

    // MANAGEMENT OF OBSERVERS

    /**
     * This method notify all the attached observers that the wizard has been selected.
     */
    private void notifyWizardSelectedObserver(){
        for(PlayerLoginInfoObserver observer : playerObservers)
            observer.wizardSelectedObserverUpdate(this.nickname, this.wizard);
    }

    /**
     * This method notify all the attached observers that the tower has been selected.
     */
    private void notifyTowerSelectedObserver(){
        for(PlayerLoginInfoObserver observer : playerObservers)
            observer.towerSelectedObserverUpdate(this.nickname, this.towerType);
    }

    /**
     * Creates a reduced version fo this class that can be sent over the network.
     * @return the reduced version of this
     */
    public ReducedPlayerLoginInfo reduce() {
        return new ReducedPlayerLoginInfo(nickname, towerType, wizard);
    }
}
