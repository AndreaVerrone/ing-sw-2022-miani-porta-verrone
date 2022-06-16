package it.polimi.ingsw.client.view.cli.matchmaking.widgets;

import it.polimi.ingsw.client.ReducedPlayerLoginInfo;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.*;

/**
 * A widget used to display all the necessary infos of the matchmaking phase
 */
public class MatchmakingView extends StatefulWidget {

    /**
     * A map of all the current players in the lobby
     */
    private final Map<String, PlayerView> players = new HashMap<>();

    /**
     * The nickname of the player that need to take decisions
     */
    private String nicknameSelected;
    /**
     * The header of the widget containing information about
     * the number of players and the difficulty
     */
    private final Widget header;
    /**
     * A widget containing the current number of players
     */
    private final Text actualPlayers = new Text("0");

    /**
     * A widget used to display the id of the game
     */
    private final Widget gameID;
    /**
     * The number of players requested in this game
     */
    private final int numPlayers;

    /**
     * Creates a new widget used to show the infos of the matchmaking
     * @param playersInfo all the players in the lobby
     * @param numPlayers the maximum number of players
     * @param expert {@code true} if the expert rules are selected, {@code false} otherwise
     */
    public MatchmakingView(Collection<ReducedPlayerLoginInfo> playersInfo, int numPlayers, boolean expert, String gameID){
        setContent(playersInfo);
        this.numPlayers = numPlayers;
        this.gameID = new Text(Translator.getLabelGameID()+gameID);
        header = new Row(List.of(
                new Text(Translator.getNumOfPlayers()),
                actualPlayers,
                new Text("/"+numPlayers),
                new SizedBox(2f, 0f),
                new Text(Translator.getDisplayDifficulty(expert))
        ));
        create();
    }

    private void setContent(Collection<ReducedPlayerLoginInfo> playersInfo){
        players.clear();
        for (ReducedPlayerLoginInfo info : playersInfo){
            players.put(info.nickname(), new PlayerView(info));
        }
        String numPlayers = String.valueOf(playersInfo.size());
        actualPlayers.setText(numPlayers);
    }

    /**
     * Changes the player that need to take a decision
     * @param nickname the nickname of the player
     */
    public void setSelected(String nickname){
        setState(() -> nicknameSelected = nickname);
    }

    /**
     * Update this widget showing the infos passed as a parameter
     * @param playerLoginInfos the new player list of this lobby
     * @return {@code true} if the lobby is full, {@code false} otherwise
     */
    public boolean update(Collection<ReducedPlayerLoginInfo> playerLoginInfos){
        setState(() -> setContent(playerLoginInfos));
        return playerLoginInfos.size() == numPlayers;
    }

    /**
     * Changes the tower of the specified player, updating this widget
     * @param nickname the nickname of the player
     * @param tower the tower to assign
     */
    public void modify(String nickname, TowerType tower){
        players.get(nickname).setTower(tower);
    }

    /**
     * Changes the wizard of the specified player, updating this widget
     * @param nickname the nickname of the player
     * @param wizard the wizard to assign
     */
    public void modify(String nickname, Wizard wizard){
        players.get(nickname).setWizard(wizard);
    }


    @Override
    protected Widget build() {
        Collection<Widget> widgets = new ArrayList<>();
        widgets.add(gameID);
        widgets.add(header);
        widgets.add(new SizedBox(0,2));
        for (String nickname : players.keySet()){
            Widget widget = players.get(nickname);
            if (nickname.equals(nicknameSelected))
                widget = new Border(widget, BorderType.SINGLE);
            else
                widget = new Padding(widget, 0, 1);
            widgets.add(widget);
        }
        return new Column(widgets);
    }
}
