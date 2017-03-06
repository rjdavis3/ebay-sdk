package com.ebay.sell.inventory.inventoryitems.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ShipToLocationAvailability {

	private int quantity;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof ShipToLocationAvailability)) {
			return false;
		}
		final ShipToLocationAvailability shipToLocationAvailability = (ShipToLocationAvailability) object;
		return new EqualsBuilder().append(getQuantity(), shipToLocationAvailability.getQuantity()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getQuantity()).toHashCode();
	}

}
