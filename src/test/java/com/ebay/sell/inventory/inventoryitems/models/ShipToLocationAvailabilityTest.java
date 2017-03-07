package com.ebay.sell.inventory.inventoryitems.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ShipToLocationAvailabilityTest {

	@Test
	public void givenSameInstanceWhenTestingEqualityThenReturnTrue() {
		final ShipToLocationAvailability leftHandShipToLocationAvailability = new ShipToLocationAvailability();
		leftHandShipToLocationAvailability.setQuantity(20);

		final ShipToLocationAvailability rightHandShipToLocationAvailability = leftHandShipToLocationAvailability;

		assertTrue(leftHandShipToLocationAvailability.equals(rightHandShipToLocationAvailability));
		assertEquals(leftHandShipToLocationAvailability.hashCode(), rightHandShipToLocationAvailability.hashCode());
	}

	@Test
	public void givenSomeStringWhenTestingEqualityThenReturnFalse() {
		final ShipToLocationAvailability leftHandShipToLocationAvailability = new ShipToLocationAvailability();
		leftHandShipToLocationAvailability.setQuantity(20);

		final String rightHandShipToLocationAvailability = "test123";

		assertFalse(leftHandShipToLocationAvailability.equals(rightHandShipToLocationAvailability));
		assertFalse(leftHandShipToLocationAvailability.hashCode() == rightHandShipToLocationAvailability.hashCode());
	}

	@Test
	public void givenDifferentQuantityWhenTestingEqualityThenReturnFalse() {
		final ShipToLocationAvailability leftHandShipToLocationAvailability = new ShipToLocationAvailability();
		leftHandShipToLocationAvailability.setQuantity(20);

		final ShipToLocationAvailability rightHandShipToLocationAvailability = new ShipToLocationAvailability();
		rightHandShipToLocationAvailability.setQuantity(21);

		assertFalse(leftHandShipToLocationAvailability.equals(rightHandShipToLocationAvailability));
		assertFalse(leftHandShipToLocationAvailability.hashCode() == rightHandShipToLocationAvailability.hashCode());
	}

	@Test
	public void givenSameFieldsWhenTestingEqualityThenReturnTrue() {
		final ShipToLocationAvailability leftHandShipToLocationAvailability = new ShipToLocationAvailability();
		leftHandShipToLocationAvailability.setQuantity(20);

		final ShipToLocationAvailability rightHandShipToLocationAvailability = new ShipToLocationAvailability();
		rightHandShipToLocationAvailability.setQuantity(20);

		assertTrue(leftHandShipToLocationAvailability.equals(rightHandShipToLocationAvailability));
		assertEquals(leftHandShipToLocationAvailability.hashCode(), rightHandShipToLocationAvailability.hashCode());
	}
}
