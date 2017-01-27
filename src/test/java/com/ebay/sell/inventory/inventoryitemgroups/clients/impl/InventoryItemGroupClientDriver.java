package com.ebay.sell.inventory.inventoryitemgroups.clients.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.ebay.EbaySdk;
import com.ebay.sell.inventory.inventoryitemgroups.clients.InventoryItemGroupClient;
import com.ebay.sell.inventory.inventoryitemgroups.models.InventoryItemGroup;

public class InventoryItemGroupClientDriver {

	private static final String OAUTH_USER_TOKEN = System.getenv("EBAY_OAUTH_USER_TOKEN");

	private final InventoryItemGroupClient inventoryItemGroupClient = new InventoryItemGroupClientImpl(
			EbaySdk.SANDBOX_URI, OAUTH_USER_TOKEN);

	@Test
	@Ignore
	public void givenSomeInventoryItemGroupKeyWhenRetrievingInventoryItemGroupThenReturnInventoryItemGroup()
			throws Exception {
		final String inventoryItemGroupKey = "91a4597d-70a1-4e35-8c98-4995ca25be58";
		final InventoryItemGroup actualInventoryItemGroup = inventoryItemGroupClient
				.getInventoryItemGroup(inventoryItemGroupKey);

		assertEquals("Clif Bar Energy Bar", actualInventoryItemGroup.getTitle());
	}

	@Test
	public void givenSomeInventoryItemGroupWhenUpdatingInventoryItemGroupThenReturn204StatusCode() throws Exception {
		final InventoryItemGroup inventoryItemGroup = inventoryItemGroupClient
				.getInventoryItemGroup("91a4597d-70a1-4e35-8c98-4995ca25be58");
		inventoryItemGroup.setSubtitle("Great tasting energy bar!");

		inventoryItemGroupClient.updateInventoryItemGroup(inventoryItemGroup);
	}
}
