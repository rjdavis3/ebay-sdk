package com.ebay.sell.inventory.locations.clients.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;

import com.ebay.EbaySdk;
import com.ebay.identity.oauth2.token.models.UserToken;
import com.ebay.identity.ouath2.token.clients.impl.TokenClientImpl;
import com.ebay.models.RequestRetryConfiguration;
import com.ebay.sell.inventory.locations.clients.InventoryLocationClient;
import com.ebay.sell.inventory.locations.models.Address;
import com.ebay.sell.inventory.locations.models.InventoryLocation;
import com.ebay.sell.inventory.locations.models.Location;

public class InventoryLocationClientDriver {

	private static final String CLIENT_ID = System.getenv("EBAY_CLIENT_ID");
	private static final String CLIENT_SECRET = System.getenv("EBAY_CLIENT_SECRET");
	private static final String REFRESH_TOKEN = System.getenv("EBAY_REFRESH_TOKEN");

	private final InventoryLocationClient inventoryLocationClient = new InventoryLocationClientImpl(EbaySdk.SANDBOX_URI,
			new UserToken(new TokenClientImpl(EbaySdk.SANDBOX_URI, CLIENT_ID, CLIENT_SECRET), REFRESH_TOKEN),
			RequestRetryConfiguration.newBuilder().withMininumWait(5, TimeUnit.SECONDS).withTimeout(2, TimeUnit.MINUTES)
					.build());

	@Test
	public void givenSomeInventoryLocationKeyWhenRetrievingInventoryLocationThenReturnInventoryLocation()
			throws Exception {
		final String inventoryLocationKey = "Testy_McTestface";
		final InventoryLocation actualInventoryLocation = inventoryLocationClient
				.getInventoryLocation(inventoryLocationKey);

		assertEquals("Testy_McTestface", actualInventoryLocation.getMerchantLocationKey());
		assertFalse(actualInventoryLocation.hasErrors());
	}

	@Test
	@Ignore
	public void givenSomeInventoryLocationWhenCreatingInventoryLocationThenReturn204StatusCode() throws Exception {
		final InventoryLocation inventoryLocation = new InventoryLocation();
		inventoryLocation.setMerchantLocationKey("Testy_McTestface");
		inventoryLocation.setName("Testy_McTestface");
		final Location location = new Location();
		final Address address = new Address();
		address.setAddressLine1("224 Wyoming Avenue");
		address.setAddressLine2("Rear");
		address.setCity("Scranton");
		address.setStateOrProvince("PA");
		address.setCountry("US");
		address.setPostalCode("18503");
		location.setAddress(address);
		inventoryLocation.setLocation(location);
		inventoryLocationClient.createInventoryLocation(inventoryLocation);
	}

	@Test
	@Ignore
	public void givenSomeInventoryLocationKeyWhenDeletingInventoryLocationThenReturn200StatusCode() throws Exception {
		final String inventoryLocationKey = "Testy_McTestface";
		inventoryLocationClient.deleteInventoryLocation(inventoryLocationKey);
	}
}
