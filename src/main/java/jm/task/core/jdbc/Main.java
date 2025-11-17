package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Fedor", "Krasnov", (byte) 32);
        userService.saveUser("Igor", "Seleznev", (byte) 23);
        userService.saveUser("Alex", "Petrov", (byte) 50);
        userService.saveUser("Dmitriy", "Vasin", (byte) 45);
        userService.removeUserById(2);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
     }
}
