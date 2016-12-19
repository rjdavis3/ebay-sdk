package com.ebay.sell.inventory.offers.models;

import java.math.BigDecimal;

public class Tax {

	private boolean applyTax;
	private String thirdPartyTaxCategory;
	private BigDecimal vatPercentage;

	public boolean isApplyTax() {
		return applyTax;
	}

	public void setApplyTax(boolean applyTax) {
		this.applyTax = applyTax;
	}

	public String getThirdPartyTaxCategory() {
		return thirdPartyTaxCategory;
	}

	public void setThirdPartyTaxCategory(String thirdPartyTaxCategory) {
		this.thirdPartyTaxCategory = thirdPartyTaxCategory;
	}

	public BigDecimal getVatPercentage() {
		return vatPercentage;
	}

	public void setVatPercentage(BigDecimal vatPercentage) {
		this.vatPercentage = vatPercentage;
	}
}
