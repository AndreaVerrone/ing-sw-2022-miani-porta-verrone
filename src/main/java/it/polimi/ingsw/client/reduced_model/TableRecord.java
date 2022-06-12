package it.polimi.ingsw.client.reduced_model;

import it.polimi.ingsw.server.model.player.Assistant;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * This is a class representing all the elements that are on the table during a game
 * @param assistantsList the list of the assistant cards of the player that are in the deck
 * @param assistantsUsed the map between the last player and its last assistant card used
 * @param clouds the clouds on the table
 * @param reducedIslands the islands on the table
 */
public record TableRecord(
        List<Assistant> assistantsList,
        Map<String, Assistant> assistantsUsed,
        List<ReducedCloud> clouds,

        List<ReducedSchoolBoard> schoolBoardList,
        Collection<ReducedIsland> reducedIslands) implements Serializable {
}
