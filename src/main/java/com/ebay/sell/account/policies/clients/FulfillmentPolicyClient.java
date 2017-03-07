package com.ebay.sell.account.policies.clients;

import java.util.List;

import com.ebay.models.Marketplace;
import com.ebay.sell.account.policies.models.PolicyCategoryType;
import com.ebay.sell.account.policies.models.FulfillmentPolicy;

public interface FulfillmentPolicyClient {

	public List<FulfillmentPolicy> getFulfillmentPolicies(final Marketplace marketplace);

	public FulfillmentPolicy getDefaultFulfillmentPolicy(final Marketplace marketplace, final PolicyCategoryType.Name name);

}
