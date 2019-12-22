package pro.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pro.backend.model.OrderedPizza;

@Repository
public interface OrderedPizzaDao extends JpaRepository<OrderedPizza, Long> {

}
