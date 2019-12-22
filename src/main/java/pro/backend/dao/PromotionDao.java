package pro.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.backend.model.PizzaPrice;
import pro.backend.model.Promotion;

@Repository
public interface PromotionDao extends JpaRepository<Promotion, Long> {

}
