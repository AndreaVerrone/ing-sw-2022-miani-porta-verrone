package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.view.gui.listeners.CloudListener;
import it.polimi.ingsw.client.view.gui.utils.image_getters.StudentImageType;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a cloud in the view
 */
public class Cloud {

    /**
     * {@code ImageView} of the cloud
     */
    private final ImageView cloudView;

    /**
     * Grid where the clouds are placed
     */
    private final GridPane islandGrid;

    /**
     * Column of the grid where the cloud is placed
     */
    private final int column;

    /**
     * Row of the grid where the cloud is placed
     */
    private final int row;

    /**
     * Students placed on the cloud
     */
    private final List<Pawn> students;

    private final CloudListener cloudListener;

    /**
     * This class represents a cloud , allowing to fill it with students and to remove all of them
     * @param cloudView {@code ImageView} of the cloud
     * @param islandGrid Grid where the clouds are placed
     * @param column Column of the grid where the cloud is placed
     * @param row Row of the grid where the cloud is placed
     * @param cloudID the ID of the cloud
     * @param gui the considered gui
     */
    public Cloud(GUI gui, int cloudID, ImageView cloudView, GridPane islandGrid, int column, int row){
        this.cloudView = cloudView;
        this.islandGrid = islandGrid;
        this.column = column;
        this.row = row;

        students = new ArrayList<>(3);

        cloudListener = new CloudListener(gui, cloudID);
        cloudView.setOnMouseClicked(cloudListener);
    }

    /**
     * Method to add a student to the cloud
     * @param color color of the student added
     */
    public void addStudent(PawnType color){
        ImageView studentsView = new ImageView(StudentImageType.typeConverter(color).getImage());
        islandGrid.add(studentsView, column, row);
        Pawn student = new Pawn(studentsView, color);
        students.add(student);
        if(students.size() == 1){
            studentsView.setTranslateY(-6);
            return;
        }
        if(students.size() == 2){
            studentsView.setTranslateX(55);
            studentsView.setTranslateY(-23);
            return;
        }
        if(students.size() == 3) {
            studentsView.setTranslateX(30);
            studentsView.setTranslateY(33);
        }
        if(students.size() == 4){
            studentsView.setTranslateX(80);
            studentsView.setTranslateY(20);
        }
    }

    /**
     * Method to remove all students from the cloud
     */
    public void removeAllStudents(){
        for(Pawn color: students){
            islandGrid.getChildren().remove(color.getImageView());
        }
        students.clear();
    }

    /**
     * Method to update the students on a cloud
     * @param students new students on the cloud
     */
    public void updateStudents(StudentList students){
        removeAllStudents();
        for(PawnType pawnType: PawnType.values()){
            for(int i= 0; i < students.getNumOf(pawnType); i++){
                addStudent(pawnType);
            }
        }
     }

    /**
     * Method to enable the listeners on the cloud
     */
    public void enableLocationListener(){
        cloudListener.enableListener();
    }

    /**
     * method to disable the listeners on the cloud
     */
    public void disableLocationListener() {
        cloudListener.disableListener();
    }
}
