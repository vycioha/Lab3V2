package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class databaseUtil {

    public static Connection connectToDb() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost;database=LABFOUR;integratedSecurity=true;";

        //Jei reikės prisijungimo su slapyvardziu ir slaptazodziu
//        String username = "";
//        String password = "";
//
//        Connection con = DriverManager.getConnection(connectionUrl, username, password);

        Connection con = DriverManager.getConnection(connectionUrl);

        return con;
    }

    public static void disconnectFromDb(Connection connection, Statement statement){

    }

}
