package com.ebay.sell.inventoryitems.clients.impl;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;

import com.ebay.sell.inventoryitems.clients.InventoryClient;
import com.ebay.sell.inventoryitems.models.InventoryItem;

public class InventoryClientImpl implements InventoryClient {

	private final Client REST_CLIENT = ClientBuilder.newClient()
			.property(ClientProperties.CONNECT_TIMEOUT, 60000)
			.property(ClientProperties.READ_TIMEOUT, 600000)
			.register(MoxyJsonFeature.class);

	private static final String OAUTH_USER_TOKEN_PREFIX = "Bearer ";
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String INVENTORY_ITEM_RESOURCE = "https://api.sandbox.ebay.com/sell/inventory/v1/inventory_item";

	private String oauthUserToken;

	public InventoryClientImpl(final String oauthUserToken) {
		this.oauthUserToken = new StringBuilder()
				.append(OAUTH_USER_TOKEN_PREFIX).append(oauthUserToken)
				.toString();
	}

	@Override
	public InventoryItem get(final String sku) {
		final Response response = REST_CLIENT.target(INVENTORY_ITEM_RESOURCE)
				.path(sku).request()
				.header(AUTHORIZATION_HEADER, oauthUserToken).get();
		if (Status.OK.getStatusCode() == response.getStatus()) {
			return response.readEntity(InventoryItem.class);
		}
		return null;
	}

}
