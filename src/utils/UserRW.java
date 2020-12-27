package utils;

import model.Course;
import model.User;

import java.io.*;
import java.util.ArrayList;

public class UserRW {

    public static void WriteUserDataToFile(User user) {
        try {
            String nameOfFile = "Users.lib";
            new File("C:/Users/vpi/IdeaProjects/guicourse/" + nameOfFile);

            BufferedWriter outStream = new BufferedWriter(new FileWriter(nameOfFile, true));

            outStream.write("\n" + user.getName() + ";" + user.getSurname() + ";" + user.getBankAccount() + ";" + user.getYear() + ";" + user.isModerator() + ";" + user.getLogin() + ";" + user.getPsw() + ";" + user.getMyEnrolledCourses());
            outStream.close();
        } catch (IOException e) {
            System.out.println("Fail.\n");
        }
    }

    public static ArrayList<User> GetAllUsers(){
        File file = new File("C:/Users/vpi/IdeaProjects/CourseIS/Users.lib");
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
        File file = new File("C:/Users/vpi/IdeaProjects/guicourse/Users.lib");
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
