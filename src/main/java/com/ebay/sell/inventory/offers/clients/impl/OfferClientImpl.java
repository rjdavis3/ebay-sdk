package com.ebay.sell.inventory.offers.clients.impl;

import java.net.URI;
import java.util.Locale;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Variant;

import org.glassfish.jersey.client.ClientProperties;

import com.ebay.exceptions.EbayErrorException;
import com.ebay.sell.inventory.offers.clients.OfferClient;
import com.ebay.sell.inventory.offers.models.Listing;
import com.ebay.sell.inventory.offers.models.Offer;
import com.ebay.sell.inventory.offers.models.Offers;

public class OfferClientImpl implements OfferClient {

	static final String OFFER_RESOURCE = "/sell/inventory/v1/offer";
	static final String AUTHORIZATION_HEADER = "Authorization";
	static final String OAUTH_USER_TOKEN_PREFIX = "Bearer ";
	static final String SKU_QUERY_PARAMETER = "sku";
	static final String PUBLISH_SUBRESOURCE = "publish";

	private static final Client REST_CLIENT = ClientBuilder.newClient()
			.property(ClientProperties.CONNECT_TIMEOUT, 60000).property(ClientProperties.READ_TIMEOUT, 600000);
	private static final String UTF_8_ENCODING = "utf-8";
	private static final Variant ENTITY_VARIANT = new Variant(MediaType.APPLICATION_JSON_TYPE, Locale.US,
			UTF_8_ENCODING);

	private final URI baseUri;
	private final String oauthUserToken;

	public OfferClientImpl(final URI baseUri, final String oauthUserToken) {
		this.baseUri = baseUri;
		this.oauthUserToken = new StringBuilder().append(OAUTH_USER_TOKEN_PREFIX).append(oauthUserToken).toString();
	}

	@Override
	public Offer getOffer(final String offerId) {
		final Response response = getWebTarget().path(offerId).request().header(AUTHORIZATION_HEADER, oauthUserToken)
				.get();
		if (Status.OK.getStatusCode() == response.getStatus()) {
			final Offer offer = response.readEntity(Offer.class);
			offer.setOfferId(offerId);
			return offer;
		} else if (Status.NOT_FOUND.getStatusCode() == response.getStatus()) {
			return null;
		}
		throw new EbayErrorException(response);
	}

	@Override
	public void updateOffer(final Offer offer) {
		final Entity<Offer> offerEntity = Entity.entity(offer, ENTITY_VARIANT);
		final Response response = getWebTarget().path(offer.getOfferId()).request()
				.header(AUTHORIZATION_HEADER, oauthUserToken).put(offerEntity);
		if (Status.NO_CONTENT.getStatusCode() != response.getStatus()) {
			throw new EbayErrorException(response);
		}
	}

	@Override
	public void createOffer(final Offer offer) {
		final Entity<Offer> offerEntity = Entity.entity(offer, ENTITY_VARIANT);
		final Response response = getWebTarget().request().header(AUTHORIZATION_HEADER, oauthUserToken)
				.post(offerEntity);
		if (Status.CREATED.getStatusCode() != response.getStatus()) {
			throw new EbayErrorException(response);
		}
		final Offer createdOffer = response.readEntity(Offer.class);
		offer.setOfferId(createdOffer.getOfferId());
	}

	@Override
	public Offer getOfferBySku(final String sku) {
		final Response response = getWebTarget().queryParam(SKU_QUERY_PARAMETER, sku).request()
				.header(AUTHORIZATION_HEADER, oauthUserToken).get();
		if (Status.OK.getStatusCode() == response.getStatus()) {
			final Offers offers = response.readEntity(Offers.class);
			return offers.getOffers().stream().findFirst().get();
		} else if (Status.NOT_FOUND.getStatusCode() == response.getStatus()) {
			return null;
		}
		throw new EbayErrorException(response);
	}

	@Override
	public String publishOffer(final String offerId) {
		final Response response = getWebTarget().path(offerId).path(PUBLISH_SUBRESOURCE).request()
				.header(AUTHORIZATION_HEADER, oauthUserToken).post(null);
		if (Status.OK.getStatusCode() == response.getStatus()) {
			final Listing listing = response.readEntity(Listing.class);
			return listing.getListingId();
		}
		throw new EbayErrorException(response);
	}

	private WebTarget getWebTarget() {
		return REST_CLIENT.target(baseUri).path(OFFER_RESOURCE);
	}
}
