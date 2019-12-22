package pro.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Promotion")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Promotion {

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
    @JsonView(Views.Overview.class)
    private PromotionType type;

    @Getter
    @Setter
    @Column(nullable = false)
    @JsonView(Views.Overview.class)
    private Timestamp dateFrom;

    @Getter
    @Setter
    @Column(nullable = false)
    @JsonView(Views.Overview.class)
    private Timestamp dateTo;

    @Getter
    @Setter
    @Column(nullable = false)
    @JsonView(Views.Detail.class)
    private BigDecimal discount;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "promotions")
    @JsonIgnore
    private List<PizzaType> pizzas;

}
