package com.ebay.sell.inventory.inventoryitems.clients.impl;

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
import com.ebay.sell.inventory.inventoryitems.clients.InventoryItemClient;
import com.ebay.sell.inventory.inventoryitems.models.InventoryItem;
import com.ebay.sell.inventory.inventoryitems.models.InventoryItems;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.github.restdriver.clientdriver.capture.JsonBodyCapture;

public class InventoryItemClientImplTest {

	private static final char FORWARD_SLASH = '/';
	private static final String SOME_OAUTH_USER_TOKEN = "v1-ebay-oauth-token";
	private static final String SOME_SKU = "1444";
	private static final String SOME_EBAY_ERROR_MESSAGE = "{\r\n  \"errors\": [\r\n    {\r\n      \"errorId\": 25710,\r\n      \"domain\": \"API_INVENTORY\",\r\n      \"subdomain\": \"Selling\",\r\n      \"category\": \"REQUEST\",\r\n      \"message\": \"We didn't find the entity you are requesting. Please verify the request\"\r\n    }\r\n  ]\r\n}";

	private InventoryItemClient inventoryItemClient;

	@Rule
	public ClientDriverRule driver = new ClientDriverRule();

	@Before
	public void setUp() {
		final URI baseUri = URI.create(driver.getBaseUrl());
		inventoryItemClient = new InventoryItemClientImpl(baseUri, SOME_OAUTH_USER_TOKEN);
	}

