package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public UserDaoHibernateImpl() {
    }

    {
        try {
            sessionFactory = Util.getSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUsersTable() {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE SCHEMA IF NOT EXISTS Task_1_1_3_4").executeUpdate();
            session.createSQLQuery("CREATE table IF NOT EXISTS Task_1_1_3_4.Users (id BIGINT not null auto_increment, " +
                    "name varchar(45) not null, lastName varchar(45) not null, age int not null, PRIMARY KEY (id))").executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    @Override
    public void dropUsersTable() {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP table IF EXISTS Task_1_1_3_4.Users").executeUpdate();
            session.createSQLQuery("DROP SCHEMA IF EXISTS Task_1_1_3_4").executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(user);
            session.getTransaction();
            transaction.commit();
            System.out.println("User с именем " + lastName + " " + name + " добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction();
            transaction.commit();
            System.out.println("User с номером id " + id + " удален из базы данных");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            users = session.createQuery("from User").getResultList();
            session.getTransaction();
            transaction.commit();
            System.out.println("Список оставшихся Users в базе данных:");
            for (User user : users) {
                System.out.println(user);
            }
            } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction();
            transaction.commit();
            System.out.println("Таблица очищена");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
