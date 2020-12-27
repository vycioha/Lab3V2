package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class CourseIS implements Serializable {

    private ArrayList<User> allUsers;
    private ArrayList<Course> allCourses;
    private String sysName;
    private LocalDate dateCreated;

    public CourseIS() {
    }

    public CourseIS(ArrayList<User> allUsers,
                    ArrayList<Course> allCourses,
                    String sysName,
                    LocalDate dateCreated) {
        this.allUsers = allUsers;
        this.allCourses = allCourses;
        this.sysName = sysName;
        this.dateCreated = dateCreated;
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
    }

    public ArrayList<Course> getAllCourses() {
        return allCourses;
    }

    public Course getCourseByName(String courseName){
        return allCourses.stream().filter(x -> x.getCourseName().equals(courseName)).findFirst().orElse(null);
    }

    public void setAllCourses(ArrayList<Course> allCourses) {
        this.allCourses = allCourses;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void RemoveCourseByName(String name){
        for(int i = 0; i < allCourses.size(); i++){
            if(allCourses.get(i).getCourseName().contains(name)){
                allCourses.remove(allCourses.get(i));
                break;
            }
        }
    }

    public void RemoveUserByName(String name){
        for(int i = 0; i < allUsers.size(); i++){
            if(allUsers.get(i).getLogin().contains(name)){
                allUsers.remove(allUsers.get(i));
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "CourseIS{" +
                "Users in the system=" + allUsers +
                ", Courses in the system=" + allCourses +
                ", Name='" + sysName + '\'' +
                ", Created=" + dateCreated +
                '}';
    }
}
