package com.ebay.sell.inventoryitems.clients.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ebay.exceptions.EbayErrorException;
import com.ebay.sell.inventoryitems.clients.InventoryItemClient;
import com.ebay.sell.inventoryitems.models.InventoryItem;
import com.ebay.sell.inventoryitems.models.InventoryItems;

public class InventoryItemClientImplTest {

	private static final String SOME_OAUTH_USER_TOKEN = "v1-ebay-oauth-token";
	private static final String SOME_SKU = "1444";
	private static final String SOME_EBAY_ERROR_MESSAGE = "{\r\n  \"errors\": [\r\n    {\r\n      \"errorId\": 25710,\r\n      \"domain\": \"API_INVENTORY\",\r\n      \"subdomain\": \"Selling\",\r\n      \"category\": \"REQUEST\",\r\n      \"message\": \"We didn't find the entity you are requesting. Please verify the request\"\r\n    }\r\n  ]\r\n}";

	private InventoryItemClient inventoryItemClient;

	@Mock
	private Client client;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		inventoryItemClient = new InventoryItemClientImpl(client, SOME_OAUTH_USER_TOKEN);
	}

	@Test
	public void givenSomeValidSkuWhenRetrievingInventoryItemThenReturnInventoryItem() {
		final Status expectedStatus = Status.OK;
		final InventoryItem expectedInventoryItem = new InventoryItem();
		expectedInventoryItem.setSku(SOME_SKU);

		final WebTarget webTarget = mock(WebTarget.class);
		when(client.target(InventoryItemClientImpl.INVENTORY_ITEM_RESOURCE))
				.thenReturn(webTarget);
		when(webTarget.path(SOME_SKU)).thenReturn(webTarget);
		final Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
		when(webTarget.request()).thenReturn(invocationBuilder);
		when(
				invocationBuilder.header(
						eq(InventoryItemClientImpl.AUTHORIZATION_HEADER),
						anyString())).thenReturn(invocationBuilder);
		final Response response = mock(Response.class);
		final int statusCode = expectedStatus.getStatusCode();
		when(response.getStatus()).thenReturn(statusCode);
		when(response.readEntity(InventoryItem.class)).thenReturn(
				expectedInventoryItem);
		when(invocationBuilder.get()).thenReturn(response);

		final InventoryItem actualInventoryItem = inventoryItemClient
				.getInventoryItem(SOME_SKU);

		assertEquals(SOME_SKU, actualInventoryItem.getSku());
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeInvalidSkuWhenRetrievingInventoryItemThenThrowNewEbayErrorExceptionWith404StatusCodeAndSomeEbayErrorMessage() {
		final Status expectedStatus = Status.NOT_FOUND;
		final InventoryItem expectedInventoryItem = new InventoryItem();
		expectedInventoryItem.setSku(SOME_SKU);

		final WebTarget webTarget = mock(WebTarget.class);
		when(client.target(InventoryItemClientImpl.INVENTORY_ITEM_RESOURCE))
				.thenReturn(webTarget);
		when(webTarget.path(SOME_SKU)).thenReturn(webTarget);
		final Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
		when(webTarget.request()).thenReturn(invocationBuilder);
		when(
				invocationBuilder.header(
						eq(InventoryItemClientImpl.AUTHORIZATION_HEADER),
						anyString())).thenReturn(invocationBuilder);
		final Response response = mock(Response.class);
		final int statusCode = expectedStatus.getStatusCode();
		when(response.getStatus()).thenReturn(statusCode);
		when(response.readEntity(String.class)).thenReturn(
				SOME_EBAY_ERROR_MESSAGE);
		when(invocationBuilder.get()).thenReturn(response);

		inventoryItemClient.getInventoryItem(SOME_SKU);
	}

	@Test
	public void givenSomeInventoryItemWhenUpdatingInventoryItemThenReturn204StatusCode() {
		final InventoryItem inventoryItem = new InventoryItem();
		inventoryItem.setSku(SOME_SKU);
		final Status expectedStatus = Status.NO_CONTENT;

		final WebTarget webTarget = mock(WebTarget.class);
		when(client.target(InventoryItemClientImpl.INVENTORY_ITEM_RESOURCE))
				.thenReturn(webTarget);
		when(webTarget.path(SOME_SKU)).thenReturn(webTarget);
		final Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
		when(webTarget.request()).thenReturn(invocationBuilder);
		when(
				invocationBuilder.header(
						eq(InventoryItemClientImpl.AUTHORIZATION_HEADER),
						anyString())).thenReturn(invocationBuilder);
		final Response response = mock(Response.class);
		final int statusCode = expectedStatus.getStatusCode();
		when(response.getStatus()).thenReturn(statusCode);
		when(invocationBuilder.put(any(Entity.class))).thenReturn(response);

		inventoryItemClient.updateInventoryItem(inventoryItem);
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeInventoryItemWithInvalidConditionWhenUpdatingInventoryItemThenThrowNewEbayErrorExceptionWith400StatusCodeAndSomeEbayErrorMessage() {
		final InventoryItem inventoryItem = new InventoryItem();
		inventoryItem.setSku(SOME_SKU);
		inventoryItem.setCondition("JUNK");
		final Status expectedStatus = Status.BAD_REQUEST;

		final WebTarget webTarget = mock(WebTarget.class);
		when(client.target(InventoryItemClientImpl.INVENTORY_ITEM_RESOURCE))
				.thenReturn(webTarget);
		when(webTarget.path(SOME_SKU)).thenReturn(webTarget);
		final Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
		when(webTarget.request()).thenReturn(invocationBuilder);
		when(
				invocationBuilder.header(
						eq(InventoryItemClientImpl.AUTHORIZATION_HEADER),
						anyString())).thenReturn(invocationBuilder);
		final Response response = mock(Response.class);
		final int statusCode = expectedStatus.getStatusCode();
		when(response.getStatus()).thenReturn(statusCode);
		when(response.readEntity(String.class)).thenReturn(
				SOME_EBAY_ERROR_MESSAGE);
		when(invocationBuilder.put(any(Entity.class))).thenReturn(response);

		inventoryItemClient.updateInventoryItem(inventoryItem);
	}

	@Test
	public void givenSomeValidSkuWhenDeletingInventoryItemThenReturn204StatusCode() {
		final Status expectedStatus = Status.NO_CONTENT;
		final InventoryItem expectedInventoryItem = new InventoryItem();
		expectedInventoryItem.setSku(SOME_SKU);

		final WebTarget webTarget = mock(WebTarget.class);
		when(client.target(InventoryItemClientImpl.INVENTORY_ITEM_RESOURCE))
				.thenReturn(webTarget);
		when(webTarget.path(SOME_SKU)).thenReturn(webTarget);
		final Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
		when(webTarget.request()).thenReturn(invocationBuilder);
		when(
				invocationBuilder.header(
						eq(InventoryItemClientImpl.AUTHORIZATION_HEADER),
						anyString())).thenReturn(invocationBuilder);
		final Response response = mock(Response.class);
		final int statusCode = expectedStatus.getStatusCode();
		when(response.getStatus()).thenReturn(statusCode);
		when(invocationBuilder.delete()).thenReturn(response);

		inventoryItemClient.deleteInventoryItem(SOME_SKU);
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeInvalidSkuWhenDeletingInventoryItemThenThrowNewEbayErrorExceptionWith404StatusCodeAndSomeEbayErrorMessage() {
		final Status expectedStatus = Status.NOT_FOUND;
		final InventoryItem expectedInventoryItem = new InventoryItem();
		expectedInventoryItem.setSku(SOME_SKU);

		final WebTarget webTarget = mock(WebTarget.class);
		when(client.target(InventoryItemClientImpl.INVENTORY_ITEM_RESOURCE))
				.thenReturn(webTarget);
		when(webTarget.path(SOME_SKU)).thenReturn(webTarget);
		final Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
		when(webTarget.request()).thenReturn(invocationBuilder);
		when(
				invocationBuilder.header(
						eq(InventoryItemClientImpl.AUTHORIZATION_HEADER),
						anyString())).thenReturn(invocationBuilder);
		final Response response = mock(Response.class);
		final int statusCode = expectedStatus.getStatusCode();
		when(response.getStatus()).thenReturn(statusCode);
		when(response.readEntity(String.class)).thenReturn(
				SOME_EBAY_ERROR_MESSAGE);
		when(invocationBuilder.delete()).thenReturn(response);

		inventoryItemClient.deleteInventoryItem(SOME_SKU);
	}

	@Test
	public void givenSomeLimitAndSomeOffsetWhenRetrievingInventoryItemsThenReturnInventoryItems() {
		final int limit = 1;
		final int offset = 2;

		final Status expectedStatus = Status.OK;
		final InventoryItems expectedInventoryItems = new InventoryItems();
		expectedInventoryItems.setLimit(limit);

		final WebTarget webTarget = mock(WebTarget.class);
		when(client.target(InventoryItemClientImpl.INVENTORY_ITEM_RESOURCE))
				.thenReturn(webTarget);
		when(
				webTarget.queryParam(InventoryItemClientImpl.LIMIT_QUERY_PARAMETER,
						limit)).thenReturn(webTarget);
		when(
				webTarget.queryParam(
						InventoryItemClientImpl.OFFSET_QUERY_PARAMETER, offset))
				.thenReturn(webTarget);
		final Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
		when(webTarget.request()).thenReturn(invocationBuilder);
		when(
				invocationBuilder.header(
						eq(InventoryItemClientImpl.AUTHORIZATION_HEADER),
						anyString())).thenReturn(invocationBuilder);
		final Response response = mock(Response.class);
		final int statusCode = expectedStatus.getStatusCode();
		when(response.getStatus()).thenReturn(statusCode);
		when(response.readEntity(InventoryItems.class)).thenReturn(
				expectedInventoryItems);
		when(invocationBuilder.get()).thenReturn(response);

		final InventoryItems actualInventoryItems = inventoryItemClient
				.getInventoryItems(offset, limit);

		assertEquals(limit, actualInventoryItems.getLimit());
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeInvalidAuthorizationWhenRetrievingInventoryItemsThenThrowNewEbayErrorExceptionWith401StatusCodeAndSomeEbayErrorMessage() {
		final int limit = 1;
		final int offset = 2;

		final Status expectedStatus = Status.UNAUTHORIZED;

		final WebTarget webTarget = mock(WebTarget.class);
		when(client.target(InventoryItemClientImpl.INVENTORY_ITEM_RESOURCE))
				.thenReturn(webTarget);
		when(
				webTarget.queryParam(InventoryItemClientImpl.LIMIT_QUERY_PARAMETER,
						limit)).thenReturn(webTarget);
		when(
				webTarget.queryParam(
						InventoryItemClientImpl.OFFSET_QUERY_PARAMETER, offset))
				.thenReturn(webTarget);
		final Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
		when(webTarget.request()).thenReturn(invocationBuilder);
		when(
				invocationBuilder.header(
						eq(InventoryItemClientImpl.AUTHORIZATION_HEADER),
						anyString())).thenReturn(invocationBuilder);
		final Response response = mock(Response.class);
		final int statusCode = expectedStatus.getStatusCode();
		when(response.getStatus()).thenReturn(statusCode);
		when(response.readEntity(String.class)).thenReturn(
				SOME_EBAY_ERROR_MESSAGE);
		when(invocationBuilder.get()).thenReturn(response);

		inventoryItemClient.getInventoryItems(offset, limit);
	}

}
