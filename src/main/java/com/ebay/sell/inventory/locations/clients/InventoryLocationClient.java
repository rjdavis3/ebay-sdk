package com.ebay.sell.inventory.locations.clients;

import com.ebay.sell.inventory.locations.models.InventoryLocation;

public interface InventoryLocationClient {

	public InventoryLocation getInventoryLocation(final String merchantLocationKey);

	public void deleteInventoryLocation(final String merchantLocationKey);

	public void createInventoryLocation(final InventoryLocation inventoryLocation);

}
