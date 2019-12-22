package pro.backend.dto;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pro.backend.model.Address;
import pro.backend.model.OrderedPizza;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class OrderDto {

	@Getter
	@Setter
	@NonNull
	@Size(min=1, message = "Zamów co najmniej jedną pizzę")
	@Size(max=10, message = "Zamów co najwyżej 10 pizz")
	private List<OrderedPizza> pizzas;

	@Getter
	@Setter
	@NonNull
	@Valid
	private Address address;

	@Getter
	@Setter
	private String comments;
}
