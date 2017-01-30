package com.ebay.sell.inventory.offers.clients.impl;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.ClientProperties;

import com.ebay.clients.impl.EbayClientImpl;
import com.ebay.identity.oauth2.token.models.UserToken;
import com.ebay.sell.inventory.offers.clients.OfferClient;
import com.ebay.sell.inventory.offers.models.Listing;
import com.ebay.sell.inventory.offers.models.Offer;
import com.ebay.sell.inventory.offers.models.Offers;

public class OfferClientImpl extends EbayClientImpl implements OfferClient {

	static final String OFFER_RESOURCE = "/sell/inventory/v1/offer";
	static final String SKU_QUERY_PARAMETER = "sku";
	static final String PUBLISH_SUBRESOURCE = "publish";

	private static final Client REST_CLIENT = ClientBuilder.newClient()
			.property(ClientProperties.CONNECT_TIMEOUT, 60000).property(ClientProperties.READ_TIMEOUT, 600000);

	private final URI baseUri;

	public OfferClientImpl(final URI baseUri, final UserToken userToken) {
		super(userToken);
		this.baseUri = baseUri;
	}

	@Override
	public Offer getOffer(final String offerId) {
		final WebTarget webTarget = getWebTarget().path(offerId);
		final Offer offer = get(webTarget, Offer.class, Status.OK, Status.NOT_FOUND);
		if (offer != null) {
			offer.setOfferId(offerId);
		}
		return offer;
	}

	@Override
	public void updateOffer(final Offer offer) {
		final WebTarget webTarget = getWebTarget().path(offer.getOfferId());
		put(webTarget, offer, Status.NO_CONTENT);
	}

	@Override
	public void createOffer(final Offer offer) {
		final WebTarget webTarget = getWebTarget();
		final Offer createdOffer = post(webTarget, offer, Offer.class, Status.CREATED);
		offer.setOfferId(createdOffer.getOfferId());
	}

	@Override
	public Offer getOfferBySku(final String sku) {
		final WebTarget webTarget = getWebTarget().queryParam(SKU_QUERY_PARAMETER, sku);
		final Offers offers = get(webTarget, Offers.class, Status.OK, Status.NOT_FOUND);
		if (offers == null) {
			return null;
		}
		return offers.getOffers().stream().findFirst().get();
	}

	@Override
	public String publishOffer(final String offerId) {
		final WebTarget webTarget = getWebTarget().path(offerId).path(PUBLISH_SUBRESOURCE);
		final Listing listing = post(webTarget, null, Listing.class, Status.OK);
		return listing.getListingId();
	}

	private WebTarget getWebTarget() {
		return REST_CLIENT.target(baseUri).path(OFFER_RESOURCE);
	}
}
