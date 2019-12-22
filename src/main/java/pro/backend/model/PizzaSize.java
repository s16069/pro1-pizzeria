package pro.backend.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public enum PizzaSize {
	S("S", 30, new BigDecimal(0.8)),
	M("M", 40, new BigDecimal(1)),
	L("L", 50, new BigDecimal(1.2)),
	XL("XL", 60, new BigDecimal(1.4));

	private String name;

	private int diameter;

	private BigDecimal priceMultiplier;

	PizzaSize(String name, int diameter, BigDecimal priceMultiplier) {
		this.name = name;
		this.diameter = diameter;
		this.priceMultiplier = priceMultiplier;
	}

	public String getName() {
		return name;
	}

	public int getDiameter() {
		return diameter;
	}

	public BigDecimal getPriceMultiplier() {
		return priceMultiplier;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
