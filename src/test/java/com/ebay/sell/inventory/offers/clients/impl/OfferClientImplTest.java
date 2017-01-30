package com.ebay.sell.inventory.offers.clients.impl;

import static com.github.restdriver.clientdriver.RestClientDriver.giveEmptyResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Arrays;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ebay.exceptions.EbayErrorException;
import com.ebay.identity.oauth2.token.clients.TokenClient;
import com.ebay.identity.oauth2.token.models.Token;
import com.ebay.identity.oauth2.token.models.UserToken;
import com.ebay.sell.inventory.offers.clients.OfferClient;
import com.ebay.sell.inventory.offers.models.Listing;
import com.ebay.sell.inventory.offers.models.Offer;
import com.ebay.sell.inventory.offers.models.Offers;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.github.restdriver.clientdriver.capture.JsonBodyCapture;

public class OfferClientImplTest {

	private static final char FORWARD_SLASH = '/';
	private static final String SOME_OAUTH_USER_TOKEN = "v1-ebay-oauth-token";
	private static final String SOME_NEW_OAUTH_USER_TOKEN = "v1-ebay-oauth-token-1";
	private static final String SOME_OFFER_ID = "5005317010";
	private static final String SOME_SKU = "540007";
	private static final String SOME_LISTING_ID = "223412345678";
	private static final String SOME_EBAY_ERROR_MESSAGE = "{\r\n  \"errors\": [\r\n    {\r\n      \"errorId\": 25710,\r\n      \"domain\": \"API_INVENTORY\",\r\n      \"subdomain\": \"Selling\",\r\n      \"category\": \"REQUEST\",\r\n      \"message\": \"We didn't find the entity you are requesting. Please verify the request\"\r\n    }\r\n  ]\r\n}";
	private static final String SOME_REFRESH_TOKEN = "some-refresh-token";

	private OfferClient offerClient;

	@Rule
	public ClientDriverRule driver = new ClientDriverRule();

	@Mock
	private TokenClient tokenClient;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		final URI baseUri = URI.create(driver.getBaseUrl());

		final Token token = new Token();
		token.setAccessToken(SOME_OAUTH_USER_TOKEN);
		token.setRefreshToken(SOME_REFRESH_TOKEN);
		when(tokenClient.refreshAccessToken(SOME_REFRESH_TOKEN)).thenReturn(token);

		final UserToken userToken = new UserToken(tokenClient, SOME_REFRESH_TOKEN);

