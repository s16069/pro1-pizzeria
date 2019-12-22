package pro.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pro.backend.model.Ingredient;
import pro.backend.model.Promotion;
import pro.backend.model.RoleNames;
import pro.backend.model.Views;
import pro.backend.service.PromotionService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/promotions")
public class PromotionController {
    
    @Autowired
    private PromotionService promotionService;


    @GetMapping
    @JsonView(Views.Overview.class)
    public List<Promotion> list() {
        return promotionService.list();
    }

    @GetMapping("/{id}")
    @JsonView(Views.Detail.class)
    public ResponseEntity<Promotion> get(@PathVariable("id") long id) {
        return ResponseEntity.of(promotionService.findById(id));
    }

    @PostMapping
    @Secured(RoleNames.ROLE_BOSS)
    @JsonView(Views.Detail.class)
    public ResponseEntity<Promotion> add(@RequestBody @Valid Promotion promotion) {
        Promotion added = promotionService.add(promotion);
        return ResponseEntity.ok().body(added);
    }

    @PutMapping("/{id}")
    @Secured(RoleNames.ROLE_BOSS)
    @JsonView(Views.Detail.class)
    public ResponseEntity<Promotion> update(@PathVariable("id") long id, @RequestBody @Valid Promotion update) {
        Optional<Promotion> updated = promotionService.update(id, update);
        return ResponseEntity.of(updated);
    }

    @DeleteMapping("/{id}")
    @Secured(RoleNames.ROLE_BOSS)
    @JsonView(Views.Detail.class)
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        promotionService.delete(id);
        return ResponseEntity.ok().body(null);
    }
}
