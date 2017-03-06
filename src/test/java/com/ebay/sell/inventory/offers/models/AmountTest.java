package com.ebay.sell.inventory.offers.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class AmountTest {

	@Test
	public void givenSameInstanceWhenTestingEqualityThenReturnTrue() {
		final Amount leftHandAmount = new Amount();
		leftHandAmount.setCurrency("USD");
		leftHandAmount.setValue(new BigDecimal("10"));

		final Amount rightHandAmount = leftHandAmount;

		assertTrue(leftHandAmount.equals(rightHandAmount));
		assertEquals(leftHandAmount.hashCode(), rightHandAmount.hashCode());
	}

	@Test
	public void givenSomeStringWhenTestingEqualityThenReturnFalse() {
		final Amount leftHandAmount = new Amount();
		leftHandAmount.setCurrency("USD");
		leftHandAmount.setValue(new BigDecimal("10"));

		final String rightHandAmount = "test123";

		assertFalse(leftHandAmount.equals(rightHandAmount));
		assertFalse(leftHandAmount.hashCode() == rightHandAmount.hashCode());
	}

	@Test
	public void givenDifferentValueWhenTestingEqualityThenReturnFalse() {
		final Amount leftHandAmount = new Amount();
		leftHandAmount.setCurrency("USD");
		leftHandAmount.setValue(new BigDecimal("10"));

		final Amount rightHandAmount = new Amount();
		rightHandAmount.setCurrency("USD");
		rightHandAmount.setValue(new BigDecimal("9"));

		assertFalse(leftHandAmount.equals(rightHandAmount));
		assertFalse(leftHandAmount.hashCode() == rightHandAmount.hashCode());
	}

	@Test
	public void givenSameFieldsWhenTestingEqualityThenReturnTrue() {
		final Amount leftHandAmount = new Amount();
		leftHandAmount.setCurrency("USD");
		leftHandAmount.setValue(new BigDecimal("10"));

		final Amount rightHandAmount = new Amount();
		rightHandAmount.setCurrency("USD");
		rightHandAmount.setValue(new BigDecimal("10"));

		assertTrue(leftHandAmount.equals(rightHandAmount));
		assertEquals(leftHandAmount.hashCode(), rightHandAmount.hashCode());
	}
}
