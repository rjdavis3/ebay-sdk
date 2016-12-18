package com.ebay.sell.inventoryitemgroups.clients.impl;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ebay.exceptions.EbayErrorException;
import com.ebay.sell.inventoryitemgroups.clients.InventoryItemGroupClient;
import com.ebay.sell.inventoryitemgroups.models.InventoryItemGroup;

public class InventoryItemGroupClientImpl implements InventoryItemGroupClient {

	static final String INVENTORY_ITEM_GROUP_RESOURCE = "https://api.sandbox.ebay.com/sell/inventory/v1/inventory_item_group";
	static final String AUTHORIZATION_HEADER = "Authorization";
	static final String OAUTH_USER_TOKEN_PREFIX = "Bearer ";
	static final String LIMIT_QUERY_PARAMETER = "limit";
	static final String OFFSET_QUERY_PARAMETER = "offset";

	private final Client client;
	private final String oauthUserToken;

	public InventoryItemGroupClientImpl(final Client client,
			final String oauthUserToken) {
		this.client = client;
		this.oauthUserToken = new StringBuilder()
				.append(OAUTH_USER_TOKEN_PREFIX).append(oauthUserToken)
				.toString();
	}

	@Override
	public InventoryItemGroup getInventoryItemGroup(
			final String inventoryItemGroupKey) {
		final Response response = client.target(INVENTORY_ITEM_GROUP_RESOURCE)
				.path(inventoryItemGroupKey).request()
				.header(AUTHORIZATION_HEADER, oauthUserToken).get();
		if (Status.OK.getStatusCode() == response.getStatus()) {
			final InventoryItemGroup inventoryItemGroup = response
					.readEntity(InventoryItemGroup.class);
			inventoryItemGroup.setInventoryItemGroupKey(inventoryItemGroupKey);
			return inventoryItemGroup;
		}
		throw new EbayErrorException(response);
	}

}
