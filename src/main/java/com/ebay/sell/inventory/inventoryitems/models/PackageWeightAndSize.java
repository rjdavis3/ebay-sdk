package com.ebay.sell.inventory.inventoryitems.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PackageWeightAndSize {

	private Dimension dimensions;
	private String packageType;
	private Weight weight;

	public Dimension getDimensions() {
		return dimensions;
	}

	public void setDimensions(Dimension dimensions) {
		this.dimensions = dimensions;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public Weight getWeight() {
		return weight;
	}

	public void setWeight(Weight weight) {
		this.weight = weight;
	}

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof PackageWeightAndSize)) {
			return false;
		}
		final PackageWeightAndSize packageWeightAndSize = (PackageWeightAndSize) object;
		return new EqualsBuilder().append(getDimensions(), packageWeightAndSize.getDimensions())
				.append(getPackageType(), packageWeightAndSize.getPackageType())
				.append(getWeight(), packageWeightAndSize.getWeight()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getDimensions()).append(getPackageType()).append(getWeight()).toHashCode();
	}

}
