package com.ebay.sell.inventory.inventoryitems.models;

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

}
