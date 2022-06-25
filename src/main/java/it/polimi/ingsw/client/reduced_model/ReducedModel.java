package it.polimi.ingsw.client.reduced_model;

import it.polimi.ingsw.server.model.player.Assistant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This is a class representing all the elements that are on the table during a game
 */
public class ReducedModel implements Serializable {

    /**
     * the list of the assistant cards of the player that are in the deck
     */
    private final Collection<Assistant> assistantsList;
    /**
     * A list of assistants used in the planning phase
     */
    private final Collection<Assistant> assistantsUsed;
    /**
     * the clouds on the table
     */
    private final Collection<ReducedCloud> clouds;
    /**
     * a list of all the information about the players
     */
    private final Collection<ReducedPlayer> playersList;
    /**
     * the islands on the table
     */
    private final Collection<ReducedIsland> reducedIslands;
    /**
     * the position of mother nature
     */
    private final int motherNaturePosition;
    /**
     * if this class represent an expert game
     */
    private final boolean isExpertGame;
    /**
     * the character cards in this game
     */
    private final Collection<ReducedCharacter> characterCards;

    private ReducedModel(
            Collection<Assistant> assistantsList,
            Collection<Assistant> assistantsUsed,
            Collection<ReducedCloud> clouds,
            Collection<ReducedPlayer> playersList,
            Collection<ReducedIsland> reducedIslands,
            int motherNaturePosition,
            Collection<ReducedCharacter> characterCards,
            boolean isExpertGame) {
        this.assistantsList = new ArrayList<>(assistantsList);
        this.assistantsUsed = new ArrayList<>(assistantsUsed);
        this.clouds = new ArrayList<>(clouds);
        this.playersList = new ArrayList<>(playersList);
        this.reducedIslands = new ArrayList<>(reducedIslands);
        this.motherNaturePosition = motherNaturePosition;
        this.isExpertGame = isExpertGame;
        this.characterCards = new ArrayList<>(characterCards);
    }

    /**
     * Creates a reduced representation of all the model for an expert game
     *
     * @param assistantsList       the list of the assistant cards of the player that are in the deck
     * @param assistantsUsed       the map between the last player and its last assistant card used
     * @param clouds               the clouds on the table
     * @param playersList          a list of all the information about the players
     * @param reducedIslands       the islands on the table
     * @param motherNaturePosition the position of mother nature
     * @param characterCards       the character cards in this game
     */
    public ReducedModel(
            Collection<Assistant> assistantsList,
            Collection<Assistant> assistantsUsed,
            Collection<ReducedCloud> clouds,
            Collection<ReducedPlayer> playersList,
            Collection<ReducedIsland> reducedIslands,
            int motherNaturePosition,
            Collection<ReducedCharacter> characterCards) {
        this(assistantsList, assistantsUsed, clouds, playersList,
                reducedIslands, motherNaturePosition, characterCards, true);
    }

    /**
     * Creates a reduced representation of all the model for a standard game
     *
     * @param assistantsList       the list of the assistant cards of the player that are in the deck
     * @param assistantsUsed       the map between the last player and its last assistant card used
     * @param clouds               the clouds on the table
     * @param playersList          a list of all the information about the players
     * @param reducedIslands       the islands on the table
     * @param motherNaturePosition the position of mother nature
     */
    public ReducedModel(Collection<Assistant> assistantsList,
                        Collection<Assistant> assistantsUsed,
                        Collection<ReducedCloud> clouds,
                        Collection<ReducedPlayer> playersList,
                        Collection<ReducedIsland> reducedIslands,
                        int motherNaturePosition) {
        this(assistantsList, assistantsUsed, clouds, playersList,
                reducedIslands, motherNaturePosition, new ArrayList<>(), false);
    }

    public Collection<Assistant> getAssistantsList() {
        return assistantsList;
    }

    public Collection<Assistant> getAssistantsUsed() {
        return assistantsUsed;
    }

    public Collection<ReducedCloud> getClouds() {
        return clouds;
    }

    public Collection<ReducedPlayer> getPlayersList() {
        return playersList;
    }

    public Collection<ReducedIsland> getReducedIslands() {
        return reducedIslands;
    }

    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }

    public boolean isExpertGame() {
        return isExpertGame;
    }

    public Collection<ReducedCharacter> getCharacterCards() {
        return characterCards;
    }
}