		offerClient = new OfferClientImpl(baseUri, userToken);
	}

	@Test
	public void givenSomeValidOfferIdWhenRetrievingOfferThenReturnOffer() {
		final Status expectedResponseStatus = Status.OK;
		final Offer expectedOffer = new Offer();
		expectedOffer.setSku(SOME_SKU);

		final String expectedResponseBody = new JSONObject(expectedOffer).toString();
		mockGetOffer(expectedResponseStatus, expectedResponseBody);

		final Offer actualOffer = offerClient.getOffer(SOME_OFFER_ID);

		assertEquals(SOME_OFFER_ID, actualOffer.getOfferId());
		assertEquals(SOME_SKU, actualOffer.getSku());
	}

	@Test
	public void givenSomeInvalidOfferIdWhenRetrievingOfferThenReturnNullOffer() {
		final Status expectedResponseStatus = Status.NOT_FOUND;

		final String expectedResponseBody = null;
		mockGetOffer(expectedResponseStatus, expectedResponseBody);

		final Offer actualOffer = offerClient.getOffer(SOME_OFFER_ID);

		assertNull(actualOffer);
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeInternalServerErrorAndSomeOfferIdWhenRetrievingOfferThenThrowNewEbayErrorException() {
		final Status expectedResponseStatus = Status.INTERNAL_SERVER_ERROR;

		final String expectedResponseBody = SOME_EBAY_ERROR_MESSAGE;
		mockGetOffer(expectedResponseStatus, expectedResponseBody);

		offerClient.getOffer(SOME_OFFER_ID);
	}

	@Test
	public void givenSomeOfferWhenUpdatingOfferThenReturn204StatusCode() {
		final Status expectedResponseStatus = Status.NO_CONTENT;

		final Offer offer = new Offer();
		offer.setOfferId(SOME_OFFER_ID);
		offer.setSku(SOME_SKU);

		final JsonBodyCapture actualResponseBody = mockUpdateOffer(expectedResponseStatus);

		offerClient.updateOffer(offer);

		final JsonNode actualResponseBodyJsonNode = actualResponseBody.getContent();
		assertEquals(SOME_OFFER_ID, actualResponseBodyJsonNode.get("offerId").asText());
		assertEquals(SOME_SKU, actualResponseBodyJsonNode.get("sku").asText());
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeOfferWithInvalidMarketPlaceIdWhenUpdatingOfferThenThrowNewEbayErrorExceptionWith400StatusCodeAndSomeEbayErrorMessage() {
		final Offer offer = new Offer();
		offer.setOfferId(SOME_OFFER_ID);
		offer.setSku(SOME_SKU);
		final Status expectedResponseStatus = Status.BAD_REQUEST;

		mockUpdateOffer(expectedResponseStatus);

		offerClient.updateOffer(offer);
	}

	@Test
	public void givenSomeOfferWhenCreatingOfferThenReturn201StatusCodeAndOfferId() {
		final Offer offer = new Offer();
		offer.setOfferId(null);
		offer.setSku(SOME_SKU);
		final Status expectedResponseStatus = Status.CREATED;

		final Offer createdOffer = new Offer();
		createdOffer.setOfferId(SOME_OFFER_ID);
		createdOffer.setSku(SOME_SKU);
		final String expectedResponseBody = new JSONObject(createdOffer).toString();
		final JsonBodyCapture actualResponseBody = mockCreateOffer(expectedResponseStatus, expectedResponseBody);

		offerClient.createOffer(offer);

		final JsonNode actualResponseBodyJsonNode = actualResponseBody.getContent();
		assertEquals(SOME_SKU, actualResponseBodyJsonNode.get("sku").asText());

		assertEquals(SOME_OFFER_ID, offer.getOfferId());
		assertEquals(SOME_SKU, offer.getSku());

	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeAlreadyExistingOfferWhenCreatingOfferThenThrowNewEbayErrorExceptionWith400StatusCodeAndSomeEbayErrorMessage() {
		final Offer offer = new Offer();
		offer.setOfferId(SOME_OFFER_ID);
		offer.setSku(SOME_SKU);
		final Status expectedResponseStatus = Status.BAD_REQUEST;

		final String expectedResponseBody = SOME_EBAY_ERROR_MESSAGE;
		mockCreateOffer(expectedResponseStatus, expectedResponseBody);

		offerClient.createOffer(offer);
	}

	@Test
	public void givenSomeValidSkuWhenRetrievingOfferThenReturnOffer() {
		final Status expectedResponseStatus = Status.OK;
		final Offer expectedOffer = new Offer();
		expectedOffer.setOfferId(SOME_OFFER_ID);
		expectedOffer.setSku(SOME_SKU);

		final Offers offers = new Offers();
		offers.setOffers(Arrays.asList(expectedOffer));
		final String expectedResponseBody = new JSONObject(offers).toString();
		mockGetOfferBySku(expectedResponseStatus, expectedResponseBody, SOME_OAUTH_USER_TOKEN);

		final Offer actualOffer = offerClient.getOfferBySku(SOME_SKU);

		assertEquals(SOME_OFFER_ID, actualOffer.getOfferId());
		assertEquals(SOME_SKU, actualOffer.getSku());
	}

	@Test
	public void givenSomeValidSkuAndExpiredAccessTokenWhenRetrievingOfferThenRefreshAccessTokenAndReturnOffer() {
		mockGetOfferBySku(Status.UNAUTHORIZED, SOME_EBAY_ERROR_MESSAGE, SOME_OAUTH_USER_TOKEN);

		final Token token = new Token();
		token.setAccessToken(SOME_NEW_OAUTH_USER_TOKEN);
		token.setRefreshToken(SOME_REFRESH_TOKEN);
		when(tokenClient.refreshAccessToken(SOME_REFRESH_TOKEN)).thenReturn(token);

		final Status expectedResponseStatus = Status.OK;
		final Offer expectedOffer = new Offer();
		expectedOffer.setOfferId(SOME_OFFER_ID);
		expectedOffer.setSku(SOME_SKU);

		final Offers offers = new Offers();
		offers.setOffers(Arrays.asList(expectedOffer));
		final String expectedResponseBody = new JSONObject(offers).toString();
		mockGetOfferBySku(expectedResponseStatus, expectedResponseBody, SOME_NEW_OAUTH_USER_TOKEN);

		final Offer actualOffer = offerClient.getOfferBySku(SOME_SKU);

		assertEquals(SOME_OFFER_ID, actualOffer.getOfferId());
		assertEquals(SOME_SKU, actualOffer.getSku());
	}

	@Test
	public void givenSomeInvalidSkuWhenRetrievingOfferThenReturnNullOffer() {
		final Status expectedResponseStatus = Status.NOT_FOUND;

		final String expectedResponseBody = null;
		mockGetOfferBySku(expectedResponseStatus, expectedResponseBody, SOME_OAUTH_USER_TOKEN);

		final Offer actualOffer = offerClient.getOfferBySku(SOME_SKU);

		assertNull(actualOffer);
	}

	@Test(expected = EbayErrorException.class)
	public void givenInternalServerErrorAndSomeSkuWhenRetrievingOfferThenThrowNewEbayErrorException() {
		final Status expectedResponseStatus = Status.INTERNAL_SERVER_ERROR;

		final String expectedResponseBody = SOME_EBAY_ERROR_MESSAGE;
		mockGetOfferBySku(expectedResponseStatus, expectedResponseBody, SOME_OAUTH_USER_TOKEN);

		offerClient.getOfferBySku(SOME_SKU);
	}

	@Test
	public void givenSomeValidOfferIdWhenPublishingOfferThenReturnListingId() {
		final Status expectedResponseStatus = Status.OK;

		final Listing listing = new Listing();
		listing.setListingId(SOME_LISTING_ID);
		final String expectedResponseBody = new JSONObject(listing).toString();
		mockPublishOffer(expectedResponseStatus, expectedResponseBody);

		final String actualListingId = offerClient.publishOffer(SOME_OFFER_ID);

		assertEquals(SOME_LISTING_ID, actualListingId);
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeInvalidOfferIdWhenPublishingOfferThenThrowNewEbayErrorException() {
		final Status expectedResponseStatus = Status.NOT_FOUND;

		final String expectedResponseBody = SOME_EBAY_ERROR_MESSAGE;
		mockPublishOffer(expectedResponseStatus, expectedResponseBody);

		offerClient.publishOffer(SOME_OFFER_ID);
	}

	private void mockGetOffer(final Status expectedResponseStatus, final String expectedResponseBody) {
		driver.addExpectation(
				onRequestTo(new StringBuilder().append(OfferClientImpl.OFFER_RESOURCE).append(FORWARD_SLASH)
						.append(SOME_OFFER_ID).toString())
								.withHeader(OfferClientImpl.AUTHORIZATION_HEADER,
										new StringBuilder().append(OfferClientImpl.OAUTH_USER_TOKEN_PREFIX)
												.append(SOME_OAUTH_USER_TOKEN).toString())
								.withMethod(Method.GET),
				giveResponse(expectedResponseBody, MediaType.APPLICATION_JSON)
						.withStatus(expectedResponseStatus.getStatusCode()));
	}

	private JsonBodyCapture mockUpdateOffer(final Status expectedResponseStatus) {
		final JsonBodyCapture jsonBodyCapture = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(new StringBuilder().append(OfferClientImpl.OFFER_RESOURCE).append(FORWARD_SLASH)
						.append(SOME_OFFER_ID).toString())
								.withHeader(OfferClientImpl.AUTHORIZATION_HEADER,
										new StringBuilder().append(OfferClientImpl.OAUTH_USER_TOKEN_PREFIX)
												.append(SOME_OAUTH_USER_TOKEN).toString())
								.withMethod(Method.PUT).capturingBodyIn(jsonBodyCapture),
				giveEmptyResponse().withStatus(expectedResponseStatus.getStatusCode()));
		return jsonBodyCapture;
	}

	private JsonBodyCapture mockCreateOffer(final Status expectedResponseStatus, final String expectedResponseBody) {
		final JsonBodyCapture jsonBodyCapture = new JsonBodyCapture();
		driver.addExpectation(
				onRequestTo(OfferClientImpl.OFFER_RESOURCE)
						.withHeader(OfferClientImpl.AUTHORIZATION_HEADER,
								new StringBuilder().append(OfferClientImpl.OAUTH_USER_TOKEN_PREFIX)
										.append(SOME_OAUTH_USER_TOKEN).toString())
						.withMethod(Method.POST).capturingBodyIn(jsonBodyCapture),
				giveResponse(expectedResponseBody, MediaType.APPLICATION_JSON)
						.withStatus(expectedResponseStatus.getStatusCode()));
		return jsonBodyCapture;
	}

	private void mockGetOfferBySku(final Status expectedResponseStatus, final String expectedResponseBody,
			final String expectedOauthUserToken) {
		driver.addExpectation(
				onRequestTo(OfferClientImpl.OFFER_RESOURCE).withParam(OfferClientImpl.SKU_QUERY_PARAMETER, SOME_SKU)
						.withHeader(OfferClientImpl.AUTHORIZATION_HEADER,
								new StringBuilder().append(OfferClientImpl.OAUTH_USER_TOKEN_PREFIX)
										.append(expectedOauthUserToken).toString())
						.withMethod(Method.GET),
				giveResponse(expectedResponseBody, MediaType.APPLICATION_JSON)
						.withStatus(expectedResponseStatus.getStatusCode()));
	}

	private void mockPublishOffer(final Status expectedResponseStatus, final String expectedResponseBody) {
		driver.addExpectation(
				onRequestTo(new StringBuilder().append(OfferClientImpl.OFFER_RESOURCE).append(FORWARD_SLASH)
						.append(SOME_OFFER_ID).append(FORWARD_SLASH).append(OfferClientImpl.PUBLISH_SUBRESOURCE)
						.toString())
								.withHeader(OfferClientImpl.AUTHORIZATION_HEADER,
										new StringBuilder().append(OfferClientImpl.OAUTH_USER_TOKEN_PREFIX)
												.append(SOME_OAUTH_USER_TOKEN).toString())
								.withMethod(Method.POST),
				giveResponse(expectedResponseBody, MediaType.APPLICATION_JSON)
						.withStatus(expectedResponseStatus.getStatusCode()));
	}

}
