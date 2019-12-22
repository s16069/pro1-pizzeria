package pro.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import pro.backend.config.security.PizzaUserDetails;
import pro.backend.dao.UserDao;
import pro.backend.model.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pro.backend.model.UserRole;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
	Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User findByLogin(String login) {
		return userDao.findByLogin(login);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByLogin(username.toLowerCase());
		if (user == null) {
			throw new UsernameNotFoundException("Username Not Found: " + username);
		}

		return new PizzaUserDetails(user);
	}

	@Transactional
	public User register(User user) {
		logger.info("Adding user");

		user.setPass(passwordEncoder.encode(user.getPass()));
		user.setUserRole(UserRole.ROLE_CLIENT);

		return userDao.save(user);
	}

	@Transactional
	public User addUser(User user) {
		logger.info("Adding user");

		user.setPass(passwordEncoder.encode(user.getPass()));

		return userDao.save(user);
	}

	public List<User> findAll() {
		return userDao.findAll();
	}

	public Optional<User> findById(long userId) {
		return userDao.findById(userId);
	}
}
