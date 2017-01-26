package com.ebay.identity.oauth2.token.clients;

import com.ebay.identity.oauth2.token.models.Token;

public interface TokenClient {

	public Token getAccessToken(final String ruName, final String code);

	public Token refreshAccessToken(final String refreshToken);

}
