package utils;

import model.Course;
import model.CourseIS;
import model.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class UserRW {

//    public static User LoadUserFromFile(CourseIS courseIS) {
//        try {
//            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("2labDB.lib"));
//            courseIS = (CourseIS) ois.readObject();
//            ois.close();
//        } catch (ClassNotFoundException e) {
//            System.out.println("Failed with class recognition.");
//        } catch (IOException e) {
//            //e.printStackTrace();
//            System.out.println("Failed to open file.");
//        }
//        return courseIS;
//    }
//
//    public static void writeCourseISToFile(CourseIS courseIS) {
//        try {
//            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("2labDB.lib"));
//            out.writeObject(courseIS);
//            out.close();
//        } catch (IOException e) {
//            System.out.println("Fail.\n");
//        }
//    }

    public static void WriteUserDataToFile(User user) {
        try {
            String nameOfFile = "Users.lib";
            new File("../Lab3V3/" + nameOfFile);
            BufferedWriter outStream = new BufferedWriter(new FileWriter(nameOfFile, true));

            outStream.write(user.getName() + ";" + user.getSurname() + ";" + user.getBankAccount() + ";" + user.getYear() + ";" + user.isModerator() + ";" + user.getLogin() + ";" + user.getPsw() + ";" + user.getMyEnrolledCourses() + "\n");
            outStream.close();
        } catch (IOException e) {
            System.out.println("Fail.\n");
        }
    }

    public static void RemoveUserDataFromFile(User user) throws IOException {
        ArrayList<User> users = GetAllUsers();

        int index = users.indexOf(users.stream().filter(x -> x.getLogin().equals(user.getLogin())).findFirst().orElse(null));

        users.remove(index);

        Files.newBufferedWriter(Paths.get("../Lab3V3/Users.lib"), StandardOpenOption.TRUNCATE_EXISTING);

        users.forEach(x -> WriteUserDataToFile(x));
    }

    public static ArrayList<User> GetAllUsers(){
        File file = new File("../Lab3V3/Users.lib");
        BufferedReader br = null;
        ArrayList<User> users = new ArrayList<User>();
        try {

            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null){
                users.add(ParseUserFromString(line));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static User ReadUser(String username){
        File file = new File("../Lab3V3/Users.lib");
        BufferedReader br = null;
        ArrayList<User> users = new ArrayList<User>();
        try {

            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null){
                users.add(ParseUserFromString(line));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return users.stream().filter(x -> x.getLogin().equals(username)).findFirst().orElse(null);
    }

    public static User ParseUserFromString(String line){
        String[] information = line.split(";");
        String name = information[0];
        String surname = information[1];
        String username = information[5];
        String password = information[6];
        Integer birthYear = Integer.parseInt(information[3]);
        ArrayList<Course> enrolledCourses = new ArrayList<Course>();
        Boolean isModerator = Boolean.parseBoolean(information[4]);
        ArrayList<Course> managedCourses = new ArrayList<Course>();
        String bankAccount = information[2];

        User user = new User(name
                            ,surname
                            ,username
                            ,password
                            ,birthYear
                            ,enrolledCourses
                            ,isModerator
                            ,managedCourses
                            ,bankAccount);

        return  user;
    }
}
