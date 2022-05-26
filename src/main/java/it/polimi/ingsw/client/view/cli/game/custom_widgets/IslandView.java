package it.polimi.ingsw.client.view.cli.game.custom_widgets;

import java.util.List;

import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

public class IslandView extends StatefulWidget {

    /**
     * ID of the island.
     */
    private final int ID;

    /**
     * Students on island.
     */
    private StudentList students;

    /**
     * color of the tower on island.
     */
    private TowerType tower;

    /**
     * if mother nature is on island.
     */
    private Boolean motherNatureIsPresent;

    /**
     * num of ban on island.
     */
    private int ban;

    /**
     * the size of the island.
     */
    private int size;

    public IslandView(StudentList students, int ID, TowerType towerType, Boolean motherNatureIsPresent, int size, int ban) {

        // initial setting of the attributes on island
        this.students = students;
        this.ID = ID;
        this.tower=towerType;
        this.motherNatureIsPresent=motherNatureIsPresent;
        this.size=size;
        this.ban=ban;

        create();
    }

    public void setStudents(StudentList students) {
        this.students = students;
    }

    public void setTower(TowerType tower) {
        this.tower = tower;
    }

    public void setMotherNatureIsPresent(Boolean motherNatureIsPresent) {
        this.motherNatureIsPresent = motherNatureIsPresent;
    }

    public void setBan(int ban) {
        this.ban = ban;
    }

    public void setSize(int size) {
        this.size = size;
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

            // the name of the island
            Text islandName = new Text("Island "+this.ID).addTextStyle(TextStyle.ITALIC).addTextStyle(TextStyle.BOLD);

            // the students on the island
            Text blueStudents = new Text(" █ : "+ this.students.getNumOf(PawnType.BLUE_UNICORNS)).setForegroundColor(Color.BLUE);
            Text greenStudents = new Text(" █ : "+ this.students.getNumOf(PawnType.GREEN_FROGS)).setForegroundColor(Color.GREEN);
            Text yellowStudents = new Text(" █ : "+ this.students.getNumOf(PawnType.YELLOW_GNOMES)).setForegroundColor(Color.BRIGHT_YELLOW);
            Text redStudents = new Text(" █ : "+ this.students.getNumOf(PawnType.RED_DRAGONS)).setForegroundColor(Color.RED);
            Text pinkStudents = new Text(" █ : "+ this.students.getNumOf(PawnType.PINK_FAIRIES)).setForegroundColor(Color.MAGENTA);

            // the tower on the island
            Text towerText = new Text("");
            if(tower!=null){
                towerText.setText(" tower: █ ");
                switch (tower){
                    case BLACK -> towerText.setForegroundColor(Color.BLACK);
                    case WHITE -> towerText.setForegroundColor(Color.WHITE);
                    case GREY -> towerText.setForegroundColor(Color.GREY);
                }
            }

            // mother nature on island
            Text motherNature = new Text("").setForegroundColor(Color.YELLOW);
            if(motherNatureIsPresent){
                motherNature.setText("Mother Nature: \uD83C\uDF38");
            }

            // the size of the island
            Text sizeText = new Text("Size: "+this.size);

            // the ban on the island
            Text banText = new Text("ban: "+this.ban);

            // LAYOUT OF THE WIDGET
            // row of students
            Row studentRow = new Row(List.of(blueStudents,greenStudents,yellowStudents,redStudents,pinkStudents));

            // row of tower and mother nature
            Row rowTowerAndMotherNature = new Row(List.of(towerText,motherNature));

            return new Border(
                    new Column(List.of(
                            islandName,
                            sizeText,
                            studentRow,
                            rowTowerAndMotherNature,
                            banText)),
                    BorderType.SINGLE);
        }
}



// MODO 1
            /*
            Column studentColumn = new Column(List.of(blueStudents,redStudents,yellowStudents,greenStudents,pinkStudents));
            Border borderStudent = new Border(studentColumn,BorderType.SINGLE);
            Text towerText = new Text("");
            Text motherNature = new Text("").setForegroundColor(Color.YELLOW);
            if(tower!=null){
                towerText.setText("tower");
                switch (tower){
                    case BLACK -> towerText.setForegroundColor(Color.BLACK);
                    case WHITE -> towerText.setForegroundColor(Color.WHITE);
                    case GREY -> towerText.setForegroundColor(Color.GREY);
                }
            }
            if(motherNatureIsPresent){
                motherNature.setText("Mother nature");
            }
            Column column = new Column(List.of(borderStudent));
            Column column2 = new Column(List.of(towerText,motherNature));
            Row row = new Row(List.of(column,column2));
            Column column3 = new Column(List.of(islandName,row));
            Border border = new Border(column3, BorderType.DOUBLE);
            return border;
            */
