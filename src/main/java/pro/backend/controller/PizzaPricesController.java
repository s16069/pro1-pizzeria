package pro.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pro.backend.model.PizzaPrice;
import pro.backend.model.RoleNames;
import pro.backend.model.Views;
import pro.backend.service.PizzaService;

import javax.validation.Valid;

@RestController
@RequestMapping("/pizzas/{pizzaId}/prices")
@Secured(RoleNames.ROLE_BOSS)
public class PizzaPricesController {

	@Autowired
	private PizzaService pizzaService;

	@PostMapping
	@JsonView(Views.Detail.class)
	public ResponseEntity<PizzaPrice> addPrice(@PathVariable("pizzaId") long pizzaId, @RequestBody @Valid PizzaPrice pizzaPrice) {
		return ResponseEntity.of(pizzaService.addPrice(pizzaId, pizzaPrice));
	}

	@PutMapping("/{priceId}")
	@JsonView(Views.Detail.class)
	public ResponseEntity<PizzaPrice> updatePrice(@PathVariable("pizzaId") long pizzaId,  @PathVariable("priceId") long priceId, @RequestBody @Valid PizzaPrice update) {
		return ResponseEntity.of(pizzaService.updatePrice(pizzaId, priceId, update));
	}

	@DeleteMapping("/{priceId}")
	@JsonView(Views.Detail.class)
	public ResponseEntity<Void> deletePrice(@PathVariable("pizzaId") long pizzaId, @PathVariable("priceId") long priceId) {
		pizzaService.deletePrice(pizzaId, priceId);
		return ResponseEntity.ok().body(null);
	}

}