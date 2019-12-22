package pro.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.backend.dao.PizzaPriceDao;
import pro.backend.dao.PromotionDao;
import pro.backend.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = false)
public class PromotionService {

	@Autowired
	private PromotionDao promotionDao;

	public Optional<Promotion> findById(long id) {
		return promotionDao.findById(id);
	}

	public List<Promotion> list() {
		return promotionDao.findAll();
	}

	@Transactional
    public Promotion add(Promotion promotion) {

		promotion.setId(null);

		return promotionDao.saveAndFlush(promotion);
    }

    @Transactional
    public Optional<Promotion> update(long id, Promotion update) {
		Optional<Promotion> optional = promotionDao.findById(id);
		if (!optional.isPresent()) {
			return Optional.empty();
		}

		Promotion existing = optional.get();

		existing.setName(update.getName());
		existing.setType(update.getType());
		existing.setDiscount(update.getDiscount());
		existing.setDateFrom(update.getDateFrom());
		existing.setDateTo(update.getDateTo());

		return Optional.of(promotionDao.saveAndFlush(existing));
    }

    @Transactional
	public void delete(long id) {
		Optional<Promotion> optional = promotionDao.findById(id);
		if (optional.isPresent()) {
			promotionDao.delete(optional.get());
		}
	}
}
