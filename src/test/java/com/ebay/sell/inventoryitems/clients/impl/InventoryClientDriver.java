package com.ebay.sell.inventoryitems.clients.impl;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientProperties;
import org.junit.Ignore;
import org.junit.Test;

import com.ebay.exceptions.EbayErrorException;
import com.ebay.sell.inventoryitems.clients.InventoryClient;
import com.ebay.sell.inventoryitems.models.InventoryItem;
import com.ebay.sell.inventoryitems.models.InventoryItems;

public class InventoryClientDriver {

	private final Client REST_CLIENT = ClientBuilder.newClient()
			.property(ClientProperties.CONNECT_TIMEOUT, 60000)
			.property(ClientProperties.READ_TIMEOUT, 600000);
	private static final String OAUTH_USER_TOKEN = System
			.getenv("EBAY_OAUTH_USER_TOKEN");

	private final InventoryClient inventoryClient = new InventoryClientImpl(
			REST_CLIENT, OAUTH_USER_TOKEN);

	@Test
	@Ignore
	public void givenSomeSkuWhenRetrievingInventoryItemThenReturnInventoryItem()
			throws Exception {
		final InventoryItem actualInventoryItem = inventoryClient
				.getInventoryItem("540008");

		assertEquals("540008", actualInventoryItem.getSku());
		assertEquals("NEW", actualInventoryItem.getCondition());
	}

	@Test(expected = EbayErrorException.class)
	@Ignore
	public void givenSomeInvalidSkuWhenRetrievingInventoryItemThenThrowNewEbayErrorException()
			throws Exception {
		inventoryClient.getInventoryItem("540009103184");
	}

	@Test
	public void givenSomeLimitAndSomeOffsetWhenRetrievingInventoryItemsThenReturnInventoryItems()
			throws Exception {
		final InventoryItems actualInventoryItems = inventoryClient
				.getInventoryItems(2, 1);

		assertEquals(1, actualInventoryItems.getLimit());
		assertEquals("/sell/inventory/v1/inventory_item?offset=2&limit=1",
				actualInventoryItems.getHref());
		assertEquals("/sell/inventory/v1/inventory_item?offset=3&limit=1",
				actualInventoryItems.getNext());
		assertEquals(1, actualInventoryItems.getInventoryItems().size());
	}

}
