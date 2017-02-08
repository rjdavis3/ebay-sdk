package com.ebay.sell.inventory.inventoryitemgroups.clients.impl;

import java.net.URI;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response.Status;

import com.ebay.clients.impl.EbayClientImpl;
import com.ebay.identity.oauth2.token.models.UserToken;
import com.ebay.models.RequestRetryConfiguration;
import com.ebay.sell.inventory.inventoryitemgroups.clients.InventoryItemGroupClient;
import com.ebay.sell.inventory.inventoryitemgroups.models.InventoryItemGroup;

public class InventoryItemGroupClientImpl extends EbayClientImpl implements InventoryItemGroupClient {

	static final String INVENTORY_ITEM_GROUP_RESOURCE = "/sell/inventory/v1/inventory_item_group";

	public InventoryItemGroupClientImpl(final URI baseUri, final UserToken userToken,
			final RequestRetryConfiguration requestRetryConfiguration) {
		super(baseUri, userToken, requestRetryConfiguration);
	}

	@Override
	public InventoryItemGroup getInventoryItemGroup(final String inventoryItemGroupKey) {
		final WebTarget webTarget = getWebTarget().path(inventoryItemGroupKey);
		final InventoryItemGroup inventoryItemGroup = get(webTarget, InventoryItemGroup.class, Status.OK);
		inventoryItemGroup.setInventoryItemGroupKey(inventoryItemGroupKey);
		return inventoryItemGroup;
	}

	@Override
	public void deleteInventoryItemGroup(final String inventoryItemGroupKey) {
		final WebTarget webTarget = getWebTarget().path(inventoryItemGroupKey);
		delete(webTarget, Status.NO_CONTENT);
	}

	@Override
	public void updateInventoryItemGroup(final InventoryItemGroup inventoryItemGroup) {
		final WebTarget webTarget = getWebTarget().path(inventoryItemGroup.getInventoryItemGroupKey());
		put(webTarget, inventoryItemGroup, Status.NO_CONTENT);
	}

	@Override
	protected WebTarget getWebTarget() {
		return super.getWebTarget().path(INVENTORY_ITEM_GROUP_RESOURCE);
	}
}
