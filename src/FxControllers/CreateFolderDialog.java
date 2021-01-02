package FxControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Course;
import model.CourseIS;
import utils.DataRW;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static utils.DataRW.writeCourseISToFile;
import javafx.scene.control.ListView;

public class CreateFolderDialog implements Initializable {

    public TextField newFolderName;

    private CourseIS courseIS;
    private Course course;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseIS = DataRW.loadCourseISFromFile(courseIS);
    }

    public void setCourse(Course course){
        this.course = course;
    }

    public void AddFolderButton(ActionEvent actionEvent) throws IOException {

        if(newFolderName != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/FolderFileManager.fxml"));

            Parent root = loader.load();

            FolderFileManager folderFileManager = loader.getController();

            int indexOfCourse = courseIS.getAllCourses().indexOf(courseIS.getAllCourses().stream().filter(x -> x.getCourseName().equals(course.getCourseName())).findFirst().orElse(null));

            course.CreateFolder(newFolderName.getText());

            courseIS.getAllCourses().remove(indexOfCourse);

            courseIS.getAllCourses().add(course);

            writeCourseISToFile(courseIS);

            ArrayList<String> courseFolders = new ArrayList<String>();

            course.getCourseFolders().forEach(x -> courseFolders.add(x.getFolderName()));

            ObservableList<String> folders = FXCollections.observableArrayList(courseFolders);

            folderFileManager.setFolderList(folders);

            closeStage(actionEvent);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Folder has been created");

            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Folder name was not entered");

            alert.showAndWait();
        }
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)event.getSource();
        Stage stage  = (Stage)source.getScene().getWindow();
        stage.close();
    }
}
