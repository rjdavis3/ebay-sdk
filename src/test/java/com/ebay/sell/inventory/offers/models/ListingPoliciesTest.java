package com.ebay.sell.inventory.offers.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ListingPoliciesTest {

	@Test
	public void givenSameInstanceWhenTestingEqualityThenReturnTrue() {
		final ListingPolicies leftHandListingPolicies = new ListingPolicies();
		leftHandListingPolicies.setFulfillmentPolicyId("4000010401401");
		leftHandListingPolicies.setPaymentPolicyId("440401401055556");
		leftHandListingPolicies.setReturnPolicyId("4550101011525454");

		final ListingPolicies rightHandListingPolicies = leftHandListingPolicies;

		assertTrue(leftHandListingPolicies.equals(rightHandListingPolicies));
		assertEquals(leftHandListingPolicies.hashCode(), rightHandListingPolicies.hashCode());
	}

	@Test
	public void givenSomeStringWhenTestingEqualityThenReturnFalse() {
		final ListingPolicies leftHandListingPolicies = new ListingPolicies();
		leftHandListingPolicies.setFulfillmentPolicyId("4000010401401");
		leftHandListingPolicies.setPaymentPolicyId("440401401055556");
		leftHandListingPolicies.setReturnPolicyId("4550101011525454");

		final String rightHandListingPolicies = "test123";

		assertFalse(leftHandListingPolicies.equals(rightHandListingPolicies));
		assertFalse(leftHandListingPolicies.hashCode() == rightHandListingPolicies.hashCode());
	}

	@Test
	public void givenDifferentReturnPolicyIdWhenTestingEqualityThenReturnFalse() {
		final ListingPolicies leftHandListingPolicies = new ListingPolicies();
		leftHandListingPolicies.setFulfillmentPolicyId("4000010401401");
		leftHandListingPolicies.setPaymentPolicyId("440401401055556");
		leftHandListingPolicies.setReturnPolicyId("4550101011525454");

		final ListingPolicies rightHandListingPolicies = new ListingPolicies();
		rightHandListingPolicies.setFulfillmentPolicyId("4000010401401");
		rightHandListingPolicies.setPaymentPolicyId("440401401055556");
		rightHandListingPolicies.setReturnPolicyId("455010101152544");

		assertFalse(leftHandListingPolicies.equals(rightHandListingPolicies));
		assertFalse(leftHandListingPolicies.hashCode() == rightHandListingPolicies.hashCode());
	}

	@Test
	public void givenSameFieldsWhenTestingEqualityThenReturnTrue() {
		final ListingPolicies leftHandListingPolicies = new ListingPolicies();
		leftHandListingPolicies.setFulfillmentPolicyId("4000010401401");
		leftHandListingPolicies.setPaymentPolicyId("440401401055556");
		leftHandListingPolicies.setReturnPolicyId("4550101011525454");

		final ListingPolicies rightHandListingPolicies = new ListingPolicies();
		rightHandListingPolicies.setFulfillmentPolicyId("4000010401401");
		rightHandListingPolicies.setPaymentPolicyId("440401401055556");
		rightHandListingPolicies.setReturnPolicyId("4550101011525454");

		assertTrue(leftHandListingPolicies.equals(rightHandListingPolicies));
		assertEquals(leftHandListingPolicies.hashCode(), rightHandListingPolicies.hashCode());
	}
}
