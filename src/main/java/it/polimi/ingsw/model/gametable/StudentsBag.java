package it.polimi.ingsw.model.gametable;
import it.polimi.ingsw.model.LastRoundException;
import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.StudentList;
import it.polimi.ingsw.model.gametable.exceptions.EmptyBagException;

import java.util.Random;

/**
 * Class to handle the bag of students
 */
class StudentsBag {

    private final StudentList students;
    /**
     * Class constructor
     */
    public StudentsBag() {
        students = new StudentList();
    }

    /**
     * Gets the number of students contained in this bag
     * @return the number of students in this bag
     */
    protected int studentsRemaining(){
        return students.numAllStudents();
    }

    /**
     * Takes a random type of student from the bag and controls it's in the bag, if not continues drawing until
     * it finds one and removes it
     * @return the type of the student randomly taken
     */
    public PawnType draw() throws EmptyBagException {
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
        return type;
    }

    /**
     * fills the bag with the students given
     * @param students a {@code StudentList} of students to add to the bag
     * @throws NullPointerException if the {@code StudentList} given is null
     */
    public void fillWith(StudentList students) throws NullPointerException {
        if (students == null) throw new NullPointerException();
        this.students.add(students);
    }
}

