package com.ebay.sell.account.policies.clients.impl;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response.Status;

import com.ebay.clients.impl.EbayClientImpl;
import com.ebay.identity.oauth2.token.models.UserToken;
import com.ebay.models.Marketplace;
import com.ebay.models.RequestRetryConfiguration;
import com.ebay.sell.account.policies.clients.ReturnPolicyClient;
import com.ebay.sell.account.policies.models.PolicyCategoryType;
import com.ebay.sell.account.policies.models.ReturnPolicies;
import com.ebay.sell.account.policies.models.ReturnPolicy;

public class ReturnPolicyClientImpl extends EbayClientImpl implements ReturnPolicyClient {

	static final String RETURN_POLICY_RESOURCE = "/sell/account/v1/return_policy";
	static final String MARKETPLACE_ID_QUERY_PARAMETER = "marketplace_id";
	static final String NO_DEFAULT_FULFILLMENT_POLICY_MESSAGE = "No default return policy found for marketplace with ID of %s and CatgegoryType with name of %s";

	public ReturnPolicyClientImpl(final URI baseUri, final UserToken userToken,
			final RequestRetryConfiguration requestRetryConfiguration) {
		super(baseUri, userToken, requestRetryConfiguration);
	}

	@Override
	public List<ReturnPolicy> getReturnPolicies(final Marketplace marketplace) {
		final WebTarget webTarget = getWebTarget().queryParam(MARKETPLACE_ID_QUERY_PARAMETER, marketplace.getId());
		final ReturnPolicies returnPolicies = get(webTarget, ReturnPolicies.class, Status.OK);
		return returnPolicies.getReturnPolicies();
	}

	@Override
	public ReturnPolicy getDefaultReturnPolicy(final Marketplace marketplace, final PolicyCategoryType.Name name) {
		final WebTarget webTarget = getWebTarget().queryParam(MARKETPLACE_ID_QUERY_PARAMETER, marketplace.getId());
		final ReturnPolicies returnPolicies = get(webTarget, ReturnPolicies.class, Status.OK);
		final Optional<ReturnPolicy> optionalDefaultReturnPolicy = returnPolicies.getReturnPolicies().stream()
				.filter(findDefaultPolicyForCategoryType(name)).findFirst();
		if (optionalDefaultReturnPolicy.isPresent()) {
			return optionalDefaultReturnPolicy.get();
		}
		throw new IllegalArgumentException(
				String.format(NO_DEFAULT_FULFILLMENT_POLICY_MESSAGE, marketplace.getId(), name.toString()));
	}

	@Override
	protected WebTarget getWebTarget() {
		return super.getWebTarget().path(RETURN_POLICY_RESOURCE);
	}

	private Predicate<ReturnPolicy> findDefaultPolicyForCategoryType(final PolicyCategoryType.Name name) {
		return returnPolicy -> {
			final PolicyCategoryType categoryType = returnPolicy.getCategoryTypes().stream().findFirst().get();
			return categoryType.isDefault() && name.toString().equals(categoryType.getName());
		};
	}

}
