package lesson23.jdbc_recap.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {

    public static Connection dbCon = getDatabaseConnection();

    private static final String DB_URL = "jdbc:mysql://localhost:3306/advanced_database";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";

    private static Connection getDatabaseConnection() {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Database connection failed");;
        }
        return connection;
    }

}
