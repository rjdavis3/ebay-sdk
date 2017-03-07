package com.ebay.sell.inventory.inventoryitemgroups.models;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class InventoryItemGroup {

	private String inventoryItemGroupKey;
	private String title;
	private String subtitle;
	private String description;
	private List<String> imageUrls = new LinkedList<>();
	private VariesBy variesBy;
	private Set<String> variantSKUs = new HashSet<>();

	public String getInventoryItemGroupKey() {
		return inventoryItemGroupKey;
	}

	public void setInventoryItemGroupKey(String inventoryItemGroupKey) {
		this.inventoryItemGroupKey = inventoryItemGroupKey;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

	public VariesBy getVariesBy() {
		return variesBy;
	}

	public void setVariesBy(VariesBy variesBy) {
		this.variesBy = variesBy;
	}

	public Set<String> getVariantSKUs() {
		return variantSKUs;
	}

	public void setVariantSKUs(Set<String> variantSKUs) {
		this.variantSKUs = variantSKUs;
	}

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof InventoryItemGroup)) {
			return false;
		}
		final InventoryItemGroup inventoryItemGroup = (InventoryItemGroup) object;
		return new EqualsBuilder().append(getInventoryItemGroupKey(), inventoryItemGroup.getInventoryItemGroupKey())
				.append(getTitle(), inventoryItemGroup.getTitle())
				.append(getSubtitle(), inventoryItemGroup.getSubtitle())
				.append(getDescription(), inventoryItemGroup.getDescription())
				.append(getImageUrls(), inventoryItemGroup.getImageUrls())
				.append(getVariesBy(), inventoryItemGroup.getVariesBy())
				.append(getVariantSKUs(), inventoryItemGroup.getVariantSKUs()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getInventoryItemGroupKey()).append(getTitle()).append(getSubtitle())
				.append(getDescription()).append(getImageUrls()).append(getVariesBy()).append(getVariantSKUs())
				.toHashCode();
	}

}
