package pro.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.backend.dao.IngredientDao;
import pro.backend.model.*;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = false)
public class IngredientService {

	@Autowired
	private IngredientDao ingredientDao;

	public Optional<Ingredient> findById(long id) {
		return ingredientDao.findById(id);
	}

	public List<Ingredient> list() {
		return ingredientDao.findAll();
	}

	@Transactional
    public Ingredient add(Ingredient ingredient) {
		return ingredientDao.saveAndFlush(ingredient);
    }

    @Transactional
    public Optional<Ingredient> update(long id, Ingredient update) {
		Optional<Ingredient> optional = ingredientDao.findById(id);
		if (!optional.isPresent()) {
			return Optional.empty();
		}

		Ingredient existing = optional.get();

		existing.setName(update.getName());
		existing.setSpicy(update.isSpicy());
		existing.setPrice(update.getPrice());

		return Optional.of(ingredientDao.saveAndFlush(existing));
    }

    @Transactional
	public void delete(long id) {
		Optional<Ingredient> optional = ingredientDao.findById(id);
		if (optional.isPresent()) {
			ingredientDao.delete(optional.get());
		}
	}
}
