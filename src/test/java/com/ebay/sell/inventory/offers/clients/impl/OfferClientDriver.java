package com.ebay.sell.inventory.offers.clients.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

import com.ebay.EbaySdk;
import com.ebay.identity.oauth2.token.models.UserToken;
import com.ebay.identity.ouath2.token.clients.impl.TokenClientImpl;
import com.ebay.sell.inventory.offers.clients.OfferClient;
import com.ebay.sell.inventory.offers.models.Offer;

public class OfferClientDriver {

	private static final String CLIENT_ID = System.getenv("EBAY_CLIENT_ID");
	private static final String CLIENT_SECRET = System.getenv("EBAY_CLIENT_SECRET");
	private static final String REFRESH_TOKEN = System.getenv("EBAY_REFRESH_TOKEN");

	private final OfferClient offerClient = new OfferClientImpl(EbaySdk.SANDBOX_URI,
			new UserToken(new TokenClientImpl(EbaySdk.SANDBOX_URI, CLIENT_ID, CLIENT_SECRET), REFRESH_TOKEN));

	@Test
	@Ignore
	public void givenSomeOfferIdWhenRetrievingOfferThenReturnOffer() throws Exception {
		final String offerId = "5005317010";
		final Offer actualOffer = offerClient.getOffer(offerId);
		assertEquals(offerId, actualOffer.getOfferId());
		assertEquals("540007", actualOffer.getSku());
	}

	@Test
	@Ignore
	public void givenSomeOfferWhenUpdatingOfferThenReturn204StatusCode() throws Exception {
		final String offerId = "5005317010";
		final Offer offer = offerClient.getOffer(offerId);
		offer.setListingDescription("did this update?");
		offerClient.updateOffer(offer);
	}

	@Test
	@Ignore
	public void givenSomeOfferWhenCreatingOfferThenReturn201StatusCode() throws Exception {
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
	public void givenSomeSkuWhenRetrievingOfferThenReturnOffer() throws Exception {
		final String sku = "540002";
		final Offer actualOffer = offerClient.getOfferBySku(sku);
		assertEquals("5006155010", actualOffer.getOfferId());
	}

	@Test
	public void givenSomeOfferIdWhenPublishingOfferThenReturnListingId() throws Exception {
		final String offerId = "5006155010";
		final String actualListingId = offerClient.publishOffer(offerId);
		assertNotNull(actualListingId);
	}
}
