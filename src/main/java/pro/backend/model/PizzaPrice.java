package pro.backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "PizzaPrice")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PizzaPrice {

    @Id
    @Getter
    @Setter
    @GeneratedValue
    @Column(name = "id")
    @JsonView(Views.Detail.class)
    protected Long id;

    @Getter
    @Setter
    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "pizza_id", nullable = false, updatable = false)
    @JsonIgnore
    private PizzaType pizzaType;

    @Getter
    @Setter
    @NonNull
    @Column(nullable = false)
    @JsonView(Views.Detail.class)
    private PizzaDough dough;

    @Getter
    @Setter
    @NonNull
    @Column(nullable = false)
    @JsonView(Views.Detail.class)
    private PizzaSize size;

    @Getter
    @Setter
    @NonNull
    @Column(nullable = false)
    @JsonView(Views.Detail.class)
    private BigDecimal price;

    public PizzaPrice(@NonNull PizzaType pizzaType, @NonNull PizzaDough dough, @NonNull PizzaSize size, @NonNull BigDecimal price) {
        this.pizzaType = pizzaType;
        this.dough = dough;
        this.size = size;
        this.price = price;
    }
}