package pro.backend.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

// Order is reserved word :(

@Entity
@Table(name = "Order_")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	@Id
	@Getter
	@Setter
	@GeneratedValue
	@Column(name = "id")
	@JsonView(Views.Overview.class)
	private long id;

	@Getter
	@Setter
	@Column(nullable = true)
	@JsonView(Views.Detail.class)
	private String comments;

	@Getter
	@Setter
	@Embedded
	@JsonView(Views.Detail.class)
	@Valid
	private Address address;

	@Getter
	@Setter
	@Column(nullable = false)
	@JsonView(Views.Overview.class)
	private OrderStatus status;

	@Getter
	@Setter
	@Column(nullable = false)
	@JsonView(Views.Overview.class)
	private Timestamp timeCreated;

	@Getter
	@Setter
	@Column(nullable = true)
	@JsonView(Views.Detail.class)
	private Timestamp timeAccepted;

	@Getter
	@Setter
	@Column(nullable = true)
	@JsonView(Views.Detail.class)
	private Timestamp timePrepared;

	@Getter
	@Setter
	@Column(nullable = true)
	@JsonView(Views.Detail.class)
	private Timestamp timeDelivered;

	@Getter
	@Setter
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
	@OrderBy("id")
	@Fetch(FetchMode.JOIN)
	@JsonView(Views.Detail.class)
	private List<OrderedPizza> positions = new ArrayList<>();

	@Getter
	@Setter
	@ManyToOne()
	@JoinColumn(name = "client_id")
	@JsonView(Views.Detail.class)
	private User client;

	public Order(OrderStatus status, String comments) {
		this();

		setStatus(status);
		setComments(comments);
	}

	@JsonView(Views.Overview.class)
	public BigDecimal getPrice() {
		BigDecimal sum = new BigDecimal(0);

		for (OrderedPizza position : positions)
			sum = sum.add(position.getPrice());

		return sum;
	}

	public void addPosition(OrderedPizza position) {
		if (position == null)
			throw new NullPointerException("position cannot be null");

		if (position.getOrder() != this)
			throw new IllegalArgumentException("position belongs to other order");

		if (!positions.contains(position)) {
			this.positions.add(position);
			position.setOrder(this);
		}
	}
}
