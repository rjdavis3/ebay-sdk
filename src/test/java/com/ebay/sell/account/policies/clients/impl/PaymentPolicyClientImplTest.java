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
import com.ebay.sell.account.policies.clients.PaymentPolicyClient;
import com.ebay.sell.account.policies.models.PaymentPolicies;
import com.ebay.sell.account.policies.models.PaymentPolicy;
import com.ebay.sell.account.policies.models.PolicyCategoryType;
import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverRule;

public class PaymentPolicyClientImplTest {

	private static final String SOME_OAUTH_USER_TOKEN = "v1-ebay-oauth-token";
	private static final String SOME_REFRESH_TOKEN = "some-refresh-token";
	private static final Marketplace SOME_MARKETPLACE = Marketplace.UNITED_STATES;

	private PaymentPolicyClient paymentPolicyClient;

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
		paymentPolicyClient = new PaymentPolicyClientImpl(baseUri, userToken, requestRetryConfiguration);
	}

	@Test
	public void givenSomeValidMarketplaceWhenRetrievingPaymentPoliciesThenReturnPaymentPoliciesForMarketplace() {
		final PaymentPolicy firstExpectedPaymentPolicy = buildThirdExepctedPaymentPolicy();
		final PaymentPolicy secondExpectedPaymentPolicy = buildSecondExepctedPaymentPolicy();
		final PaymentPolicy thirdExpectedPaymentPolicy = buildThirdExepctedPaymentPolicy();

		final PaymentPolicies paymentPolicies = new PaymentPolicies();
		paymentPolicies.setPaymentPolicies(
				Arrays.asList(firstExpectedPaymentPolicy, secondExpectedPaymentPolicy, thirdExpectedPaymentPolicy));
		final String responseBody = new JSONObject(paymentPolicies).toString();
		mockGetPaymentPolicies(Status.OK, responseBody);

		final List<PaymentPolicy> actualPaymentPolciies = paymentPolicyClient.getPaymentPolicies(SOME_MARKETPLACE);

		assertEquals(3, actualPaymentPolciies.size());

		assertEquals(firstExpectedPaymentPolicy.getCategoryTypes().get(0).getName(),
				actualPaymentPolciies.get(0).getCategoryTypes().get(0).getName());
		assertEquals(firstExpectedPaymentPolicy.getCategoryTypes().get(0).isDefault(),
				actualPaymentPolciies.get(0).getCategoryTypes().get(0).isDefault());
		assertEquals(firstExpectedPaymentPolicy.getPaymentPolicyId(),
				actualPaymentPolciies.get(0).getPaymentPolicyId());

		assertEquals(secondExpectedPaymentPolicy.getCategoryTypes().get(0).getName(),
				actualPaymentPolciies.get(1).getCategoryTypes().get(0).getName());
		assertEquals(secondExpectedPaymentPolicy.getCategoryTypes().get(0).isDefault(),
				actualPaymentPolciies.get(1).getCategoryTypes().get(0).isDefault());
		assertEquals(secondExpectedPaymentPolicy.getPaymentPolicyId(),
				actualPaymentPolciies.get(1).getPaymentPolicyId());

		assertEquals(thirdExpectedPaymentPolicy.getCategoryTypes().get(0).getName(),
				actualPaymentPolciies.get(2).getCategoryTypes().get(0).getName());
		assertEquals(thirdExpectedPaymentPolicy.getCategoryTypes().get(0).isDefault(),
				actualPaymentPolciies.get(2).getCategoryTypes().get(0).isDefault());
		assertEquals(thirdExpectedPaymentPolicy.getPaymentPolicyId(),
				actualPaymentPolciies.get(2).getPaymentPolicyId());
	}

	@Test
	public void givenSomesMarketplaceAndSomeCategoryTypeNameWithDefaultPolicyWhenRetrievingDefaultPolicyThenReturnDefaultPaymentPolicy() {
		final PaymentPolicy firstExpectedPaymentPolicy = buildFirstExepctedPaymentPolicy();
		final PaymentPolicy secondExpectedPaymentPolicy = buildSecondExepctedPaymentPolicy();
		final PaymentPolicy thirdExpectedPaymentPolicy = buildThirdExepctedPaymentPolicy();

		final PaymentPolicies paymentPolicies = new PaymentPolicies();
		paymentPolicies.setPaymentPolicies(
				Arrays.asList(firstExpectedPaymentPolicy, secondExpectedPaymentPolicy, thirdExpectedPaymentPolicy));
		final String responseBody = new JSONObject(paymentPolicies).toString();
		mockGetPaymentPolicies(Status.OK, responseBody);

		final PaymentPolicy actualDefaultPaymentPolicy = paymentPolicyClient.getDefaultPaymentPolicy(SOME_MARKETPLACE,
				PolicyCategoryType.Name.ALL_EXCLUDING_MOTORS_VEHICLES);

		assertEquals(thirdExpectedPaymentPolicy.getCategoryTypes().get(0).getName(),
				actualDefaultPaymentPolicy.getCategoryTypes().get(0).getName());
		assertEquals(thirdExpectedPaymentPolicy.getCategoryTypes().get(0).isDefault(),
				actualDefaultPaymentPolicy.getCategoryTypes().get(0).isDefault());
		assertEquals(thirdExpectedPaymentPolicy.getPaymentPolicyId(), actualDefaultPaymentPolicy.getPaymentPolicyId());
	}

	@Test
	public void givenSomesMarketplaceAndSomeCategoryTypeNameWithoutDefaultPolicyWhenRetrievingDefaultPolicyThenThrowNewIllegalArgumentException() {
		final PaymentPolicy firstExpectedPaymentPolicy = buildFirstExepctedPaymentPolicy();
		final PaymentPolicy secondExpectedPaymentPolicy = buildSecondExepctedPaymentPolicy();
		final PaymentPolicy thirdExpectedPaymentPolicy = buildThirdExepctedPaymentPolicy();

		final PaymentPolicies paymentPolicies = new PaymentPolicies();
		paymentPolicies.setPaymentPolicies(
				Arrays.asList(firstExpectedPaymentPolicy, secondExpectedPaymentPolicy, thirdExpectedPaymentPolicy));
		final String responseBody = new JSONObject(paymentPolicies).toString();
		mockGetPaymentPolicies(Status.OK, responseBody);

		try {
			paymentPolicyClient.getDefaultPaymentPolicy(SOME_MARKETPLACE, PolicyCategoryType.Name.MOTORS_VEHICLES);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(
					String.format(PaymentPolicyClientImpl.NO_DEFAULT_FULFILLMENT_POLICY_MESSAGE,
							SOME_MARKETPLACE.getId(), PolicyCategoryType.Name.MOTORS_VEHICLES.toString()),
					e.getMessage());
		}
	}

	@Test
	public void givenSomesMarketplaceAndSomeCategoryTypeNameWithoutCategoryTypeWhenRetrievingDefaultPolicyThenThrowNewIllegalArgumentException() {
		final PaymentPolicy firstExpectedPaymentPolicy = buildFirstExepctedPaymentPolicy();
		final PaymentPolicy secondExpectedPaymentPolicy = buildSecondExepctedPaymentPolicy();
		secondExpectedPaymentPolicy.getCategoryTypes().get(0)
				.setName(PolicyCategoryType.Name.ALL_EXCLUDING_MOTORS_VEHICLES.toString());
		final PaymentPolicy thirdExpectedPaymentPolicy = buildThirdExepctedPaymentPolicy();

		final PaymentPolicies paymentPolicies = new PaymentPolicies();
		paymentPolicies.setPaymentPolicies(
				Arrays.asList(firstExpectedPaymentPolicy, secondExpectedPaymentPolicy, thirdExpectedPaymentPolicy));
		final String responseBody = new JSONObject(paymentPolicies).toString();
		mockGetPaymentPolicies(Status.OK, responseBody);

		try {
			paymentPolicyClient.getDefaultPaymentPolicy(SOME_MARKETPLACE, PolicyCategoryType.Name.MOTORS_VEHICLES);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(
					String.format(PaymentPolicyClientImpl.NO_DEFAULT_FULFILLMENT_POLICY_MESSAGE,
							SOME_MARKETPLACE.getId(), PolicyCategoryType.Name.MOTORS_VEHICLES.toString()),
					e.getMessage());
		}
	}

	private PaymentPolicy buildFirstExepctedPaymentPolicy() {
		final PaymentPolicy firstExpectedPaymentPolicy = new PaymentPolicy();
		final PolicyCategoryType firstExpectedPaymentPolicyCategoryType = new PolicyCategoryType();
		firstExpectedPaymentPolicyCategoryType
				.setName(PolicyCategoryType.Name.ALL_EXCLUDING_MOTORS_VEHICLES.toString());
		firstExpectedPaymentPolicyCategoryType.setDefault(false);
		firstExpectedPaymentPolicy.setCategoryTypes(Arrays.asList(firstExpectedPaymentPolicyCategoryType));
		firstExpectedPaymentPolicy.setPaymentPolicyId(UUID.randomUUID().toString());
		return firstExpectedPaymentPolicy;
	}

	private PaymentPolicy buildSecondExepctedPaymentPolicy() {
		final PaymentPolicy secondExpectedPaymentPolicy = new PaymentPolicy();
		final PolicyCategoryType secondExpectedPaymentPolicyCategoryType = new PolicyCategoryType();
		secondExpectedPaymentPolicyCategoryType.setName(PolicyCategoryType.Name.MOTORS_VEHICLES.toString());
		secondExpectedPaymentPolicyCategoryType.setDefault(false);
		secondExpectedPaymentPolicy.setCategoryTypes(Arrays.asList(secondExpectedPaymentPolicyCategoryType));
		secondExpectedPaymentPolicy.setPaymentPolicyId(UUID.randomUUID().toString());
		return secondExpectedPaymentPolicy;
	}

	private PaymentPolicy buildThirdExepctedPaymentPolicy() {
		final PaymentPolicy thirdExpectedPaymentPolicy = new PaymentPolicy();
		final PolicyCategoryType thirdExpectedPaymentPolicyCategoryType = new PolicyCategoryType();
		thirdExpectedPaymentPolicyCategoryType
				.setName(PolicyCategoryType.Name.ALL_EXCLUDING_MOTORS_VEHICLES.toString());
		thirdExpectedPaymentPolicyCategoryType.setDefault(true);
		thirdExpectedPaymentPolicy.setCategoryTypes(Arrays.asList(thirdExpectedPaymentPolicyCategoryType));
		thirdExpectedPaymentPolicy.setPaymentPolicyId(UUID.randomUUID().toString());
		return thirdExpectedPaymentPolicy;
	}

	private void mockGetPaymentPolicies(final Status expectedResponseStatus, final String expectedResponseBody) {
		driver.addExpectation(
				onRequestTo(PaymentPolicyClientImpl.PAYMENT_POLICY_RESOURCE)
						.withParam(PaymentPolicyClientImpl.MARKETPLACE_ID_QUERY_PARAMETER, SOME_MARKETPLACE.getId())
						.withHeader(PaymentPolicyClientImpl.AUTHORIZATION_HEADER,
								new StringBuilder().append(PaymentPolicyClientImpl.OAUTH_USER_TOKEN_PREFIX)
										.append(SOME_OAUTH_USER_TOKEN).toString())
						.withMethod(Method.GET),
				giveResponse(expectedResponseBody, MediaType.APPLICATION_JSON)
						.withStatus(expectedResponseStatus.getStatusCode()));
	}

}
