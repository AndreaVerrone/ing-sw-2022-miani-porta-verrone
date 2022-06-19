package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.client.reduced_model.ReducedPlayerLoginInfo;
import it.polimi.ingsw.client.Reducible;
import it.polimi.ingsw.server.controller.matchmaking.observers.TowerSelectedObserver;
import it.polimi.ingsw.server.controller.matchmaking.observers.WizardSelectedObserver;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to store information about the player of a game before the creation of the game.
 */
public class PlayerLoginInfo implements Reducible<ReducedPlayerLoginInfo> {

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
    public PlayerLoginInfo(String nickname) {
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

    // MANAGEMENT OF OBSERVERS FOR SELECTING THE WIZARD
    /**
     * List of the observer on the wizard selected by the player
     */
    private final List<WizardSelectedObserver> wizardSelectedObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on the wizard selection.
     * @param observer the observer to be added
     */
    void addWizardSelectedObserver(WizardSelectedObserver observer){
        wizardSelectedObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on the wizard selection.
     * @param observer the observer to be removed
     */
    void removeWizardSelectedObserver(WizardSelectedObserver observer){
        wizardSelectedObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that the wizard has been selected.
     */
    private void notifyWizardSelectedObserver(){
        for(WizardSelectedObserver observer : wizardSelectedObservers)
            observer.wizardSelectedObserverUpdate(this.nickname, this.wizard);
    }

    // MANAGEMENT OF OBSERVERS FOR SELECTING TOWER
    /**
     * List of the observer on the tower selected by the player
     */
    private final List<TowerSelectedObserver> towerSelectedObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on the tower selection.
     * @param observer the observer to be added
     */
    void addTowerSelectedObserver(TowerSelectedObserver observer){
        towerSelectedObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on the tower selection.
     * @param observer the observer to be removed
     */
    void removeTowerSelectedObserver(TowerSelectedObserver observer){
        towerSelectedObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that the tower has been selected.
     */
    private void notifyTowerSelectedObserver(){
        for(TowerSelectedObserver observer : towerSelectedObservers)
            observer.towerSelectedObserverUpdate(this.nickname, this.towerType);
    }

    @Override
    public ReducedPlayerLoginInfo reduce() {
        return new ReducedPlayerLoginInfo(nickname, towerType, wizard);
    }
}
