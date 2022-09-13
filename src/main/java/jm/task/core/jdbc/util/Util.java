package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Kata_PP";
    private static Connection connection;
    public static Connection getConnection () {
            try {
                    Driver driver = new com.mysql.cj.jdbc.Driver();
                    DriverManager.registerDriver(driver);
                    connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return connection;
    }
}
