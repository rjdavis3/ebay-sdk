package com.ebay.clients.impl;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Variant;

import org.glassfish.jersey.client.ClientProperties;

import com.ebay.exceptions.EbayErrorException;
import com.ebay.identity.oauth2.token.models.UserToken;

public class EbayClientImpl {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String OAUTH_USER_TOKEN_PREFIX = "Bearer ";

	private static final Client REST_CLIENT = ClientBuilder.newClient()
			.property(ClientProperties.CONNECT_TIMEOUT, 60000).property(ClientProperties.READ_TIMEOUT, 600000);
	private static final String UTF_8_ENCODING = "utf-8";
	private static final Variant ENTITY_VARIANT = new Variant(MediaType.APPLICATION_JSON_TYPE, Locale.US,
			UTF_8_ENCODING);

	private final URI baseUri;

	private final UserToken userToken;

	public EbayClientImpl(final URI baseUri, final UserToken userToken) {
		this.baseUri = baseUri;
		this.userToken = userToken;
	}

	protected WebTarget getWebTarget() {
		return REST_CLIENT.target(baseUri);
	}

	protected <T> T get(final WebTarget webTarget, final Class<T> entityType, final Status... expectedStatus) {
		Response response = webTarget.request().header(AUTHORIZATION_HEADER, getUserToken()).get();
		if (Status.UNAUTHORIZED.getStatusCode() == response.getStatus()) {
			userToken.refreshToken();
			response = webTarget.request().header(AUTHORIZATION_HEADER, getUserToken()).get();
		}
		return handleResponse(response, entityType, expectedStatus);
	}

	protected <T, V> V post(final WebTarget webTarget, final T object, final Class<V> entityType,
			final Status... expectedStatus) {
		final Entity<T> entity = Entity.entity(object, ENTITY_VARIANT);
		Response response = webTarget.request().header(AUTHORIZATION_HEADER, getUserToken()).post(entity);
		if (Status.UNAUTHORIZED.getStatusCode() == response.getStatus()) {
			userToken.refreshToken();
			response = webTarget.request().header(AUTHORIZATION_HEADER, getUserToken()).post(entity);
		}
		return handleResponse(response, entityType, expectedStatus);
	}

	protected <T> void put(final WebTarget webTarget, final T object, final Status... expectedStatus) {
		final Entity<T> entity = Entity.entity(object, ENTITY_VARIANT);
		Response response = webTarget.request().header(AUTHORIZATION_HEADER, getUserToken()).put(entity);
		if (Status.UNAUTHORIZED.getStatusCode() == response.getStatus()) {
			userToken.refreshToken();
			response = webTarget.request().header(AUTHORIZATION_HEADER, getUserToken()).put(entity);
		}
		handleResponse(response, expectedStatus);
	}

	protected <T> void delete(final WebTarget webTarget, final Status... expectedStatus) {
		Response response = webTarget.request().header(AUTHORIZATION_HEADER, getUserToken()).delete();
		if (Status.UNAUTHORIZED.getStatusCode() == response.getStatus()) {
			userToken.refreshToken();
			response = webTarget.request().header(AUTHORIZATION_HEADER, getUserToken()).delete();
		}
		handleResponse(response, expectedStatus);
	}

	private <T> T handleResponse(final Response response, final Class<T> entityType, final Status... expectedStatus) {
		final List<Integer> expectedStatusCodes = Arrays.asList(expectedStatus).stream().map(Status::getStatusCode)
				.collect(Collectors.toList());
		if (expectedStatusCodes.contains(response.getStatus())) {
			return response.readEntity(entityType);
		}
		throw new EbayErrorException(response);
	}

	private void handleResponse(final Response response, final Status... expectedStatus) {
		final List<Integer> expectedStatusCodes = Arrays.asList(expectedStatus).stream().map(Status::getStatusCode)
				.collect(Collectors.toList());
		if (!expectedStatusCodes.contains(response.getStatus())) {
			throw new EbayErrorException(response);
		}
	}

	private String getUserToken() {
		return new StringBuilder().append(OAUTH_USER_TOKEN_PREFIX).append(userToken.getToken()).toString();
	}

}
