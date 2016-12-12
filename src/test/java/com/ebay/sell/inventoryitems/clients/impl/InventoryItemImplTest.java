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

import com.ebay.sell.inventoryitems.clients.InventoryClient;
import com.ebay.sell.inventoryitems.models.InventoryItem;

public class InventoryItemImplTest {

	private static final String SOME_OAUTH_USER_TOKEN = "v1-ebay-oauth-token";
	private static final String SOME_SKU = "1444";

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
		when(client.target(InventoryClientImpl.INVENTORY_ITEM_RESOURCE)).thenReturn(webTarget);
		when(webTarget.path(SOME_SKU)).thenReturn(webTarget);
		final Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
		when(webTarget.request()).thenReturn(invocationBuilder);
		when(invocationBuilder.header(eq(InventoryClientImpl.AUTHORIZATION_HEADER), anyString()))
				.thenReturn(invocationBuilder);
		final Response response = mock(Response.class);
		final int statusCode = expectedStatus.getStatusCode();
		when(response.getStatus()).thenReturn(statusCode);
		when(response.readEntity(InventoryItem.class)).thenReturn(expectedInventoryItem);
		when(invocationBuilder.get()).thenReturn(response);

		final InventoryItem actualInventoryItem = inventoryClient.get(SOME_SKU);

		assertEquals(SOME_SKU, actualInventoryItem.getSku());
	}

}
