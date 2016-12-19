package com.ebay.sell.inventory.offers.models;

import java.util.List;

public class Offer {

	private String offerId;
	private int availableQuantity;
	private String categoryId;
	private String format;
	private Listing listing;
	private String listingDescription;
	private ListingPolicies listingPolicies;
	private String marketplaceId;
	private String merchantLocationKey;
	private PricingSummary pricingSummary;
	private int quantityLimitPerBuyer;
	private String sku;
	private String status;
	private List<String> storeCategoryNames;
	private Tax tax;

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Listing getListing() {
		return listing;
	}

	public void setListing(Listing listing) {
		this.listing = listing;
	}

	public String getListingDescription() {
		return listingDescription;
	}

	public void setListingDescription(String listingDescription) {
		this.listingDescription = listingDescription;
	}

	public ListingPolicies getListingPolicies() {
		return listingPolicies;
	}

	public void setListingPolicies(ListingPolicies listingPolicies) {
		this.listingPolicies = listingPolicies;
	}

	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}

	public String getMerchantLocationKey() {
		return merchantLocationKey;
	}

	public void setMerchantLocationKey(String merchantLocationKey) {
		this.merchantLocationKey = merchantLocationKey;
	}

	public PricingSummary getPricingSummary() {
		return pricingSummary;
	}

	public void setPricingSummary(PricingSummary pricingSummary) {
		this.pricingSummary = pricingSummary;
	}

	public int getQuantityLimitPerBuyer() {
		return quantityLimitPerBuyer;
	}

	public void setQuantityLimitPerBuyer(int quantityLimitPerBuyer) {
		this.quantityLimitPerBuyer = quantityLimitPerBuyer;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getStoreCategoryNames() {
		return storeCategoryNames;
	}

	public void setStoreCategoryNames(List<String> storeCategoryNames) {
		this.storeCategoryNames = storeCategoryNames;
	}

	public Tax getTax() {
		return tax;
	}

	public void setTax(Tax tax) {
		this.tax = tax;
	}

}
