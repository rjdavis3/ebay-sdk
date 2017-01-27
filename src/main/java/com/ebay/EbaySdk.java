package com.ebay;

import java.net.URI;

import com.ebay.sell.inventory.inventoryitemgroups.clients.InventoryItemGroupClient;
import com.ebay.sell.inventory.inventoryitemgroups.clients.impl.InventoryItemGroupClientImpl;
import com.ebay.sell.inventory.inventoryitemgroups.models.InventoryItemGroup;
import com.ebay.sell.inventory.inventoryitems.clients.InventoryItemClient;
import com.ebay.sell.inventory.inventoryitems.clients.impl.InventoryItemClientImpl;
import com.ebay.sell.inventory.inventoryitems.models.InventoryItem;
import com.ebay.sell.inventory.inventoryitems.models.InventoryItems;
import com.ebay.sell.inventory.offers.clients.OfferClient;
import com.ebay.sell.inventory.offers.clients.impl.OfferClientImpl;
import com.ebay.sell.inventory.offers.models.Offer;

public class EbaySdk implements InventoryItemGroupClient, InventoryItemClient, OfferClient {

	public static final URI SANDBOX_URI = URI.create("https://api.sandbox.ebay.com");
	public static final URI PRODUCTION_URI = URI.create("https://api.ebay.com");

	private final InventoryItemClient inventoryItemClient;
	private final InventoryItemGroupClient inventoryItemGroupClient;
	private final OfferClient offerClient;

	public EbaySdk(final String oauthUserToken, final boolean sandbox) {
		final URI baseUri = sandbox ? SANDBOX_URI : PRODUCTION_URI;
		inventoryItemClient = new InventoryItemClientImpl(baseUri, oauthUserToken);
		inventoryItemGroupClient = new InventoryItemGroupClientImpl(baseUri, oauthUserToken);
		offerClient = new OfferClientImpl(baseUri, oauthUserToken);
	}

	@Override
	public Offer getOffer(final String offerId) {
		return offerClient.getOffer(offerId);
	}

	@Override
	public void createOffer(final Offer offer) {
		offerClient.createOffer(offer);
	}

	@Override
	public void updateOffer(final Offer offer) {
		offerClient.updateOffer(offer);
	}

	@Override
	public Offer getOfferBySku(final String sku) {
		return offerClient.getOfferBySku(sku);
	}

	@Override
	public String publishOffer(final String offerId) {
		return offerClient.publishOffer(offerId);
	}

	@Override
	public InventoryItem getInventoryItem(final String sku) {
		return inventoryItemClient.getInventoryItem(sku);
	}

	@Override
	public void updateInventoryItem(final InventoryItem inventoryItem) {
		inventoryItemClient.updateInventoryItem(inventoryItem);
	}

	@Override
	public void deleteInventoryItem(final String sku) {
		inventoryItemClient.deleteInventoryItem(sku);
	}

	@Override
	public InventoryItems getInventoryItems(final int offset, final int limit) {
		return inventoryItemClient.getInventoryItems(offset, limit);
	}

	@Override
	public InventoryItemGroup getInventoryItemGroup(final String inventoryItemGroupKey) {
		return inventoryItemGroupClient.getInventoryItemGroup(inventoryItemGroupKey);
	}

	@Override
	public void deleteInventoryItemGroup(final String inventoryItemGroupKey) {
		inventoryItemGroupClient.deleteInventoryItemGroup(inventoryItemGroupKey);
	}

	@Override
	public void updateInventoryItemGroup(final InventoryItemGroup inventoryItemGroup) {
		inventoryItemGroupClient.updateInventoryItemGroup(inventoryItemGroup);
	}

}
