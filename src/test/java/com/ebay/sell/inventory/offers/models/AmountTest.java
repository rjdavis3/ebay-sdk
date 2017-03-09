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
	public void givenSameValuesToTwoDecimalPlacesAndUSDCurrencyWhenTestingEqualityThenReturnTrue() {
		final Amount leftHandAmount = new Amount();
		leftHandAmount.setCurrency("USD");
		leftHandAmount.setValue(new BigDecimal("20.016"));

		final Amount rightHandAmount = new Amount();
		rightHandAmount.setCurrency("USD");
		rightHandAmount.setValue(new BigDecimal("20.02"));

		assertTrue(leftHandAmount.equals(rightHandAmount));
		assertEquals(leftHandAmount.hashCode(), rightHandAmount.hashCode());
	}

	@Test
	public void givenSameValuesToTwoDecimalPlacesAndNullCurrencyWhenTestingEqualityThenReturnFalse() {
		final Amount leftHandAmount = new Amount();
		leftHandAmount.setCurrency(null);
		leftHandAmount.setValue(new BigDecimal("20.016"));

		final Amount rightHandAmount = new Amount();
		rightHandAmount.setCurrency(null);
		rightHandAmount.setValue(new BigDecimal("20.02"));

		assertFalse(leftHandAmount.equals(rightHandAmount));
		assertFalse(leftHandAmount.hashCode() == rightHandAmount.hashCode());
	}

	@Test
	public void givenSameValuesToTenDecimalPlacesAndNullCurrencyWhenTestingEqualityThenReturnTrue() {
		final Amount leftHandAmount = new Amount();
		leftHandAmount.setCurrency(null);
		leftHandAmount.setValue(new BigDecimal("20.123456789267"));

		final Amount rightHandAmount = new Amount();
		rightHandAmount.setCurrency(null);
		rightHandAmount.setValue(new BigDecimal("20.123456789268"));

		assertTrue(leftHandAmount.equals(rightHandAmount));
		assertEquals(leftHandAmount.hashCode(), rightHandAmount.hashCode());
	}

	@Test
	public void givenSameFieldsWithNullValueWhenTestingEqualityThenReturnTrue() {
		final Amount leftHandAmount = new Amount();
		leftHandAmount.setCurrency("USD");
		leftHandAmount.setValue(null);

		final Amount rightHandAmount = new Amount();
		rightHandAmount.setCurrency("USD");
		rightHandAmount.setValue(null);

		assertTrue(leftHandAmount.equals(rightHandAmount));
		assertEquals(leftHandAmount.hashCode(), rightHandAmount.hashCode());
	}
}
