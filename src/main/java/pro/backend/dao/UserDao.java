package pro.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.backend.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
