package pro.backend.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

@Embeddable
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Address {

	@Getter
	@Setter
	@Column
	@NotNull
	@Size(min=2, message = "Minimalnie 2 znaki")
	@Size(max=16, message = "Maksymalnie 16 znaków")
	@JsonView(Views.Detail.class)
	private String city;

	@Getter
	@Setter
	@Column
	@NotNull
	@Size(min=2, message = "Minimalnie 2 znaki")
	@Size(max=16, message = "Maksymalnie 16 znaków")
	@JsonView(Views.Detail.class)
	private String street;

	@Getter
	@Setter
	@Column
	@NotNull
	@Size(min=1, message = "Minimalnie 1 znak")
	@Size(max=16, message = "Maksymalnie 16 znaków")
	@JsonView(Views.Detail.class)
	private String homeNr;

	@Getter
	@Setter
	@Column
	@Size(max=16, message = "Maksymalnie 16 znaków")
	@JsonView(Views.Detail.class)
	private String flatNr;
}
