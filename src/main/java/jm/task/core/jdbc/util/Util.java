package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static volatile Connection connection;

    private Util() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static Connection getConnection() throws Exception {
        if (connection == null) {
            synchronized (Util.class) {
                if (connection == null) {
                    new Util();
                }
            }
        }
        return connection;
    }
}

