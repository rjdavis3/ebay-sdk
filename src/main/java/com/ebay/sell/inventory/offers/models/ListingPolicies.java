package com.ebay.sell.inventory.offers.models;

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

}
