package com.ebay.sell.inventory.inventoryitems.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class PackageWeightAndSizeTest {

	@Test
	public void givenSameInstanceWhenTestingEqualityThenReturnTrue() {
		final PackageWeightAndSize leftHandPackageWeightAndSize = new PackageWeightAndSize();
		final Dimension leftHandDimension = new Dimension();
		leftHandDimension.setHeight(new BigDecimal("10"));
		leftHandDimension.setLength(new BigDecimal("4"));
		leftHandDimension.setWidth(new BigDecimal("6"));
		leftHandDimension.setUnit("INCH");
		leftHandPackageWeightAndSize.setDimensions(leftHandDimension);
		leftHandPackageWeightAndSize.setPackageType("MAILING_BOX");
		final Weight leftHandWeight = new Weight();
		leftHandWeight.setValue(new BigDecimal("10"));
		leftHandWeight.setUnit("GRAM");
		leftHandPackageWeightAndSize.setWeight(leftHandWeight);

		final PackageWeightAndSize rightHandPackageWeightAndSize = leftHandPackageWeightAndSize;

		assertTrue(leftHandPackageWeightAndSize.equals(rightHandPackageWeightAndSize));
		assertEquals(leftHandPackageWeightAndSize.hashCode(), rightHandPackageWeightAndSize.hashCode());
	}

	@Test
	public void givenSomeStringWhenTestingEqualityThenReturnFalse() {
		final PackageWeightAndSize leftHandPackageWeightAndSize = new PackageWeightAndSize();
		final Dimension leftHandDimension = new Dimension();
		leftHandDimension.setHeight(new BigDecimal("10"));
		leftHandDimension.setLength(new BigDecimal("4"));
		leftHandDimension.setWidth(new BigDecimal("6"));
		leftHandDimension.setUnit("INCH");
		leftHandPackageWeightAndSize.setDimensions(leftHandDimension);
		leftHandPackageWeightAndSize.setPackageType("MAILING_BOX");
		final Weight leftHandWeight = new Weight();
		leftHandWeight.setValue(new BigDecimal("10"));
		leftHandWeight.setUnit("GRAM");
		leftHandPackageWeightAndSize.setWeight(leftHandWeight);

		final String rightHandPackageWeightAndSize = "test123";

		assertFalse(leftHandPackageWeightAndSize.equals(rightHandPackageWeightAndSize));
		assertFalse(leftHandPackageWeightAndSize.hashCode() == rightHandPackageWeightAndSize.hashCode());
	}

	@Test
	public void givenDifferentWeightWhenTestingEqualityThenReturnFalse() {
		final PackageWeightAndSize leftHandPackageWeightAndSize = new PackageWeightAndSize();
		final Dimension leftHandDimension = new Dimension();
		leftHandDimension.setHeight(new BigDecimal("10"));
		leftHandDimension.setLength(new BigDecimal("4"));
		leftHandDimension.setWidth(new BigDecimal("6"));
		leftHandDimension.setUnit("INCH");
		leftHandPackageWeightAndSize.setDimensions(leftHandDimension);
		leftHandPackageWeightAndSize.setPackageType("MAILING_BOX");
		final Weight leftHandWeight = new Weight();
		leftHandWeight.setValue(new BigDecimal("10"));
		leftHandWeight.setUnit("GRAM");
		leftHandPackageWeightAndSize.setWeight(leftHandWeight);

		final PackageWeightAndSize rightHandPackageWeightAndSize = new PackageWeightAndSize();
		final Dimension rightHandDimension = new Dimension();
		rightHandDimension.setHeight(new BigDecimal("10"));
		rightHandDimension.setLength(new BigDecimal("4"));
		rightHandDimension.setWidth(new BigDecimal("6"));
		rightHandDimension.setUnit("INCH");
		rightHandPackageWeightAndSize.setDimensions(rightHandDimension);
		rightHandPackageWeightAndSize.setPackageType("MAILING_BOX");
		final Weight rightHandWeight = new Weight();
		rightHandWeight.setValue(new BigDecimal("10.1"));
		rightHandWeight.setUnit("GRAM");
		rightHandPackageWeightAndSize.setWeight(rightHandWeight);

		assertFalse(leftHandPackageWeightAndSize.equals(rightHandPackageWeightAndSize));
		assertFalse(leftHandPackageWeightAndSize.hashCode() == rightHandPackageWeightAndSize.hashCode());
	}

	@Test
	public void givenSameFieldsWhenTestingEqualityThenReturnTrue() {
		final PackageWeightAndSize leftHandPackageWeightAndSize = new PackageWeightAndSize();
		final Dimension leftHandDimension = new Dimension();
		leftHandDimension.setHeight(new BigDecimal("10"));
		leftHandDimension.setLength(new BigDecimal("4"));
		leftHandDimension.setWidth(new BigDecimal("6"));
		leftHandDimension.setUnit("INCH");
		leftHandPackageWeightAndSize.setDimensions(leftHandDimension);
		leftHandPackageWeightAndSize.setPackageType("MAILING_BOX");
		final Weight leftHandWeight = new Weight();
		leftHandWeight.setValue(new BigDecimal("10"));
		leftHandWeight.setUnit("GRAM");
		leftHandPackageWeightAndSize.setWeight(leftHandWeight);

		final PackageWeightAndSize rightHandPackageWeightAndSize = new PackageWeightAndSize();
		final Dimension rightHandDimension = new Dimension();
		rightHandDimension.setHeight(new BigDecimal("10"));
		rightHandDimension.setLength(new BigDecimal("4"));
		rightHandDimension.setWidth(new BigDecimal("6"));
		rightHandDimension.setUnit("INCH");
		rightHandPackageWeightAndSize.setDimensions(rightHandDimension);
		rightHandPackageWeightAndSize.setPackageType("MAILING_BOX");
		final Weight rightHandWeight = new Weight();
		rightHandWeight.setValue(new BigDecimal("10"));
		rightHandWeight.setUnit("GRAM");
		rightHandPackageWeightAndSize.setWeight(rightHandWeight);

		assertTrue(leftHandPackageWeightAndSize.equals(rightHandPackageWeightAndSize));
		assertEquals(leftHandPackageWeightAndSize.hashCode(), rightHandPackageWeightAndSize.hashCode());
	}
}
