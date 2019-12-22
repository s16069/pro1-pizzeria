package pro.backend.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "Ingredient")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ingredient {

    @Id
    @Getter
    @Setter
    @GeneratedValue
    @Column(name = "id")
    @JsonView(Views.Overview.class)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    @JsonView(Views.Overview.class)
    private String name;

    @Getter
    @Setter
    @Column(nullable = false)
    @JsonView(Views.Detail.class)
    private boolean spicy;

    @Getter
    @Setter
    @Column(nullable = false)
    @JsonView(Views.Detail.class)
    private BigDecimal price;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "ingredients")
    @JsonIgnore
    private List<PizzaType> pizzas;

    public Ingredient(Long id, String name, boolean spicy, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.spicy = spicy;
        this.price = price;
    }
}
