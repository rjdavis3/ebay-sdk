package com.ebay.sell.inventory.inventoryitemgroups.models;

import java.util.LinkedList;
import java.util.List;

import com.ebay.clients.models.EbayResponse;

public class InventoryItemGroup extends EbayResponse {

	private String inventoryItemGroupKey;
	private String title;
	private String subtitle;
	private String description;
	private List<String> imageUrls = new LinkedList<>();
	private VariesBy variesBy;
	private List<String> variantSKUs = new LinkedList<>();

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

	public List<String> getVariantSKUs() {
		return variantSKUs;
	}

	public void setVariantSKUs(List<String> variantSKUs) {
		this.variantSKUs = variantSKUs;
	}

}
