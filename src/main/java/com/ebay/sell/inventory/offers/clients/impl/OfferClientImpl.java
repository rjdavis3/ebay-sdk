package com.ebay.sell.inventory.offers.clients.impl;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ebay.exceptions.EbayErrorException;
import com.ebay.sell.inventory.offers.clients.OfferClient;
import com.ebay.sell.inventory.offers.models.Offer;

public class OfferClientImpl implements OfferClient {

	static final String OFFER_RESOURCE = "https://api.sandbox.ebay.com/sell/inventory/v1/offer";
	static final String AUTHORIZATION_HEADER = "Authorization";
	static final String OAUTH_USER_TOKEN_PREFIX = "Bearer ";

	private final Client client;
	private final String oauthUserToken;

	public OfferClientImpl(final Client client, final String oauthUserToken) {
		this.client = client;
		this.oauthUserToken = new StringBuilder()
				.append(OAUTH_USER_TOKEN_PREFIX).append(oauthUserToken)
				.toString();
	}

	@Override
	public Offer getOffer(final String offerId) {
		final Response response = client.target(OFFER_RESOURCE)
				.path(offerId).request()
				.header(AUTHORIZATION_HEADER, oauthUserToken).get();
		if (Status.OK.getStatusCode() == response.getStatus()) {
			final Offer offer = response.readEntity(Offer.class);
			offer.setOfferId(offerId);
			return offer;
		}
		throw new EbayErrorException(response);
	}
}
