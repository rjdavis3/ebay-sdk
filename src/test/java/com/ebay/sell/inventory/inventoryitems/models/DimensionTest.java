package com.ebay.sell.inventory.inventoryitems.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class DimensionTest {

	@Test
	public void givenSameInstanceWhenTestingEqualityThenReturnTrue() {
		final Dimension leftHandDimension = new Dimension();
		leftHandDimension.setHeight(new BigDecimal("10"));
		leftHandDimension.setLength(new BigDecimal("4"));
		leftHandDimension.setWidth(new BigDecimal("6"));
		leftHandDimension.setUnit("INCH");

		final Dimension rightHandDimension = leftHandDimension;

		assertTrue(leftHandDimension.equals(rightHandDimension));
		assertEquals(leftHandDimension.hashCode(), rightHandDimension.hashCode());
	}

	@Test
	public void givenSomeStringWhenTestingEqualityThenReturnFalse() {
		final Dimension leftHandDimension = new Dimension();
		leftHandDimension.setHeight(new BigDecimal("10"));
		leftHandDimension.setLength(new BigDecimal("4"));
		leftHandDimension.setWidth(new BigDecimal("6"));
		leftHandDimension.setUnit("INCH");

		final String rightHandDimension = "test123";

		assertFalse(leftHandDimension.equals(rightHandDimension));
		assertFalse(leftHandDimension.hashCode() == rightHandDimension.hashCode());
	}

	@Test
	public void givenDifferentUnitWhenTestingEqualityThenReturnFalse() {
		final Dimension leftHandDimension = new Dimension();
		leftHandDimension.setHeight(new BigDecimal("10"));
		leftHandDimension.setLength(new BigDecimal("4"));
		leftHandDimension.setWidth(new BigDecimal("6"));
		leftHandDimension.setUnit("INCH");

		final Dimension rightHandDimension = new Dimension();
		rightHandDimension.setHeight(new BigDecimal("10"));
		rightHandDimension.setLength(new BigDecimal("4"));
		rightHandDimension.setWidth(new BigDecimal("6"));
		rightHandDimension.setUnit("FEET");

		assertFalse(leftHandDimension.equals(rightHandDimension));
		assertFalse(leftHandDimension.hashCode() == rightHandDimension.hashCode());
	}

	@Test
	public void givenSameFieldsWhenTestingEqualityThenReturnTrue() {
		final Dimension leftHandDimension = new Dimension();
		leftHandDimension.setHeight(new BigDecimal("10.0000000"));
		leftHandDimension.setLength(new BigDecimal("4.3333333333333333333333333333"));
		leftHandDimension.setWidth(new BigDecimal("6"));
		leftHandDimension.setUnit("INCH");

		final Dimension rightHandDimension = new Dimension();
		rightHandDimension.setHeight(new BigDecimal("10"));
		rightHandDimension.setLength(new BigDecimal("4.3333333333"));
		rightHandDimension.setWidth(new BigDecimal("6"));
		rightHandDimension.setUnit("INCH");

		assertTrue(leftHandDimension.equals(rightHandDimension));
		assertEquals(leftHandDimension.hashCode(), rightHandDimension.hashCode());
	}

	@Test
	public void givenSameFieldsWithNullValuesWhenTestingEqualityThenReturnTrue() {
		final Dimension leftHandDimension = new Dimension();
		leftHandDimension.setHeight(null);
		leftHandDimension.setLength(null);
		leftHandDimension.setWidth(null);
		leftHandDimension.setUnit("INCH");

		final Dimension rightHandDimension = new Dimension();
		rightHandDimension.setHeight(null);
		rightHandDimension.setLength(null);
		rightHandDimension.setWidth(null);
		rightHandDimension.setUnit("INCH");

		assertTrue(leftHandDimension.equals(rightHandDimension));
		assertEquals(leftHandDimension.hashCode(), rightHandDimension.hashCode());
	}
}
