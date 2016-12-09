package com.ebay.sell.inventoryitems.clients.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ebay.sell.inventoryitems.clients.InventoryClient;
import com.ebay.sell.inventoryitems.models.InventoryItem;

public class InventoryItemClientDriver {

	private static final String OAUTH_USER_TOKEN = System.getenv("EBAY_OAUTH_USER_TOKEN");
	
	@Test
	public void givenSomeSkuWhenRetrievingInventoryItemThenReturnInventoryItem() throws Exception {
		final InventoryClient inventoryClient = new InventoryClientImpl(OAUTH_USER_TOKEN);
		final InventoryItem actualInventoryItem = inventoryClient.get("540008");
		
		assertEquals("540008", actualInventoryItem.getSku());
		assertEquals("NEW", actualInventoryItem.getCondition());
	}
	
}
