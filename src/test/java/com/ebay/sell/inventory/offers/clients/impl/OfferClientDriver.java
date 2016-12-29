package com.ebay.sell.inventory.offers.clients.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientProperties;
import org.junit.Ignore;
import org.junit.Test;

import com.ebay.sell.inventory.offers.clients.OfferClient;
import com.ebay.sell.inventory.offers.models.Offer;

public class OfferClientDriver {

	private final Client REST_CLIENT = ClientBuilder.newClient()
			.property(ClientProperties.CONNECT_TIMEOUT, 60000)
			.property(ClientProperties.READ_TIMEOUT, 600000);
	private static final String OAUTH_USER_TOKEN = System
			.getenv("EBAY_OAUTH_USER_TOKEN");

	private final OfferClient offerClient = new OfferClientImpl(REST_CLIENT,
			OAUTH_USER_TOKEN);

	@Test
	@Ignore
	public void givenSomeInventoryItemGroupKeyWhenRetrievingInventoryItemGroupThenReturnInventoryItemGroup()
			throws Exception {
		final String offerId = "5005317010";
		final Offer actualOffer = offerClient.getOffer(offerId);
		assertEquals(offerId, actualOffer.getOfferId());
		assertEquals("540007", actualOffer.getSku());
	}

	@Test
	@Ignore
	public void givenSomeOfferWhenUpdatingOfferThenReturn204StatusCode()
			throws Exception {
		final String offerId = "5005317010";
		final Offer offer = offerClient.getOffer(offerId);
		offer.setListingDescription("did this update?");
		offerClient.updateOffer(offer);
	}

	@Test
	@Ignore
	public void givenSomeOfferWhenCreatingOfferThenReturn201StatusCode()
			throws Exception {
		final String offerId = "5006154010";
		final Offer offer = offerClient.getOffer(offerId);
		offer.setOfferId(null);
		offer.setSku("540003");
		offer.setListingDescription("testing another create");
		offerClient.createOffer(offer);
		assertNotNull(offer.getOfferId());
	}

	@Test
	@Ignore
	public void givenSomeSkuWhenRetrievingOfferThenReturnOffer()
			throws Exception {
		final String sku = "540002";
		final Offer actualOffer = offerClient.getOfferBySku(sku);
		assertEquals("5006155010", actualOffer.getOfferId());
	}

	@Test
	public void givenSomeOfferIdWhenPublishingOfferThenReturnListingId()
			throws Exception {
		final String offerId = "5006155010";
		final String actualListingId = offerClient.publishOffer(offerId);
		assertNotNull(actualListingId);
	}
}
