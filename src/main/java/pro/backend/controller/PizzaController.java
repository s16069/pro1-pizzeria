package pro.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonView;

import pro.backend.model.*;
import pro.backend.service.PizzaService;;import javax.validation.Valid;

@RestController
@RequestMapping("/pizzas")
public class PizzaController {

	@Autowired
	private PizzaService pizzaService;

	@GetMapping
	@JsonView(Views.Overview.class)
	public List<PizzaType> list() {

		return pizzaService.list();
	}

	@GetMapping("/{id}")
	@JsonView(Views.Detail.class)
	public ResponseEntity<PizzaType> get(@PathVariable("id") long id) {
		return ResponseEntity.of(pizzaService.findById(id));
	}

	@PostMapping
	@Secured(RoleNames.ROLE_BOSS)
	@JsonView(Views.Detail.class)
	public ResponseEntity<PizzaType> add(@RequestBody @Valid PizzaType pizzaType) {
		PizzaType pizza = pizzaService.add(pizzaType);
		return ResponseEntity.ok().body(pizza);
	}

	@PutMapping("/{id}")
	@Secured(RoleNames.ROLE_BOSS)
	@JsonView(Views.Detail.class)
	public ResponseEntity<PizzaType> update(@PathVariable("id") long id, @RequestBody @Valid PizzaType update) {
		Optional<PizzaType> pizza = pizzaService.update(id, update);
		return ResponseEntity.of(pizza);
	}

	@DeleteMapping("/{id}")
	@Secured(RoleNames.ROLE_BOSS)
	@JsonView(Views.Detail.class)
	public ResponseEntity<Void> delete(@PathVariable("id") long id) {
		pizzaService.delete(id);
		return ResponseEntity.ok().body(null);
	}
}