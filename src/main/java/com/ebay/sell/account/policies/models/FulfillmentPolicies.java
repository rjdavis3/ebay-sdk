package com.ebay.sell.account.policies.models;

import java.util.LinkedList;
import java.util.List;

public class FulfillmentPolicies {

	private int total;
	private List<FulfillmentPolicy> fulfillmentPolicies = new LinkedList<>();

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<FulfillmentPolicy> getFulfillmentPolicies() {
		return fulfillmentPolicies;
	}

	public void setFulfillmentPolicies(List<FulfillmentPolicy> fulfillmentPolicies) {
		this.fulfillmentPolicies = fulfillmentPolicies;
	}

}
