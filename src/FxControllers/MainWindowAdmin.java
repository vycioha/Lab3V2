package FxControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import utils.DataRW;
import utils.UserRW;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static utils.DataRW.writeCourseISToFile;

//implements Initializable -> Inteface, kuris pradiniam fxml formos langui priskirs reikšmes.
public class MainWindowAdmin implements Initializable {

    public Button logOutBtn;
    public ListView<String> CourseList;
    public ListView<String> UserList;
    public Text loggedAs;
    public ListView<String> AdminManageAllCourses;
    public Button OpenAddCourseDialogBtn;
    public TextField updateFieldCourseTitle;
    public DatePicker updateFieldEndDate;
    public DatePicker updateFieldStartDate;
    public ListView<String> ManageCoursesAllUsers;
    private User user;

    private ObservableList<Course> courseObservableList = FXCollections.observableArrayList();
    private ObservableList<User> userObservableList = FXCollections.observableArrayList();
    private CourseIS courseIS = new CourseIS();
    private ArrayList<User> allUsers = new ArrayList<User>();
    private String OriginalCourseTitleToUpdate = null;

    public void setCourseList(ArrayList<Course> courseList){
        CourseList.getItems().clear();
        courseList.forEach(x -> CourseList.getItems().add(x.getCourseName()));
    }

    public void setAdminManageAllCourses(ArrayList<Course> courseList){
        AdminManageAllCourses.getItems().clear();
        courseList.forEach(x -> AdminManageAllCourses.getItems().add(x.getCourseName()));
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setLoggedAs(String username){
        loggedAs.setText("logged as: " + username);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        courseIS = DataRW.loadCourseISFromFile(courseIS);
        allUsers = UserRW.GetAllUsers();

        ArrayList<String> courseArrayList = new ArrayList<String>();
        ArrayList<String> userArrayList = new ArrayList<String>();

        courseObservableList.addAll(courseIS.getAllCourses());
        userObservableList.addAll(allUsers);

        courseIS.getAllCourses().forEach(x -> courseArrayList.add(x.getCourseName()));
        allUsers.forEach(x -> userArrayList.add(x.getName() + " " + x.getSurname() + " - " + x.getLogin()));

        if(courseArrayList.size() > 0){
            ObservableList<String> courses = FXCollections.observableArrayList(courseArrayList);

            CourseList.getItems().addAll(courses);
            AdminManageAllCourses.getItems().addAll(courses);
        }
        else{
            CourseList = new ListView<>();
        }

        if(userArrayList.size() > 0){
            ObservableList<String> users = FXCollections.observableArrayList(userArrayList);

            ManageCoursesAllUsers.getItems().addAll(users);
        }
        else{
            ManageCoursesAllUsers = new ListView<>();
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

    public void DeleteSelectedCourse(){
        String courseName = AdminManageAllCourses.getSelectionModel().getSelectedItem();
        if(courseName != null){
            Course course = courseIS.getCourseByName(courseName);
            courseIS.getAllCourses().remove(course);
            writeCourseISToFile(courseIS);

            setCourseList(courseIS.getAllCourses());
            setAdminManageAllCourses(courseIS.getAllCourses());
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Nothing was selected or course does not exist.");

            alert.showAndWait();
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

    public void UpdateCourseInformation(ActionEvent actionEvent) {
        String newCourseTitle = updateFieldCourseTitle.getText();
        LocalDate newEndDate = updateFieldEndDate.getValue();
        LocalDate newStartDate = updateFieldStartDate.getValue();

        Course course = courseIS.getCourseByName(OriginalCourseTitleToUpdate);

        if(newCourseTitle != null && newEndDate != null && newStartDate != null){
            courseIS.getAllCourses().remove(course);

            course.setCourseName(newCourseTitle);
            course.setCourseStart(newStartDate);
            course.setCourseEnd(newEndDate);

            courseIS.getAllCourses().add(course);
            writeCourseISToFile(courseIS);

            setCourseList(courseIS.getAllCourses());
            setAdminManageAllCourses(courseIS.getAllCourses());
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Nothing was selected or course does not exist.");

            alert.showAndWait();
        }
    }

    public void FillCourseInformationFields(MouseEvent mouseEvent) {
        String courseName = AdminManageAllCourses.getSelectionModel().getSelectedItem();
        if(courseName != null){
            Course course = courseIS.getCourseByName(courseName);

            updateFieldCourseTitle.setText(course.getCourseName());
            updateFieldEndDate.setValue(course.getCourseEnd());
            updateFieldStartDate.setValue(course.getCourseStart());

            OriginalCourseTitleToUpdate = updateFieldCourseTitle.getText();
        }
    }
}
