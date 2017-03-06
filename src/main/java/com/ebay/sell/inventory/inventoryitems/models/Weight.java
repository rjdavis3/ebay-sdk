package com.ebay.sell.inventory.inventoryitems.models;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Weight {

	private String unit;
	private BigDecimal value;

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Weight)) {
			return false;
		}
		final Weight weight = (Weight) object;
		return new EqualsBuilder().append(getUnit(), weight.getUnit()).append(getValue(), weight.getValue()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getUnit()).append(getValue()).toHashCode();
	}

}
