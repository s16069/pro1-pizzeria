package pro.backend.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pro.backend.model.User;
import pro.backend.model.UserRole;
import pro.backend.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class UsersInit {

    @Autowired
    private UserService userService;

    @PostConstruct
    @Transactional
    private void postConstruct() {
        if (userService.findAll().size() == 0) {
            addUsers();
        }
    }

    private void addUsers() {
        User boss = new User();
        boss.setId(1L);
        boss.setUserRole(UserRole.ROLE_BOSS);
        boss.setLogin("boss");
        boss.setPass("asdf");
        boss.setName("Boss");
        boss.setSurname("Boss");
        boss.setTel("123456789");
        boss.setEmail("a@a.a");
        userService.addUser(boss);

        User employee = new User();
        employee.setId(2L);
        employee.setUserRole(UserRole.ROLE_EMPLOYEE);
        employee.setLogin("employee");
        employee.setPass("asdf");
        employee.setName("Employee");
        employee.setSurname("Employee");
        employee.setTel("123456789");
        employee.setEmail("a@a.a");
        userService.addUser(employee);

        User client = new User();
        client.setId(3L);
        client.setUserRole(UserRole.ROLE_CLIENT);
        client.setLogin("client");
        client.setPass("asdf");
        client.setName("Client");
        client.setSurname("Client");
        client.setTel("123456789");
        client.setEmail("a@a.a");
        userService.addUser(client);
    }
}