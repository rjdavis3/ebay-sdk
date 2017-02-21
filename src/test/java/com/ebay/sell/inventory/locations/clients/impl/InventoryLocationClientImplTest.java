package com.ebay.sell.inventory.locations.clients.impl;

import static com.github.restdriver.clientdriver.RestClientDriver.giveEmptyResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ebay.clients.models.ErrorResponse;
import com.ebay.exceptions.EbayErrorResponseException;
import com.ebay.exceptions.EbayNotFoundResponseException;
import com.ebay.identity.oauth2.token.clients.TokenClient;
import com.ebay.identity.oauth2.token.models.Token;
import com.ebay.identity.oauth2.token.models.UserToken;
import com.ebay.models.RequestRetryConfiguration;
import com.ebay.sell.inventory.locations.clients.InventoryLocationClient;
import com.ebay.sell.inventory.locations.models.InventoryLocation;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.github.restdriver.clientdriver.capture.JsonBodyCapture;

public class InventoryLocationClientImplTest {

	private static final char FORWARD_SLASH = '/';
	private static final String SOME_OAUTH_USER_TOKEN = "v1-ebay-oauth-token";
	private static final String SOME_NEW_OAUTH_USER_TOKEN = "v1-ebay-oauth-token-1";
	private static final String SOME_MERCHANT_LOCATION_KEY = "Ricks_Store";
	private static final String SOME_NAME = "Rick's Store";
	private static final String SOME_REFRESH_TOKEN = "some-refresh-token";

