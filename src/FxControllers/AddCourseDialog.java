package FxControllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;
import utils.DataRW;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static utils.DataRW.writeCourseISToFile;

public class AddCourseDialog implements Initializable {
    public TextField courseTitle;
    public DatePicker courseStartDate;
    public DatePicker courseEndDate;
    public Button AddCourseButtonId;

    private ObservableList<Course> appMainObservableList;
    private CourseIS courseIS;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseIS = DataRW.loadCourseISFromFile(courseIS);
    }

    public void setAppMainObservableList(ObservableList<Course> observableList) {
        this.appMainObservableList = observableList;
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)event.getSource();
        Stage stage  = (Stage)source.getScene().getWindow();
        stage.close();
    }

    public void AddCourseToList(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/MainWindowAdmin.fxml"));

        Parent root = loader.load();

        MainWindowAdmin mainWindow = loader.getController();

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

        courseIS.getAllCourses().add(course);
        writeCourseISToFile(courseIS);

        ArrayList<Course> test = courseIS.getAllCourses();

        //REFRESH NEVEIKIA.
        mainWindow.setCourseList(test);
        mainWindow.setAdminManageAllCourses(test);
        //

        closeStage(actionEvent);
    }
}
