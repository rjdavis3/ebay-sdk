package com.ebay.sell.account.policies.clients;

import java.util.List;

import com.ebay.models.Marketplace;
import com.ebay.sell.account.policies.models.PaymentPolicy;
import com.ebay.sell.account.policies.models.PolicyCategoryType;

public interface PaymentPolicyClient {

	public List<PaymentPolicy> getPaymentPolicies(final Marketplace marketplace);

	public PaymentPolicy getDefaultPaymentPolicy(final Marketplace marketplace, final PolicyCategoryType.Name name);

}
