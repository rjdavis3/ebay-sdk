package com.ebay.sell.inventory.inventoryitemgroups.clients;

import com.ebay.sell.inventory.inventoryitemgroups.models.InventoryItemGroup;

public interface InventoryItemGroupClient {

	public InventoryItemGroup getInventoryItemGroup(
			final String inventoryItemGroupKey);

	public void deleteInventoryItemGroup(final String inventoryItemGroupKey);

	public void updateInventoryItemGroup(
			final InventoryItemGroup inventoryItemGroup);

}
