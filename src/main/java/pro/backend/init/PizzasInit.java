package pro.backend.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pro.backend.model.Ingredient;
import pro.backend.model.PizzaType;
import pro.backend.service.IngredientService;
import pro.backend.service.PizzaService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

@Component
@DependsOn("IngredientsInit")
public class PizzasInit {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private IngredientService ingredientService;

    @PostConstruct
    @Transactional
    private void postConstruct() {
        if(pizzaService.list().size() == 0) {
           addPizzas();
        }
    }

    private void addPizzas() {
        List<Ingredient> ingredients = ingredientService.list();

        PizzaType p1 = new PizzaType(1L, "Carbonara", "Bardzo smaczna", new BigDecimal(20));
        p1.setIngredients(ingredients);

        pizzaService.add(p1);

        PizzaType p2 = new PizzaType(2L, "Cappriciosa", "Bardzo smaczna", new BigDecimal(25));
        p2.setIngredients(ingredients);

        pizzaService.add(p2);
    }
}