package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        System.out.println("done епта");

        userService.saveUser("Марина", "Андреева", (byte) 34);
        userService.saveUser("Иван", "Иванов", (byte) 44);
        userService.saveUser("Петр", "Петров", (byte) 54);
        userService.saveUser("Залупа", "Блядская", (byte) 64);

        List<User> users = userService.getAllUsers();
        System.out.println(users);

        userService.cleanUsersTable();
        System.out.println("Таблица очищена");

        userService.dropUsersTable();
        System.out.println("Таблица удалена");
    }
}
