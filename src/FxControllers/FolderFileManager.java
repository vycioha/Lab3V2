package FxControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
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

public class FolderFileManager implements Initializable {
    public ListView<String> folderList;
    public ListView<String> fileList;
    public Button createFolderBtn;

    private Course course;
    private CourseIS courseIS = new CourseIS();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseIS = DataRW.loadCourseISFromFile(courseIS);
    }

    public void setCourse(Course course){
        this.course = course;
    }

    public void getFiles(MouseEvent mouseEvent) {
        String folderName = folderList.getSelectionModel().getSelectedItem();
        if(folderName != null){

            fileList.getItems().clear();

            Folder folder = course.getCourseFolders().stream().filter(x -> x.getFolderName().equals(folderName)).findFirst().orElse(null);

            ArrayList<String> folderFiles = new ArrayList<String>();

            if(folder != null){

                folder.getFolderFiles().forEach(x -> folderFiles.add(x.getFilename()));

                ObservableList<String> files = FXCollections.observableArrayList(folderFiles);

                fileList.getItems().addAll(files);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("folder by selected name does not exist");
                alert.showAndWait();
            }
        }
        else{
            fileList = new ListView<>();
        }
    }

    public void setFolderList(ObservableList<String> folders){
        folderList.getItems().clear();
        folderList.getItems().addAll(folders);
    }

    public void deleteFolder(ActionEvent actionEvent) {
        String folderName = folderList.getSelectionModel().getSelectedItem();
        if(folderName != null){
            course.DeleteFolder(folderName);

            int indexOfCourse = courseIS.getAllCourses().indexOf(courseIS.getAllCourses().stream().filter(x -> x.getCourseName().equals(course.getCourseName())).findFirst().orElse(null));

            courseIS.getAllCourses().remove(indexOfCourse);

            courseIS.getAllCourses().add(course);

            writeCourseISToFile(courseIS);

            folderList.getItems().clear();
            course.getCourseFolders().forEach(x -> folderList.getItems().add(x.getFolderName()));
        }
    }

    public void createFolder(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/CreateFolderDialog.fxml"));

        Parent parent = loader.load();

        CreateFolderDialog createFolderDialog = loader.getController();

        createFolderDialog.setCourse(course);

        Scene scene = new Scene(parent, 300, 100);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void deleteFile(ActionEvent actionEvent) {
        String folderName = folderList.getSelectionModel().getSelectedItem();
        Folder folder = course.getCourseFolders().stream().filter(x -> x.getFolderName().equals(folderName)).findFirst().orElse(null);

        String fileName = fileList.getSelectionModel().getSelectedItem();

        if(fileName != null && folder != null){

            int indexOfCourse = courseIS.getAllCourses().indexOf(courseIS.getAllCourses().stream().filter(x -> x.getCourseName().equals(course.getCourseName())).findFirst().orElse(null));

            int indexOfFolder = course.getCourseFolders().indexOf(course.getCourseFolders().stream().filter(x -> x.getFolderName().equals(folder.getFolderName())).findFirst().orElse(null));

            folder.DeleteFile(fileName);

            courseIS.getAllCourses().get(indexOfCourse).getCourseFolders().remove(indexOfFolder);

            courseIS.getAllCourses().get(indexOfCourse).getCourseFolders().add(folder);

            writeCourseISToFile(courseIS);

            fileList.getItems().clear();

            course.getCourseFolders().get(indexOfFolder).getFolderFiles().forEach(x -> fileList.getItems().add(x.getFilename()));
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Folder or File not selected");

            alert.showAndWait();
        }
    }

    public void setFileList(ObservableList<String> files){
        fileList.getItems().clear();
        fileList.getItems().addAll(files);
    }

    public void createFile(ActionEvent actionEvent) throws IOException {
        String folderName = folderList.getSelectionModel().getSelectedItem();

        if(folderName != null){
            Folder folder = course.getCourseFolders().stream().filter(x -> x.getFolderName().equals(folderName)).findFirst().orElse(null);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/CreateFileDialog.fxml"));

            Parent parent = loader.load();

            CreateFileDialog createFileDialog = loader.getController();

            createFileDialog.setFolder(folder);
            createFileDialog.setCourse(course);

            Scene scene = new Scene(parent, 300, 100);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Folder not selected");

            alert.showAndWait();
        }

    }

    public void LoadCourseFolders(ActionEvent actionEvent) {
        folderList.getItems().clear();

        ArrayList<String> courseFolderList = new ArrayList<String>();

        course.getCourseFolders().forEach(x -> courseFolderList.add(x.getFolderName()));

        ObservableList<String> folders = FXCollections.observableArrayList(courseFolderList);

        folderList.getItems().addAll(folders);
    }
}
