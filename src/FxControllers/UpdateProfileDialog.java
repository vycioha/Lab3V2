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

public class UpdateProfileDialog {
//    public TextField courseTitle;
//    public TextField courseTitle;
//    public TextField courseTitle;
    public Button AddCourseButtonId;

    private ObservableList<Course> appMainObservableList;

    public void UpdateProfileButton(ActionEvent actionEvent) {

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
