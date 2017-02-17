package com.ebay.sell.inventory.inventoryitems.clients.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;

import com.ebay.EbaySdk;
import com.ebay.exceptions.EbayErrorException;
import com.ebay.identity.oauth2.token.models.UserToken;
import com.ebay.identity.ouath2.token.clients.impl.TokenClientImpl;
import com.ebay.models.RequestRetryConfiguration;
import com.ebay.sell.inventory.inventoryitems.clients.InventoryItemClient;
import com.ebay.sell.inventory.inventoryitems.models.InventoryItem;
import com.ebay.sell.inventory.inventoryitems.models.InventoryItems;

public class InventoryItemClientDriver {

	private static final String CLIENT_ID = System.getenv("EBAY_CLIENT_ID");
	private static final String CLIENT_SECRET = System.getenv("EBAY_CLIENT_SECRET");
	private static final String REFRESH_TOKEN = System.getenv("EBAY_REFRESH_TOKEN");

	private final InventoryItemClient inventoryItemClient = new InventoryItemClientImpl(EbaySdk.SANDBOX_URI,
			new UserToken(new TokenClientImpl(EbaySdk.SANDBOX_URI, CLIENT_ID, CLIENT_SECRET), REFRESH_TOKEN),
			RequestRetryConfiguration.newBuilder().withMininumWait(5, TimeUnit.SECONDS).withTimeout(2, TimeUnit.MINUTES)
					.build());

	@Test
	@Ignore
	public void givenSomeSkuWhenRetrievingInventoryItemThenReturnInventoryItem() {
		final InventoryItem actualInventoryItem = inventoryItemClient.getInventoryItem("540008");

		assertEquals("540008", actualInventoryItem.getSku());
		assertEquals("NEW", actualInventoryItem.getCondition());
	}

	@Test
	@Ignore
	public void givenInvalidSkuWhenRetrievingInventoryItemThenReturnInventoryItemWithErrors() {
		final InventoryItem actualInventoryItem = inventoryItemClient.getInventoryItem("540008-bojangles");

		assertTrue(actualInventoryItem.hasErrors());
	}

	@Test
	public void givenSomeInventoryItemWhenUpdatingWithNewAspectsThenUpdateInventoryItem() {
		final InventoryItem inventoryItem = inventoryItemClient.getInventoryItem("540008");

		final Map<String, List<String>> expectedAspects = new HashMap<>();
		expectedAspects.put("Size", Arrays.asList("12 - 2.12oz (60g) Bars"));
		expectedAspects.put("Flavor", Arrays.asList("Banana Nut Muffin"));

		inventoryItem.getProduct().setAspects(expectedAspects);
		inventoryItemClient.updateInventoryItem(inventoryItem);

		final InventoryItem actualInventoryItem = inventoryItemClient.getInventoryItem("540008");

		assertEquals("540008", actualInventoryItem.getSku());
		assertEquals("NEW", actualInventoryItem.getCondition());
		assertEquals(expectedAspects.get("Flavor"), actualInventoryItem.getProduct().getAspects().get("Flavor"));
		assertEquals(expectedAspects.get("Size"), actualInventoryItem.getProduct().getAspects().get("Size"));
	}

	@Test
	@Ignore
	public void givenSomeInventoryItemWhenUpdatingAspectsThenUpdateInventoryItem() {
		final InventoryItem inventoryItem = inventoryItemClient.getInventoryItem("540008");

		inventoryItem.getProduct().getAspects().get("Flavor").add("Coconut");
		inventoryItemClient.updateInventoryItem(inventoryItem);

		final InventoryItem actualInventoryItem = inventoryItemClient.getInventoryItem("540008");

		assertEquals("540008", actualInventoryItem.getSku());
		assertEquals("NEW", actualInventoryItem.getCondition());
		assertEquals(Arrays.asList("Chocolate Chip Peanut Crunch", "Banana", "Coconut"),
				actualInventoryItem.getProduct().getAspects().get("Flavor"));
		assertEquals(Arrays.asList("12 - 2.4 oz (68 g) bar [28.8 oz (816 g)] box", "Large"),
				actualInventoryItem.getProduct().getAspects().get("Size"));
	}

	@Ignore
	@Test(expected = EbayErrorException.class)
	public void givenSomeInvalidSkuWhenRetrievingInventoryItemThenThrowNewEbayErrorException() throws Exception {
		inventoryItemClient.getInventoryItem("540009103184");
	}

	@Ignore
	@Test
	public void givenSomeLimitAndSomeOffsetWhenRetrievingInventoryItemsThenReturnInventoryItems() throws Exception {
		final InventoryItems actualInventoryItems = inventoryItemClient.getInventoryItems(2, 1);

		assertEquals(1, actualInventoryItems.getLimit());
		assertEquals("/sell/inventory/v1/inventory_item?offset=2&limit=1", actualInventoryItems.getHref());
		assertEquals("/sell/inventory/v1/inventory_item?offset=3&limit=1", actualInventoryItems.getNext());
		assertEquals(1, actualInventoryItems.getInventoryItems().size());
	}

}
