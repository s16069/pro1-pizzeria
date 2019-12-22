package pro.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.backend.model.PizzaPrice;

@Repository
public interface PizzaPriceDao extends JpaRepository<PizzaPrice, Long> {

}
