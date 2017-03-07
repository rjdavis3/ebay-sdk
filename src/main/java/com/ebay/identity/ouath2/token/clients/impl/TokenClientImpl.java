package com.ebay.identity.ouath2.token.clients.impl;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.ebay.exceptions.EbayErrorResponseException;
import com.ebay.identity.oauth2.token.clients.TokenClient;
import com.ebay.identity.oauth2.token.models.Token;

public class TokenClientImpl implements TokenClient {

	public static final String TOKEN_RESOURCE = "/identity/v1/oauth2/token";
	public static final String GRANT_TYPE = "grant_type";
	public static final String REFRESH_TOKEN = "refresh_token";
	public static final String REDIRECT_URI = "redirect_uri";
	public static final String CODE = "code";
	public static final String AUTHORIZATION_CODE = "authorization_code";

	private static final Client REST_CLIENT = ClientBuilder.newClient()
			.property(ClientProperties.CONNECT_TIMEOUT, 60000).property(ClientProperties.READ_TIMEOUT, 600000)
			.register(JacksonFeature.class);
	private final URI baseUri;

	public TokenClientImpl(final URI baseUri, final String clientId, final String clientSecret) {
		this.baseUri = baseUri;
		final HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(clientId, clientSecret);
		REST_CLIENT.register(feature);
	}

	@Override
	public Token getAccessToken(final String ruName, final String code) {
		final Form form = new Form().param(GRANT_TYPE, AUTHORIZATION_CODE).param(CODE, code).param(REDIRECT_URI,
				ruName);
		final Response response = postForm(form);
		if (Status.OK.getStatusCode() == response.getStatus()) {
			return response.readEntity(Token.class);
		}
		throw new EbayErrorResponseException(response);
	}

	@Override
	public Token refreshAccessToken(final String refreshToken) {
		final Form form = new Form().param(GRANT_TYPE, REFRESH_TOKEN).param(REFRESH_TOKEN, refreshToken);
		final Response response = postForm(form);
		if (Status.OK.getStatusCode() == response.getStatus()) {
			return response.readEntity(Token.class);
		}
		throw new EbayErrorResponseException(response);
	}

	private Response postForm(final Form form) {
		return REST_CLIENT.target(baseUri).path(TOKEN_RESOURCE).request().post(Entity.form(form));
	}

}
