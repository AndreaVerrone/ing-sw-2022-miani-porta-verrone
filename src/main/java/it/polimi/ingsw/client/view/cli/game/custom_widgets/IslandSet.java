package it.polimi.ingsw.client.view.cli.game.custom_widgets;

import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.gametable.Island;

import java.util.ArrayList;
import java.util.List;

public class IslandSet extends StatefulWidget {

    //List<Island> islands = new ArrayList<>();

    List<Island> islands = new ArrayList<>();

    List<IslandView> islandCards;

    public IslandSet(List<IslandView> islandCards) {
        this.islandCards = islandCards;
        create();
    }

    /**
     * A method used to define by which Widgets this StatefulWidget is composed.
     * This method is run every time something in the content change or when it should be
     * displayed, so no heavy processes should be done inside it.
     *
     * @return a Widget describing how this should be drawn on screen
     */
    @Override
    protected Widget build() {

        List<SizedBox> list = new ArrayList<>();

        for(IslandView i : islandCards){
            list.add(new SizedBox(i,0,0));
        }


        Row row = new Row(List.of(list.get(0), list.get(1), list.get(2), list.get(3)));
        Row row2 = new Row(List.of(list.get(11), list.get(1), list.get(2), list.get(4)));
        Row row4 = new Row(List.of(list.get(10), list.get(1), list.get(2), list.get(4)));


        SizedBox s1 = new SizedBox(30,4);



        SizedBox s2 = new SizedBox(islandCards.get(0),0,0);
        SizedBox s3 = new SizedBox(islandCards.get(0),0,0);
        SizedBox s4 = new SizedBox(islandCards.get(0),0,0);
        SizedBox s5 = new SizedBox(islandCards.get(0),0,0);
        SizedBox s6 = new SizedBox(islandCards.get(0),0,0);
        SizedBox s7 = new SizedBox(islandCards.get(0),0,0);
        SizedBox s8 = new SizedBox(islandCards.get(0),0,0);
        SizedBox s9 = new SizedBox(islandCards.get(0),0,0);
        SizedBox s10 = new SizedBox(islandCards.get(0),0,0);
        SizedBox s11 = new SizedBox(islandCards.get(0),0,0);
        SizedBox s12 = new SizedBox(islandCards.get(0),0,0);






        /* MODO 1
        SizedBox s1 = new SizedBox(islandWidth*2,islandHeight);

        Row row1 = new Row(List.of(
                islandCards.get(0),
                islandCards.get(1),
                islandCards.get(2),
                islandCards.get(3)
        ));

        Row row2 = new Row(List.of(
                islandCards.get(11),
                s1,
                islandCards.get(4)
        ));

        Row row3 = new Row(List.of(
                islandCards.get(10),
                s1,
                islandCards.get(5)
        ));

        Row row4 = new Row(List.of(
                islandCards.get(9),
                islandCards.get(8),
                islandCards.get(7),
                islandCards.get(6)
        ));

        Column grid = new Column(List.of(row1, row2, row3, row4));
        */

        /* MODO 2
        int islandWidth = islandCards.get(0).getWidth();
        int islandHeight = islandCards.get(0).getHeight();

        Text header = new Text("ISLANDS").addTextStyle(TextStyle.BOLD).addTextStyle(TextStyle.ITALIC);

        SizedBox s1 = new SizedBox(islandWidth*3,islandHeight);

        Row row1 = new Row(List.of(
                islandCards.get(0),
                islandCards.get(1),
                islandCards.get(2),
                islandCards.get(3),
                islandCards.get(4)
        ));

        Row row2 = new Row(List.of(
                islandCards.get(11),
                s1,
                islandCards.get(5)
        ));

        Row row3 = new Row(List.of(
                islandCards.get(10),
                islandCards.get(9),
                islandCards.get(8),
                islandCards.get(7),
                islandCards.get(6)
        ));

        Column grid = new Column(List.of(header,row1, row2, row3));

        return grid;
        */


        return new Border(new Column (List.of(new Border(s1,BorderType.SINGLE),s2)));
    }
}
