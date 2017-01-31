package com.ebay;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.ebay.sell.inventory.inventoryitems.models.InventoryItem;

public class EbaySdkDriver {

	private static final String CLIENT_ID = System.getenv("EBAY_CLIENT_ID");
	private static final String CLIENT_SECRET = System.getenv("EBAY_CLIENT_SECRET");
	private static final String REFRESH_TOKEN = System.getenv("EBAY_REFRESH_TOKEN");
	private static final String RU_NAME = System.getenv("EBAY_RU_NAME");
	private static final String AUTHORIZATION_CODE = System.getenv("EBAY_AUTHORIZATION_CODE");

	@Test
	public void givenRefreshTokenAndSkuWhenRetrievingInventoryItemThenReturnInventoryItem() {
		final EbaySdk ebaySdk = EbaySdk.newBuilder().withClientId(CLIENT_ID).withClientSecret(CLIENT_SECRET)
				.withRefreshToken(REFRESH_TOKEN).withSandbox(true).build();

		final InventoryItem actualInventoryItem = ebaySdk.getInventoryItem("540008");

		assertEquals("540008", actualInventoryItem.getSku());
		assertEquals("NEW", actualInventoryItem.getCondition());
	}

	@Test
	@Ignore
	public void givenRuNameAndAuthorizationCodeAndSkuWhenRetrievingInventoryItemThenReturnInventoryItem() {
		final EbaySdk ebaySdk = EbaySdk.newBuilder().withClientId(CLIENT_ID).withClientSecret(CLIENT_SECRET)
				.withRuName(RU_NAME).withCode(AUTHORIZATION_CODE).withSandbox(true).build();

		final InventoryItem actualInventoryItem = ebaySdk.getInventoryItem("540008");

		assertEquals("540008", actualInventoryItem.getSku());
		assertEquals("NEW", actualInventoryItem.getCondition());
	}

}
