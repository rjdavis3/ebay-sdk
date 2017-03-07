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
import com.ebay.sell.account.policies.clients.FulfillmentPolicyClient;
import com.ebay.sell.account.policies.models.PolicyCategoryType;
import com.ebay.sell.account.policies.models.FulfillmentPolicies;
import com.ebay.sell.account.policies.models.FulfillmentPolicy;

public class FulfillmentPolicyClientImpl extends EbayClientImpl implements FulfillmentPolicyClient {

	static final String FULFILLMENT_POLICY_RESOURCE = "/sell/account/v1/fulfillment_policy";
	static final String MARKETPLACE_ID_QUERY_PARAMETER = "marketplace_id";
	static final String NO_DEFAULT_FULFILLMENT_POLICY_MESSAGE = "No default fulfillment policy found for marketplace with ID of %s and CatgegoryType with name of %s";

	public FulfillmentPolicyClientImpl(final URI baseUri, final UserToken userToken,
			final RequestRetryConfiguration requestRetryConfiguration) {
		super(baseUri, userToken, requestRetryConfiguration);
	}

	@Override
	public List<FulfillmentPolicy> getFulfillmentPolicies(final Marketplace marketplace) {
		final WebTarget webTarget = getWebTarget().queryParam(MARKETPLACE_ID_QUERY_PARAMETER, marketplace.getId());
		final FulfillmentPolicies fulfillmentPolicies = get(webTarget, FulfillmentPolicies.class, Status.OK);
		return fulfillmentPolicies.getFulfillmentPolicies();
	}

	@Override
	public FulfillmentPolicy getDefaultFulfillmentPolicy(final Marketplace marketplace, final PolicyCategoryType.Name name) {
		final WebTarget webTarget = getWebTarget().queryParam(MARKETPLACE_ID_QUERY_PARAMETER, marketplace.getId());
		final FulfillmentPolicies fulfillmentPolicies = get(webTarget, FulfillmentPolicies.class, Status.OK);
		final Optional<FulfillmentPolicy> optionalDefaultFulfillmentPolicy = fulfillmentPolicies
				.getFulfillmentPolicies().stream().filter(findDefaultPolicyForCategoryType(name)).findFirst();
		if (optionalDefaultFulfillmentPolicy.isPresent()) {
			return optionalDefaultFulfillmentPolicy.get();
		}
		throw new IllegalArgumentException(
				String.format(NO_DEFAULT_FULFILLMENT_POLICY_MESSAGE, marketplace.getId(), name.toString()));
	}

	@Override
	protected WebTarget getWebTarget() {
		return super.getWebTarget().path(FULFILLMENT_POLICY_RESOURCE);
	}

	private Predicate<FulfillmentPolicy> findDefaultPolicyForCategoryType(final PolicyCategoryType.Name name) {
		return fulfillmentPolicy -> {
			final PolicyCategoryType categoryType = fulfillmentPolicy.getCategoryTypes().stream().findFirst().get();
			return categoryType.isDefault() && name.toString().equals(categoryType.getName());
		};
	}

}
