package com.ebay.sell.inventory.inventoryitems.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class WeightTest {

	@Test
	public void givenSameInstanceWhenTestingEqualityThenReturnTrue() {
		final Weight leftHandWeight = new Weight();
		leftHandWeight.setValue(new BigDecimal("10"));
		leftHandWeight.setUnit("GRAM");

		final Weight rightHandWeight = leftHandWeight;

		assertTrue(leftHandWeight.equals(rightHandWeight));
		assertEquals(leftHandWeight.hashCode(), rightHandWeight.hashCode());
	}

	@Test
	public void givenSomeStringWhenTestingEqualityThenReturnFalse() {
		final Weight leftHandWeight = new Weight();
		leftHandWeight.setValue(new BigDecimal("10"));
		leftHandWeight.setUnit("GRAM");

		final String rightHandWeight = "test123";

		assertFalse(leftHandWeight.equals(rightHandWeight));
		assertFalse(leftHandWeight.hashCode() == rightHandWeight.hashCode());
	}

	@Test
	public void givenDifferentUnitWhenTestingEqualityThenReturnFalse() {
		final Weight leftHandWeight = new Weight();
		leftHandWeight.setValue(new BigDecimal("10"));
		leftHandWeight.setUnit("GRAM");

		final Weight rightHandWeight = new Weight();
		rightHandWeight.setValue(new BigDecimal("10"));
		rightHandWeight.setUnit("POUND");

		assertFalse(leftHandWeight.equals(rightHandWeight));
		assertFalse(leftHandWeight.hashCode() == rightHandWeight.hashCode());
	}

	@Test
	public void givenSameFieldsWhenTestingEqualityThenReturnTrue() {
		final Weight leftHandWeight = new Weight();
		leftHandWeight.setValue(new BigDecimal("10"));
		leftHandWeight.setUnit("GRAM");

		final Weight rightHandWeight = new Weight();
		rightHandWeight.setValue(new BigDecimal("10"));
		rightHandWeight.setUnit("GRAM");

		assertTrue(leftHandWeight.equals(rightHandWeight));
		assertEquals(leftHandWeight.hashCode(), rightHandWeight.hashCode());
	}
}
