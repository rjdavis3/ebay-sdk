package com.ebay.sell.inventory.locations.models;

import com.ebay.clients.models.EbayResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryLocation extends EbayResponse {

	private String merchantLocationKey;
	private String merchantLocationStatus;
	private String name;
	private Location location;

	public String getMerchantLocationKey() {
		return merchantLocationKey;
	}

	public void setMerchantLocationKey(String merchantLocationKey) {
		this.merchantLocationKey = merchantLocationKey;
	}

	public String getMerchantLocationStatus() {
		return merchantLocationStatus;
	}

	public void setMerchantLocationStatus(String merchantLocationStatus) {
		this.merchantLocationStatus = merchantLocationStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
