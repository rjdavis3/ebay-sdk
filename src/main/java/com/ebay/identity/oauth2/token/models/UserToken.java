package com.ebay.identity.oauth2.token.models;

import com.ebay.identity.oauth2.token.clients.TokenClient;

public class UserToken {

	private final TokenClient tokenClient;
	private final String refreshToken;

	private String accessToken;

	public UserToken(final TokenClient tokenClient, final String refreshToken) {
		this.tokenClient = tokenClient;
		this.refreshToken = refreshToken;
		refreshToken(tokenClient, refreshToken);
	}

	public synchronized String getToken() {
		return accessToken;
	}

	public synchronized void refreshToken() {
		refreshToken(tokenClient, refreshToken);
	}

	private synchronized void refreshToken(final TokenClient tokenClient, final String refreshToken) {
		final Token token = tokenClient.refreshAccessToken(refreshToken);
		this.accessToken = token.getAccessToken();
	}

}
