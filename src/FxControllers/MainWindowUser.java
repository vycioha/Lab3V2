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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Course;
import model.CourseIS;
import model.User;
import utils.DataRW;
import utils.UserRW;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static utils.DataRW.writeCourseISToFile;

//implements Initializable -> Inteface, kuris pradiniam fxml formos langui priskirs reikšmes.
public class MainWindowUser implements Initializable {

    public Button logOutBtn;
    public Text profName;
    public Text profSurname;
    public Text profUsername;
    public Text profYear;
    public Text profBank;
    public Button profUpdate;

    public TextField profUserInput;
    public TextField profSurnameInput;
    public TextField profBirthYearInput;
    public TextField profNameInput;
    public TextField profBankAccInput;

    private ObservableList<Course> courseObservableListIamEnrolled = FXCollections.observableArrayList();
    private ObservableList<Course> courseObservableListAll = FXCollections.observableArrayList();

    public ListView<String> CoursesIamEnrolledIn;
    public Text IamEnrolledCourseTitle;
    public Text IamEnrolledCourseStartDate;
    public Text IamEnrolledCourseEndDate;

    public ListView<String> AllCourses;
    public Text AllCoursesCourseTitle;
    public Text AllCoursesCourseStartDate;
    public Text AllCoursesCourseEndDate;

    private User user;
    public Text loggedAs;
    public Text setProfileInformation = null;

    public void setUser(User user){
        this.user = user;
    }

    public void setLoggedAs(String username){
        loggedAs.setText("logged as: " + username);
    }

    private CourseIS courseIS = new CourseIS();
    private ArrayList<User> allUsers = new ArrayList<User>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseIS = DataRW.loadCourseISFromFile(courseIS);
        allUsers = UserRW.GetAllUsers();

        ArrayList<String> courseArrayListAll = new ArrayList<String>();

        courseObservableListAll.addAll(courseIS.getAllCourses());

        courseIS.getAllCourses().forEach(x -> courseArrayListAll.add(x.getCourseName()));

        if(courseArrayListAll.size() > 0){
            ObservableList<String> users = FXCollections.observableArrayList(courseArrayListAll);

            AllCourses.getItems().addAll(users);
        }
        else{
            AllCourses = new ListView<>();
        }
    }

    public void LogOut() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/LoginPage.fxml"));

        Parent root = loader.load();

        Stage stage = (Stage)logOutBtn.getScene().getWindow();
        stage.setTitle("Login page");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void UpdateProfileInformation(ActionEvent actionEvent) throws IOException {
        if(profUserInput != null &&
            profSurnameInput != null &&
            profNameInput != null &&
            profBirthYearInput != null &&
            profBankAccInput != null){

            courseIS.getAllUsers().remove(user);
            UserRW.RemoveUserDataFromFile(user);

            user.setLogin(profUserInput.getText());
            user.setName(profNameInput.getText());
            user.setSurname(profSurnameInput.getText());
                                            //teksto validacija - turi buti irasytas numeris - NEPADARYTA.
            user.setYear(Integer.parseInt(profBirthYearInput.getText()));
            user.setBankAccount(profBankAccInput.getText());

            courseIS.getAllUsers().add(user);
            DataRW.writeCourseISToFile(courseIS);
            UserRW.WriteUserDataToFile(user);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Not every field is filled");
            alert.showAndWait();
        }
    }

    public void LoadUserInformation(ActionEvent actionEvent) {
        profUserInput.setText(user.getLogin());
        profSurnameInput.setText(user.getSurname());
        profBirthYearInput.setText(String.valueOf(user.getYear()));
        profNameInput.setText(user.getName());
        profBankAccInput.setText(user.getBankAccount());
    }

    public void GetCourseInfoFromEnrolled(MouseEvent mouseEvent) {
        String courseName = CoursesIamEnrolledIn.getSelectionModel().getSelectedItem();

        if(courseName != null){

            Course course = courseIS.getCourseByName(courseName);

            IamEnrolledCourseTitle.setText(course.getCourseName());
            IamEnrolledCourseStartDate.setText(course.getCourseStart().toString());
            IamEnrolledCourseEndDate.setText(course.getCourseEnd().toString());
        }
    }

    public void GetCourseInfoFromAllCourses(MouseEvent mouseEvent) {
        String courseName = AllCourses.getSelectionModel().getSelectedItem();

        if(courseName != null){

            Course course = courseIS.getCourseByName(courseName);

            AllCoursesCourseTitle.setText(course.getCourseName());
            AllCoursesCourseStartDate.setText(course.getCourseStart().toString());
            AllCoursesCourseEndDate.setText(course.getCourseEnd().toString());
        }
    }

    public void GetCoursesIamEnrolledin(ActionEvent actionEvent) {
        ArrayList<String> courseArrayListIamEnrolled = new ArrayList<String>();

        CoursesIamEnrolledIn.getItems().clear();

        for(int i = 0; i < courseIS.getAllCourses().size(); i++){
            Course course = courseIS.getCourseByName(AllCourses.getItems().get(i));

            ArrayList<User> enrolledUsers = course.GetEnrolledUsers();

            for(int j = 0; j < enrolledUsers.size(); j++){
                if(enrolledUsers.get(j).getLogin().equals(user.getLogin())){
                    courseObservableListIamEnrolled.add(course);
                    courseArrayListIamEnrolled.add(course.getCourseName());
                    break;
                }
            }
        }

        if(courseArrayListIamEnrolled.size() > 0){
            ObservableList<String> courses = FXCollections.observableArrayList(courseArrayListIamEnrolled);

            CoursesIamEnrolledIn.getItems().addAll(courses);
        }
        else{
            CoursesIamEnrolledIn = new ListView<>();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("You aren't enrolled in any course");
            alert.showAndWait();
        }
    }
}
