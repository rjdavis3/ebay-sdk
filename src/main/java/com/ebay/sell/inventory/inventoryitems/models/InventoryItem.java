package com.ebay.sell.inventory.inventoryitems.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class InventoryItem {

	private String sku;
	private String condition;
	private String conditionDescription;
	private Availability availability;
	private PackageWeightAndSize packageWeightAndSize;
	private Product product;

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

	public String getConditionDescription() {
		return conditionDescription;
	}

	public void setConditionDescription(String conditionDescription) {
		this.conditionDescription = conditionDescription;
	}

	public Availability getAvailability() {
		return availability;
	}

	public void setAvailability(Availability availability) {
		this.availability = availability;
	}

	public PackageWeightAndSize getPackageWeightAndSize() {
		return packageWeightAndSize;
	}

	public void setPackageWeightAndSize(PackageWeightAndSize packageWeightAndSize) {
		this.packageWeightAndSize = packageWeightAndSize;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof InventoryItem)) {
			return false;
		}
		final InventoryItem inventoryItem = (InventoryItem) object;
		return new EqualsBuilder().append(getSku(), inventoryItem.getSku())
				.append(getCondition(), inventoryItem.getCondition())
				.append(getConditionDescription(), inventoryItem.getConditionDescription())
				.append(getAvailability(), inventoryItem.getAvailability())
				.append(getPackageWeightAndSize(), inventoryItem.getPackageWeightAndSize())
				.append(getProduct(), inventoryItem.getProduct()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getSku()).append(getCondition()).append(getConditionDescription())
				.append(getAvailability()).append(getPackageWeightAndSize()).append(getProduct()).toHashCode();
	}

}
