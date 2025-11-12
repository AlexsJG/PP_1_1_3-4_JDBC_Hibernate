package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private String hibernate;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static volatile Connection connection;
    private static volatile SessionFactory sessionFactory;

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
    private Util(String hibernate) {
        this.hibernate = hibernate;
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.addAnnotatedClass(User.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (Util.class) {
                if (sessionFactory == null) {
                    new Util("hibernate");
                }
            }
        }
        return sessionFactory;
    }
}

