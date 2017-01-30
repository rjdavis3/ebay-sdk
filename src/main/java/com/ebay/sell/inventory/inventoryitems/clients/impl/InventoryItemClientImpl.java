package com.ebay.sell.inventory.inventoryitems.clients.impl;

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

import com.ebay.clients.impl.EbayClientImpl;
import com.ebay.sell.inventory.inventoryitems.clients.InventoryItemClient;
import com.ebay.sell.inventory.inventoryitems.models.InventoryItem;
import com.ebay.sell.inventory.inventoryitems.models.InventoryItems;

public class InventoryItemClientImpl extends EbayClientImpl implements InventoryItemClient {

	static final String INVENTORY_ITEM_RESOURCE = "/sell/inventory/v1/inventory_item";
	static final String AUTHORIZATION_HEADER = "Authorization";
	static final String OAUTH_USER_TOKEN_PREFIX = "Bearer ";
	static final String LIMIT_QUERY_PARAMETER = "limit";
	static final String OFFSET_QUERY_PARAMETER = "offset";

	private static final Client REST_CLIENT = ClientBuilder.newClient()
			.property(ClientProperties.CONNECT_TIMEOUT, 60000).property(ClientProperties.READ_TIMEOUT, 600000);
	private static final String UTF_8_ENCODING = "utf-8";
	private static final Variant ENTITY_VARIANT = new Variant(MediaType.APPLICATION_JSON_TYPE, Locale.US,
			UTF_8_ENCODING);

	private final URI baseUri;
	private final String oauthUserToken;

	public InventoryItemClientImpl(final URI baseUri, final String oauthUserToken) {
		this.baseUri = baseUri;
		this.oauthUserToken = new StringBuilder().append(OAUTH_USER_TOKEN_PREFIX).append(oauthUserToken).toString();
	}

	@Override
	public InventoryItem getInventoryItem(final String sku) {
		final Response response = getWebTarget().path(sku).request().header(AUTHORIZATION_HEADER, oauthUserToken).get();
		return handleResponse(response, InventoryItem.class, Status.OK);
	}

	@Override
	public void updateInventoryItem(final InventoryItem inventoryItem) {
		final Entity<InventoryItem> inventoryItemEntity = Entity.entity(inventoryItem, ENTITY_VARIANT);
		final Response response = getWebTarget().path(inventoryItem.getSku()).request()
				.header(AUTHORIZATION_HEADER, oauthUserToken).put(inventoryItemEntity);
		handleResponse(response, Status.NO_CONTENT);
	}

	@Override
	public void deleteInventoryItem(final String sku) {
		final Response response = getWebTarget().path(sku).request().header(AUTHORIZATION_HEADER, oauthUserToken)
				.delete();
		handleResponse(response, Status.NO_CONTENT);
	}

	@Override
	public InventoryItems getInventoryItems(final int offset, final int limit) {
		final Response response = getWebTarget().queryParam(OFFSET_QUERY_PARAMETER, offset)
				.queryParam(LIMIT_QUERY_PARAMETER, limit).request().header(AUTHORIZATION_HEADER, oauthUserToken).get();
		return handleResponse(response, InventoryItems.class, Status.OK);
	}

	private WebTarget getWebTarget() {
		return REST_CLIENT.target(baseUri).path(INVENTORY_ITEM_RESOURCE);
	}

}
