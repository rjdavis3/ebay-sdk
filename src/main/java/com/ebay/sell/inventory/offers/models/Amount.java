package com.ebay.sell.inventory.offers.models;

import java.math.BigDecimal;

public class Amount {

	private String currency;
	private BigDecimal value;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
