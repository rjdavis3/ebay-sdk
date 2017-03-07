package com.ebay.sell.account.policies.models;

import java.util.LinkedList;
import java.util.List;

public class PaymentPolicies {

	private int total;
	private List<PaymentPolicy> paymentPolicies = new LinkedList<>();

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<PaymentPolicy> getPaymentPolicies() {
		return paymentPolicies;
	}

	public void setPaymentPolicies(List<PaymentPolicy> paymentPolicies) {
		this.paymentPolicies = paymentPolicies;
	}

}
