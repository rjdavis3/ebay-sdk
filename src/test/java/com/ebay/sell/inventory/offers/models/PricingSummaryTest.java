package com.ebay.sell.inventory.offers.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class PricingSummaryTest {

	@Test
	public void givenSameInstanceWhenTestingEqualityThenReturnTrue() {
		final PricingSummary leftHandPricingSummary = new PricingSummary();
		final Amount leftHandAmount = new Amount();
		leftHandAmount.setCurrency("USD");
		leftHandAmount.setValue(new BigDecimal("10"));
		leftHandPricingSummary.setPrice(leftHandAmount);

		final PricingSummary rightHandPricingSummary = leftHandPricingSummary;

		assertTrue(leftHandPricingSummary.equals(rightHandPricingSummary));
		assertEquals(leftHandPricingSummary.hashCode(), rightHandPricingSummary.hashCode());
	}

	@Test
	public void givenSomeStringWhenTestingEqualityThenReturnFalse() {
		final PricingSummary leftHandPricingSummary = new PricingSummary();
		final Amount leftHandAmount = new Amount();
		leftHandAmount.setCurrency("USD");
		leftHandAmount.setValue(new BigDecimal("10"));
		leftHandPricingSummary.setPrice(leftHandAmount);

		final String rightHandPricingSummary = "test123";

		assertFalse(leftHandPricingSummary.equals(rightHandPricingSummary));
		assertFalse(leftHandPricingSummary.hashCode() == rightHandPricingSummary.hashCode());
	}

	@Test
	public void givenDifferentPriceWhenTestingEqualityThenReturnFalse() {
		final PricingSummary leftHandPricingSummary = new PricingSummary();
		final Amount leftHandAmount = new Amount();
		leftHandAmount.setCurrency("USD");
		leftHandAmount.setValue(new BigDecimal("9.99"));
		leftHandPricingSummary.setPrice(leftHandAmount);

		final PricingSummary rightHandPricingSummary = new PricingSummary();
		final Amount rightHandAmount = new Amount();
		rightHandAmount.setCurrency("USD");
		rightHandAmount.setValue(new BigDecimal("10"));
		rightHandPricingSummary.setPrice(rightHandAmount);

		assertFalse(leftHandPricingSummary.equals(rightHandPricingSummary));
		assertFalse(leftHandPricingSummary.hashCode() == rightHandPricingSummary.hashCode());
	}

	@Test
	public void givenSameFieldsWhenTestingEqualityThenReturnTrue() {
		final PricingSummary leftHandPricingSummary = new PricingSummary();
		final Amount leftHandAmount = new Amount();
		leftHandAmount.setCurrency("USD");
		leftHandAmount.setValue(new BigDecimal("9.99"));
		leftHandPricingSummary.setPrice(leftHandAmount);

		final PricingSummary rightHandPricingSummary = new PricingSummary();
		final Amount rightHandAmount = new Amount();
		rightHandAmount.setCurrency("USD");
		rightHandAmount.setValue(new BigDecimal("9.99"));
		rightHandPricingSummary.setPrice(rightHandAmount);

		assertTrue(leftHandPricingSummary.equals(rightHandPricingSummary));
		assertEquals(leftHandPricingSummary.hashCode(), rightHandPricingSummary.hashCode());
	}
}
