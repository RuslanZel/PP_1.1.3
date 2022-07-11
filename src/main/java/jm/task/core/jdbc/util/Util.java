package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/mydbtest?useSSL=false";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";
    public static Connection connection = getConnection();

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                connection.setAutoCommit(false);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return connection;
    }
}
