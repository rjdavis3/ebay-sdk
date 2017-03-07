package com.ebay.sell.inventory.inventoryitems.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Availability {

	private ShipToLocationAvailability shipToLocationAvailability;

	public ShipToLocationAvailability getShipToLocationAvailability() {
		return shipToLocationAvailability;
	}

	public void setShipToLocationAvailability(ShipToLocationAvailability shipToLocationAvailability) {
		this.shipToLocationAvailability = shipToLocationAvailability;
	}

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Availability)) {
			return false;
		}
		final Availability availability = (Availability) object;
		return new EqualsBuilder().append(getShipToLocationAvailability(), availability.getShipToLocationAvailability())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getShipToLocationAvailability()).toHashCode();
	}

}
