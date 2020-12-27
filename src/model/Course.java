package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


public class Course implements Serializable {

    private String courseName;
    private int numberOfEnrolledUsers;
    private ArrayList<Folder> courseFolders;
    private ArrayList<User> courseModerators;
    private LocalDate courseStart;
    private LocalDate courseEnd;
    private LocalDate courseCreated;
    private LocalDate courseModified;
    private ArrayList<User> usersEnrolled;

    public Course(String courseName,
                  int numberOfEnrolledUsers,
                  ArrayList<CourseFile> courseFiles,
                  ArrayList<Folder> courseFolders,
                  ArrayList<User> courseModerators,
                  LocalDate courseStart,
                  LocalDate courseEnd,
                  LocalDate courseCreated,
                  LocalDate courseModified,
                  ArrayList<User> usersEnrolled) {
        this.courseName = courseName;
        this.numberOfEnrolledUsers = numberOfEnrolledUsers;
        this.courseFolders = courseFolders;
        this.courseModerators = courseModerators;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseCreated = courseCreated;
        this.courseModified = courseModified;
        this.usersEnrolled = usersEnrolled;
    }

    public int getNumberOfEnrolledUsers() {
        return numberOfEnrolledUsers;
    }

    public void setNumberOfEnrolledUsers(int numberOfEnrolledUsers) {
        this.numberOfEnrolledUsers = numberOfEnrolledUsers;
    }

    public ArrayList<Folder> getCourseFolders() {
        return courseFolders;
    }

    public void setCourseFolders(ArrayList<Folder> courseFolders) {
        this.courseFolders = courseFolders;
    }

    public ArrayList<User> getCourseModerators() {
        return courseModerators;
    }

    public void setCourseModerators(ArrayList<User> courseModerators) {
        this.courseModerators = courseModerators;
    }

    public LocalDate getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(LocalDate courseStart) {
        this.courseStart = courseStart;
    }

    public LocalDate getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(LocalDate courseEnd) {
        this.courseEnd = courseEnd;
    }

    public LocalDate getCourseCreated() {
        return courseCreated;
    }

    public void setCourseCreated(LocalDate courseCreated) {
        this.courseCreated = courseCreated;
    }

    public LocalDate getCourseModified() {
        return courseModified;
    }

    public void setCourseModified(LocalDate courseModified) {
        this.courseModified = courseModified;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void UpdateStartDate(LocalDate date){
        courseStart = date;
    }

    public void UpdateEndDate(LocalDate date){
        courseEnd = date;
    }

    public void UpdateTitle(String newTitle){
        courseName = newTitle;
    }

    public void UpdateDateModified(){
        courseModified = LocalDate.now();
    }

    public void EnrollUserToACourse(User user){
        boolean isEnrolled = false;

        for(int i = 0; i < usersEnrolled.size(); i++){
            if(usersEnrolled.get(i).getLogin().equals(user.getLogin())){
                isEnrolled = true;
            }
        }

        if(!isEnrolled){
            usersEnrolled.add(user);
            System.out.println("User was enrolled to the specified course");
        }
        else{
            System.out.println("Same user cannot be enrolled more than once");
        }

    }

    public void RemoveUserFromACourse(String username){
        User user = usersEnrolled.stream().filter(x -> x.getLogin().equals(username)).findFirst().orElse(null);
        if(user != null){
            usersEnrolled.remove(user);
        }
        else{
            System.out.println("This user does not exist or isn't enrolled.");
        }
    }

    public void CreateFolder(String nameOfFolder){
        if(!courseFolders.contains(nameOfFolder)){
            courseFolders.add(new Folder(nameOfFolder, new Date(), new Date(), new ArrayList<CourseFile>()));
        }
        else
        {
            System.out.println("Folder with that name already exists.");
        }
    }

    public void DeleteFolder(String folderName){
        if(courseFolders.stream().filter(x -> x.getFolderName().equals(folderName)).findFirst().orElse(null) != null){
            int folderIndex = courseFolders.indexOf(courseFolders.stream().filter(x -> x.getFolderName().equals(folderName)).findFirst().orElse(null));
            courseFolders.remove(folderIndex);
        }
        else{
            System.out.println("Folder with that name does not exist");
        }
    }

    public Folder GetFolderByName(String folderName){
        return courseFolders.stream().filter(x -> x.getFolderName().equals(folderName)).findFirst().orElse(null);
    }

    public ArrayList<User> GetEnrolledUsers(){
        return usersEnrolled;
    }

    @Override
    public String toString() {
        return "Course " +
                "Title - " + courseName +
                ", Starting date - " + courseStart +
                ", Ending date - " + courseEnd +
                ' ';
    }
}
