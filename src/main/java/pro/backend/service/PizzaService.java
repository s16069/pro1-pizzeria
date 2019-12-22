package pro.backend.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pro.backend.dao.IngredientDao;
import pro.backend.dao.PizzaPriceDao;
import pro.backend.dao.PizzaTypeDao;
import pro.backend.model.*;

@Service
@Transactional(readOnly = false)
public class PizzaService {

	@Autowired
	private PizzaTypeDao pizzaTypeDao;

	@Autowired
    private PizzaPriceDao pizzaPriceDao;

	@Autowired
	private IngredientDao ingredientDao;

	public List<PizzaType> list() {
		List<PizzaType> list = pizzaTypeDao.findAll();

		for(PizzaType item : list)
		{
			//Hibernate.initialize(item);
		}
		
		return list;
	}

	public Optional<PizzaType> findById(long id) {
		return pizzaTypeDao.findById(id);
	}

	@Transactional
    public PizzaType add(PizzaType pizza) {

		List<PizzaPrice> prices = pizza.getPizzaPrices();

		PizzaType saved = pizzaTypeDao.saveAndFlush(pizza);

		for(PizzaPrice pizzaPrice : prices) {
			pizzaPrice.setId(null);
			pizzaPrice.setPizzaType(saved);
			pizzaPriceDao.saveAndFlush(pizzaPrice);
		}

		return saved;
    }

    @Transactional
    public Optional<PizzaType> update(long id, PizzaType update) {
		Optional<PizzaType> optional = pizzaTypeDao.findById(id);
		if(!optional.isPresent()) {
			return Optional.empty();
		}

		PizzaType existing = optional.get();

		existing.setBasePrice(update.getBasePrice());
		existing.setDescription(update.getDescription());
		existing.setImage(update.getImage());
		existing.setIsSpicy(update.getIsSpicy());
		existing.setIsVegan(update.getIsVegan());
		existing.setIsVegetarian(update.getIsVegetarian());

		return Optional.of(pizzaTypeDao.saveAndFlush(existing));
    }

    @Transactional
	public void delete(long id) {
		Optional<PizzaType> optional = pizzaTypeDao.findById(id);
		if(optional.isPresent()) {
			pizzaTypeDao.delete(optional.get());
		}
	}

	@Transactional
	public Optional<PizzaPrice> addPrice(long pizzaId, PizzaPrice pizzaPrice) {
		Optional<PizzaType> optional = pizzaTypeDao.findById(pizzaId);
		if(!optional.isPresent()) {
			return Optional.empty();
		}

		pizzaPrice.setPizzaType(optional.get());

		return Optional.of(pizzaPriceDao.save(pizzaPrice));
	}

	@Transactional
	public Optional<PizzaPrice> updatePrice(long pizzaId, long priceId, PizzaPrice update) {
		Optional<PizzaPrice> optional = pizzaPriceDao.findById(priceId);
		if(!optional.isPresent()) {
			return Optional.empty();
		}

		PizzaPrice existing = optional.get();

		existing.setPrice(update.getPrice());
		existing.setDough(update.getDough());
		existing.setSize(update.getSize());

		return Optional.of(pizzaPriceDao.saveAndFlush(existing));
	}

	@Transactional
	public void deletePrice(long pizzaId, long priceId) {
		Optional<PizzaPrice> optional = pizzaPriceDao.findById(priceId);
		if(optional.isPresent()) {
			pizzaPriceDao.delete(optional.get());
		}
	}

	public Optional<PizzaType> addIngredient(long pizzaId, long ingredientId) {
		Optional<PizzaType> pizzaType = pizzaTypeDao.findById(pizzaId);
		if (!pizzaType.isPresent()) {
			return Optional.empty();
		}

		Optional<Ingredient> ingredient = ingredientDao.findById(ingredientId);
		if (!ingredient.isPresent()) {
			return Optional.empty();
		}

		pizzaType.get().getIngredients().add(ingredient.get());

		return Optional.of(pizzaTypeDao.saveAndFlush(pizzaType.get()));
	}

	public Optional<PizzaType> deleteIngredient(long pizzaId, long ingredientId) {
		Optional<PizzaType> pizzaType = pizzaTypeDao.findById(pizzaId);
		if (!pizzaType.isPresent()) {
			return Optional.empty();
		}

		Optional<Ingredient> ingredient = ingredientDao.findById(ingredientId);
		if (!ingredient.isPresent()) {
			return Optional.empty();
		}

		pizzaType.get().getIngredients().remove(ingredient.get());

		return Optional.of(pizzaTypeDao.saveAndFlush(pizzaType.get()));
	}
}
