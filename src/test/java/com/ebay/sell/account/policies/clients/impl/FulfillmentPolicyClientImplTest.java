package com.ebay.sell.account.policies.clients.impl;

import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ebay.identity.oauth2.token.clients.TokenClient;
import com.ebay.identity.oauth2.token.models.Token;
import com.ebay.identity.oauth2.token.models.UserToken;
import com.ebay.models.Marketplace;
import com.ebay.models.RequestRetryConfiguration;
import com.ebay.sell.account.policies.clients.FulfillmentPolicyClient;
import com.ebay.sell.account.policies.models.PolicyCategoryType;
import com.ebay.sell.account.policies.models.FulfillmentPolicies;
import com.ebay.sell.account.policies.models.FulfillmentPolicy;
import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverRule;

public class FulfillmentPolicyClientImplTest {

	private static final String SOME_OAUTH_USER_TOKEN = "v1-ebay-oauth-token";
	private static final String SOME_REFRESH_TOKEN = "some-refresh-token";
	private static final Marketplace SOME_MARKETPLACE = Marketplace.UNITED_STATES;

	private FulfillmentPolicyClient fulfillmentPolicyClient;

	@Rule
	public ClientDriverRule driver = new ClientDriverRule();

	@Mock
	private TokenClient tokenClient;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		final URI baseUri = URI.create(driver.getBaseUrl());

		final Token token = new Token();
		token.setAccessToken(SOME_OAUTH_USER_TOKEN);
		token.setRefreshToken(SOME_REFRESH_TOKEN);
		when(tokenClient.refreshAccessToken(SOME_REFRESH_TOKEN)).thenReturn(token);

		final UserToken userToken = new UserToken(tokenClient, SOME_REFRESH_TOKEN);

