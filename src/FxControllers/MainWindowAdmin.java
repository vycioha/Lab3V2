package FxControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import utils.DataRW;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

//implements Initializable -> Inteface, kuris pradiniam fxml formos langui priskirs reikšmes.
public class MainWindowAdmin implements Initializable {

    public Button logOutBtn;
    public ListView<String> CourseList;
    public ListView<String> UserList;
    public Text loggedAs;
    private User user;

    private ObservableList<Course> courseObservableList = FXCollections.observableArrayList();

    private CourseIS courseIS = new CourseIS();

    public void setUser(User user){
        this.user = user;
    }

    public void setLoggedAs(String username){
        loggedAs.setText("logged as: " + username);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        courseIS = DataRW.loadCourseISFromFile(courseIS);

        ArrayList<String> courseArrayList = new ArrayList<String>();

        courseObservableList.addAll(courseIS.getAllCourses());

        //HARD CODE - pakeisti.

        LocalDate localDate = LocalDate.of(2018,10,10);

        ArrayList<User> hardCodedUsers =  new ArrayList<User>();

        User hardCodedUser =  new User("name","surname", "admin1", "admin1", 1996, new ArrayList<Course>(), false, new ArrayList<Course>(), "bank account");

        hardCodedUsers.add(hardCodedUser);

        courseIS.getAllCourses().add(new Course("Anglu_kalba"
                , 0
                , new ArrayList<CourseFile>()
                , new ArrayList<Folder>()
                , new ArrayList<User>()
                ,localDate,localDate,localDate,localDate
                , hardCodedUsers));

        //HARD CODE - END

        courseIS.getAllCourses().forEach(x -> courseArrayList.add(x.getCourseName()));

        if(courseArrayList.size() > 0){
            ObservableList<String> courses = FXCollections.observableArrayList(courseArrayList);

            CourseList.getItems().addAll(courses);
        }
        else{
            CourseList = new ListView<>();
        }
    }

    public void PopulateEnrolledUserList(javafx.scene.input.MouseEvent event){
        String courseName = CourseList.getSelectionModel().getSelectedItem();
        if(courseName != null){

            UserList.getItems().clear();

            Course course = courseIS.getCourseByName(courseName);

            ArrayList<String> enrolledUserNames = new ArrayList<String>();

            course.GetEnrolledUsers().forEach(x -> enrolledUserNames.add(x.getName() + " " + x.getSurname() + " - " + x.getLogin()));

            ObservableList<String> users = FXCollections.observableArrayList(enrolledUserNames);

            UserList.getItems().addAll(users);
        }
        else{
            UserList = new ListView<>();
        }
    }

    public void LogOut() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/LoginPage.fxml"));

        Parent root = loader.load();

        Stage stage = (Stage)logOutBtn.getScene().getWindow();
        stage.setTitle("Login page");
        stage.setScene(new Scene(root, 300, 400));
        stage.show();
    }

    public void OpenAddCourseDialog(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddCourseDialog.fxml"));

        Parent parent = loader.load();

        AddCourseDialog dialogController = loader.<AddCourseDialog>getController();

        dialogController.setAppMainObservableList(courseObservableList);

        Scene scene = new Scene(parent, 300, 200);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
