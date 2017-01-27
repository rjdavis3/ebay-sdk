package com.ebay.sell.inventory.inventoryitemgroups.clients.impl;

import java.net.URI;
import java.util.Locale;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Variant;

import org.glassfish.jersey.client.ClientProperties;

import com.ebay.exceptions.EbayErrorException;
import com.ebay.sell.inventory.inventoryitemgroups.clients.InventoryItemGroupClient;
import com.ebay.sell.inventory.inventoryitemgroups.models.InventoryItemGroup;

public class InventoryItemGroupClientImpl implements InventoryItemGroupClient {

	static final String INVENTORY_ITEM_GROUP_RESOURCE = "/sell/inventory/v1/inventory_item_group";
	static final String AUTHORIZATION_HEADER = "Authorization";
	static final String OAUTH_USER_TOKEN_PREFIX = "Bearer ";

	private static final String UTF_8_ENCODING = "utf-8";
	private static final Variant ENTITY_VARIANT = new Variant(MediaType.APPLICATION_JSON_TYPE, Locale.US,
			UTF_8_ENCODING);

	private static final Client REST_CLIENT = ClientBuilder.newClient()
			.property(ClientProperties.CONNECT_TIMEOUT, 60000).property(ClientProperties.READ_TIMEOUT, 600000);
	private final URI baseUri;
	private final String oauthUserToken;

	public InventoryItemGroupClientImpl(final URI baseUri, final String oauthUserToken) {
		this.baseUri = baseUri;
		this.oauthUserToken = new StringBuilder().append(OAUTH_USER_TOKEN_PREFIX).append(oauthUserToken).toString();
	}

	@Override
	public InventoryItemGroup getInventoryItemGroup(final String inventoryItemGroupKey) {
		final Response response = getWebTarget().path(inventoryItemGroupKey).request()
				.header(AUTHORIZATION_HEADER, oauthUserToken).get();
		if (Status.OK.getStatusCode() == response.getStatus()) {
			final InventoryItemGroup inventoryItemGroup = response.readEntity(InventoryItemGroup.class);
			inventoryItemGroup.setInventoryItemGroupKey(inventoryItemGroupKey);
			return inventoryItemGroup;
		}
		throw new EbayErrorException(response);
	}

	@Override
	public void deleteInventoryItemGroup(final String inventoryItemGroupKey) {
		final Response response = getWebTarget().path(inventoryItemGroupKey).request()
				.header(AUTHORIZATION_HEADER, oauthUserToken).delete();
		if (Status.NO_CONTENT.getStatusCode() != response.getStatus()) {
			throw new EbayErrorException(response);
		}
	}

	@Override
	public void updateInventoryItemGroup(final InventoryItemGroup inventoryItemGroup) {
		final String inventoryItemGroupKey = inventoryItemGroup.getInventoryItemGroupKey();
		final Entity<InventoryItemGroup> inventoryItemGroupEntity = Entity.entity(inventoryItemGroup, ENTITY_VARIANT);

		final Response response = getWebTarget().path(inventoryItemGroupKey).request()
				.header(AUTHORIZATION_HEADER, oauthUserToken).put(inventoryItemGroupEntity);
		if (Status.NO_CONTENT.getStatusCode() != response.getStatus()) {
			throw new EbayErrorException(response);
		}
	}

	private WebTarget getWebTarget() {
		return REST_CLIENT.target(baseUri).path(INVENTORY_ITEM_GROUP_RESOURCE);
	}
}
