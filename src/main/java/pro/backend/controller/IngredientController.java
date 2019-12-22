package pro.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pro.backend.model.Ingredient;
import pro.backend.model.RoleNames;
import pro.backend.model.Views;
import pro.backend.service.IngredientService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    @JsonView(Views.Overview.class)
    public List<Ingredient> list() {
        return ingredientService.list();
    }

    @GetMapping("/{id}")
    @JsonView(Views.Detail.class)
    public ResponseEntity<Ingredient> get(@PathVariable("id") long id) {
        return ResponseEntity.of(ingredientService.findById(id));
    }

    @PostMapping
    @Secured(RoleNames.ROLE_BOSS)
    @JsonView(Views.Detail.class)
    public ResponseEntity<Ingredient> add(@RequestBody @Valid Ingredient ingredient) {
        Ingredient added = ingredientService.add(ingredient);
        return ResponseEntity.ok().body(added);
    }

    @PutMapping("/{id}")
    @Secured(RoleNames.ROLE_BOSS)
    @JsonView(Views.Detail.class)
    public ResponseEntity<Ingredient> update(@PathVariable("id") long id, @RequestBody @Valid Ingredient update) {
        Optional<Ingredient> updated = ingredientService.update(id, update);
        return ResponseEntity.of(updated);
    }

    @DeleteMapping("/{id}")
    @Secured(RoleNames.ROLE_BOSS)
    @JsonView(Views.Detail.class)
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        ingredientService.delete(id);
        return ResponseEntity.ok().body(null);
    }
}
