package com.ebay.sell.inventory.offers.clients.impl;

import java.util.Locale;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Variant;

import com.ebay.exceptions.EbayErrorException;
import com.ebay.sell.inventory.offers.clients.OfferClient;
import com.ebay.sell.inventory.offers.models.Offer;

public class OfferClientImpl implements OfferClient {

	static final String OFFER_RESOURCE = "https://api.sandbox.ebay.com/sell/inventory/v1/offer";
	static final String AUTHORIZATION_HEADER = "Authorization";
	static final String OAUTH_USER_TOKEN_PREFIX = "Bearer ";

	private static final String UTF_8_ENCODING = "utf-8";
	private static final Variant ENTITY_VARIANT = new Variant(
			MediaType.APPLICATION_JSON_TYPE, Locale.US, UTF_8_ENCODING);

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
		final Response response = client.target(OFFER_RESOURCE).path(offerId)
				.request().header(AUTHORIZATION_HEADER, oauthUserToken).get();
		if (Status.OK.getStatusCode() == response.getStatus()) {
			final Offer offer = response.readEntity(Offer.class);
			offer.setOfferId(offerId);
			return offer;
		}
		throw new EbayErrorException(response);
	}

	@Override
	public void updateOffer(final Offer offer) {
		final Entity<Offer> offerEntity = Entity.entity(offer, ENTITY_VARIANT);
		final Response response = client.target(OFFER_RESOURCE)
				.path(offer.getOfferId()).request()
				.header(AUTHORIZATION_HEADER, oauthUserToken).put(offerEntity);
		if (Status.NO_CONTENT.getStatusCode() != response.getStatus()) {
			throw new EbayErrorException(response);
		}
	}

	@Override
	public void createOffer(final Offer offer) {
		final Entity<Offer> offerEntity = Entity.entity(offer, ENTITY_VARIANT);
		final Response response = client.target(OFFER_RESOURCE).request()
				.header(AUTHORIZATION_HEADER, oauthUserToken).post(offerEntity);
		if (Status.CREATED.getStatusCode() != response.getStatus()) {
			throw new EbayErrorException(response);
		}
		final Offer createdOffer = response.readEntity(Offer.class);
		offer.setOfferId(createdOffer.getOfferId());
	}
}
