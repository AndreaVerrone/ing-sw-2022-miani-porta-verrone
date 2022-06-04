package it.polimi.ingsw.client.view.cli.game.custom_widgets.schoolboard;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import java.util.ArrayList;
import java.util.List;

class DiningRoomView extends StatefulWidget {

    /**
     * students in the dining room.
     */
    private StudentList actualStudentList;

    /**
     * The max number of students in one table.
     */
    private final int MAX_STUD = 10;

    /**
     * The number of tables in the dining room.
     */
    private final int NUM_OF_TABLES = 5;

    /**
     * The constructor of the class
     * @param actualStudentList the student list in the dining room
     */
    DiningRoomView(StudentList actualStudentList) {
        setState(()->this.actualStudentList = actualStudentList);
        create();
    }

    /* NOT NEEDED
    public void setActualStudentList(StudentList actualStudentList) {
        setState(()->this.actualStudentList=actualStudentList);
    }*/

    /**
     * A method used to define by which Widgets this StatefulWidget is composed.
     * This method is run every time something in the content change or when it should be
     * displayed, so no heavy processes should be done inside it.
     *
     * @return a Widget describing how this should be drawn on screen
     */
    @Override
    protected Widget build() {

        // THE HEADER
        Text header = new Text(Translator.getDiningRoomViewHeader()).addTextStyle(TextStyle.ITALIC).addTextStyle(TextStyle.BOLD);

        // THE TABLE OF STUDENTS
        // the colum of the tables of students
        Column studentTable = new Column();

        // this is a list containing all the Pawn Type
        /* NOTE:
         * it is needed to do that, instead of PawnType.values(), since I need
         * the list of pawn type with this orde
         */
        List<PawnType> pawnTypeList = new ArrayList<>(
                List.of(
                    PawnType.BLUE_UNICORNS,
                    PawnType.GREEN_FROGS,
                    PawnType.YELLOW_GNOMES,
                    PawnType.RED_DRAGONS,
                    PawnType.PINK_FAIRIES)
        );

        // fill the column with the tables of student row by row (i.e., table by table)
        for(int i=0;i<NUM_OF_TABLES;i++){
            studentTable.addChild(fillTable(actualStudentList.getNumOf(pawnTypeList.get(i)),pawnTypeList.get(i)));
        }

        // return the widget
        return new Border (
                new Column(List.of(header,studentTable)),
                BorderType.SINGLE
        );
    }

    /**
     * This method will create a string with as many █ as specified in the parameter.
     * <p>
     * (e.g., {@code createString(3)} will return:  █  █  █ )
     * @param numOfStud the num of █ to insert in the string
     * @return the string made of {@code numOfStud} █
     */
    private String createString(int numOfStud){
        return " █ ".repeat(numOfStud);
    }

    /**
     * This method will return the table of the students of the color specified in the parameter
     * @param numOfStud the num of student that are present in the table of the {@code color} pawn type
     * @param color the Pawn type of the student
     * @return the table widget
     */
    private Row fillTable(int numOfStud, PawnType color){

        // create a string with as many colored █ as many are the student in the dining room
        Text studentsInTable = new Text("");
        switch (color){
            case BLUE_UNICORNS -> studentsInTable = new Text(createString(numOfStud)).setForegroundColor(Color.BLUE);
            case GREEN_FROGS -> studentsInTable = new Text(createString(numOfStud)).setForegroundColor(Color.GREEN);
            case YELLOW_GNOMES -> studentsInTable = new Text(createString(numOfStud)).setForegroundColor(Color.YELLOW);
            case RED_DRAGONS -> studentsInTable = new Text(createString(numOfStud)).setForegroundColor(Color.RED);
            case PINK_FAIRIES -> studentsInTable = new Text(createString(numOfStud)).setForegroundColor(Color.MAGENTA);
        }

        // create as many gray █ as many are the student that are not present in the dining room
        Text studentsMissingInTable= new Text(createString(MAX_STUD-numOfStud)).setForegroundColor(Color.GREY);

        // concatenate the student present and the one not present
        return new Row(List.of(studentsInTable,studentsMissingInTable));
    }
}








/*
        int numOfSpace = 10;
        StringBuilder padding = new StringBuilder();
        padding.append(" █ ".repeat(numOfSpace));
        String string = padding.toString();

        List<String> list = new ArrayList<>();
        for (int i=0;i<6;i++){
            string.concat(" █ ");
        }


        Text tw = new Text(" █ ").setForegroundColor(Color.WHITE);
        Text tb = new Text(" █ ").setForegroundColor(Color.BLACK);
        Row template = new Row(List.of(tw, tw, tb, tw, tw, tb, tw, tw, tb, tw ));

        Text blueStudents = new Text(padding.toString()).setForegroundColor(Color.BLUE);
        Text greenStudents = new Text(padding.toString()).setForegroundColor(Color.GREEN);
        Text yellowStudents = new Text(padding.toString()).setForegroundColor(Color.BRIGHT_YELLOW);
        Text redStudents = new Text(padding.toString()).setForegroundColor(Color.RED);
        Text pinkStudents = new Text(padding.toString()).setForegroundColor(Color.MAGENTA);

        Column studentColumn = new Column(List.of(blueStudents, greenStudents, yellowStudents, redStudents, pinkStudents));

        Column column = new Column(List.of(
                header,
                //new Border(template,BorderType.SINGLE),
                studentColumn
                //new Border(studentColumn, BorderType.SINGLE)

                //new Border(blueStudents,BorderType.SINGLE),
                //new Border(greenStudents,BorderType.SINGLE),
                //new Border(yellowStudents,BorderType.SINGLE),
                //new Border(redStudents,BorderType.SINGLE),
                //new Border(pinkStudents,BorderType.SINGLE)
                ));
        */

    /*
    public DiningRoomView(StudentList actualStudentList) {
        this.actualStudentList = actualStudentList;
        create();
    }*/
