package pro.backend.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

@Entity
@Table(name = "OrderedPizza")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderedPizza {

	private static final int MAX_AMOUNT = 5;

	@Id
	@Getter
	@Setter
	@GeneratedValue
	@Column(name = "id")
	@JsonView(Views.Overview.class)
	protected Long id;

	@Getter
	@Setter
	@Column(nullable = false)
	@Min(value = 1, message = "Zamów co najmniej jedną pizzę")
	@Max(value = MAX_AMOUNT, message = "Nie można zamówić więcej niż " + MAX_AMOUNT + " pizz")
	@JsonView(Views.Overview.class)
	protected int amount;

	@Getter
	@Setter
	@NonNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "order_id", nullable = false, updatable = false)
	@JsonIgnore
	protected Order order;

	@Getter
	@Setter
	@NonNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "pizza_id", nullable = false, updatable = false)
	@JsonView(Views.Overview.class)
	private PizzaType pizzaType;

	@Getter
	@Setter
	@NonNull
	@Column(nullable = false)
	@JsonView(Views.Overview.class)
	private PizzaDough dough;

	@Getter
	@Setter
	@NonNull
	@Column(nullable = false)
	@JsonView(Views.Overview.class)
	private PizzaSize size;

	@Getter
	@Setter
	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinTable(name = "OrderedPizza_Ingredient", joinColumns = { @JoinColumn(name = "ordered_pizza_id") }, inverseJoinColumns = { @JoinColumn(name = "ingredient_id") })
	@JsonView(Views.Detail.class)
	List<Ingredient> ingredients;

	@JsonView(Views.Overview.class)
	public BigDecimal getPrice() {
		return pizzaType.getBasePrice().multiply(dough.getPriceMultiplier()).multiply(size.getPriceMultiplier())
				.multiply(new BigDecimal(amount));
	}
}
