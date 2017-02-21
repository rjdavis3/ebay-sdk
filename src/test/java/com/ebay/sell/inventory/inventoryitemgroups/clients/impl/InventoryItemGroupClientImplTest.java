package com.ebay.sell.inventory.inventoryitemgroups.clients.impl;

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
import com.ebay.sell.inventory.inventoryitemgroups.clients.InventoryItemGroupClient;
import com.ebay.sell.inventory.inventoryitemgroups.models.InventoryItemGroup;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.github.restdriver.clientdriver.capture.JsonBodyCapture;

public class InventoryItemGroupClientImplTest {

	private static final char FORWARD_SLASH = '/';
	private static final String SOME_OAUTH_USER_TOKEN = "v1-ebay-oauth-token";
	private static final String SOME_NEW_OAUTH_USER_TOKEN = "v1-ebay-oauth-token-1";
	private static final String SOME_INVENTORY_ITEM_GROUP_KEY = "1444";
	private static final String SOME_TITLE = "Clif Bar Energy Bar";
	private static final String SOME_REFRESH_TOKEN = "some-refresh-token";

	private InventoryItemGroupClient inventoryItemGroupClient;

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
		inventoryItemGroupClient = new InventoryItemGroupClientImpl(baseUri, userToken, requestRetryConfiguration);
	}

	@Test
	public void givenSomeValidInventoryItemGroupKeyWhenRetrievingInventoryItemGroupThenReturnInventoryItemGroup() {
		final Status expectedResponseStatus = Status.OK;
		final InventoryItemGroup expectedInventoryItemGroup = new InventoryItemGroup();
		expectedInventoryItemGroup.setTitle(SOME_TITLE);
		final String expectedResponseBody = new JSONObject(expectedInventoryItemGroup).toString();

		mockGet(expectedResponseStatus, expectedResponseBody);

		final InventoryItemGroup actualInventoryItemGroup = inventoryItemGroupClient
				.getInventoryItemGroup(SOME_INVENTORY_ITEM_GROUP_KEY);

		assertEquals(SOME_INVENTORY_ITEM_GROUP_KEY, actualInventoryItemGroup.getInventoryItemGroupKey());
		assertEquals(SOME_TITLE, actualInventoryItemGroup.getTitle());
	}

	@Test(expected = EbayNotFoundResponseException.class)
	public void givenSomeInvalidInventoryItemGroupKeyWhenRetrievingInventoryItemGroupThenThrowNewEbayNotFoundResponseException() {
		final Status expectedResponseStatus = Status.NOT_FOUND;
		final ErrorResponse errorResponse = new ErrorResponse();
		final String expectedResponseBody = new JSONObject(errorResponse).toString();

		mockGet(expectedResponseStatus, expectedResponseBody);

		inventoryItemGroupClient.getInventoryItemGroup(SOME_INVENTORY_ITEM_GROUP_KEY);
	}

	@Test
	public void givenSomeInventoryItemWhenUpdatingInventoryItemGroupThenReturn204StatusCode() {
		final Status expectedResponseStatus = Status.NO_CONTENT;
		final InventoryItemGroup inventoryItemGroup = new InventoryItemGroup();
		inventoryItemGroup.setInventoryItemGroupKey(SOME_INVENTORY_ITEM_GROUP_KEY);

		final JsonBodyCapture actualResponseBody = mockPut(expectedResponseStatus, SOME_OAUTH_USER_TOKEN);

		inventoryItemGroupClient.updateInventoryItemGroup(inventoryItemGroup);

		final JsonNode actualResponseBodyJsonNode = actualResponseBody.getContent();
		assertEquals(SOME_INVENTORY_ITEM_GROUP_KEY, actualResponseBodyJsonNode.get("inventoryItemGroupKey").asText());
	}

	@Test
	public void givenSomeInventoryItemAndExpiredAccessTokenWhenUpdatingInventoryItemGroupThenRefreshAcessTokenAndUpdateInventoryItemAndReturn204StatusCode() {
		mockPut(Status.UNAUTHORIZED, SOME_OAUTH_USER_TOKEN);

		final Token token = new Token();
		token.setAccessToken(SOME_NEW_OAUTH_USER_TOKEN);
		token.setRefreshToken(SOME_REFRESH_TOKEN);
		when(tokenClient.refreshAccessToken(SOME_REFRESH_TOKEN)).thenReturn(token);

		final Status expectedResponseStatus = Status.NO_CONTENT;
		final InventoryItemGroup inventoryItemGroup = new InventoryItemGroup();
		inventoryItemGroup.setInventoryItemGroupKey(SOME_INVENTORY_ITEM_GROUP_KEY);

		final JsonBodyCapture actualResponseBody = mockPut(expectedResponseStatus, SOME_NEW_OAUTH_USER_TOKEN);

		inventoryItemGroupClient.updateInventoryItemGroup(inventoryItemGroup);

		final JsonNode actualResponseBodyJsonNode = actualResponseBody.getContent();
		assertEquals(SOME_INVENTORY_ITEM_GROUP_KEY, actualResponseBodyJsonNode.get("inventoryItemGroupKey").asText());
	}

	@Test(expected = EbayErrorResponseException.class)
	public void givenSomeInventoryItemGroupWithInvalidTitleWhenUpdatingInventoryItemGroupThenThrowNewEbayErrorResponseException() {
		final Status expectedResponseStatus = Status.BAD_REQUEST;
		final InventoryItemGroup inventoryItemGroup = new InventoryItemGroup();
		inventoryItemGroup.setInventoryItemGroupKey(SOME_INVENTORY_ITEM_GROUP_KEY);

		mockPut(expectedResponseStatus, SOME_OAUTH_USER_TOKEN);

		inventoryItemGroupClient.updateInventoryItemGroup(inventoryItemGroup);
	}

	@Test
	public void givenSomeValidInventoryItemGroupKeyWhenDeletingInventoryItemGroupThenReturn204StatusCode() {
		final Status expectedResponseStatus = Status.NO_CONTENT;

		mockDelete(expectedResponseStatus);

		inventoryItemGroupClient.deleteInventoryItemGroup(SOME_INVENTORY_ITEM_GROUP_KEY);
	}

	@Test(expected = EbayErrorResponseException.class)
	public void givenSomeInvalidInventoryItemGroupKeyWhenDeletingInventoryItemGroupThenReturnInventoryItemGroupWithError() {
		final Status expectedResponseStatus = Status.NOT_FOUND;

		mockDelete(expectedResponseStatus);

		inventoryItemGroupClient.deleteInventoryItemGroup(SOME_INVENTORY_ITEM_GROUP_KEY);
	}

	private void mockGet(final Status expectedResponseStatus, final String expectedResponseBody) {
		driver.addExpectation(
				onRequestTo(new StringBuilder().append(InventoryItemGroupClientImpl.INVENTORY_ITEM_GROUP_RESOURCE)
						.append(FORWARD_SLASH).append(SOME_INVENTORY_ITEM_GROUP_KEY).toString())
								.withHeader(InventoryItemGroupClientImpl.AUTHORIZATION_HEADER,
										new StringBuilder().append(InventoryItemGroupClientImpl.OAUTH_USER_TOKEN_PREFIX)
												.append(SOME_OAUTH_USER_TOKEN).toString())
								.withMethod(Method.GET),
				giveResponse(expectedResponseBody, MediaType.APPLICATION_JSON)
						.withStatus(expectedResponseStatus.getStatusCode()));
	}

	private JsonBodyCapture mockPut(final Status expectedResponseStatus, final String expectedOauthUserToken) {
		final JsonBodyCapture jsonBodyCapture = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(new StringBuilder().append(InventoryItemGroupClientImpl.INVENTORY_ITEM_GROUP_RESOURCE)
						.append(FORWARD_SLASH).append(SOME_INVENTORY_ITEM_GROUP_KEY).toString())
								.withHeader(InventoryItemGroupClientImpl.AUTHORIZATION_HEADER,
										new StringBuilder().append(InventoryItemGroupClientImpl.OAUTH_USER_TOKEN_PREFIX)
												.append(expectedOauthUserToken).toString())
								.withMethod(Method.PUT).capturingBodyIn(jsonBodyCapture),
				giveEmptyResponse().withStatus(expectedResponseStatus.getStatusCode()));
		return jsonBodyCapture;
	}

	private void mockDelete(final Status expectedResponseStatus) {
		driver.addExpectation(
				onRequestTo(new StringBuilder().append(InventoryItemGroupClientImpl.INVENTORY_ITEM_GROUP_RESOURCE)
						.append(FORWARD_SLASH).append(SOME_INVENTORY_ITEM_GROUP_KEY).toString())
								.withHeader(InventoryItemGroupClientImpl.AUTHORIZATION_HEADER,
										new StringBuilder().append(InventoryItemGroupClientImpl.OAUTH_USER_TOKEN_PREFIX)
												.append(SOME_OAUTH_USER_TOKEN).toString())
								.withMethod(Method.DELETE),
				giveEmptyResponse().withStatus(expectedResponseStatus.getStatusCode()));
	}

}
