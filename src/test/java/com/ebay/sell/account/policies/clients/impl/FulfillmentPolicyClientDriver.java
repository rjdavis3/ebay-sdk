package com.ebay.sell.account.policies.clients.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.ebay.EbaySdk;
import com.ebay.identity.oauth2.token.models.UserToken;
import com.ebay.identity.ouath2.token.clients.impl.TokenClientImpl;
import com.ebay.models.Marketplace;
import com.ebay.models.RequestRetryConfiguration;
import com.ebay.sell.account.policies.clients.FulfillmentPolicyClient;
import com.ebay.sell.account.policies.models.PolicyCategoryType;
import com.ebay.sell.account.policies.models.FulfillmentPolicy;

public class FulfillmentPolicyClientDriver {

	private static final String CLIENT_ID = System.getenv("EBAY_CLIENT_ID");
	private static final String CLIENT_SECRET = System.getenv("EBAY_CLIENT_SECRET");
	private static final String REFRESH_TOKEN = System.getenv("EBAY_REFRESH_TOKEN");

	private final FulfillmentPolicyClient fulfillmentPolicyClient = new FulfillmentPolicyClientImpl(EbaySdk.SANDBOX_URI,
			new UserToken(new TokenClientImpl(EbaySdk.SANDBOX_URI, CLIENT_ID, CLIENT_SECRET), REFRESH_TOKEN),
			RequestRetryConfiguration.newBuilder().withMininumWait(5, TimeUnit.SECONDS).withTimeout(2, TimeUnit.MINUTES)
					.build());

	@Test
	public void givenUnitedStatesMarketplaceWhenRetrievingFulfillmentPoliciesThenReturnFufillmentPolicies() {
		final List<FulfillmentPolicy> actualFulfillmentPolicies = fulfillmentPolicyClient
				.getFulfillmentPolicies(Marketplace.UNITED_STATES);

		assertEquals(2, actualFulfillmentPolicies.size());
		final FulfillmentPolicy defualtFulfillmentPolicy = actualFulfillmentPolicies.stream().filter(
				fulfillmentPolicy -> fulfillmentPolicy.getCategoryTypes().stream().findFirst().get().isDefault())
				.findFirst().get();
		assertEquals("international shipping policy worldwide", defualtFulfillmentPolicy.getName());
	}

	@Test
	public void givenUnitedStatesMarketplaceAndAllExcludingMotorVehicalesCategoryTypeNameWhenRetrievingDefaultFulfillmentPolicyThenReturnDefaultFufillmentPolicy() {
		final FulfillmentPolicy actualFulfillmentPolicy = fulfillmentPolicyClient.getDefaultFulfillmentPolicy(
				Marketplace.UNITED_STATES, PolicyCategoryType.Name.ALL_EXCLUDING_MOTORS_VEHICLES);

		assertEquals("international shipping policy worldwide", actualFulfillmentPolicy.getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void givenChinaMarketplaceAndAllExcludingMotorVehicalesCategoryTypeNameWhenRetrievingDefaultFulfillmentPolicyThenThrowNewIllegalArgumentException() {
		fulfillmentPolicyClient.getDefaultFulfillmentPolicy(Marketplace.CHINA,
				PolicyCategoryType.Name.ALL_EXCLUDING_MOTORS_VEHICLES);
	}

}