		final RequestRetryConfiguration requestRetryConfiguration = RequestRetryConfiguration.newBuilder()
				.withMininumWait(100, TimeUnit.MILLISECONDS).withTimeout(300, TimeUnit.MILLISECONDS).build();
		fulfillmentPolicyClient = new FulfillmentPolicyClientImpl(baseUri, userToken, requestRetryConfiguration);
	}

	@Test
	public void givenSomeValidMarketplaceWhenRetrievingFulfillmentPoliciesThenReturnFulfillmentPoliciesForMarketplace() {
		final FulfillmentPolicy firstExpectedFulfillmentPolicy = buildThirdExepctedFulfillmentPolicy();
		final FulfillmentPolicy secondExpectedFulfillmentPolicy = buildSecondExepctedFulfillmentPolicy();
		final FulfillmentPolicy thirdExpectedFulfillmentPolicy = buildThirdExepctedFulfillmentPolicy();

		final FulfillmentPolicies fulfillmentPolicies = new FulfillmentPolicies();
		fulfillmentPolicies.setFulfillmentPolicies(Arrays.asList(firstExpectedFulfillmentPolicy,
				secondExpectedFulfillmentPolicy, thirdExpectedFulfillmentPolicy));
		final String responseBody = new JSONObject(fulfillmentPolicies).toString();
		mockGetFulfillmentPolicies(Status.OK, responseBody);

		final List<FulfillmentPolicy> actualFulfillmentPolciies = fulfillmentPolicyClient
				.getFulfillmentPolicies(SOME_MARKETPLACE);

		assertEquals(3, actualFulfillmentPolciies.size());

		assertEquals(firstExpectedFulfillmentPolicy.getCategoryTypes().get(0).getName(),
				actualFulfillmentPolciies.get(0).getCategoryTypes().get(0).getName());
		assertEquals(firstExpectedFulfillmentPolicy.getCategoryTypes().get(0).isDefault(),
				actualFulfillmentPolciies.get(0).getCategoryTypes().get(0).isDefault());
		assertEquals(firstExpectedFulfillmentPolicy.getFulfillmentPolicyId(),
				actualFulfillmentPolciies.get(0).getFulfillmentPolicyId());

		assertEquals(secondExpectedFulfillmentPolicy.getCategoryTypes().get(0).getName(),
				actualFulfillmentPolciies.get(1).getCategoryTypes().get(0).getName());
		assertEquals(secondExpectedFulfillmentPolicy.getCategoryTypes().get(0).isDefault(),
				actualFulfillmentPolciies.get(1).getCategoryTypes().get(0).isDefault());
		assertEquals(secondExpectedFulfillmentPolicy.getFulfillmentPolicyId(),
				actualFulfillmentPolciies.get(1).getFulfillmentPolicyId());

		assertEquals(thirdExpectedFulfillmentPolicy.getCategoryTypes().get(0).getName(),
				actualFulfillmentPolciies.get(2).getCategoryTypes().get(0).getName());
		assertEquals(thirdExpectedFulfillmentPolicy.getCategoryTypes().get(0).isDefault(),
				actualFulfillmentPolciies.get(2).getCategoryTypes().get(0).isDefault());
		assertEquals(thirdExpectedFulfillmentPolicy.getFulfillmentPolicyId(),
				actualFulfillmentPolciies.get(2).getFulfillmentPolicyId());
	}

	@Test
	public void givenSomesMarketplaceAndSomeCategoryTypeNameWithDefaultPolicyWhenRetrievingDefaultPolicyThenReturnDefaultFulfillmentPolicy() {
		final FulfillmentPolicy firstExpectedFulfillmentPolicy = buildFirstExepctedFulfillmentPolicy();
		final FulfillmentPolicy secondExpectedFulfillmentPolicy = buildSecondExepctedFulfillmentPolicy();
		final FulfillmentPolicy thirdExpectedFulfillmentPolicy = buildThirdExepctedFulfillmentPolicy();

		final FulfillmentPolicies fulfillmentPolicies = new FulfillmentPolicies();
		fulfillmentPolicies.setFulfillmentPolicies(Arrays.asList(firstExpectedFulfillmentPolicy,
				secondExpectedFulfillmentPolicy, thirdExpectedFulfillmentPolicy));
		final String responseBody = new JSONObject(fulfillmentPolicies).toString();
		mockGetFulfillmentPolicies(Status.OK, responseBody);

		final FulfillmentPolicy actualDefaultFulfillmentPolicy = fulfillmentPolicyClient
				.getDefaultFulfillmentPolicy(SOME_MARKETPLACE, PolicyCategoryType.Name.ALL_EXCLUDING_MOTORS_VEHICLES);

		assertEquals(thirdExpectedFulfillmentPolicy.getCategoryTypes().get(0).getName(),
				actualDefaultFulfillmentPolicy.getCategoryTypes().get(0).getName());
		assertEquals(thirdExpectedFulfillmentPolicy.getCategoryTypes().get(0).isDefault(),
				actualDefaultFulfillmentPolicy.getCategoryTypes().get(0).isDefault());
		assertEquals(thirdExpectedFulfillmentPolicy.getFulfillmentPolicyId(),
				actualDefaultFulfillmentPolicy.getFulfillmentPolicyId());
	}

	@Test
	public void givenSomesMarketplaceAndSomeCategoryTypeNameWithoutDefaultPolicyWhenRetrievingDefaultPolicyThenThrowNewIllegalArgumentException() {
		final FulfillmentPolicy firstExpectedFulfillmentPolicy = buildFirstExepctedFulfillmentPolicy();
		final FulfillmentPolicy secondExpectedFulfillmentPolicy = buildSecondExepctedFulfillmentPolicy();
		final FulfillmentPolicy thirdExpectedFulfillmentPolicy = buildThirdExepctedFulfillmentPolicy();

		final FulfillmentPolicies fulfillmentPolicies = new FulfillmentPolicies();
		fulfillmentPolicies.setFulfillmentPolicies(Arrays.asList(firstExpectedFulfillmentPolicy,
				secondExpectedFulfillmentPolicy, thirdExpectedFulfillmentPolicy));
		final String responseBody = new JSONObject(fulfillmentPolicies).toString();
		mockGetFulfillmentPolicies(Status.OK, responseBody);

		try {
			fulfillmentPolicyClient.getDefaultFulfillmentPolicy(SOME_MARKETPLACE, PolicyCategoryType.Name.MOTORS_VEHICLES);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(String.format(FulfillmentPolicyClientImpl.NO_DEFAULT_FULFILLMENT_POLICY_MESSAGE,
					SOME_MARKETPLACE.getId(), PolicyCategoryType.Name.MOTORS_VEHICLES.toString()), e.getMessage());
		}
	}

	@Test
	public void givenSomesMarketplaceAndSomeCategoryTypeNameWithoutCategoryTypeWhenRetrievingDefaultPolicyThenThrowNewIllegalArgumentException() {
		final FulfillmentPolicy firstExpectedFulfillmentPolicy = buildFirstExepctedFulfillmentPolicy();
		final FulfillmentPolicy secondExpectedFulfillmentPolicy = buildSecondExepctedFulfillmentPolicy();
		secondExpectedFulfillmentPolicy.getCategoryTypes().get(0)
				.setName(PolicyCategoryType.Name.ALL_EXCLUDING_MOTORS_VEHICLES.toString());
		final FulfillmentPolicy thirdExpectedFulfillmentPolicy = buildThirdExepctedFulfillmentPolicy();

		final FulfillmentPolicies fulfillmentPolicies = new FulfillmentPolicies();
		fulfillmentPolicies.setFulfillmentPolicies(Arrays.asList(firstExpectedFulfillmentPolicy,
				secondExpectedFulfillmentPolicy, thirdExpectedFulfillmentPolicy));
		final String responseBody = new JSONObject(fulfillmentPolicies).toString();
		mockGetFulfillmentPolicies(Status.OK, responseBody);

		try {
			fulfillmentPolicyClient.getDefaultFulfillmentPolicy(SOME_MARKETPLACE, PolicyCategoryType.Name.MOTORS_VEHICLES);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(String.format(FulfillmentPolicyClientImpl.NO_DEFAULT_FULFILLMENT_POLICY_MESSAGE,
					SOME_MARKETPLACE.getId(), PolicyCategoryType.Name.MOTORS_VEHICLES.toString()), e.getMessage());
		}
	}

	private FulfillmentPolicy buildFirstExepctedFulfillmentPolicy() {
		final FulfillmentPolicy firstExpectedFulfillmentPolicy = new FulfillmentPolicy();
		final PolicyCategoryType firstExpectedFulfillmentPolicyCategoryType = new PolicyCategoryType();
		firstExpectedFulfillmentPolicyCategoryType.setName(PolicyCategoryType.Name.ALL_EXCLUDING_MOTORS_VEHICLES.toString());
		firstExpectedFulfillmentPolicyCategoryType.setDefault(false);
		firstExpectedFulfillmentPolicy.setCategoryTypes(Arrays.asList(firstExpectedFulfillmentPolicyCategoryType));
		firstExpectedFulfillmentPolicy.setFulfillmentPolicyId(UUID.randomUUID().toString());
		return firstExpectedFulfillmentPolicy;
	}

	private FulfillmentPolicy buildSecondExepctedFulfillmentPolicy() {
		final FulfillmentPolicy secondExpectedFulfillmentPolicy = new FulfillmentPolicy();
		final PolicyCategoryType secondExpectedFulfillmentPolicyCategoryType = new PolicyCategoryType();
		secondExpectedFulfillmentPolicyCategoryType.setName(PolicyCategoryType.Name.MOTORS_VEHICLES.toString());
		secondExpectedFulfillmentPolicyCategoryType.setDefault(false);
		secondExpectedFulfillmentPolicy.setCategoryTypes(Arrays.asList(secondExpectedFulfillmentPolicyCategoryType));
		secondExpectedFulfillmentPolicy.setFulfillmentPolicyId(UUID.randomUUID().toString());
		return secondExpectedFulfillmentPolicy;
	}

	private FulfillmentPolicy buildThirdExepctedFulfillmentPolicy() {
		final FulfillmentPolicy thirdExpectedFulfillmentPolicy = new FulfillmentPolicy();
		final PolicyCategoryType thirdExpectedFulfillmentPolicyCategoryType = new PolicyCategoryType();
		thirdExpectedFulfillmentPolicyCategoryType.setName(PolicyCategoryType.Name.ALL_EXCLUDING_MOTORS_VEHICLES.toString());
		thirdExpectedFulfillmentPolicyCategoryType.setDefault(true);
		thirdExpectedFulfillmentPolicy.setCategoryTypes(Arrays.asList(thirdExpectedFulfillmentPolicyCategoryType));
		thirdExpectedFulfillmentPolicy.setFulfillmentPolicyId(UUID.randomUUID().toString());
		return thirdExpectedFulfillmentPolicy;
	}

	private void mockGetFulfillmentPolicies(final Status expectedResponseStatus, final String expectedResponseBody) {
		driver.addExpectation(
				onRequestTo(FulfillmentPolicyClientImpl.FULFILLMENT_POLICY_RESOURCE)
						.withParam(FulfillmentPolicyClientImpl.MARKETPLACE_ID_QUERY_PARAMETER, SOME_MARKETPLACE.getId())
						.withHeader(FulfillmentPolicyClientImpl.AUTHORIZATION_HEADER,
								new StringBuilder().append(FulfillmentPolicyClientImpl.OAUTH_USER_TOKEN_PREFIX)
										.append(SOME_OAUTH_USER_TOKEN).toString())
						.withMethod(Method.GET),
				giveResponse(expectedResponseBody, MediaType.APPLICATION_JSON)
						.withStatus(expectedResponseStatus.getStatusCode()));
	}

}