	@Test
	public void givenSomeValidSkuWhenRetrievingInventoryItemThenReturnInventoryItem() {
		final Status expectedResponseStatus = Status.OK;
		final InventoryItem expectedInventoryItem = new InventoryItem();
		expectedInventoryItem.setSku(SOME_SKU);

		final String expectedResponseBody = new JSONObject(expectedInventoryItem).toString();
		mockGetInventoryItem(expectedResponseStatus, expectedResponseBody);

		final InventoryItem actualInventoryItem = inventoryItemClient.getInventoryItem(SOME_SKU);

		assertEquals(SOME_SKU, actualInventoryItem.getSku());
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeInvalidSkuWhenRetrievingInventoryItemThenThrowNewEbayErrorExceptionWith404StatusCodeAndSomeEbayErrorMessage() {
		final Status expectedResponseStatus = Status.NOT_FOUND;

		final String expectedResponseBody = SOME_EBAY_ERROR_MESSAGE;
		mockGetInventoryItem(expectedResponseStatus, expectedResponseBody);

		inventoryItemClient.getInventoryItem(SOME_SKU);
	}

	@Test
	public void givenSomeInventoryItemWhenUpdatingInventoryItemThenReturn204StatusCode() {
		final Status expectedResponseStatus = Status.NO_CONTENT;

		final InventoryItem inventoryItem = new InventoryItem();
		inventoryItem.setSku(SOME_SKU);

		final JsonBodyCapture actualResponseBody = mockUpdateInventoryItem(expectedResponseStatus);

		inventoryItemClient.updateInventoryItem(inventoryItem);

		final JsonNode actualResponseBodyJsonNode = actualResponseBody.getContent();
		assertEquals(SOME_SKU, actualResponseBodyJsonNode.get("sku").asText());
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeInventoryItemWithInvalidConditionWhenUpdatingInventoryItemThenThrowNewEbayErrorExceptionWith400StatusCodeAndSomeEbayErrorMessage() {
		final Status expectedResponseStatus = Status.BAD_REQUEST;

		final InventoryItem inventoryItem = new InventoryItem();
		inventoryItem.setSku(SOME_SKU);
		inventoryItem.setCondition("JUNK");

		mockUpdateInventoryItem(expectedResponseStatus);

		inventoryItemClient.updateInventoryItem(inventoryItem);
	}

	@Test
	public void givenSomeValidSkuWhenDeletingInventoryItemThenReturn204StatusCode() {
		final Status expectedResponseStatus = Status.NO_CONTENT;

		mockDeleteInventoryItem(expectedResponseStatus);

		inventoryItemClient.deleteInventoryItem(SOME_SKU);
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeInvalidSkuWhenDeletingInventoryItemThenThrowNewEbayErrorExceptionWith404StatusCodeAndSomeEbayErrorMessage() {
		final Status expectedResponseStatus = Status.NOT_FOUND;

		mockDeleteInventoryItem(expectedResponseStatus);

		inventoryItemClient.deleteInventoryItem(SOME_SKU);
	}

	@Test
	public void givenSomeLimitAndSomeOffsetWhenRetrievingInventoryItemsThenReturnInventoryItems() {
		final int limit = 1;
		final int offset = 2;

		final Status expectedResponseStatus = Status.OK;
		final InventoryItems expectedInventoryItems = new InventoryItems();
		expectedInventoryItems.setLimit(limit);
		final String expectedResponseBody = new JSONObject(expectedInventoryItems).toString();

		driver.addExpectation(
				onRequestTo(InventoryItemClientImpl.INVENTORY_ITEM_RESOURCE)
						.withParam(InventoryItemClientImpl.LIMIT_QUERY_PARAMETER, limit)
						.withParam(InventoryItemClientImpl.OFFSET_QUERY_PARAMETER, offset)
						.withHeader(InventoryItemClientImpl.AUTHORIZATION_HEADER,
								new StringBuilder().append(InventoryItemClientImpl.OAUTH_USER_TOKEN_PREFIX)
										.append(SOME_OAUTH_USER_TOKEN).toString())
						.withMethod(Method.GET),
				giveResponse(expectedResponseBody, MediaType.APPLICATION_JSON)
						.withStatus(expectedResponseStatus.getStatusCode()));

		final InventoryItems actualInventoryItems = inventoryItemClient.getInventoryItems(offset, limit);

		assertEquals(limit, actualInventoryItems.getLimit());
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeInvalidAuthorizationWhenRetrievingInventoryItemsThenThrowNewEbayErrorExceptionWith401StatusCodeAndSomeEbayErrorMessage() {
		final int limit = 1;
		final int offset = 2;

		final Status expectedResponseStatus = Status.UNAUTHORIZED;
		final String expectedResponseBody = SOME_EBAY_ERROR_MESSAGE;
		driver.addExpectation(
				onRequestTo(InventoryItemClientImpl.INVENTORY_ITEM_RESOURCE)
						.withParam(InventoryItemClientImpl.LIMIT_QUERY_PARAMETER, limit)
						.withParam(InventoryItemClientImpl.OFFSET_QUERY_PARAMETER, offset)
						.withHeader(InventoryItemClientImpl.AUTHORIZATION_HEADER,
								new StringBuilder().append(InventoryItemClientImpl.OAUTH_USER_TOKEN_PREFIX)
										.append(SOME_OAUTH_USER_TOKEN).toString())
						.withMethod(Method.GET),
				giveResponse(expectedResponseBody, MediaType.APPLICATION_JSON)
						.withStatus(expectedResponseStatus.getStatusCode()));

		inventoryItemClient.getInventoryItems(offset, limit);
	}

	private void mockGetInventoryItem(final Status expectedResponseStatus, final String expectedResponseBody) {
		driver.addExpectation(
				onRequestTo(new StringBuilder().append(InventoryItemClientImpl.INVENTORY_ITEM_RESOURCE)
						.append(FORWARD_SLASH).append(SOME_SKU).toString())
								.withHeader(InventoryItemClientImpl.AUTHORIZATION_HEADER,
										new StringBuilder().append(InventoryItemClientImpl.OAUTH_USER_TOKEN_PREFIX)
												.append(SOME_OAUTH_USER_TOKEN).toString())
								.withMethod(Method.GET),
				giveResponse(expectedResponseBody, MediaType.APPLICATION_JSON)
						.withStatus(expectedResponseStatus.getStatusCode()));
	}

	private JsonBodyCapture mockUpdateInventoryItem(final Status expectedResponseStatus) {
		final JsonBodyCapture jsonBodyCapture = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(new StringBuilder().append(InventoryItemClientImpl.INVENTORY_ITEM_RESOURCE)
						.append(FORWARD_SLASH).append(SOME_SKU).toString())
								.withHeader(InventoryItemClientImpl.AUTHORIZATION_HEADER,
										new StringBuilder().append(InventoryItemClientImpl.OAUTH_USER_TOKEN_PREFIX)
												.append(SOME_OAUTH_USER_TOKEN).toString())
								.withMethod(Method.PUT).capturingBodyIn(jsonBodyCapture),
				giveEmptyResponse().withStatus(expectedResponseStatus.getStatusCode()));
		return jsonBodyCapture;
	}

	private void mockDeleteInventoryItem(final Status expectedResponseStatus) {
		driver.addExpectation(
				onRequestTo(new StringBuilder().append(InventoryItemClientImpl.INVENTORY_ITEM_RESOURCE)
						.append(FORWARD_SLASH).append(SOME_SKU).toString())
								.withHeader(InventoryItemClientImpl.AUTHORIZATION_HEADER,
										new StringBuilder().append(InventoryItemClientImpl.OAUTH_USER_TOKEN_PREFIX)
												.append(SOME_OAUTH_USER_TOKEN).toString())
								.withMethod(Method.DELETE),
				giveEmptyResponse().withStatus(expectedResponseStatus.getStatusCode()));
	}
}
