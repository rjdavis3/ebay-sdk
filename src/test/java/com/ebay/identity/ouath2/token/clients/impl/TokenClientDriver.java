package com.ebay.identity.ouath2.token.clients.impl;

import static org.junit.Assert.assertNotNull;

import java.net.URI;

import org.junit.Ignore;
import org.junit.Test;

import com.ebay.identity.oauth2.token.clients.TokenClient;
import com.ebay.identity.oauth2.token.models.Token;

public class TokenClientDriver {

	private static final URI SANDBOX_URI = URI.create("https://api.sandbox.ebay.com");
	private static final String CLIENT_ID = System.getenv("EBAY_CLIENT_ID");
	private static final String CLIENT_SECRET = System.getenv("EBAY_CLIENT_SECRET");

	private final TokenClient tokenClient = new TokenClientImpl(SANDBOX_URI, CLIENT_ID, CLIENT_SECRET);

	@Test
	public void givenValidRuNameAndValidAuthorizationCodeWhenGeneratingAccessTokenThenReturnToken() {
		final String ruName = "some-RuName";
		final String code = "some-authorization-code";
		final Token actualToken = tokenClient.getAccessToken(ruName, code);

		assertNotNull(actualToken.getAccessToken());
		assertNotNull(actualToken.getRefreshToken());
		printToken(actualToken);
	}

	@Test
	@Ignore
	public void givenValidRefreshTokenWhenRefreshingAccessTokenThenReturnToken() {
		final String refreshToken = "some-refresh-token";
		final Token actualToken = tokenClient.refreshAccessToken(refreshToken);

		assertNotNull(actualToken.getAccessToken());
		printToken(actualToken);
	}

	private void printToken(final Token actualToken) {
		System.out.println("access token: " + actualToken.getAccessToken());
		System.out.println("expires in: " + actualToken.getExpiresIn());
		System.out.println("refresh token: " + actualToken.getRefreshToken());
		System.out.println("refresh token expires in: " + actualToken.getRefreshTokenExpiresIn());
		System.out.println("token type: " + actualToken.getTokenType());
	}
}
