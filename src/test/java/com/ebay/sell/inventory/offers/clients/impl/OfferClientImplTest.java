package com.ebay.sell.inventory.offers.clients.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ebay.exceptions.EbayErrorException;
import com.ebay.sell.inventory.offers.clients.OfferClient;
import com.ebay.sell.inventory.offers.models.Offer;

public class OfferClientImplTest {

	private static final String SOME_OAUTH_USER_TOKEN = "v1-ebay-oauth-token";
	private static final String OFFER_ID = "5005317010";
	private static final String SOME_SKU = "540007";
	private static final String SOME_EBAY_ERROR_MESSAGE = "{\r\n  \"errors\": [\r\n    {\r\n      \"errorId\": 25710,\r\n      \"domain\": \"API_INVENTORY\",\r\n      \"subdomain\": \"Selling\",\r\n      \"category\": \"REQUEST\",\r\n      \"message\": \"We didn't find the entity you are requesting. Please verify the request\"\r\n    }\r\n  ]\r\n}";

	private OfferClient offerClient;

	@Mock
	private Client client;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		offerClient = new OfferClientImpl(client, SOME_OAUTH_USER_TOKEN);
	}

	@Test
	public void givenSomeValidOfferIdWhenRetrievingOfferThenReturnOffer() {
		final Status expectedStatus = Status.OK;
		final Offer expectedOffer = new Offer();
		expectedOffer.setSku(SOME_SKU);

		final WebTarget webTarget = mock(WebTarget.class);
		when(client.target(OfferClientImpl.OFFER_RESOURCE)).thenReturn(
				webTarget);
		when(webTarget.path(OFFER_ID)).thenReturn(webTarget);
		final Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
		when(webTarget.request()).thenReturn(invocationBuilder);
		when(
				invocationBuilder.header(
						eq(OfferClientImpl.AUTHORIZATION_HEADER), anyString()))
				.thenReturn(invocationBuilder);
		final Response response = mock(Response.class);
		final int statusCode = expectedStatus.getStatusCode();
		when(response.getStatus()).thenReturn(statusCode);
		when(response.readEntity(Offer.class)).thenReturn(expectedOffer);
		when(invocationBuilder.get()).thenReturn(response);

		final Offer actualOffer = offerClient.getOffer(OFFER_ID);

		assertEquals(OFFER_ID, actualOffer.getOfferId());
		assertEquals(SOME_SKU, actualOffer.getSku());
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeInvalidOfferIdWhenRetrievingOfferThenThrowNewEbayErrorExceptionWith404StatusCodeAndSomeEbayErrorMessage() {
		final Status expectedStatus = Status.NOT_FOUND;

		final WebTarget webTarget = mock(WebTarget.class);
		when(client.target(OfferClientImpl.OFFER_RESOURCE)).thenReturn(
				webTarget);
		when(webTarget.path(OFFER_ID)).thenReturn(webTarget);
		final Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
		when(webTarget.request()).thenReturn(invocationBuilder);
		when(
				invocationBuilder.header(
						eq(OfferClientImpl.AUTHORIZATION_HEADER), anyString()))
				.thenReturn(invocationBuilder);
		final Response response = mock(Response.class);
		final int statusCode = expectedStatus.getStatusCode();
		when(response.getStatus()).thenReturn(statusCode);
		when(response.readEntity(String.class)).thenReturn(
				SOME_EBAY_ERROR_MESSAGE);
		when(invocationBuilder.get()).thenReturn(response);

		offerClient.getOffer(OFFER_ID);
	}

	@Test
	public void givenSomeOfferWhenUpdatingOfferThenReturn204StatusCode() {
		final Offer offer = new Offer();
		offer.setOfferId(OFFER_ID);
		offer.setSku(SOME_SKU);
		final Status expectedStatus = Status.NO_CONTENT;

		final WebTarget webTarget = mock(WebTarget.class);
		when(client.target(OfferClientImpl.OFFER_RESOURCE)).thenReturn(
				webTarget);
		when(webTarget.path(OFFER_ID)).thenReturn(webTarget);
		final Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
		when(webTarget.request()).thenReturn(invocationBuilder);
		when(
				invocationBuilder.header(
						eq(OfferClientImpl.AUTHORIZATION_HEADER), anyString()))
				.thenReturn(invocationBuilder);
		final Response response = mock(Response.class);
		final int statusCode = expectedStatus.getStatusCode();
		when(response.getStatus()).thenReturn(statusCode);
		when(invocationBuilder.put(any(Entity.class))).thenReturn(response);

		offerClient.updateOffer(offer);
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeOfferWithInvalidMarketPlaceIdWhenUpdatingOfferThenThrowNewEbayErrorExceptionWith400StatusCodeAndSomeEbayErrorMessage() {
		final Offer offer = new Offer();
		offer.setOfferId(OFFER_ID);
		offer.setSku(SOME_SKU);
		final Status expectedStatus = Status.BAD_REQUEST;

		final WebTarget webTarget = mock(WebTarget.class);
		when(client.target(OfferClientImpl.OFFER_RESOURCE)).thenReturn(
				webTarget);
		when(webTarget.path(OFFER_ID)).thenReturn(webTarget);
		final Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
		when(webTarget.request()).thenReturn(invocationBuilder);
		when(
				invocationBuilder.header(
						eq(OfferClientImpl.AUTHORIZATION_HEADER), anyString()))
				.thenReturn(invocationBuilder);
		final Response response = mock(Response.class);
		final int statusCode = expectedStatus.getStatusCode();
		when(response.getStatus()).thenReturn(statusCode);
		when(response.readEntity(String.class)).thenReturn(
				SOME_EBAY_ERROR_MESSAGE);
		when(invocationBuilder.put(any(Entity.class))).thenReturn(response);

		offerClient.updateOffer(offer);
	}

}
