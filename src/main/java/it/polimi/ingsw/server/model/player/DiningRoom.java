package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import it.polimi.ingsw.server.model.utils.exceptions.ReachedMaxStudentException;
import it.polimi.ingsw.server.observers.StudentsInDiningRoomObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing the dining room of a school board. Here the students are placed in tables accordingly
 * to their type, so that a table contains only students of a type and a maximum of 10 students in total.
 */
class DiningRoom {

    @SuppressWarnings("FieldCanBeLocal")
    private final int MAX_STUDENTS_PER_TABLE = 10;
    private final StudentList tables = new StudentList();

    /**
     * This is the nickname of the player to which this dining room is associated to.
     */
    private final String nickNameOfPlayer;

    DiningRoom(String nickNameOfPlayer) {
        this.nickNameOfPlayer = nickNameOfPlayer;
    }

    /**
     * Adds a student of the corresponding type in the correct table. It also checks if the added student is on
     * one of the coins symbols and returns a {@code boolean} representing this.
     * @param type the type of student to add
     * @return {@code true} if the student lands on a coin symbol, {@code false} otherwise
     * @throws ReachedMaxStudentException if it tries to add a student on a full table
     */
    boolean addStudentOf(PawnType type) throws ReachedMaxStudentException {
        int numStudents = tables.getNumOf(type);
        if (numStudents == MAX_STUDENTS_PER_TABLE) throw new ReachedMaxStudentException();
        try {
            tables.changeNumOf(type, 1);
        } catch (NotEnoughStudentException e) {
            e.printStackTrace();
        }
        numStudents += 1;
        notifyStudentsInDiningRoomObservers(this.nickNameOfPlayer,tables.clone());
        return numStudents == 3 || numStudents == 6 || numStudents == 9;
    }

    /**
     * Remove a student of the corresponding type in the tables.
     * @param type the type of student to remove
     * @throws NotEnoughStudentException if there aren't students of that type
     */
    void removeStudentOf(PawnType type) throws NotEnoughStudentException {
        tables.changeNumOf(type, -1);
        notifyStudentsInDiningRoomObservers(this.nickNameOfPlayer,tables.clone());
    }

    /**
     * Get the number of students in a table of a particular type
     * @param type the type of student to get the number
     * @return the number of students of that type
     */
    int getNumStudentsOf(PawnType type){
        return tables.getNumOf(type);
    }

    // MANAGEMENT OF OBSERVERS ON STUDENTS IN DINING ROOM
    /**
     * List of the observer on the students dining room.
     */
    private final List<StudentsInDiningRoomObserver> studentsInDiningRoomObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on the students in dining room.
     * @param observer the observer to be added
     */
    void addStudentsInDiningRoomObserver(StudentsInDiningRoomObserver observer){
        studentsInDiningRoomObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on the students dining room.
     * @param observer the observer to be removed
     */
    void removeStudentsInDiningRoomObserver(StudentsInDiningRoomObserver observer){
        studentsInDiningRoomObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on the students dining room.
     * @param nickName the nickname of the player that has the school board on which the changes have been happened
     * @param actualStudents the actual student list in dining room
     */
    private void notifyStudentsInDiningRoomObservers(String nickName, StudentList actualStudents){
        for(StudentsInDiningRoomObserver observer : studentsInDiningRoomObservers)
            observer.studentsInDiningRoomObserverUpdate(nickName,actualStudents);
    }
}
