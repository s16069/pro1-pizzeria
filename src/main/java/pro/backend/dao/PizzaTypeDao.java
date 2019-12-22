package pro.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pro.backend.model.PizzaType;

@Repository
public interface PizzaTypeDao extends JpaRepository<PizzaType, Long> {

}
