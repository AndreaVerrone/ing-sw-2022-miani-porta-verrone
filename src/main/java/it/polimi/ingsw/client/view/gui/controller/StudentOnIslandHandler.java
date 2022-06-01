package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.utils.image_getters.StudentImageType;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Class that represents the students on an island
 */
public class StudentOnIslandHandler implements EventHandler<MouseEvent> {

    /**
     * {@code StudentList} of the students on the island
     */
    private final StudentList students = new StudentList();

    /**
     * True if the animation to see teh students is triggered
     */
    private boolean isTriggered;

    /**
     * Pane use to display the {@code StudentList}
     */
    private final FlowPane studentsView = new FlowPane(Orientation.VERTICAL);

    /**
     * Animation to move the window that displays the students up and down
     */
    private TranslateTransition translateTransition;

    /**
     * Animation to fade the window that display the student in and out
     */
    private FadeTransition fadeTransition;

    /**
     * This class represents all the student on a certain island, allowing to show them through an animation
     * @param islandGrid Grid used to place the students
     * @param islandColumn Column of the island of the students on the grid
     * @param islandRow Row of the island of the students on the grid
     */
    public StudentOnIslandHandler(GridPane islandGrid, int islandColumn, int islandRow){

        islandGrid.add(studentsView, islandColumn, islandRow);
        studentsView.setVisible(false);
        studentsView.setMouseTransparent(true);

        paneSetUp();
        animationSetUp();
    }

    /**
     * Method to set up the pane where the students will be displayed
     */
    private void paneSetUp(){
        studentsView.setPadding(new Insets(5,5,5,5));
        studentsView.setVgap(5);
        studentsView.setMinHeight(140);
        studentsView.setBackground(Background.fill(Color.ANTIQUEWHITE));
        studentsView.setBorder(Border.stroke(Color.BLACK));
        studentsView.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
    }

    /**
     * Method to set up the animation used to show and hide the students
     */
    private void animationSetUp(){
        translateTransition = new TranslateTransition(Duration.millis(500), studentsView);
        fadeTransition = new FadeTransition(Duration.millis(500), studentsView);
    }

    /**
     * Method used to fill the pane with the students every time is showed
     */
    private void fillPaneWithStudents(){
        studentsView.getChildren().clear();
        for (PawnType type : PawnType.values()) {
            ImageView studentIcon = new ImageView(StudentImageType.typeConverter(type).getIcon());
            Label studentLabel = new Label();
            studentLabel.setGraphic(studentIcon);
            studentLabel.setText(type + " = " + students.getNumOf(type));
            studentLabel.setTextFill(StudentImageType.typeConverter(type).getColor());
            studentsView.getChildren().add(studentLabel);
        }
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        //TO prevent bug on animations
        translateTransition.stop();
        fadeTransition.stop();
        if(mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED){
            if(!isTriggered){
                studentsView.setTranslateY(0);
                fillPaneWithStudents();
                studentsView.setVisible(true);
                addTranslateUpAnimation();
                addFadeInAnimation();
                isTriggered = true;
            }
        }
        else if(mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED){
            if(isTriggered){
                addTranslateDownAnimation();
                addFadeOutAnimation();
                isTriggered = false;
            }
        }
    }

    /**
     * Animation use to move the display up
     */
    private void addTranslateUpAnimation(){
        translateTransition.setByY(-120);
        translateTransition.play();
    }

    /**
     * Animation to fade the display in order to show it
     */
    private void addFadeInAnimation(){
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    /**
     * Animation to move the display down
     */
    private void addTranslateDownAnimation(){
        translateTransition.setByY(studentsView.getTranslateY() * -1);
        translateTransition.play();
    }

    /**
     * Animation to fade the display out in order to hide it
     */
    private void addFadeOutAnimation(){
        fadeTransition.setFromValue(fadeTransition.getToValue());
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }

    public StudentList getStudents() {
        return students;
    }
}
