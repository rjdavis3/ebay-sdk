package com.ebay.identity.ouath2.token.clients.impl;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.ebay.exceptions.EbayErrorException;
import com.ebay.identity.oauth2.token.clients.TokenClient;
import com.ebay.identity.oauth2.token.models.Token;

public class TokenClientImpl implements TokenClient {

	static final String TOKEN_RESOURCE = "https://api.sandbox.ebay.com/identity/v1/oauth2/token";
	static final String GRANT_TYPE = "grant_type";
	static final String REDIRECT_URI = "redirect_uri";
	static final String REFRESH_TOKEN = "refresh_token";
	static final String CODE_QUERY_PARAMETER = "code";
	static final String AUTHORIZATION_CODE = "authorization_code";

	private static final Client REST_CLIENT = ClientBuilder.newClient()
			.property(ClientProperties.CONNECT_TIMEOUT, 60000).property(ClientProperties.READ_TIMEOUT, 600000);

	public TokenClientImpl(final String clientId, final String clientSecret) {
		final HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(clientId, clientSecret);
		REST_CLIENT.register(feature);
	}

	@Override
	public Token getAccessToken(final String ruName, final String code) {
		final Form form = new Form().param(GRANT_TYPE, AUTHORIZATION_CODE).param(CODE_QUERY_PARAMETER, code)
				.param(REDIRECT_URI, ruName);
		final Response response = REST_CLIENT.target(TOKEN_RESOURCE).request().post(Entity.form(form));
		if (Status.OK.getStatusCode() == response.getStatus()) {
			return response.readEntity(Token.class);
		}
		throw new EbayErrorException(response);
	}

	@Override
	public Token refreshAccessToken(final String refreshToken) {
		final Form form = new Form().param(GRANT_TYPE, REFRESH_TOKEN).param(REFRESH_TOKEN, refreshToken);
		final Response response = REST_CLIENT.target(TOKEN_RESOURCE).request().post(Entity.form(form));
		if (Status.OK.getStatusCode() == response.getStatus()) {
			return response.readEntity(Token.class);
		}
		throw new EbayErrorException(response);
	}

}
