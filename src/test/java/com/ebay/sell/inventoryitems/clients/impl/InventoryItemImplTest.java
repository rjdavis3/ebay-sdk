package com.ebay.sell.inventoryitems.clients.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ebay.exceptions.EbayErrorException;
import com.ebay.sell.inventoryitems.clients.InventoryClient;
import com.ebay.sell.inventoryitems.models.InventoryItem;

public class InventoryItemImplTest {

	private static final String SOME_OAUTH_USER_TOKEN = "v1-ebay-oauth-token";
	private static final String SOME_SKU = "1444";
	private static final String SOME_NOT_FOUND_INVENTORY_ITEM_EBAY_ERROR = "{\r\n  \"errors\": [\r\n    {\r\n      \"errorId\": 25710,\r\n      \"domain\": \"API_INVENTORY\",\r\n      \"subdomain\": \"Selling\",\r\n      \"category\": \"REQUEST\",\r\n      \"message\": \"We didn't find the entity you are requesting. Please verify the request\"\r\n    }\r\n  ]\r\n}";

	private InventoryClient inventoryClient;

	@Mock
	private Client client;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		inventoryClient = new InventoryClientImpl(client, SOME_OAUTH_USER_TOKEN);
	}

	@Test
	public void givenSomeValidSkuWhenRetrievingInventoryItemThenReturnInventoryItem() {
		final Status expectedStatus = Status.OK;
		final InventoryItem expectedInventoryItem = new InventoryItem();
		expectedInventoryItem.setSku(SOME_SKU);

		final WebTarget webTarget = mock(WebTarget.class);
		when(client.target(InventoryClientImpl.INVENTORY_ITEM_RESOURCE))
				.thenReturn(webTarget);
		when(webTarget.path(SOME_SKU)).thenReturn(webTarget);
		final Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
		when(webTarget.request()).thenReturn(invocationBuilder);
		when(
				invocationBuilder.header(
						eq(InventoryClientImpl.AUTHORIZATION_HEADER),
						anyString())).thenReturn(invocationBuilder);
		final Response response = mock(Response.class);
		final int statusCode = expectedStatus.getStatusCode();
		when(response.getStatus()).thenReturn(statusCode);
		when(response.readEntity(InventoryItem.class)).thenReturn(
				expectedInventoryItem);
		when(invocationBuilder.get()).thenReturn(response);

		final InventoryItem actualInventoryItem = inventoryClient.get(SOME_SKU);

		assertEquals(SOME_SKU, actualInventoryItem.getSku());
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeInvalidSkuWhenRetrievingInventoryItemThenThrowNewEbayErrorExceptionWith404StatusCodeAnd25710ErrorId() {
		final Status expectedStatus = Status.NOT_FOUND;
		final InventoryItem expectedInventoryItem = new InventoryItem();
		expectedInventoryItem.setSku(SOME_SKU);

		final WebTarget webTarget = mock(WebTarget.class);
		when(client.target(InventoryClientImpl.INVENTORY_ITEM_RESOURCE))
				.thenReturn(webTarget);
		when(webTarget.path(SOME_SKU)).thenReturn(webTarget);
		final Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
		when(webTarget.request()).thenReturn(invocationBuilder);
		when(
				invocationBuilder.header(
						eq(InventoryClientImpl.AUTHORIZATION_HEADER),
						anyString())).thenReturn(invocationBuilder);
		final Response response = mock(Response.class);
		final int statusCode = expectedStatus.getStatusCode();
		when(response.getStatus()).thenReturn(statusCode);
		when(response.readEntity(String.class)).thenReturn(
				SOME_NOT_FOUND_INVENTORY_ITEM_EBAY_ERROR);
		when(invocationBuilder.get()).thenReturn(response);

		inventoryClient.get(SOME_SKU);
	}
}
