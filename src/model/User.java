package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    String name;
    String surname;
    String login;
    String psw;
    int year;
    ArrayList<Course> myEnrolledCourses;
    boolean isModerator = false;
    ArrayList<Course> myManagedCourses;
    String bankAccount;

    public User(String name
            ,String surname
            ,String login
            ,String psw
            ,int year
            ,ArrayList<Course> myEnrolledCourses
            ,boolean isModerator
            ,ArrayList<Course> myManagedCourses
            ,String bankAccount) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.psw = psw;
        this.year = year;
        this.myEnrolledCourses = myEnrolledCourses;
        this.isModerator = isModerator;
        this.myManagedCourses = myManagedCourses;
        this.bankAccount = bankAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<Course> getMyEnrolledCourses() {
        return myEnrolledCourses;
    }

    public void setMyEnrolledCourses(ArrayList<Course> myEnrolledCourses) {
        this.myEnrolledCourses = myEnrolledCourses;
    }

    public boolean isModerator() {
        return isModerator;
    }

    public void setModerator(boolean moderator) {
        isModerator = moderator;
    }

    public ArrayList<Course> getMyManagedCourses() {
        return myManagedCourses;
    }

    public void setMyManagedCourses(ArrayList<Course> myManagedCourses) {
        this.myManagedCourses = myManagedCourses;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

//    @Override
//    public String toString() {
//        return "Hello" + name + " " + surname + "(" + login + ")";
//    }
}
