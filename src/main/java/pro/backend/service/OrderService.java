package pro.backend.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pro.backend.dao.PizzaTypeDao;
import pro.backend.dao.OrderDao;
import pro.backend.dao.OrderedPizzaDao;
import pro.backend.dao.UserDao;
import pro.backend.model.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Service
@Transactional(readOnly = false)
public class OrderService {

	@Autowired
	private PizzaTypeDao pizzaTypeDao;
	
	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderedPizzaDao orderedPizzaDao;
	
	@Autowired
	private UserDao userDao;
	
	@Transactional
	public Optional<Order> addOrder(String login, List<OrderedPizza> orderedPizzas, Address address, String comments) {
		User user = userDao.findByLogin(login);
		
		Order order = new Order();

		List<OrderedPizza> positions = new ArrayList<>();
		for (OrderedPizza inOrderedPizza : orderedPizzas) {
			Long orderedPizzaId = inOrderedPizza.getPizzaType().getId();
			if(orderedPizzaId == null) {
				return Optional.empty();
			}

			Optional<PizzaType> optionalPizzaType = pizzaTypeDao.findById(orderedPizzaId);
			if(!optionalPizzaType.isPresent()) {
				return Optional.empty();
			}

			PizzaType pizzaType = optionalPizzaType.get();

			PizzaSize size = inOrderedPizza.getSize();

			PizzaDough dough = inOrderedPizza.getDough();

			int amount = inOrderedPizza.getAmount();

			OrderedPizza orderedPizza = new OrderedPizza();
			orderedPizza.setOrder(order);
			orderedPizza.setAmount(amount);
			orderedPizza.setPizzaType(pizzaType);
			orderedPizza.setDough(dough);
			orderedPizza.setSize(size);

			positions.add(orderedPizza);
		}

		setStatus(order, OrderStatus.CREATED);
		order.setClient(user);
		order.setAddress(address);
		order.setComments(comments);

		orderDao.saveAndFlush(order);

		order.setPositions(positions);

		orderedPizzaDao.saveAll(positions);


		orderDao.saveAndFlush(order);

		return orderDao.findById(order.getId());
	}

	public static List<Order> listNewOrders(Session session) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Order> criteria = criteriaBuilder.createQuery(Order.class);

		Root<Order> orderRoot = criteria.from(Order.class);
		criteria.select(orderRoot);
		criteria.where(criteriaBuilder.equal(orderRoot.get("status"), OrderStatus.CREATED));

		return session.createQuery(criteria).getResultList();
	}

	public List<Order> list(String login) {
		User user = userDao.findByLogin(login);

		boolean client = user.getUserRole() == UserRole.ROLE_CLIENT;

		if(client) {
			return orderDao.findByClient(user);
		} else {
			Timestamp from = Timestamp.from(Instant.now().minus(1, ChronoUnit.DAYS));
			Timestamp to = Timestamp.from(Instant.now());
			return orderDao.findByTimeCreatedBetween(from, to);
		}
	}

	public Optional<Order> get(String login, long orderId) {
		User user = userDao.findByLogin(login);

		Optional<Order> order = orderDao.findById(orderId);

		if (!order.isPresent() || !canView(user, order.get())) {
			return Optional.empty();
		}

		return order;
	}

	private boolean canView(User user, Order order) {

		boolean client = user.getUserRole() == UserRole.ROLE_CLIENT;

		return !client || order.getClient().getId().equals(user.getId());
	}

	@Transactional
	public Optional<Order> changeStatus(long orderId, OrderStatus newStatus) {
		Optional<Order> optional = orderDao.findById(orderId);

		if (!optional.isPresent()) {
			return Optional.empty();
		}

		Order order = optional.get();

		OrderStatus currentStatus = order.getStatus();
		int currentOrdinal = currentStatus.ordinal();
		int newOrdinal = newStatus.ordinal();

		if(newOrdinal != currentOrdinal + 1) {
			throw new IllegalArgumentException("Nie można zmienić stanu z " + currentStatus + " na " + newStatus);
		}

		setStatus(order, newStatus);

		return Optional.of(orderDao.saveAndFlush(order));
	}

	private void setStatus(Order order, OrderStatus newStatus) {
		order.setStatus(newStatus);
		Timestamp timestamp = Timestamp.from(Instant.now());
		switch (newStatus) {
			case CREATED:
				order.setTimeCreated(timestamp);
				break;
			case ACCEPTED:
				order.setTimeAccepted(timestamp);
				break;
			case PREPARED:
				order.setTimePrepared(timestamp);
				break;
			case DELIVERED:
				order.setTimeDelivered(timestamp);
				break;
		}
	}
}
