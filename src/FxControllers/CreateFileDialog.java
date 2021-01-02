package FxControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Course;
import model.CourseIS;
import model.Folder;
import utils.DataRW;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static utils.DataRW.writeCourseISToFile;

public class CreateFileDialog implements Initializable {

    public TextField newFileName;

    private CourseIS courseIS;
    private Folder folder;
    private Course course;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseIS = DataRW.loadCourseISFromFile(courseIS);
    }

    public void setFolder(Folder folder){
        this.folder = folder;
    }

    public void setCourse(Course course){
        this.course = course;
    }

    public void AddFileBtn(ActionEvent actionEvent) throws IOException {

        if(newFileName != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/FolderFileManager.fxml"));

            Parent root = loader.load();

            FolderFileManager folderFileManager = loader.getController();

            int indexOfCourse = courseIS.getAllCourses().indexOf(courseIS.getAllCourses().stream().filter(x -> x.getCourseName().equals(course.getCourseName())).findFirst().orElse(null));

            int indexOfFolder = course.getCourseFolders().indexOf(course.getCourseFolders().stream().filter(x -> x.getFolderName().equals(folder.getFolderName())).findFirst().orElse(null));

            folder.CreateFile(newFileName.getText());

            courseIS.getAllCourses().get(indexOfCourse).getCourseFolders().remove(indexOfFolder);

            courseIS.getAllCourses().get(indexOfCourse).getCourseFolders().add(folder);

            writeCourseISToFile(courseIS);

            ArrayList<String> folderFiles = new ArrayList<String>();

            folder.getFolderFiles().forEach(x -> folderFiles.add(x.getFilename()));

            ObservableList<String> files = FXCollections.observableArrayList(folderFiles);

            folderFileManager.setFileList(files);

            closeStage(actionEvent);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("File has been created");

            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("File name was not entered");

            alert.showAndWait();
        }
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)event.getSource();
        Stage stage  = (Stage)source.getScene().getWindow();
        stage.close();
    }
}
