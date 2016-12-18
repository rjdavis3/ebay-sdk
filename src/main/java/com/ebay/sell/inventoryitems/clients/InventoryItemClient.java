package com.ebay.sell.inventoryitems.clients;

import com.ebay.sell.inventoryitems.models.InventoryItem;
import com.ebay.sell.inventoryitems.models.InventoryItems;

public interface InventoryItemClient {

	public InventoryItem getInventoryItem(final String sku);

	public void updateInventoryItem(final InventoryItem inventoryItem);

	public void deleteInventoryItem(final String sku);

	public InventoryItems getInventoryItems(final int offset, final int limit);

}
