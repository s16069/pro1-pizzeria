package pro.backend.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public enum PizzaDough {
	THIN("Cienkie", "Cienkie", new BigDecimal(1)),
	THICK("Grube", "Grube", new BigDecimal(1.2));

	private String name;

	private String description;

	private BigDecimal priceMultiplier;

	PizzaDough(String name, String description, BigDecimal priceMultiplier) {
		this.name = name;
		this.description = description;
		this.priceMultiplier = priceMultiplier;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}


	public BigDecimal getPriceMultiplier() {
		return priceMultiplier;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
