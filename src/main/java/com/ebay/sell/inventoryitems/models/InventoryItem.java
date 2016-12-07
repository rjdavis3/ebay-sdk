package com.ebay.sell.inventoryitems.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InventoryItem {

	private String sku;
	private String condition;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
