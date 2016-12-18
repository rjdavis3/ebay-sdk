package com.ebay.sell.inventoryitems.clients.impl;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ebay.exceptions.EbayErrorException;
import com.ebay.sell.inventoryitems.clients.InventoryItemClient;
import com.ebay.sell.inventoryitems.models.InventoryItem;
import com.ebay.sell.inventoryitems.models.InventoryItems;

public class InventoryItemClientImpl implements InventoryItemClient {

	static final String INVENTORY_ITEM_RESOURCE = "https://api.sandbox.ebay.com/sell/inventory/v1/inventory_item";
	static final String AUTHORIZATION_HEADER = "Authorization";
	static final String OAUTH_USER_TOKEN_PREFIX = "Bearer ";
	static final String LIMIT_QUERY_PARAMETER = "limit";
	static final String OFFSET_QUERY_PARAMETER = "offset";

	private final Client client;
	private final String oauthUserToken;

	public InventoryItemClientImpl(final Client client, final String oauthUserToken) {
		this.client = client;
		this.oauthUserToken = new StringBuilder()
				.append(OAUTH_USER_TOKEN_PREFIX).append(oauthUserToken)
				.toString();
	}

	@Override
	public InventoryItem getInventoryItem(final String sku) {
		final Response response = client.target(INVENTORY_ITEM_RESOURCE)
				.path(sku).request()
				.header(AUTHORIZATION_HEADER, oauthUserToken).get();
		if (Status.OK.getStatusCode() == response.getStatus()) {
			return response.readEntity(InventoryItem.class);
		}
		throw new EbayErrorException(response);
	}

	@Override
	public void updateInventoryItem(final InventoryItem inventoryItem) {
		final Entity<InventoryItem> inventoryItemEntity = Entity.entity(
				inventoryItem, MediaType.APPLICATION_JSON);
		final Response response = client.target(INVENTORY_ITEM_RESOURCE)
				.path(inventoryItem.getSku()).request()
				.header(AUTHORIZATION_HEADER, oauthUserToken)
				.put(inventoryItemEntity);
		if (Status.NO_CONTENT.getStatusCode() != response.getStatus()) {
			throw new EbayErrorException(response);
		}
	}

	@Override
	public void deleteInventoryItem(final String sku) {
		final Response response = client.target(INVENTORY_ITEM_RESOURCE)
				.path(sku).request()
				.header(AUTHORIZATION_HEADER, oauthUserToken).delete();
		if (Status.NO_CONTENT.getStatusCode() != response.getStatus()) {
			throw new EbayErrorException(response);
		}
	}

	@Override
	public InventoryItems getInventoryItems(final int offset, final int limit) {
		final Response response = client.target(INVENTORY_ITEM_RESOURCE)
				.queryParam(OFFSET_QUERY_PARAMETER, offset)
				.queryParam(LIMIT_QUERY_PARAMETER, limit).request()
				.header(AUTHORIZATION_HEADER, oauthUserToken).get();
		if (Status.OK.getStatusCode() == response.getStatus()) {
			return response.readEntity(InventoryItems.class);
		}
		throw new EbayErrorException(response);
	}

}
