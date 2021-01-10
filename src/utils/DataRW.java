package utils;

import model.CourseIS;

import java.io.*;
import java.util.Scanner;
import java.sql.*;

public class DataRW {

    public static CourseIS loadCourseISFromFile(CourseIS courseIS) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("2labDB.lib"));
            courseIS = (CourseIS) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Failed with class recognition.");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Failed to open file.");
        }
        return courseIS;
    }

    public static void writeCourseISToFile(CourseIS courseIS) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("2labDB.lib"));
            out.writeObject(courseIS);
            out.close();
        } catch (IOException e) {
            System.out.println("Fail.\n");
        }
    }
}
