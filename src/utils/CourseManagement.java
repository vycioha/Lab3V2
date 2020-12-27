package utils;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class CourseManagement {

    public static void manageCourses(Scanner scanner, CourseIS courseIS, User user) {

        String courseCmd = "";

        while (!courseCmd.equals("quit")) {
            System.out.println("Choose an action:\n"
                    + "\t add - add a course to system.\n"
                    + "\t upd - update a course.\n"
                    + "\t del - delete a course.\n"
                    + "\t prt - print all courses. \n"
                    + "\t enroll - enroll to course.\n"
                    + "\t folder - manage folders.\n"
                    + "\t file - manage files.\n");

            courseCmd = scanner.next();

            switch (courseCmd) {
                case "add":
                    addCourse(scanner, courseIS, user);
                    //paprasyti user inputo
                    System.out.println("Enter course info {title};{start date};{end date}");
                    String[] values = scanner.next().split(";");
                    ArrayList<User> users = new ArrayList<User>();
                    users.add(user);

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
                                            LocalDate.now(),
                                            LocalDate.now(),
                                            LocalDate.now(),
                                            LocalDate.now(),
                                            new ArrayList<User>()));
                    break;
                case "upd":
                    //sursati pagal pavadinima ir paklausti kokius duomenis atnaujinti
                    break;
                case "del":
                    //sursati pagal pavadinima ir trinti
                    break;
                case "prt":
                    //sursati pagal pavadinima ir trinti
                    courseIS.getAllCourses().forEach(course -> System.out.println(course.toString()));
                    break;
                case "enroll":
                    //sursati pagal pavadinima ir prideti i pasirinkta sarasa
                    break;
                case "folder":
                    //persokti i folderi meniu pagal nurodyta kursa
                    manageCourseFolders(scanner, courseIS);
                    break;
                case "files":
                    //persokti i failo meniu pagal nurodyta kursa. kursa nurodome viduje
                    manageCourseFiles(scanner, courseIS);
                    break;
                case "quit":
                    System.out.println("Back to main menu. \n");
                    break;
                default:
                    System.out.println("Wrong Command. \n");

            }

        }

    }

    private static void addCourse(Scanner scanner, CourseIS courseIS, User user) {
        //paprasyti user inputo
        System.out.println("Enter course info {title};{start date};{end date}");
        String[] values = scanner.next().split(";");
        ArrayList<User> users = new ArrayList<User>();
        users.add(user);

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
                                LocalDate.now(),
                                LocalDate.now(),
                                LocalDate.now(),
                                LocalDate.now(),
                                new ArrayList<User>()));
    }

    private static void manageCourseFolders(Scanner scanner, CourseIS courseIS) {
    }

    private static void manageCourseFiles(Scanner scanner, CourseIS courseIS) {
    }


}
