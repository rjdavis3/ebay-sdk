package com.ebay.sell.inventory.offers.clients;

import com.ebay.sell.inventory.offers.models.Offer;

public interface OfferClient {

	public Offer getOffer(final String offerId);

	public void updateOffer(final Offer offer);

}
