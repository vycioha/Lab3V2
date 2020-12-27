package FxControllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Course;
import model.CourseFile;
import model.Folder;
import model.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddCourseDialog {
    public TextField courseTitle;
    public DatePicker courseStartDate;
    public DatePicker courseEndDate;
    public Button AddCourseButtonId;

    private ObservableList<Course> appMainObservableList;

    public void AddCourseButton(ActionEvent actionEvent) {
        String title = courseTitle.getText();
        LocalDate courseStartDate = this.courseStartDate.getValue();
        LocalDate courseEndDate = this.courseEndDate.getValue();

        Course course = new Course(title
                                    ,0
                                    ,new ArrayList<CourseFile>()
                                    ,new ArrayList<Folder>()
                                    ,new ArrayList<User>()
                                    ,courseStartDate
                                    ,courseEndDate
                                    ,LocalDate.now()
                                    ,LocalDate.now()
                                    ,new ArrayList<User>());

        appMainObservableList.add(course);

        //add course to databse logika.

        closeStage(actionEvent);
    }

    public void setAppMainObservableList(ObservableList<Course> observableList) {
        this.appMainObservableList = observableList;
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)event.getSource();
        Stage stage  = (Stage)source.getScene().getWindow();
        stage.close();
    }
}
