package com.ebay.sell.inventoryitemgroups.clients;

import com.ebay.sell.inventoryitemgroups.models.InventoryItemGroup;

public interface InventoryItemGroupClient {

	public InventoryItemGroup getInventoryItemGroup(
			final String inventoryItemGroupKey);

}
