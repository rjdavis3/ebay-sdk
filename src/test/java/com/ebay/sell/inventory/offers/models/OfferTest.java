package com.ebay.sell.inventory.offers.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Test;

public class OfferTest {

	@Test
	public void givenSameInstanceWhenTestingEqualityThenReturnTrue() {
		final Offer leftHandOffer = buildOffer();

		final Offer rightHandOffer = leftHandOffer;

		assertTrue(leftHandOffer.equals(rightHandOffer));
		assertEquals(leftHandOffer.hashCode(), rightHandOffer.hashCode());
	}

	@Test
	public void givenSomeStringWhenTestingEqualityThenReturnFalse() {
		final Offer leftHandOffer = buildOffer();

		final String rightHandOffer = "test123";

		assertFalse(leftHandOffer.equals(rightHandOffer));
		assertFalse(leftHandOffer.hashCode() == rightHandOffer.hashCode());
	}

	@Test
	public void givenDifferentTaxWhenTestingEqualityThenReturnFalse() {
		final Offer leftHandOffer = buildOffer();

		final Offer rightHandOffer = buildOffer();
		rightHandOffer.getTax().setApplyTax(false);

		assertFalse(leftHandOffer.equals(rightHandOffer));
		assertFalse(leftHandOffer.hashCode() == rightHandOffer.hashCode());
	}

	@Test
	public void givenSameFieldsWhenTestingEqualityThenReturnTrue() {
		final Offer leftHandOffer = buildOffer();

		final Offer rightHandOffer = buildOffer();

		assertTrue(leftHandOffer.equals(rightHandOffer));
		assertEquals(leftHandOffer.hashCode(), rightHandOffer.hashCode());
	}

	private Offer buildOffer() {
		final Offer offer = new Offer();
		offer.setOfferId("4589797999");
		offer.setCategoryId("404004");
		offer.setFormat("FIXED_PRICE");
		offer.setListingDescription("Some description");
		final ListingPolicies listingPolicies = new ListingPolicies();
		listingPolicies.setFulfillmentPolicyId("4000010401401");
		listingPolicies.setPaymentPolicyId("440401401055556");
		listingPolicies.setReturnPolicyId("4550101011525454");
		offer.setListingPolicies(listingPolicies);
		offer.setMarketplaceId("EBAY_US");
		offer.setMerchantLocationKey("warehouse-1");
		final Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setValue(new BigDecimal("10"));
		final PricingSummary pricingSummary = new PricingSummary();
		pricingSummary.setPrice(amount);
		offer.setPricingSummary(pricingSummary);
		offer.setQuantityLimitPerBuyer(4);
		offer.setSku("41110");
		offer.setStoreCategoryNames(Arrays.asList("/Fashion/Men/Shirts", "/Fashion/Men/Accessories"));
		final Tax tax = new Tax();
		tax.setApplyTax(true);
		tax.setThirdPartyTaxCategory("WASTE_RECYCLING_FEE");
		tax.setVatPercentage(new BigDecimal("2.3"));
		offer.setTax(tax);
		return offer;
	}
}
