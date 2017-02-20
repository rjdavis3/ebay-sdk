package com.ebay.sell.inventory.locations.clients.impl;

import java.net.URI;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response.Status;

import com.ebay.clients.impl.EbayClientImpl;
import com.ebay.identity.oauth2.token.models.UserToken;
import com.ebay.models.RequestRetryConfiguration;
import com.ebay.sell.inventory.locations.clients.InventoryLocationClient;
import com.ebay.sell.inventory.locations.models.InventoryLocation;

public class InventoryLocationClientImpl extends EbayClientImpl implements InventoryLocationClient {

	static final String INVENTORY_LOCATION_RESOURCE = "/sell/inventory/v1/location";

	public InventoryLocationClientImpl(final URI baseUri, final UserToken userToken,
			final RequestRetryConfiguration requestRetryConfiguration) {
		super(baseUri, userToken, requestRetryConfiguration);
	}

	@Override
	public InventoryLocation getInventoryLocation(final String merchantLocationKey) {
		final WebTarget webTarget = getWebTarget().path(merchantLocationKey);
		final InventoryLocation inventoryLocation = get(webTarget, InventoryLocation.class, Status.OK,
				Status.NOT_FOUND);
		inventoryLocation.setMerchantLocationKey(merchantLocationKey);
		return inventoryLocation;
	}

	@Override
	public void deleteInventoryLocation(final String merchantLocationKey) {
		final WebTarget webTarget = getWebTarget().path(merchantLocationKey);
		delete(webTarget, Status.OK);
	}

	@Override
	public void createInventoryLocation(final InventoryLocation inventoryLocation) {
		final WebTarget webTarget = getWebTarget().path(inventoryLocation.getMerchantLocationKey());
		post(webTarget, inventoryLocation, InventoryLocation.class, Status.NO_CONTENT);
	}

	@Override
	protected WebTarget getWebTarget() {
		return super.getWebTarget().path(INVENTORY_LOCATION_RESOURCE);
	}
}