	private InventoryLocationClient inventoryLocationClient;

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
		inventoryLocationClient = new InventoryLocationClientImpl(baseUri, userToken, requestRetryConfiguration);
	}

	@Test
	public void givenSomeValidMerchantLocationKeyWhenRetrievingInventoryLocationThenReturnInventoryLocation() {
		final Status expectedResponseStatus = Status.OK;
		final InventoryLocation expectedInventoryLocation = new InventoryLocation();
		expectedInventoryLocation.setName(SOME_NAME);
		final String expectedResponseBody = new JSONObject(expectedInventoryLocation).toString();

		mockGet(expectedResponseStatus, expectedResponseBody);

		final InventoryLocation actualInventoryLocation = inventoryLocationClient
				.getInventoryLocation(SOME_MERCHANT_LOCATION_KEY);

		assertEquals(SOME_MERCHANT_LOCATION_KEY, actualInventoryLocation.getMerchantLocationKey());
		assertEquals(SOME_NAME, actualInventoryLocation.getName());
	}

	@Test(expected = EbayNotFoundResponseException.class)
	public void givenSomeInvalidMerchantLocationKeyWhenRetrievingInventoryLocationThenThrowNewEbayNotFoundResponseException() {
		final Status expectedResponseStatus = Status.NOT_FOUND;
		final String expectedResponseBody = new JSONObject(new ErrorResponse()).toString();

		mockGet(expectedResponseStatus, expectedResponseBody);

		inventoryLocationClient.getInventoryLocation(SOME_MERCHANT_LOCATION_KEY);
	}

	@Test
	public void givenSomeInventoryItemWhenCreatingInventoryLocationThenReturn204StatusCode() {
		final Status expectedResponseStatus = Status.NO_CONTENT;
		final InventoryLocation inventoryLocation = new InventoryLocation();
		inventoryLocation.setMerchantLocationKey(SOME_MERCHANT_LOCATION_KEY);

		final JsonBodyCapture actualResponseBody = mockCreate(expectedResponseStatus, SOME_OAUTH_USER_TOKEN);

		inventoryLocationClient.createInventoryLocation(inventoryLocation);

		final JsonNode actualResponseBodyJsonNode = actualResponseBody.getContent();
		assertEquals(SOME_MERCHANT_LOCATION_KEY, actualResponseBodyJsonNode.get("merchantLocationKey").asText());
	}

	@Test
	public void givenSomeInventoryItemAndExpiredAccessTokenWhenCreatingInventoryLocationThenRefreshAcessTokenAndUpdateInventoryItemAndReturn204StatusCode() {
		mockCreate(Status.UNAUTHORIZED, SOME_OAUTH_USER_TOKEN);

		final Token token = new Token();
		token.setAccessToken(SOME_NEW_OAUTH_USER_TOKEN);
		token.setRefreshToken(SOME_REFRESH_TOKEN);
		when(tokenClient.refreshAccessToken(SOME_REFRESH_TOKEN)).thenReturn(token);

		final Status expectedResponseStatus = Status.NO_CONTENT;
		final InventoryLocation inventoryLocation = new InventoryLocation();
		inventoryLocation.setMerchantLocationKey(SOME_MERCHANT_LOCATION_KEY);

		final JsonBodyCapture actualResponseBody = mockCreate(expectedResponseStatus, SOME_NEW_OAUTH_USER_TOKEN);

		inventoryLocationClient.createInventoryLocation(inventoryLocation);

		final JsonNode actualResponseBodyJsonNode = actualResponseBody.getContent();
		assertEquals(SOME_MERCHANT_LOCATION_KEY, actualResponseBodyJsonNode.get("merchantLocationKey").asText());
	}

	@Test(expected = EbayErrorResponseException.class)
	public void givenSomeInventoryItemWithInvalidAuthorizationWhenCreatingInventoryLocationThenThrowNewEbayErrorExceptionWith401StatusCodeAndSomeEbayErrorMessage() {
		final Status expectedResponseStatus = Status.BAD_REQUEST;
		final InventoryLocation inventoryLocation = new InventoryLocation();
		inventoryLocation.setMerchantLocationKey(SOME_MERCHANT_LOCATION_KEY);

		mockCreate(expectedResponseStatus, SOME_OAUTH_USER_TOKEN);

		inventoryLocationClient.createInventoryLocation(inventoryLocation);
	}

	@Test
	public void givenSomeValidMerchantLocationKeyWhenDeletingInventoryLocationThenReturn200StatusCode() {
		final Status expectedResponseStatus = Status.OK;

		mockDelete(expectedResponseStatus);

		inventoryLocationClient.deleteInventoryLocation(SOME_MERCHANT_LOCATION_KEY);
	}

	@Test(expected = EbayErrorResponseException.class)
	public void givenSomeInvalidMerchantLocationKeyWhenDeletingInventoryLocationThenReturnInventoryLocationWithError() {
		final Status expectedResponseStatus = Status.NOT_FOUND;

		mockDelete(expectedResponseStatus);

		inventoryLocationClient.deleteInventoryLocation(SOME_MERCHANT_LOCATION_KEY);
	}

	private void mockGet(final Status expectedResponseStatus, final String expectedResponseBody) {
		driver.addExpectation(
				onRequestTo(new StringBuilder().append(InventoryLocationClientImpl.INVENTORY_LOCATION_RESOURCE)
						.append(FORWARD_SLASH).append(SOME_MERCHANT_LOCATION_KEY).toString())
								.withHeader(InventoryLocationClientImpl.AUTHORIZATION_HEADER,
										new StringBuilder().append(InventoryLocationClientImpl.OAUTH_USER_TOKEN_PREFIX)
												.append(SOME_OAUTH_USER_TOKEN).toString())
								.withMethod(Method.GET),
				giveResponse(expectedResponseBody, MediaType.APPLICATION_JSON)
						.withStatus(expectedResponseStatus.getStatusCode()));
	}

	private JsonBodyCapture mockCreate(final Status expectedResponseStatus, final String expectedOauthUserToken) {
		final JsonBodyCapture jsonBodyCapture = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(new StringBuilder().append(InventoryLocationClientImpl.INVENTORY_LOCATION_RESOURCE)
						.append(FORWARD_SLASH).append(SOME_MERCHANT_LOCATION_KEY).toString())
								.withHeader(InventoryLocationClientImpl.AUTHORIZATION_HEADER,
										new StringBuilder().append(InventoryLocationClientImpl.OAUTH_USER_TOKEN_PREFIX)
												.append(expectedOauthUserToken).toString())
								.withMethod(Method.POST).capturingBodyIn(jsonBodyCapture),
				giveEmptyResponse().withStatus(expectedResponseStatus.getStatusCode()));
		return jsonBodyCapture;
	}

	private void mockDelete(final Status expectedResponseStatus) {
		driver.addExpectation(
				onRequestTo(new StringBuilder().append(InventoryLocationClientImpl.INVENTORY_LOCATION_RESOURCE)
						.append(FORWARD_SLASH).append(SOME_MERCHANT_LOCATION_KEY).toString())
								.withHeader(InventoryLocationClientImpl.AUTHORIZATION_HEADER,
										new StringBuilder().append(InventoryLocationClientImpl.OAUTH_USER_TOKEN_PREFIX)
												.append(SOME_OAUTH_USER_TOKEN).toString())
								.withMethod(Method.DELETE),
				giveEmptyResponse().withStatus(expectedResponseStatus.getStatusCode()));
	}

}
