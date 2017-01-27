package com.ebay.sell.inventory.inventoryitemgroups.clients.impl;

import static com.github.restdriver.clientdriver.RestClientDriver.giveEmptyResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.junit.Assert.assertEquals;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.ebay.exceptions.EbayErrorException;
import com.ebay.sell.inventory.inventoryitemgroups.clients.InventoryItemGroupClient;
import com.ebay.sell.inventory.inventoryitemgroups.models.InventoryItemGroup;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.github.restdriver.clientdriver.capture.JsonBodyCapture;

public class InventoryItemGroupClientImplTest {

	private static final char FORWARD_SLASH = '/';
	private static final String SOME_OAUTH_USER_TOKEN = "v1-ebay-oauth-token";
	private static final String SOME_INVENTORY_ITEM_GROUP_KEY = "1444";
	private static final String SOME_TITLE = "Clif Bar Energy Bar";
	private static final String SOME_EBAY_ERROR_MESSAGE = "{\r\n  \"errors\": [\r\n    {\r\n      \"errorId\": 25710,\r\n      \"domain\": \"API_INVENTORY\",\r\n      \"subdomain\": \"Selling\",\r\n      \"category\": \"REQUEST\",\r\n      \"message\": \"We didn't find the entity you are requesting. Please verify the request\"\r\n    }\r\n  ]\r\n}";

	private InventoryItemGroupClient inventoryItemGroupClient;

	@Rule
	public ClientDriverRule driver = new ClientDriverRule();

	@Before
	public void setUp() {
		final URI baseUri = URI.create(driver.getBaseUrl());
		inventoryItemGroupClient = new InventoryItemGroupClientImpl(baseUri, SOME_OAUTH_USER_TOKEN);
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

	@Test(expected = EbayErrorException.class)
	public void givenSomeInvalidInventoryItemGroupKeyWhenRetrievingInventoryItemGroupThenThrowNewEbayErrorExceptionWith404StatusCodeAndSomeEbayErrorMessage() {
		final Status expectedResponseStatus = Status.NOT_FOUND;
		final String expectedResponseBody = SOME_EBAY_ERROR_MESSAGE;

		mockGet(expectedResponseStatus, expectedResponseBody);

		inventoryItemGroupClient.getInventoryItemGroup(SOME_INVENTORY_ITEM_GROUP_KEY);
	}

	@Test
	public void givenSomeInventoryItemWhenUpdatingInventoryItemGroupThenReturn204StatusCode() {
		final Status expectedResponseStatus = Status.NO_CONTENT;
		final InventoryItemGroup inventoryItemGroup = new InventoryItemGroup();
		inventoryItemGroup.setInventoryItemGroupKey(SOME_INVENTORY_ITEM_GROUP_KEY);

		final JsonBodyCapture actualResponseBody = mockPut(expectedResponseStatus);

		inventoryItemGroupClient.updateInventoryItemGroup(inventoryItemGroup);

		final JsonNode actualResponseBodyJsonNode = actualResponseBody.getContent();
		assertEquals(SOME_INVENTORY_ITEM_GROUP_KEY, actualResponseBodyJsonNode.get("inventoryItemGroupKey").asText());
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeInventoryItemWithInvalidAuthorizationWhenUpdatingInventoryItemGroupThenThrowNewEbayErrorExceptionWith401StatusCodeAndSomeEbayErrorMessage() {
		final Status expectedResponseStatus = Status.BAD_REQUEST;
		final InventoryItemGroup inventoryItemGroup = new InventoryItemGroup();
		inventoryItemGroup.setInventoryItemGroupKey(SOME_INVENTORY_ITEM_GROUP_KEY);

		mockPut(expectedResponseStatus);

		inventoryItemGroupClient.updateInventoryItemGroup(inventoryItemGroup);
	}

	@Test
	public void givenSomeValidInventoryItemGroupKeyWhenDeletingInventoryItemGroupThenReturn204StatusCode() {
		final Status expectedResponseStatus = Status.NO_CONTENT;

		mockDelete(expectedResponseStatus);

		inventoryItemGroupClient.deleteInventoryItemGroup(SOME_INVENTORY_ITEM_GROUP_KEY);
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeInvalidInventoryItemGroupKeyWhenDeletingInventoryItemGroupThenThrowNewEbayErrorExceptionWith404StatusCodeAndSomeEbayErrorMessage() {
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

	private JsonBodyCapture mockPut(final Status expectedResponseStatus) {
		final JsonBodyCapture jsonBodyCapture = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(new StringBuilder().append(InventoryItemGroupClientImpl.INVENTORY_ITEM_GROUP_RESOURCE)
						.append(FORWARD_SLASH).append(SOME_INVENTORY_ITEM_GROUP_KEY).toString())
								.withHeader(InventoryItemGroupClientImpl.AUTHORIZATION_HEADER,
										new StringBuilder().append(InventoryItemGroupClientImpl.OAUTH_USER_TOKEN_PREFIX)
												.append(SOME_OAUTH_USER_TOKEN).toString())
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
