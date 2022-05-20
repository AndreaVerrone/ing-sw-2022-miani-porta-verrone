package it.polimi.ingsw.server.model.gametable;

import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.exceptions.EmptyBagException;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import it.polimi.ingsw.server.observers.EmptyStudentBagObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class to handle the bag of students
 */
class StudentsBag {
    /**
     * Students in the bag
     */
    private final StudentList students;
    /**
     * Class constructor
     */
    StudentsBag() {
        students = new StudentList();
    }

    /**
     * Gets the number of students contained in this bag
     * @return the number of students in this bag
     */
    int studentsRemaining(){
        return students.numAllStudents();
    }

    /**
     * Takes and removes a random student from the bag
     * @return the type of the student randomly taken
     * @throws EmptyBagException if the bag is empty
     */
    PawnType draw() throws EmptyBagException {
        PawnType type;
        if(students.numAllStudents() == 0) throw new EmptyBagException();
        do {
            int random = new Random().nextInt(PawnType.values().length);
            type = PawnType.values()[random];
        }while (students.getNumOf(type)<=0);
        try {
            students.changeNumOf(type, -1);
        } catch (NotEnoughStudentException e) {
            throw new EmptyBagException();
        }

        // checks for observer update
        if(students.numAllStudents() == 0) {
            // notify the observer that the student bag is empty
            notifyEmptyStudentBagObserver();
        }

        return type;
    }

    /**
     * Fills the bag with the students given
     * @param students a {@code StudentList} of students to add to the bag
     * @throws NullPointerException if the {@code StudentList} given is null
     */
    void fillWith(StudentList students) throws NullPointerException {
        if (students == null) throw new NullPointerException();
        this.students.add(students);
    }

    // MANAGEMENT OF THE OBSERVERS ON EMPTY STUDENT BAG
    /**
     * List of the observers on empty student bag.
     */
    private final List<EmptyStudentBagObserver> emptyStudentBagObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on empty student bag.
     * @param observer the observer to be added
     */
    void addEmptyStudentBagObserver(EmptyStudentBagObserver observer){
        emptyStudentBagObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on empty student bag.
     * @param observer the observer to be removed
     */
    void removeEmptyStudentBagObserver(EmptyStudentBagObserver observer){
        emptyStudentBagObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that the student bag is empty.
     */
    private void notifyEmptyStudentBagObserver(){
        for(EmptyStudentBagObserver observer: emptyStudentBagObservers){
            observer.emptyStudentBagObserverUpdate();
        }
    }
}
