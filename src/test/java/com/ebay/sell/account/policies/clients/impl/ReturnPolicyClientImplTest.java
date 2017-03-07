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
import com.ebay.sell.account.policies.clients.ReturnPolicyClient;
import com.ebay.sell.account.policies.models.PolicyCategoryType;
import com.ebay.sell.account.policies.models.ReturnPolicies;
import com.ebay.sell.account.policies.models.ReturnPolicy;
import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverRule;

public class ReturnPolicyClientImplTest {

	private static final String SOME_OAUTH_USER_TOKEN = "v1-ebay-oauth-token";
	private static final String SOME_REFRESH_TOKEN = "some-refresh-token";
	private static final Marketplace SOME_MARKETPLACE = Marketplace.UNITED_STATES;

	private ReturnPolicyClient returnPolicyClient;

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
		returnPolicyClient = new ReturnPolicyClientImpl(baseUri, userToken, requestRetryConfiguration);
	}

	@Test
	public void givenSomeValidMarketplaceWhenRetrievingReturnPoliciesThenReturnReturnPoliciesForMarketplace() {
		final ReturnPolicy firstExpectedReturnPolicy = buildThirdExepctedReturnPolicy();
		final ReturnPolicy secondExpectedReturnPolicy = buildSecondExepctedReturnPolicy();
		final ReturnPolicy thirdExpectedReturnPolicy = buildThirdExepctedReturnPolicy();

		final ReturnPolicies returnPolicies = new ReturnPolicies();
		returnPolicies.setReturnPolicies(
				Arrays.asList(firstExpectedReturnPolicy, secondExpectedReturnPolicy, thirdExpectedReturnPolicy));
		final String responseBody = new JSONObject(returnPolicies).toString();
		mockGetReturnPolicies(Status.OK, responseBody);

		final List<ReturnPolicy> actualReturnPolciies = returnPolicyClient.getReturnPolicies(SOME_MARKETPLACE);

		assertEquals(3, actualReturnPolciies.size());

		assertEquals(firstExpectedReturnPolicy.getCategoryTypes().get(0).getName(),
				actualReturnPolciies.get(0).getCategoryTypes().get(0).getName());
		assertEquals(firstExpectedReturnPolicy.getCategoryTypes().get(0).isDefault(),
				actualReturnPolciies.get(0).getCategoryTypes().get(0).isDefault());
		assertEquals(firstExpectedReturnPolicy.getReturnPolicyId(), actualReturnPolciies.get(0).getReturnPolicyId());

		assertEquals(secondExpectedReturnPolicy.getCategoryTypes().get(0).getName(),
				actualReturnPolciies.get(1).getCategoryTypes().get(0).getName());
		assertEquals(secondExpectedReturnPolicy.getCategoryTypes().get(0).isDefault(),
				actualReturnPolciies.get(1).getCategoryTypes().get(0).isDefault());
		assertEquals(secondExpectedReturnPolicy.getReturnPolicyId(), actualReturnPolciies.get(1).getReturnPolicyId());

		assertEquals(thirdExpectedReturnPolicy.getCategoryTypes().get(0).getName(),
				actualReturnPolciies.get(2).getCategoryTypes().get(0).getName());
		assertEquals(thirdExpectedReturnPolicy.getCategoryTypes().get(0).isDefault(),
				actualReturnPolciies.get(2).getCategoryTypes().get(0).isDefault());
		assertEquals(thirdExpectedReturnPolicy.getReturnPolicyId(), actualReturnPolciies.get(2).getReturnPolicyId());
	}

	@Test
	public void givenSomesMarketplaceAndSomeCategoryTypeNameWithDefaultPolicyWhenRetrievingDefaultPolicyThenReturnDefaultReturnPolicy() {
		final ReturnPolicy firstExpectedReturnPolicy = buildFirstExepctedReturnPolicy();
		final ReturnPolicy secondExpectedReturnPolicy = buildSecondExepctedReturnPolicy();
		final ReturnPolicy thirdExpectedReturnPolicy = buildThirdExepctedReturnPolicy();

		final ReturnPolicies returnPolicies = new ReturnPolicies();
		returnPolicies.setReturnPolicies(
				Arrays.asList(firstExpectedReturnPolicy, secondExpectedReturnPolicy, thirdExpectedReturnPolicy));
		final String responseBody = new JSONObject(returnPolicies).toString();
		mockGetReturnPolicies(Status.OK, responseBody);

		final ReturnPolicy actualDefaultReturnPolicy = returnPolicyClient.getDefaultReturnPolicy(SOME_MARKETPLACE,
				PolicyCategoryType.Name.ALL_EXCLUDING_MOTORS_VEHICLES);

		assertEquals(thirdExpectedReturnPolicy.getCategoryTypes().get(0).getName(),
				actualDefaultReturnPolicy.getCategoryTypes().get(0).getName());
		assertEquals(thirdExpectedReturnPolicy.getCategoryTypes().get(0).isDefault(),
				actualDefaultReturnPolicy.getCategoryTypes().get(0).isDefault());
		assertEquals(thirdExpectedReturnPolicy.getReturnPolicyId(), actualDefaultReturnPolicy.getReturnPolicyId());
	}

	@Test
	public void givenSomesMarketplaceAndSomeCategoryTypeNameWithoutDefaultPolicyWhenRetrievingDefaultPolicyThenThrowNewIllegalArgumentException() {
		final ReturnPolicy firstExpectedReturnPolicy = buildFirstExepctedReturnPolicy();
		final ReturnPolicy secondExpectedReturnPolicy = buildSecondExepctedReturnPolicy();
		final ReturnPolicy thirdExpectedReturnPolicy = buildThirdExepctedReturnPolicy();

		final ReturnPolicies returnPolicies = new ReturnPolicies();
		returnPolicies.setReturnPolicies(
				Arrays.asList(firstExpectedReturnPolicy, secondExpectedReturnPolicy, thirdExpectedReturnPolicy));
		final String responseBody = new JSONObject(returnPolicies).toString();
		mockGetReturnPolicies(Status.OK, responseBody);

		try {
			returnPolicyClient.getDefaultReturnPolicy(SOME_MARKETPLACE, PolicyCategoryType.Name.MOTORS_VEHICLES);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(
					String.format(ReturnPolicyClientImpl.NO_DEFAULT_FULFILLMENT_POLICY_MESSAGE,
							SOME_MARKETPLACE.getId(), PolicyCategoryType.Name.MOTORS_VEHICLES.toString()),
					e.getMessage());
		}
	}

	@Test
	public void givenSomesMarketplaceAndSomeCategoryTypeNameWithoutCategoryTypeWhenRetrievingDefaultPolicyThenThrowNewIllegalArgumentException() {
		final ReturnPolicy firstExpectedReturnPolicy = buildFirstExepctedReturnPolicy();
		final ReturnPolicy secondExpectedReturnPolicy = buildSecondExepctedReturnPolicy();
		secondExpectedReturnPolicy.getCategoryTypes().get(0)
				.setName(PolicyCategoryType.Name.ALL_EXCLUDING_MOTORS_VEHICLES.toString());
		final ReturnPolicy thirdExpectedReturnPolicy = buildThirdExepctedReturnPolicy();

		final ReturnPolicies returnPolicies = new ReturnPolicies();
		returnPolicies.setReturnPolicies(
				Arrays.asList(firstExpectedReturnPolicy, secondExpectedReturnPolicy, thirdExpectedReturnPolicy));
		final String responseBody = new JSONObject(returnPolicies).toString();
		mockGetReturnPolicies(Status.OK, responseBody);

		try {
			returnPolicyClient.getDefaultReturnPolicy(SOME_MARKETPLACE, PolicyCategoryType.Name.MOTORS_VEHICLES);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(
					String.format(ReturnPolicyClientImpl.NO_DEFAULT_FULFILLMENT_POLICY_MESSAGE,
							SOME_MARKETPLACE.getId(), PolicyCategoryType.Name.MOTORS_VEHICLES.toString()),
					e.getMessage());
		}
	}

	private ReturnPolicy buildFirstExepctedReturnPolicy() {
		final ReturnPolicy firstExpectedReturnPolicy = new ReturnPolicy();
		final PolicyCategoryType firstExpectedReturnPolicyCategoryType = new PolicyCategoryType();
		firstExpectedReturnPolicyCategoryType.setName(PolicyCategoryType.Name.ALL_EXCLUDING_MOTORS_VEHICLES.toString());
		firstExpectedReturnPolicyCategoryType.setDefault(false);
		firstExpectedReturnPolicy.setCategoryTypes(Arrays.asList(firstExpectedReturnPolicyCategoryType));
		firstExpectedReturnPolicy.setReturnPolicyId(UUID.randomUUID().toString());
		return firstExpectedReturnPolicy;
	}

	private ReturnPolicy buildSecondExepctedReturnPolicy() {
		final ReturnPolicy secondExpectedReturnPolicy = new ReturnPolicy();
		final PolicyCategoryType secondExpectedReturnPolicyCategoryType = new PolicyCategoryType();
		secondExpectedReturnPolicyCategoryType.setName(PolicyCategoryType.Name.MOTORS_VEHICLES.toString());
		secondExpectedReturnPolicyCategoryType.setDefault(false);
		secondExpectedReturnPolicy.setCategoryTypes(Arrays.asList(secondExpectedReturnPolicyCategoryType));
		secondExpectedReturnPolicy.setReturnPolicyId(UUID.randomUUID().toString());
		return secondExpectedReturnPolicy;
	}

	private ReturnPolicy buildThirdExepctedReturnPolicy() {
		final ReturnPolicy thirdExpectedReturnPolicy = new ReturnPolicy();
		final PolicyCategoryType thirdExpectedReturnPolicyCategoryType = new PolicyCategoryType();
		thirdExpectedReturnPolicyCategoryType.setName(PolicyCategoryType.Name.ALL_EXCLUDING_MOTORS_VEHICLES.toString());
		thirdExpectedReturnPolicyCategoryType.setDefault(true);
		thirdExpectedReturnPolicy.setCategoryTypes(Arrays.asList(thirdExpectedReturnPolicyCategoryType));
		thirdExpectedReturnPolicy.setReturnPolicyId(UUID.randomUUID().toString());
		return thirdExpectedReturnPolicy;
	}

	private void mockGetReturnPolicies(final Status expectedResponseStatus, final String expectedResponseBody) {
		driver.addExpectation(
				onRequestTo(ReturnPolicyClientImpl.RETURN_POLICY_RESOURCE)
						.withParam(ReturnPolicyClientImpl.MARKETPLACE_ID_QUERY_PARAMETER, SOME_MARKETPLACE.getId())
						.withHeader(ReturnPolicyClientImpl.AUTHORIZATION_HEADER,
								new StringBuilder().append(ReturnPolicyClientImpl.OAUTH_USER_TOKEN_PREFIX)
										.append(SOME_OAUTH_USER_TOKEN).toString())
						.withMethod(Method.GET),
				giveResponse(expectedResponseBody, MediaType.APPLICATION_JSON)
						.withStatus(expectedResponseStatus.getStatusCode()));
	}

}
