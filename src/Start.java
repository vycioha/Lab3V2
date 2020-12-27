import model.*;
import utils.DataRW;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import static utils.DataRW.writeCourseISToFile;
import static utils.UserRW.ReadUser;
import static utils.UserRW.WriteUserDataToFile;

public class Start {

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        String cmdMain = "";

        while(!cmdMain.equals("quit")) {

            User loggedUser = null;
            System.out.println("Choose an action: \n" +
                    "\t log - login as an existing user.\n" +
                    "\t reg - register new user. \n" +
                    "\t quit - exit the application.\n");

            cmdMain = scanner.next();

            //login
            switch (cmdMain) {
                case "log":
                case "reg":
                    loggedUser = LoginManager(scanner, cmdMain);

                    program(scanner, loggedUser);
                    break;
                case "quit":
                    System.out.println("Goodbye");
                    break;
                default:
                    System.out.println("Wrong command");
            }
        }
    }

    public static void program(Scanner scanner, User loggedUser) {

        CourseIS courseIS =
                new CourseIS(
                        new ArrayList<User>(), new ArrayList<Course>(), "Nood.le", LocalDate.now());

        courseIS = DataRW.loadCourseISFromFile(courseIS);

            String cmd = "";

            //admin while
            if(loggedUser.isModerator()){
                while (!cmd.equals("logout")) {

                    System.out.println("Choose an action: \n" +
                            "\t course - manage courses.\n" +
                            "\t user - manage users. \n" +
                            "\t save - save to file.\n" +
                            "\t logout - back to login screen.\n");
                    cmd = scanner.next(); //course

                    switch (cmd) {
                        case "course":
                            manageCourse(scanner, courseIS, loggedUser);
                            break;
                        case "user":
                            manageUsers(scanner, courseIS, loggedUser);
                            break;
                        case "save":
                            writeCourseISToFile(courseIS);
                            break;
                        case "logout":
//                            writeCourseISToFile(scanner, courseIS);
                            System.out.print("Back to login page. \n");
                            break;
                        default:
                            System.out.print("Wrong Command. \n");

                    }
                }
            }
            else{
                //user while
                while (!cmd.equals("logout")) {

                    System.out.println("Choose an area: \n" +
                            "\t course - manage courses.\n" +
                            "\t logout - back to login screen ");
                    cmd = scanner.next(); //course

                    switch (cmd) {
                        case "course":
                            manageCourse(scanner, courseIS, loggedUser);
                            break;
                        case "logout":
//                            writeCourseISToFile(scanner, courseIS);
                            System.out.print("Back to login page. \n");
                            break;
                        default:
                            System.out.print("Wrong Command. \n");

                    }
                }
            }
    }

    private static User LoginManager(Scanner scanner, String command){

        User loggedUser = null;

        while(!command.equals("reg") && !command.equals("log") || loggedUser == null){

            switch (command){
                case "reg":
                    loggedUser = Register(scanner);
                    break;
                case "log":
                    loggedUser = Login(scanner);
                    if(loggedUser == null){
                        System.out.println("Wrong username or password");
                    }
                break;
                default:
                    System.out.println("Wrong command or credentials");
            }
        }

        return loggedUser;
    }

    private static void manageUsers(Scanner scanner, CourseIS courseIS, User user) {
        String manageUsersCmd = "";

        if(user.isModerator()) {
            while (!manageUsersCmd.equals("quit")) {
                System.out.print("Choose an action:\n"
                        + "\t del - delete a user.\n"
                        + "\t prt - print all users. \n"
                        + "\t quit - back to main menu.\n");

                manageUsersCmd = scanner.next();

                    switch (manageUsersCmd) {
                        case "del":

                            System.out.println("Enter course name");
                            String courseName = scanner.next();
                            Course course = courseIS.getAllCourses().stream().filter(x -> x.getCourseName().equals(courseName)).findFirst().orElse(null);

                            if(course != null) {
                                System.out.println("Enter username of a user you'd like to remove from the course");
                                String usernameDel = scanner.next();
                                course.RemoveUserFromACourse(usernameDel);

                                courseIS.RemoveCourseByName(courseName);
                                courseIS.getAllCourses().add(course);

                                writeCourseISToFile(courseIS);
                            }else {
                                System.out.println("Course does not exist");
                            }
                            break;
                        case "prt":

                            System.out.println("Enter course name");
                            String courseNamePrt = scanner.next();
                            Course coursePrt = courseIS.getAllCourses().stream().filter(x -> x.getCourseName().equals(courseNamePrt)).findFirst().orElse(null);

                            if(coursePrt != null) {
                                for(int i = 0; i < coursePrt.GetEnrolledUsers().size(); i++){
                                    User userToDisplay = coursePrt.GetEnrolledUsers().get(i);
                                    System.out.println(i + ". " + userToDisplay.getName() + " " + userToDisplay.getSurname());
                                }
                            }else {
                                System.out.println("Course does not exist");
                            }
                            break;
                        case "quit":
                            System.out.print("Goodbye. \n");
                            break;
                        default:
                            System.out.print("Wrong Command. \n");
                    }
            }
        }
        else{
            while (!manageUsersCmd.equals("quit")) {
                System.out.print("Choose an action:\n"
                        + "\t upd - update my profile information.\n"
                        + "\t prt - print my profile information. \n"
                        + "\t quit - back to main menu.\n");

                manageUsersCmd = scanner.next();

                switch (manageUsersCmd) {
                    case "upd":

                        String feature = "";

                        User userUpdated = user;

                        while(!feature.equals("quit")){

                            System.out.print("Choose an action:\n"
                                    + "\t usr  - username.\n"
                                    + "\t pass - password. \n"
                                    + "\t bank - bank account. \n"
                                    + "\t quit - back to main menu.\n");

                            System.out.println("What would you like to update?");
                            feature = scanner.next();

                            switch(feature) {
                                case "usr":
                                    System.out.println("Enter new username");
                                    String newUsername = scanner.next();
                                    userUpdated.setLogin(newUsername);
                                    break;
                                case "pass":
                                    System.out.println("Enter new password");
                                    String newPassword = scanner.next();
                                    userUpdated.setPsw(newPassword);
                                    break;
                                case "bank":
                                    System.out.println("Enter new bank account");
                                    String newBankAccount = scanner.next();
                                    userUpdated.setBankAccount(newBankAccount);
                                    break;
                                case "quit":
                                    System.out.print("Goodbye. \n");
                                    break;
                                default:
                                    System.out.println("Wrong command entered");
                            }
                        }

                        courseIS.RemoveUserByName(user.getLogin());
                        courseIS.getAllUsers().add(userUpdated);

                        writeCourseISToFile(courseIS);
                        break;
                    case "prt":
                        break;
                    case "quit":
                        System.out.print("Goodbye. \n");
                        break;
                    default:
                        System.out.print("Wrong Command. \n");
                }
            }
        }
    }

    private static void manageCourse(Scanner scanner, CourseIS courseIS, User user) {

        String courseCmd = "";

        if(user.isModerator()){
            while (!courseCmd.equals("quit")) {
                System.out.print("Choose an action:\n"
                        + "\t add - add a course to system.\n"
                        + "\t upd - update a course.\n"
                        + "\t del - delete a course.\n"
                        + "\t prt - print all courses. \n"
                        + "\t enr - enroll to course.\n"
                        + "\t fol - manage folders.\n"
                        + "\t quit - back to main menu.\n");

                courseCmd = scanner.next();

                switch (courseCmd) {
                    case "add":
                        //paprasyti user inputo
                        System.out.println("Enter course info {title};{start date - YYYY-MM-DD};{end date - YYYY-MM-DD}");
                        String[] values = scanner.next().split(";");
                        ArrayList<User> users = new ArrayList<User>();
                        users.add(user);
                        LocalDate courseStart = LocalDate.now();
                        LocalDate courseEnd = LocalDate.now();

                        if(values.length != 3){
                            System.out.println("Not all parameters specified.");
                            break;
                        }

                        try {
                            courseStart = LocalDate.parse(values[1]);
                        } catch (Exception e) {
                            System.out.println("start date was incorrect");
                            break;
                        }

                        try {
                            courseEnd = LocalDate.parse(values[2]);
                        } catch (Exception e) {
                            System.out.println("end date was incorrect");
                            break;
                        }

                        //System.out.println(values[0] + "    " + values[1] + "    " + values[2]);
                        courseIS
                                .getAllCourses()
                                .add(
                                        new Course(
                                                values[0],
                                                0,
                                                new ArrayList<CourseFile>(),
                                                new ArrayList<Folder>(),
                                                users,
                                                courseStart,
                                                courseEnd,
                                                LocalDate.now(),
                                                LocalDate.now(),
                                                new ArrayList<User>()));

                        System.out.println("course " + values[0] + " was added");

                        break;
                    case "upd":
                        System.out.println("Enter course name that you'd like to update");
                        String courseName = scanner.next();

                        if(courseIS.getAllCourses().stream().filter(x -> x.getCourseName().equals(courseName)).findFirst().orElse(null) != null){

                            String field = "";

                            while (!field.equals("quit")) {
                                System.out.println("What would you like to update?");
                                System.out.print("Choose an action:\n"
                                        + "\t title - change the title of the course.\n"
                                        + "\t dateStart - change the start date of the course.\n"
                                        + "\t dateEnd - change the end date of the course.\n"
                                        + "\t quit - go back to previous menu.\n");
                                field = scanner.next();

                                switch (field) {
                                    case "title":
                                        System.out.println("Enter new title");
                                        String newTitle = scanner.next();

                                        Course courseZ = courseIS.getAllCourses().stream().filter(x -> x.getCourseName().equals(courseName)).findFirst().orElse(null);
                                        courseZ.UpdateTitle(newTitle);

                                        courseZ.UpdateDateModified();
                                        break;
                                    case "dateStart":
                                        System.out.println("Enter years");
                                        int yearX = Integer.parseInt(scanner.next());
                                        System.out.println("Enter months");
                                        int monthX = Integer.parseInt(scanner.next());
                                        System.out.println("Enter days");
                                        int dayX = Integer.parseInt(scanner.next());

                                        LocalDate ldX = LocalDate.of(yearX, monthX, dayX);

                                        Course courseX = courseIS.getAllCourses().stream().filter(x -> x.getCourseName().equals(courseName)).findFirst().orElse(null);
                                        courseX.UpdateStartDate(ldX);

                                        courseX.UpdateDateModified();
                                        break;
                                    case "dateEnd":
                                        System.out.println("Enter years");
                                        int yearY = Integer.parseInt(scanner.next());
                                        System.out.println("Enter months");
                                        int monthY = Integer.parseInt(scanner.next());
                                        System.out.println("Enter days");
                                        int dayY = Integer.parseInt(scanner.next());

                                        LocalDate ldY = LocalDate.of(yearY, monthY, dayY);

                                        Course courseY = courseIS.getAllCourses().stream().filter(x -> x.getCourseName().equals(courseName)).findFirst().orElse(null);
                                        courseY.UpdateEndDate(ldY);

                                        courseY.UpdateDateModified();
                                        break;
                                    case "quit":
                                        break;
                                    default:
                                        System.out.print("Wrong Command. \n");
                                }
                            }
                        }else{
                            System.out.println("404 Course not found");
                        }

                        //sursati pagal pavadinima ir paklausti kokius duomenis atnaujinti
                        break;
                    case "del":
                        //sursati pagal pavadinima ir trinti
                        System.out.println("Enter course name that you'd like to delete");
                        String courseNameDel = scanner.next();

                        if(courseIS.getAllCourses().stream().filter(x -> x.getCourseName().equals(courseNameDel)).findFirst().orElse(null) != null) {
                            int indexOfCourse = courseIS.getAllCourses().indexOf(courseIS.getAllCourses().stream().filter(x -> x.getCourseName().equals(courseNameDel)).findFirst().orElse(null));
                            courseIS.getAllCourses().remove(indexOfCourse);

                            System.out.println("course " + courseNameDel + " was deleted");
                        }else{
                            System.out.println("404 Course not found");
                        }

                        break;
                    case "prt":
                        courseIS.getAllCourses().forEach(course -> System.out.println(course.toString() + " number of enrolled users: " + course.GetEnrolledUsers().size()));
                        break;
                    case "enr":
                        //surasati naudotoja pagal username ir prideti ji i pasirinkta sarasa
                        System.out.println("Enter username of a person you'd like to enrol into a course");
                        String usernameOfAPerson = scanner.next();

                        User foundUser = FindUser(usernameOfAPerson);

                        if(foundUser == null){
                            System.out.println("User does not exist");
                            break;
                        }

                        System.out.println("Enter course name you'd like to enroll the user to");
                        String courseNameForEnrollment = scanner.next();

                        if(courseIS.getCourseByName(courseNameForEnrollment) == null) {
                            System.out.println("Course does not exist");
                            break;
                        }

                        Course course = courseIS.getCourseByName(courseNameForEnrollment);
                        course.EnrollUserToACourse(foundUser);

                        int indexOfCourse = courseIS.getAllCourses().indexOf(courseIS.getAllCourses().stream().filter(x -> x.getCourseName().equals(courseNameForEnrollment)).findFirst().orElse(null));
                        courseIS.getAllCourses().remove(indexOfCourse);

                        courseIS.getAllCourses().add(course);
                        break;
                    case "fol":
                        //persokti i folderio meniu pagal nurodyta kursa
                        System.out.println("Write the name of a course which folders you'd like to manage.");
                        String courseNameFol = scanner.next();

                        if(courseIS.getCourseByName(courseNameFol) == null) {
                            System.out.println("Course does not exist");
                            break;
                        }

                        manageCourseFolders(scanner, courseIS, courseNameFol);
                        break;
                    case "quit":
                        System.out.print("Back to main menu. \n");
                        break;
                    default:
                        System.out.print("Wrong Command. \n");
                }
            }
        }
        else{
            while (!courseCmd.equals("quit")) {
                System.out.print("Choose an action:\n"
                        + "\t prt - print all courses. \n"
                        + "\t prtme - print all courses I am enrolled in \n"
                        + "\t quit - back to main menu.\n");

                courseCmd = scanner.next();

                switch (courseCmd) {
                    case "prt":
                        courseIS.getAllCourses().forEach(course -> System.out.println(course.toString() + " number of enrolled users: " + course.GetEnrolledUsers().size()));
                        break;
                    case "prtme":
                        ArrayList<Course> allCourses = courseIS.getAllCourses();
                        for(int i = 0; i < allCourses.size(); i++){
                            ArrayList<User> users = allCourses.get(i).GetEnrolledUsers();
                            boolean iAmEnrolled = false;
                            for(int j = 0; j < users.size(); j++){
                                if(users.get(i).getName().equals(user.getName())){
                                    iAmEnrolled = true;
                                    break;
                                }
                            }
                            if(iAmEnrolled){
                                System.out.println(allCourses.get(i).toString() + " number of enrolled users: " + allCourses.get(i).GetEnrolledUsers().size());
                            }
                        }
                        break;
                    case "quit":
                        System.out.print("Back to main menu. \n");
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private static void manageCourseFolders(Scanner scanner, CourseIS courseIS, String courseName) {

        Course course = courseIS.getCourseByName(courseName);

        String cmd = "";

        while (!cmd.equals("quit")) {

            System.out.print("Choose an action:\n"
                    + "\t add - add a folder to the course.\n"
                    + "\t del - delete a folder.\n"
                    + "\t prt - display folders. \n"
                    + "\t fil - manage files.\n"
                    + "\t quit - go back to previous menu. \n");

            cmd = scanner.next();

            switch (cmd) {
                case "add":
                    System.out.println("Enter the name of the folder");
                    String folderNameAdd = scanner.next();
                    course.CreateFolder(folderNameAdd);
                    break;
                case "del":
                    System.out.println("Enter the name of the folder");
                    String folderNameDel = scanner.next();
                    course.DeleteFolder(folderNameDel);
                    break;
                case "prt":
                    ArrayList<Folder> folders = course.getCourseFolders();

                    for(int i = 0; i < folders.size(); i++){
                        System.out.println(folders.get(i).getFolderName() + " number of files inside: " + folders.get(i).getFolderFiles().size());
                    }
                    break;
                case "fil":
                    //persokti i failo meniu pagal nurodyta kursa. kursa nurodome viduje
                    Course courseFil = courseIS.getCourseByName(courseName);

                    System.out.println("Write the name of a folder which files you'd like to manage.");
                    String folderName = scanner.next();

                    if(courseFil.GetFolderByName(folderName) == null) {
                        System.out.println("Folder does not exist");
                        break;
                    }

                    Folder folder = courseFil.GetFolderByName(folderName);

                    manageCourseFiles(scanner, courseIS, folder);
                    break;
                case "quit":
                    System.out.print("Back to previous menu. \n");
                    break;
                default:
                    System.out.println("Wrong command");
            }
        }
    }

    private static void manageCourseFiles(Scanner scanner, CourseIS courseIS, Folder folder) {

        String cmd = "";

        while (!cmd.equals("quit")) {

            System.out.print("Choose an action:\n"
                    + "\t add - add a file to the course.\n"
                    + "\t del - delete a file.\n"
                    + "\t prt - display files. \n"
                    + "\t quit - go back to previous menu. \n");

            cmd = scanner.next();

            switch (cmd) {
                case "add":
                    System.out.println("Enter the name of the file");
                    String fileNameAdd = scanner.next();
                    folder.CreateFile(fileNameAdd);
                    break;
                case "del":
                    System.out.println("Enter the name of the file");
                    String fileNameDel = scanner.next();
                    folder.DeleteFile(fileNameDel);
                    break;
                case "prt":
                    ArrayList<CourseFile> files = folder.getFolderFiles();

                    for(int i = 0; i < files.size(); i++){
                        System.out.println(files.get(i).getFilename());
                    }
                    break;
                case "quit":
                    System.out.print("Back to previous menu. \n");
                    break;
                default:
                    System.out.println("Wrong command");
            }
        }
    }

    private static User Register(Scanner scanner){

        System.out.println("Enter your first name");
        String firstName = scanner.next();
        System.out.println("Enter your surname");
        String surname = scanner.next();
        System.out.println("Enter your username");
        String username = scanner.next();
        System.out.println("Enter your password");
        String password = scanner.next();
        System.out.println("Enter your birth year");
        Integer year = Integer.parseInt(scanner.next());
        System.out.println("Enter your bank account number");
        String bankAccount = scanner.next();

        User user = new User(firstName,
                             surname,
                             username,
                             password,
                             year,
                             new ArrayList<Course>(),
                             false,
                             new ArrayList<Course>(),
                             bankAccount);

        WriteUserDataToFile(user);

        return user;
    }

    private static User Login(Scanner scanner) {
        System.out.println("Enter username");
        String username = scanner.next();
        System.out.println("Enter password");
        String password = scanner.next();

        //check password
        User user = ReadUser(username);

        if(user.getPsw().equals(password)){
            return user;
        }
        else{
            return null;
        }
    }

    private static User FindUser(String username){

        return ReadUser(username);
    }
}


