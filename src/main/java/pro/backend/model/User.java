package pro.backend.model;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "User")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@Getter
	@Setter
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Getter
	@Setter
	@Column(nullable = true)
	private UserRole userRole;

	@Getter
	@Setter
	@Column(nullable = false, unique = true)
	@Size(min=4, message = "Minimalnie 4 znaki")
	@Size(max=16, message = "Maksymalnie 16 znaków")
	private String login;

	@Getter
	@Setter
	@Column(nullable = false)
	@Size(min=4, message = "Minimalnie 4 znaki")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String pass;

	@Getter
	@Setter
	@NotNull
	@Column(nullable = false)
	@Size(min=2, message = "Minimalnie 2 znaki")
	@Size(max=16, message = "Maksymalnie 16 znaków")
	private String name;

	@Getter
	@Setter
	@NotNull
	@Column(nullable = false)
	@Size(min=2, message = "Minimalnie 2 znaki")
	@Size(max=16, message = "Maksymalnie 16 znaków")
	private String surname;

	@Getter
	@Setter
	@NotNull
	@Column(nullable = false)
	@Pattern(regexp = "^[0-9]{9}$", message = "Nr telefonu składa się z 9 cyfr")
	private String tel;

	@Getter
	@Setter
	@NotNull
	@Column(nullable = false)
	@Pattern(regexp = "\\S+@\\S+\\.\\S+$", message = "Wpisz poprawny adres email")
	private String email;

	@Getter
	@Setter
	@Embedded
	@Valid
	private Address address;

	@Getter
	@Setter
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "client")
	@MapKey(name = "id")
	@JsonIgnore
	private Map<Long, Order> orderMap;


	@JsonIgnore
	public Collection<Order> getOrders() {
		return orderMap.values();
	}

	@JsonIgnore
	public Set<Long> getOrdersIds() {
		return orderMap.keySet();
	}

	public Order getOrderById(Long id) {
		if (id == null)
			throw new NullPointerException("id cannot be null");

		return orderMap.get(id);
	}

	public void addOrder(Order order) {
		if (order == null)
			throw new NullPointerException("order cannot be null");

		if (!this.orderMap.containsKey(order.getId())) {
			this.orderMap.put(order.getId(), order);
			order.setClient(this);
		}
	}
}
