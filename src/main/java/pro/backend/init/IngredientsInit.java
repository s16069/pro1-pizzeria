package pro.backend.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pro.backend.model.Ingredient;
import pro.backend.model.PizzaType;
import pro.backend.service.IngredientService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Component("IngredientsInit")
public class IngredientsInit {

    @Autowired
    private IngredientService ingredientService;

    @PostConstruct
    @Transactional
    private void postConstruct() {
        if(ingredientService.list().size() == 0) {
           add();
        }
    }

    private void add() {
        ingredientService.add(new Ingredient(1L, "Salami", true, new BigDecimal(3.0)));
        ingredientService.add(new Ingredient(2L, "Ser", false, new BigDecimal(1.0)));
        ingredientService.add(new Ingredient(3L, "Szynka", false, new BigDecimal(2.0)));
    }
}