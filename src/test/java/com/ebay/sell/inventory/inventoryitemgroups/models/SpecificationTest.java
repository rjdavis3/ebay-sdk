package com.ebay.sell.inventory.inventoryitemgroups.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class SpecificationTest {

	@Test
	public void givenSameInstanceWhenTestingEqualityThenReturnTrue() {
		final Specification leftHandSpecification = new Specification();
		leftHandSpecification.setName("Color");
		leftHandSpecification.setValues(Arrays.asList("Red", "Green", "Blue"));

		final Specification rightHandSpecification = leftHandSpecification;

		assertTrue(leftHandSpecification.equals(rightHandSpecification));
		assertEquals(leftHandSpecification.hashCode(), rightHandSpecification.hashCode());
	}

	@Test
	public void givenSomeStringWhenTestingEqualityThenReturnFalse() {
		final Specification leftHandSpecification = new Specification();
		leftHandSpecification.setName("Color");
		leftHandSpecification.setValues(Arrays.asList("Red", "Green", "Blue"));

		final String rightHandSpecification = "test123";

		assertFalse(leftHandSpecification.equals(rightHandSpecification));
		assertFalse(leftHandSpecification.hashCode() == rightHandSpecification.hashCode());
	}

	@Test
	public void givenSpecificationWithDifferentNameWhenTestingEqualityThenReturnFalse() {
		final Specification leftHandSpecification = new Specification();
		leftHandSpecification.setName("Color");
		leftHandSpecification.setValues(Arrays.asList("Red", "Green", "Blue"));

		final Specification rightHandSpecification = new Specification();
		rightHandSpecification.setName("Colour");
		rightHandSpecification.setValues(Arrays.asList("Red", "Green", "Blue"));

		assertFalse(leftHandSpecification.equals(rightHandSpecification));
		assertFalse(leftHandSpecification.hashCode() == rightHandSpecification.hashCode());
	}

	@Test
	public void givenSpecificationWithSameNameAndMissingValueWhenTestingEqualityThenReturnFalse() {
		final Specification leftHandSpecification = new Specification();
		leftHandSpecification.setName("Color");
		leftHandSpecification.setValues(Arrays.asList("Red", "Green", "Blue"));

		final Specification rightHandSpecification = new Specification();
		rightHandSpecification.setName("Color");
		rightHandSpecification.setValues(Arrays.asList("Red", "Blue"));

		assertFalse(leftHandSpecification.equals(rightHandSpecification));
		assertFalse(leftHandSpecification.hashCode() == rightHandSpecification.hashCode());
	}

	@Test
	public void givenSpecificationWithSameNameAndExtraValueWhenTestingEqualityThenReturnFalse() {
		final Specification leftHandSpecification = new Specification();
		leftHandSpecification.setName("Color");
		leftHandSpecification.setValues(Arrays.asList("Red", "Green", "Blue"));

		final Specification rightHandSpecification = new Specification();
		rightHandSpecification.setName("Color");
		rightHandSpecification.setValues(Arrays.asList("Red", "Green", "Blue", "Orange"));

		assertFalse(leftHandSpecification.equals(rightHandSpecification));
		assertFalse(leftHandSpecification.hashCode() == rightHandSpecification.hashCode());
	}

	@Test
	public void givenSpecificationWithSameNameAndDifferentValueWhenTestingEqualityThenReturnFalse() {
		final Specification leftHandSpecification = new Specification();
		leftHandSpecification.setName("Color");
		leftHandSpecification.setValues(Arrays.asList("Red", "Green", "Blue"));

		final Specification rightHandSpecification = new Specification();
		rightHandSpecification.setName("Color");
		rightHandSpecification.setValues(Arrays.asList("Red", "Green", "blue"));

		assertFalse(leftHandSpecification.equals(rightHandSpecification));
		assertFalse(leftHandSpecification.hashCode() == rightHandSpecification.hashCode());
	}

	@Test
	public void givenSpecificationWithSameNameAndSameValuesInDifferentOrderWhenTestingEqualityThenReturnFalse() {
		final Specification leftHandSpecification = new Specification();
		leftHandSpecification.setName("Color");
		leftHandSpecification.setValues(Arrays.asList("Red", "Green", "Blue", "Red"));

		final Specification rightHandSpecification = new Specification();
		rightHandSpecification.setName("Color");
		rightHandSpecification.setValues(Arrays.asList("Green", "Red", "Blue", "Red"));

		assertFalse(leftHandSpecification.equals(rightHandSpecification));
		assertFalse(leftHandSpecification.hashCode() == rightHandSpecification.hashCode());
	}

	@Test
	public void givenSpecificationWithSameNameAndSameValuesInSameOrderWhenTestingEqualityThenReturnTrue() {
		final Specification leftHandSpecification = new Specification();
		leftHandSpecification.setName("Color");
		leftHandSpecification.setValues(Arrays.asList("Red", "Green", "Blue"));

		final Specification rightHandSpecification = new Specification();
		rightHandSpecification.setName("Color");
		rightHandSpecification.setValues(Arrays.asList("Red", "Green", "Blue"));

		assertTrue(leftHandSpecification.equals(rightHandSpecification));
		assertEquals(leftHandSpecification.hashCode(), rightHandSpecification.hashCode());
	}

}
