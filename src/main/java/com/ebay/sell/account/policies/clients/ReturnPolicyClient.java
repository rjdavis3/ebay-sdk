package com.ebay.sell.account.policies.clients;

import java.util.List;

import com.ebay.models.Marketplace;
import com.ebay.sell.account.policies.models.PolicyCategoryType;
import com.ebay.sell.account.policies.models.ReturnPolicy;

public interface ReturnPolicyClient {

	public List<ReturnPolicy> getReturnPolicies(final Marketplace marketplace);

	public ReturnPolicy getDefaultReturnPolicy(final Marketplace marketplace, final PolicyCategoryType.Name name);

}
