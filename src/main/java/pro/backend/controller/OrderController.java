package pro.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import pro.backend.dto.OrderDto;
import pro.backend.model.Order;
import pro.backend.model.OrderStatus;
import pro.backend.model.RoleNames;
import pro.backend.model.Views;
import pro.backend.service.OrderService;
import pro.backend.service.UserService;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@Secured({RoleNames.ROLE_CLIENT, RoleNames.ROLE_EMPLOYEE, RoleNames.ROLE_BOSS})
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@PostMapping
	@JsonView(Views.Overview.class)
	public ResponseEntity<Order> add(@RequestBody @Valid OrderDto orderDto, Authentication authentication) {

		String login = authentication.getName();

		Optional<Order> order = orderService.addOrder(login, orderDto.getPizzas(), orderDto.getAddress(), orderDto.getComments());
		
		return ResponseEntity.of(order);
	}

	@GetMapping
	@JsonView(Views.Overview.class)
	public List<Order> list(Authentication authentication) {

		String login = authentication.getName();

		return orderService.list(login);
	}

	@GetMapping("/{orderId}")
	@JsonView(Views.Detail.class)
	public ResponseEntity<Order> get(Authentication authentication, @PathVariable long orderId) {

		String login = authentication.getName();

		return ResponseEntity.of(orderService.get(login, orderId));
	}

	@PutMapping("/{orderId}/status")
	@JsonView(Views.Detail.class)
	@Secured({RoleNames.ROLE_EMPLOYEE, RoleNames.ROLE_BOSS})
	public ResponseEntity<Order> changeStatus(@PathVariable long orderId, @RequestParam OrderStatus status) {

		return ResponseEntity.of(orderService.changeStatus(orderId, status));
	}
}