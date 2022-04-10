package it.polimi.ingsw.model;

/**
 * A class used to store list of students
 */
public class StudentList implements Cloneable{

    private int yellow = 0;
    private int blue = 0;
    private int green = 0;
    private int red = 0;
    private int pink = 0;

    /**
     * Gets the number of students of the given {@code PawnType} contained in this student list.
     * @param type the type of student to check
     * @return the number of student of that type
     */
    public int getNumOf(PawnType type){
        return switch (type){
            case YELLOW_GNOMES -> yellow;
            case BLUE_UNICORNS -> blue;
            case GREEN_FROGS -> green;
            case RED_DRAGONS -> red;
            case PINK_FAIRIES -> pink;
        };
    }

    /**
     * Change the number of students of the given {@code PawnType} adding {@code delta} on it.
     * Based on the sign of {@code delta} this could be an increase (if positive) or a decrease
     * (if negative) in the number of student of that type.
     * @param type the type of student to change the number
     * @param delta the amount to change
     * @throws NotEnoughStudentException if the {@code delta} is negative and there aren't enough
     * student to remove
     */
    public void changeNumOf(PawnType type, int delta) throws NotEnoughStudentException {
        switch (type) {
            case YELLOW_GNOMES -> {
                if (delta < 0 && -delta > yellow)
                    throw new NotEnoughStudentException();
                yellow += delta;
            }
            case BLUE_UNICORNS -> {
                if (delta < 0 && -delta > blue)
                    throw new NotEnoughStudentException();
                blue += delta;
            }
            case GREEN_FROGS -> {
                if (delta < 0 && -delta > green)
                    throw new NotEnoughStudentException();
                green += delta;
            }
            case RED_DRAGONS -> {
                if (delta < 0 && -delta > red)
                    throw new NotEnoughStudentException();
                red += delta;
            }
            case PINK_FAIRIES -> {
                if (delta < 0 && -delta > pink)
                    throw new NotEnoughStudentException();
                pink += delta;
            }
        }
    }

    /**
     * Gets the total number of students in this list.
     * @return the total number of students
     */
    public int numAllStudents(){
        return yellow + green + blue + red + pink;
    }

    /**
     * Sets all the students to be as numerous as {@code value}.
     * {@code value} must be grater or equal to 0.
     * @param value the number to assign to all student counts
     */
    public void setAllAs(int value){
        assert value >= 0 : "value is negative";

        yellow = value;
        blue = value;
        green = value;
        red = value;
        pink = value;
    }

    /**
     * A method to empty this student list. This is equivalent of calling {@code StudentList.setAllAs(0)}
     * @see #setAllAs(int)
     */
    public void empty(){
        setAllAs(0);
    }

    /**
     * Add another student list to this list. At the end of this method, the number of each student in this
     * list will be equal to the sum of that already present in this list
     * and those contained in {@code studentList}.
     * @param studentList the other {@code StudentList} to add to this
     */
    public void add(StudentList studentList){
        if (studentList.numAllStudents() == 0) return;
        yellow += studentList.yellow;
        red += studentList.red;
        blue += studentList.blue;
        green += studentList.green;
        pink += studentList.pink;
    }

    @Override
    public StudentList clone() {
        try {
            return (StudentList) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
