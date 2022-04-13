package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.StudentList;

/**
 * A class representing the dining room of a school board. Here the students are placed in tables accordingly
 * to their type, so that a table contains only students of a type and a maximum of 10 students in total.
 */
class DiningRoom {

    @SuppressWarnings("FieldCanBeLocal")
    private final int MAX_STUDENTS_PER_TABLE = 10;
    private final StudentList tables = new StudentList();

    /**
     * Adds a student of the corresponding type in the correct table. It also checks if the added student is on
     * one of the coins symbols and returns a {@code boolean} representing this.
     * @param type the type of student to add
     * @return {@code true} if the student lands on a coin symbol, {@code false} otherwise
     * @throws ReachedMaxStudentException if it tries to add a student on a full table
     */
    protected boolean addStudentOf(PawnType type) throws ReachedMaxStudentException {
        int numStudents = tables.getNumOf(type);
        if (numStudents == MAX_STUDENTS_PER_TABLE) throw new ReachedMaxStudentException();
        try {
            tables.changeNumOf(type, 1);
        } catch (NotEnoughStudentException e) {
            e.printStackTrace();
        }
        numStudents += 1;
        return numStudents == 3 || numStudents == 6 || numStudents == 9;
    }

    /**
     * Remove a student of the corresponding type in the tables.
     * @param type the type of student to remove
     * @throws NotEnoughStudentException if there aren't students of that type
     */
    protected void removeStudentOf(PawnType type) throws NotEnoughStudentException {
        tables.changeNumOf(type, -1);
    }

    /**
     * Get the number of students in a table of a particular type
     * @param type the type of student to get the number
     * @return the number of students of that type
     */
    protected int getNumStudentsOf(PawnType type){
        return tables.getNumOf(type);
    }
}
