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

public class InventoryClientDriver {

	private final Client REST_CLIENT = ClientBuilder.newClient()
			.property(ClientProperties.CONNECT_TIMEOUT, 60000)
			.property(ClientProperties.READ_TIMEOUT, 600000);
	private static final String OAUTH_USER_TOKEN = System
			.getenv("EBAY_OAUTH_USER_TOKEN");

	@Test
	@Ignore
	public void givenSomeSkuWhenRetrievingInventoryItemThenReturnInventoryItem()
			throws Exception {
		final InventoryClient inventoryClient = new InventoryClientImpl(
				REST_CLIENT, OAUTH_USER_TOKEN);
		final InventoryItem actualInventoryItem = inventoryClient.get("540008");

		assertEquals("540008", actualInventoryItem.getSku());
		assertEquals("NEW", actualInventoryItem.getCondition());
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeInvalidSkuWhenRetrievingInventoryItemThenThrowNewEbayErrorException()
			throws Exception {
		final InventoryClient inventoryClient = new InventoryClientImpl(
				REST_CLIENT, OAUTH_USER_TOKEN);
		inventoryClient.get("540009103184");
	}

}
