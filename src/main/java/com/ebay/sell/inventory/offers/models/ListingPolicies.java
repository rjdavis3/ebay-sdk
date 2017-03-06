package com.ebay.sell.inventory.offers.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ListingPolicies {

	private String fulfillmentPolicyId;
	private String paymentPolicyId;
	private String returnPolicyId;

	public String getFulfillmentPolicyId() {
		return fulfillmentPolicyId;
	}

	public void setFulfillmentPolicyId(String fulfillmentPolicyId) {
		this.fulfillmentPolicyId = fulfillmentPolicyId;
	}

	public String getPaymentPolicyId() {
		return paymentPolicyId;
	}

	public void setPaymentPolicyId(String paymentPolicyId) {
		this.paymentPolicyId = paymentPolicyId;
	}

	public String getReturnPolicyId() {
		return returnPolicyId;
	}

	public void setReturnPolicyId(String returnPolicyId) {
		this.returnPolicyId = returnPolicyId;
	}

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof ListingPolicies)) {
			return false;
		}
		final ListingPolicies listingPolicies = (ListingPolicies) object;
		return new EqualsBuilder().append(getFulfillmentPolicyId(), listingPolicies.getFulfillmentPolicyId())
				.append(getPaymentPolicyId(), listingPolicies.getPaymentPolicyId())
				.append(getReturnPolicyId(), listingPolicies.getReturnPolicyId()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getFulfillmentPolicyId()).append(getPaymentPolicyId())
				.append(getReturnPolicyId()).toHashCode();
	}

}
