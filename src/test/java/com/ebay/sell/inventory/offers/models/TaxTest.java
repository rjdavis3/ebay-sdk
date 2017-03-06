package com.ebay.sell.inventory.offers.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class TaxTest {

	@Test
	public void givenSameInstanceWhenTestingEqualityThenReturnTrue() {
		final Tax leftHandTax = new Tax();
		leftHandTax.setApplyTax(true);
		leftHandTax.setThirdPartyTaxCategory("WASTE_RECYCLING_FEE");
		leftHandTax.setVatPercentage(new BigDecimal("2.3"));

		final Tax rightHandTax = leftHandTax;

		assertTrue(leftHandTax.equals(rightHandTax));
		assertEquals(leftHandTax.hashCode(), rightHandTax.hashCode());
	}

	@Test
	public void givenSomeStringWhenTestingEqualityThenReturnFalse() {
		final Tax leftHandTax = new Tax();
		leftHandTax.setApplyTax(true);
		leftHandTax.setThirdPartyTaxCategory("WASTE_RECYCLING_FEE");
		leftHandTax.setVatPercentage(new BigDecimal("2.3"));

		final String rightHandTax = "test123";

		assertFalse(leftHandTax.equals(rightHandTax));
		assertFalse(leftHandTax.hashCode() == rightHandTax.hashCode());
	}

	@Test
	public void givenDifferentVatPercentageWhenTestingEqualityThenReturnFalse() {
		final Tax leftHandTax = new Tax();
		leftHandTax.setApplyTax(true);
		leftHandTax.setThirdPartyTaxCategory("WASTE_RECYCLING_FEE");
		leftHandTax.setVatPercentage(new BigDecimal("2.3"));

		final Tax rightHandTax = new Tax();
		rightHandTax.setApplyTax(true);
		rightHandTax.setThirdPartyTaxCategory("WASTE_RECYCLING_FEE");
		rightHandTax.setVatPercentage(new BigDecimal("2.4"));

		assertFalse(leftHandTax.equals(rightHandTax));
		assertFalse(leftHandTax.hashCode() == rightHandTax.hashCode());
	}

	@Test
	public void givenSameFieldsWhenTestingEqualityThenReturnTrue() {
		final Tax leftHandTax = new Tax();
		leftHandTax.setApplyTax(true);
		leftHandTax.setThirdPartyTaxCategory("WASTE_RECYCLING_FEE");
		leftHandTax.setVatPercentage(new BigDecimal("2.3"));

		final Tax rightHandTax = new Tax();
		rightHandTax.setApplyTax(true);
		rightHandTax.setThirdPartyTaxCategory("WASTE_RECYCLING_FEE");
		rightHandTax.setVatPercentage(new BigDecimal("2.3"));

		assertTrue(leftHandTax.equals(rightHandTax));
		assertEquals(leftHandTax.hashCode(), rightHandTax.hashCode());
	}
}
