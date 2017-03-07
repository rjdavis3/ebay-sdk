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
import com.ebay.sell.account.policies.clients.PaymentPolicyClient;
import com.ebay.sell.account.policies.models.PaymentPolicies;
import com.ebay.sell.account.policies.models.PaymentPolicy;
import com.ebay.sell.account.policies.models.PolicyCategoryType;

public class PaymentPolicyClientImpl extends EbayClientImpl implements PaymentPolicyClient {

	static final String PAYMENT_POLICY_RESOURCE = "/sell/account/v1/payment_policy";
	static final String MARKETPLACE_ID_QUERY_PARAMETER = "marketplace_id";
	static final String NO_DEFAULT_FULFILLMENT_POLICY_MESSAGE = "No default payment policy found for marketplace with ID of %s and CatgegoryType with name of %s";

	public PaymentPolicyClientImpl(final URI baseUri, final UserToken userToken,
			final RequestRetryConfiguration requestRetryConfiguration) {
		super(baseUri, userToken, requestRetryConfiguration);
	}

	@Override
	public List<PaymentPolicy> getPaymentPolicies(final Marketplace marketplace) {
		final WebTarget webTarget = getWebTarget().queryParam(MARKETPLACE_ID_QUERY_PARAMETER, marketplace.getId());
		final PaymentPolicies paymentPolicies = get(webTarget, PaymentPolicies.class, Status.OK);
		return paymentPolicies.getPaymentPolicies();
	}

	@Override
	public PaymentPolicy getDefaultPaymentPolicy(final Marketplace marketplace, final PolicyCategoryType.Name name) {
		final WebTarget webTarget = getWebTarget().queryParam(MARKETPLACE_ID_QUERY_PARAMETER, marketplace.getId());
		final PaymentPolicies paymentPolicies = get(webTarget, PaymentPolicies.class, Status.OK);
		final Optional<PaymentPolicy> optionalDefaultPaymentPolicy = paymentPolicies.getPaymentPolicies().stream()
				.filter(findDefaultPolicyForCategoryType(name)).findFirst();
		if (optionalDefaultPaymentPolicy.isPresent()) {
			return optionalDefaultPaymentPolicy.get();
		}
		throw new IllegalArgumentException(
				String.format(NO_DEFAULT_FULFILLMENT_POLICY_MESSAGE, marketplace.getId(), name.toString()));
	}

	@Override
	protected WebTarget getWebTarget() {
		return super.getWebTarget().path(PAYMENT_POLICY_RESOURCE);
	}

	private Predicate<PaymentPolicy> findDefaultPolicyForCategoryType(final PolicyCategoryType.Name name) {
		return paymentPolicy -> {
			final PolicyCategoryType categoryType = paymentPolicy.getCategoryTypes().stream().findFirst().get();
			return categoryType.isDefault() && name.toString().equals(categoryType.getName());
		};
	}

}
