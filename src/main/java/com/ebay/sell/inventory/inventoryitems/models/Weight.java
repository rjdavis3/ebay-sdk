package com.ebay.sell.inventory.inventoryitems.models;

import java.math.BigDecimal;

public class Weight {

	private String unit;
	private BigDecimal value;

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
