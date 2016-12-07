package com.ebay.sell.inventoryitems.clients;

import com.ebay.sell.inventoryitems.models.InventoryItem;

public interface InventoryClient {
	
	public InventoryItem get(final String sku);

}
