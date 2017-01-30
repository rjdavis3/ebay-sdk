package com.ebay.sell.inventory.inventoryitems.clients.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.ebay.EbaySdk;
import com.ebay.exceptions.EbayErrorException;
import com.ebay.identity.oauth2.token.models.UserToken;
import com.ebay.identity.ouath2.token.clients.impl.TokenClientImpl;
import com.ebay.sell.inventory.inventoryitems.clients.InventoryItemClient;
import com.ebay.sell.inventory.inventoryitems.models.InventoryItem;
import com.ebay.sell.inventory.inventoryitems.models.InventoryItems;

public class InventoryItemClientDriver {

	private static final String CLIENT_ID = System.getenv("EBAY_CLIENT_ID");
	private static final String CLIENT_SECRET = System.getenv("EBAY_CLIENT_SECRET");
	private static final String REFRESH_TOKEN = System.getenv("EBAY_REFRESH_TOKEN");

	private final InventoryItemClient inventoryItemClient = new InventoryItemClientImpl(EbaySdk.SANDBOX_URI,
			new UserToken(new TokenClientImpl(EbaySdk.SANDBOX_URI, CLIENT_ID, CLIENT_SECRET), REFRESH_TOKEN));

	@Test
	public void givenSomeSkuWhenRetrievingInventoryItemThenReturnInventoryItem() throws Exception {
		final InventoryItem actualInventoryItem = inventoryItemClient.getInventoryItem("540008");

		assertEquals("540008", actualInventoryItem.getSku());
		assertEquals("NEW", actualInventoryItem.getCondition());
	}

	@Test(expected = EbayErrorException.class)
	@Ignore
	public void givenSomeInvalidSkuWhenRetrievingInventoryItemThenThrowNewEbayErrorException() throws Exception {
		inventoryItemClient.getInventoryItem("540009103184");
	}

	@Test
	public void givenSomeLimitAndSomeOffsetWhenRetrievingInventoryItemsThenReturnInventoryItems() throws Exception {
		final InventoryItems actualInventoryItems = inventoryItemClient.getInventoryItems(2, 1);

		assertEquals(1, actualInventoryItems.getLimit());
		assertEquals("/sell/inventory/v1/inventory_item?offset=2&limit=1", actualInventoryItems.getHref());
		assertEquals("/sell/inventory/v1/inventory_item?offset=3&limit=1", actualInventoryItems.getNext());
		assertEquals(1, actualInventoryItems.getInventoryItems().size());
	}

}
