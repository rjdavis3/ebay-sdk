package com.ebay.sell.inventory.inventoryitems.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AvailabilityTest {

	@Test
	public void givenSameInstanceWhenTestingEqualityThenReturnTrue() {
		final Availability leftHandAvailability = new Availability();
		final ShipToLocationAvailability leftHandShipToLocationAvailability = new ShipToLocationAvailability();
		leftHandShipToLocationAvailability.setQuantity(20);
		leftHandAvailability.setShipToLocationAvailability(leftHandShipToLocationAvailability);

		final Availability rightHandAvailability = leftHandAvailability;

		assertTrue(leftHandAvailability.equals(rightHandAvailability));
		assertEquals(leftHandAvailability.hashCode(), rightHandAvailability.hashCode());
	}

	@Test
	public void givenSomeStringWhenTestingEqualityThenReturnFalse() {
		final Availability leftHandAvailability = new Availability();
		final ShipToLocationAvailability leftHandShipToLocationAvailability = new ShipToLocationAvailability();
		leftHandShipToLocationAvailability.setQuantity(20);
		leftHandAvailability.setShipToLocationAvailability(leftHandShipToLocationAvailability);

		final String rightHandAvailability = "test123";

		assertFalse(leftHandAvailability.equals(rightHandAvailability));
		assertFalse(leftHandAvailability.hashCode() == rightHandAvailability.hashCode());
	}

	@Test
	public void givenDifferentShipToLocationAvailabilityWhenTestingEqualityThenReturnFalse() {
		final Availability leftHandAvailability = new Availability();
		final ShipToLocationAvailability leftHandShipToLocationAvailability = new ShipToLocationAvailability();
		leftHandShipToLocationAvailability.setQuantity(20);
		leftHandAvailability.setShipToLocationAvailability(leftHandShipToLocationAvailability);

		final Availability rightHandAvailability = new Availability();
		final ShipToLocationAvailability rightHandShipToLocationAvailability = new ShipToLocationAvailability();
		rightHandShipToLocationAvailability.setQuantity(19);
		rightHandAvailability.setShipToLocationAvailability(rightHandShipToLocationAvailability);

		assertFalse(leftHandAvailability.equals(rightHandAvailability));
		assertFalse(leftHandAvailability.hashCode() == rightHandAvailability.hashCode());
	}

	@Test
	public void givenSameFieldsWhenTestingEqualityThenReturnTrue() {
		final Availability leftHandAvailability = new Availability();
		final ShipToLocationAvailability leftHandShipToLocationAvailability = new ShipToLocationAvailability();
		leftHandShipToLocationAvailability.setQuantity(20);
		leftHandAvailability.setShipToLocationAvailability(leftHandShipToLocationAvailability);

		final Availability rightHandAvailability = new Availability();
		final ShipToLocationAvailability rightHandShipToLocationAvailability = new ShipToLocationAvailability();
		rightHandShipToLocationAvailability.setQuantity(20);
		rightHandAvailability.setShipToLocationAvailability(rightHandShipToLocationAvailability);

		assertTrue(leftHandAvailability.equals(rightHandAvailability));
		assertEquals(leftHandAvailability.hashCode(), rightHandAvailability.hashCode());
	}
}
