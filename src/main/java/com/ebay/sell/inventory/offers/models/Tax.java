package com.ebay.sell.inventory.offers.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Tax {

	private static final int SCALE = 10;
	private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

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
		this.vatPercentage = (vatPercentage == null) ? null
				: vatPercentage.setScale(SCALE, ROUNDING_MODE).stripTrailingZeros();
	}

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Tax)) {
			return false;
		}
		final Tax tax = (Tax) object;
		return new EqualsBuilder().append(isApplyTax(), tax.isApplyTax())
				.append(getThirdPartyTaxCategory(), tax.getThirdPartyTaxCategory())
				.append(getVatPercentage(), tax.getVatPercentage()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(isApplyTax()).append(getThirdPartyTaxCategory()).append(getVatPercentage())
				.toHashCode();
	}
}
