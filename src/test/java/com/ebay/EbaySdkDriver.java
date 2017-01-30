package com.ebay;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ebay.sell.inventory.inventoryitems.models.InventoryItem;

public class EbaySdkDriver {

	private static final String CLIENT_ID = System.getenv("EBAY_CLIENT_ID");
	private static final String CLIENT_SECRET = System.getenv("EBAY_CLIENT_SECRET");
	private static final String REFRESH_TOKEN = System.getenv("EBAY_REFRESH_TOKEN");

	@Test
	public void givenSkuWhenRetrievingInventoryItemThenReturnInventoryItem() {
		final EbaySdk ebaySdk = new EbaySdk(CLIENT_ID, CLIENT_SECRET, REFRESH_TOKEN, true);

		final InventoryItem actualInventoryItem = ebaySdk.getInventoryItem("540008");

		assertEquals("540008", actualInventoryItem.getSku());
		assertEquals("NEW", actualInventoryItem.getCondition());
	}

}
