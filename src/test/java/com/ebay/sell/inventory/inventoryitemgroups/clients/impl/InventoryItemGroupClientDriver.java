package com.ebay.sell.inventory.inventoryitemgroups.clients.impl;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;

import com.ebay.EbaySdk;
import com.ebay.identity.oauth2.token.models.UserToken;
import com.ebay.identity.ouath2.token.clients.impl.TokenClientImpl;
import com.ebay.models.RequestRetryConfiguration;
import com.ebay.sell.inventory.inventoryitemgroups.clients.InventoryItemGroupClient;
import com.ebay.sell.inventory.inventoryitemgroups.models.InventoryItemGroup;

public class InventoryItemGroupClientDriver {

	private static final String CLIENT_ID = System.getenv("EBAY_CLIENT_ID");
	private static final String CLIENT_SECRET = System.getenv("EBAY_CLIENT_SECRET");
	private static final String REFRESH_TOKEN = System.getenv("EBAY_REFRESH_TOKEN");

	private final InventoryItemGroupClient inventoryItemGroupClient = new InventoryItemGroupClientImpl(
			EbaySdk.SANDBOX_URI,
			new UserToken(new TokenClientImpl(EbaySdk.SANDBOX_URI, CLIENT_ID, CLIENT_SECRET), REFRESH_TOKEN),
			RequestRetryConfiguration.newBuilder().withMininumWait(5, TimeUnit.SECONDS).withTimeout(2, TimeUnit.MINUTES)
					.build());

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
