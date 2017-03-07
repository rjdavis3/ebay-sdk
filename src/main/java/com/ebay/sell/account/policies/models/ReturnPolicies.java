package com.ebay.sell.account.policies.models;

import java.util.LinkedList;
import java.util.List;

public class ReturnPolicies {

	private int total;
	private List<ReturnPolicy> returnPolicies = new LinkedList<>();

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<ReturnPolicy> getReturnPolicies() {
		return returnPolicies;
	}

	public void setReturnPolicies(List<ReturnPolicy> returnPolicies) {
		this.returnPolicies = returnPolicies;
	}

}
