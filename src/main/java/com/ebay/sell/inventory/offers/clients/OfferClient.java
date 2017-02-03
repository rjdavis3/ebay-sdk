package com.ebay.sell.inventory.offers.clients;

import com.ebay.sell.inventory.offers.models.Offer;

public interface OfferClient {

	public Offer getOffer(final String offerId);

	public void createOffer(final Offer offer);

	public void updateOffer(final Offer offer);

	public Offer getOfferBySku(final String sku);

	public String publishOffer(final String offerId);

}
