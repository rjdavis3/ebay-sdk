package com.ebay.sell.inventoryitems.models;

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

	public void setPackageWeightAndSize(
			PackageWeightAndSize packageWeightAndSize) {
		this.packageWeightAndSize = packageWeightAndSize;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
