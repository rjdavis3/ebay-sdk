package com.ebay.sell.inventory.inventoryitems.clients.impl;

import java.net.URI;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response.Status;

import com.ebay.clients.impl.EbayClientImpl;
import com.ebay.identity.oauth2.token.models.UserToken;
import com.ebay.sell.inventory.inventoryitems.clients.InventoryItemClient;
import com.ebay.sell.inventory.inventoryitems.models.InventoryItem;
import com.ebay.sell.inventory.inventoryitems.models.InventoryItems;

public class InventoryItemClientImpl extends EbayClientImpl implements InventoryItemClient {

	public static final String INVENTORY_ITEM_RESOURCE = "/sell/inventory/v1/inventory_item";
	static final String LIMIT_QUERY_PARAMETER = "limit";
	static final String OFFSET_QUERY_PARAMETER = "offset";

	public InventoryItemClientImpl(final URI baseUri, final UserToken userToken) {
		super(baseUri, userToken);
	}

	@Override
	public InventoryItem getInventoryItem(final String sku) {
		final WebTarget webTarget = getWebTarget().path(sku);
		return get(webTarget, InventoryItem.class, Status.OK);
	}

	@Override
	public void updateInventoryItem(final InventoryItem inventoryItem) {
		final WebTarget webTarget = getWebTarget().path(inventoryItem.getSku());
		put(webTarget, inventoryItem, Status.NO_CONTENT);
	}

	@Override
	public void deleteInventoryItem(final String sku) {
		final WebTarget webTarget = getWebTarget().path(sku);
		delete(webTarget, Status.NO_CONTENT);
	}

	@Override
	public InventoryItems getInventoryItems(final int offset, final int limit) {
		final WebTarget webTarget = getWebTarget().queryParam(OFFSET_QUERY_PARAMETER, offset)
				.queryParam(LIMIT_QUERY_PARAMETER, limit);
		return get(webTarget, InventoryItems.class, Status.OK);
	}

	@Override
	protected WebTarget getWebTarget() {
		return super.getWebTarget().path(INVENTORY_ITEM_RESOURCE);
	}

}
