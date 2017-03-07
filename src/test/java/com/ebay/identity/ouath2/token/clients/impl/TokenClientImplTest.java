package com.ebay.identity.ouath2.token.clients.impl;

import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.junit.Assert.assertEquals;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.ebay.exceptions.EbayErrorResponseException;
import com.ebay.identity.oauth2.token.clients.TokenClient;
import com.ebay.identity.oauth2.token.models.Token;
import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverRule;

public class TokenClientImplTest {

	private static final char EQUAL = '=';
	private static final char AMPERSAND = '&';
	private static final String SOME_CLIENT_ID = "some-client-id";
	private static final String SOME_CLIENT_SECRET = "some-client-secret";
	private static final String SOME_ACCESS_TOKEN = "some-access-token";
	private static final int SOME_EXPIRES_IN = 7200;
	private static final String SOME_REFRESH_TOKEN = "some-refresh-token";
	private static final int SOME_REFRESH_TOKEN_EXPIRES_IN = 7200;
	private static final String SOME_TOKEN_TYPE = "User Access Token";

	private TokenClient tokenClient;

	@Rule
	public ClientDriverRule driver = new ClientDriverRule();

	@Before
	public void setUp() {
		final URI baseUri = URI.create(driver.getBaseUrl());
		tokenClient = new TokenClientImpl(baseUri, SOME_CLIENT_ID, SOME_CLIENT_SECRET);
	}

	@Test
	public void givenValidRuNameAndValidAuthorizationCodeWhenGeneratingAccessTokenThenReturnToken()
			throws JAXBException {
		final String ruName = "some-RuName";
		final String code = "some-authorization-code";

		final String expectedRequestBody = new StringBuilder().append(TokenClientImpl.GRANT_TYPE).append(EQUAL)
				.append(TokenClientImpl.AUTHORIZATION_CODE).append(AMPERSAND).append(TokenClientImpl.CODE).append(EQUAL)
				.append(code).append(AMPERSAND).append(TokenClientImpl.REDIRECT_URI).append(EQUAL).append(ruName)
				.toString();

		final Status expectedResponseStatus = Status.OK;
		final String expectedResponseBody = buildValidTokenResponseBody();

		mockRequest(expectedRequestBody, expectedResponseStatus, expectedResponseBody);

		final Token actualToken = tokenClient.getAccessToken(ruName, code);

		assertExpectedToken(actualToken);
	}

	@Test(expected = EbayErrorResponseException.class)
	public void givenValidRuNameAndInvalidAuthorizationCodeWhenGeneratingAccessTokenThenThrowNewEbayErrorException()
			throws JAXBException {
		final String ruName = "some-RuName";
		final String code = "some-authorization-code";

		final String expectedRequestBody = new StringBuilder().append(TokenClientImpl.GRANT_TYPE).append(EQUAL)
				.append(TokenClientImpl.AUTHORIZATION_CODE).append(AMPERSAND).append(TokenClientImpl.CODE).append(EQUAL)
				.append(code).append(AMPERSAND).append(TokenClientImpl.REDIRECT_URI).append(EQUAL).append(ruName)
				.toString();

		final Status expectedResponseStatus = Status.BAD_REQUEST;
		final String expectedResponseBody = "{\"error\":\"invalid_request\",\"error_description\":\"request is invalid\",\"error_uri\":null}";

		mockRequest(expectedRequestBody, expectedResponseStatus, expectedResponseBody);

		tokenClient.getAccessToken(ruName, code);
	}

	@Test
	public void givenValidRefreshTokenWhenRefreshingAccessTokenThenReturnToken() {
		final String refreshToken = "some-refresh-token";

		final String expectedRequestBody = new StringBuilder().append(TokenClientImpl.GRANT_TYPE).append(EQUAL)
				.append(TokenClientImpl.REFRESH_TOKEN).append(AMPERSAND).append(TokenClientImpl.REFRESH_TOKEN)
				.append(EQUAL).append(refreshToken).toString();

		final Status expectedResponseStatus = Status.OK;
		final String expectedResponseBody = buildValidTokenResponseBody();

		mockRequest(expectedRequestBody, expectedResponseStatus, expectedResponseBody);

		final Token actualToken = tokenClient.refreshAccessToken(refreshToken);

		assertExpectedToken(actualToken);
	}

	@Test(expected = EbayErrorResponseException.class)
	public void givenExpiredRefreshTokenWhenRefreshingAccessTokenThenThrowNewEbayErrorException() {
		final String refreshToken = "some-refresh-token";

		final String expectedRequestBody = new StringBuilder().append(TokenClientImpl.GRANT_TYPE).append(EQUAL)
				.append(TokenClientImpl.REFRESH_TOKEN).append(AMPERSAND).append(TokenClientImpl.REFRESH_TOKEN)
				.append(EQUAL).append(refreshToken).toString();

		final Status expectedResponseStatus = Status.BAD_REQUEST;
		final String expectedResponseBody = "{\"error\":\"invalid_grant\",\"error_description\":\"refresh token is invalid or expired\",\"error_uri\":null}";

		mockRequest(expectedRequestBody, expectedResponseStatus, expectedResponseBody);

		tokenClient.refreshAccessToken(refreshToken);
	}

	private String buildValidTokenResponseBody() {
		final JSONObject jsonObject = new JSONObject();
		jsonObject.put("access_token", SOME_ACCESS_TOKEN);
		jsonObject.put("expires_in", SOME_EXPIRES_IN);
		jsonObject.put("refresh_token", SOME_REFRESH_TOKEN);
		jsonObject.put("refresh_token_expires_in", SOME_REFRESH_TOKEN_EXPIRES_IN);
		jsonObject.put("token_type", SOME_TOKEN_TYPE);
		return jsonObject.toString();
	}

	private void mockRequest(final String expectedRequestBody, final Status expectedResponseStatus,
			final String expectedResponseBody) {
		driver.addExpectation(
				onRequestTo(TokenClientImpl.TOKEN_RESOURCE).withBasicAuth(SOME_CLIENT_ID, SOME_CLIENT_SECRET)
						.withMethod(Method.POST).withBody(expectedRequestBody, MediaType.APPLICATION_FORM_URLENCODED),
				giveResponse(expectedResponseBody, MediaType.APPLICATION_JSON)
						.withStatus(expectedResponseStatus.getStatusCode()));
	}

	private void assertExpectedToken(final Token actualToken) {
		assertEquals(SOME_ACCESS_TOKEN, actualToken.getAccessToken());
		assertEquals(SOME_EXPIRES_IN, actualToken.getExpiresIn());
		assertEquals(SOME_REFRESH_TOKEN, actualToken.getRefreshToken());
		assertEquals(SOME_REFRESH_TOKEN_EXPIRES_IN, actualToken.getRefreshTokenExpiresIn());
		assertEquals(SOME_TOKEN_TYPE, actualToken.getTokenType());
	}

}
