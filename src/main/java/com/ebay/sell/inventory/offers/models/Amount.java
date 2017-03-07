package com.ebay.sell.inventory.offers.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Amount {

	private static final int SCALE = 10;
	private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

	private String currency;
	private BigDecimal value;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = (value == null) ? null : value.setScale(SCALE, ROUNDING_MODE).stripTrailingZeros();
	}

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Amount)) {
			return false;
		}
		final Amount amount = (Amount) object;
		return new EqualsBuilder().append(getCurrency(), amount.getCurrency()).append(getValue(), amount.getValue())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCurrency()).append(getValue()).toHashCode();
	}

}
