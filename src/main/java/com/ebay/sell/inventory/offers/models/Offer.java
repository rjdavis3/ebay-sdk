package com.ebay.sell.inventory.offers.models;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
	private Integer quantityLimitPerBuyer;
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

	public Integer getQuantityLimitPerBuyer() {
		return quantityLimitPerBuyer;
	}

	public void setQuantityLimitPerBuyer(Integer quantityLimitPerBuyer) {
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

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Offer)) {
			return false;
		}
		final Offer offer = (Offer) object;
		return new EqualsBuilder().append(getOfferId(), offer.getOfferId())
				.append(getAvailableQuantity(), offer.getAvailableQuantity())
				.append(getCategoryId(), offer.getCategoryId()).append(getFormat(), offer.getFormat())
				.append(getListingDescription(), offer.getListingDescription())
				.append(getListingPolicies(), offer.getListingPolicies())
				.append(getMarketplaceId(), offer.getMarketplaceId())
				.append(getMerchantLocationKey(), offer.getMerchantLocationKey())
				.append(getPricingSummary(), offer.getPricingSummary())
				.append(getQuantityLimitPerBuyer(), offer.getQuantityLimitPerBuyer()).append(getSku(), offer.getSku())
				.append(getStoreCategoryNames(), offer.getStoreCategoryNames()).append(getTax(), offer.getTax())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getOfferId()).append(getAvailableQuantity()).append(getCategoryId())
				.append(getFormat()).append(getListingDescription()).append(getListingPolicies())
				.append(getMarketplaceId()).append(getMerchantLocationKey()).append(getPricingSummary())
				.append(getQuantityLimitPerBuyer()).append(getSku()).append(getStoreCategoryNames()).append(getTax())
				.toHashCode();
	}

}
