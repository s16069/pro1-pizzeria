package pro.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pro.backend.model.*;
import pro.backend.service.PizzaService;

@RestController
@RequestMapping("/pizzas/{pizzaId}/ingredients")
@Secured(RoleNames.ROLE_BOSS)
public class PizzaIngredientsController {

	@Autowired
	private PizzaService pizzaService;

	@PutMapping("/{ingredientId}")
	@JsonView(Views.Detail.class)
	public ResponseEntity<PizzaType> add(@PathVariable("pizzaId") long pizzaId, @PathVariable("ingredientId") long ingredientId) {
		return ResponseEntity.of(pizzaService.addIngredient(pizzaId, ingredientId));
	}

	@DeleteMapping("/{ingredientId}")
	@JsonView(Views.Detail.class)
	public ResponseEntity<Void> delete(@PathVariable("pizzaId") long pizzaId, @PathVariable("ingredientId") long ingredientId) {
		pizzaService.deleteIngredient(pizzaId, ingredientId);
		return ResponseEntity.ok().body(null);
	}

}