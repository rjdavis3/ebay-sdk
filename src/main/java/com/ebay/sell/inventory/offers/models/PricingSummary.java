package com.ebay.sell.inventory.offers.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PricingSummary {

	private Amount price;

	public Amount getPrice() {
		return price;
	}

	public void setPrice(Amount price) {
		this.price = price;
	}

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof PricingSummary)) {
			return false;
		}
		final PricingSummary pricingSummary = (PricingSummary) object;
		return new EqualsBuilder().append(getPrice(), pricingSummary.getPrice()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getPrice()).toHashCode();
	}

}
