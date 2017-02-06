package com.ebay.shopping.categories.clients.impl;

import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;

import com.ebay.exceptions.EbayErrorException;
import com.ebay.shopping.categories.clients.CategoryClient;
import com.ebay.shopping.categories.models.Category;
import com.ebay.shopping.categories.models.GetCategoryInfoResponse;

public class CategoryClientImpl implements CategoryClient {

	private static final Client CLIENT = ClientBuilder.newClient().property(ClientProperties.CONNECT_TIMEOUT, 60000)
			.property(ClientProperties.READ_TIMEOUT, 600000);
	private static final URI SANDBOX_URI = URI.create("http://open.api.sandbox.ebay.com/Shopping");
	private static final URI PRODUCTION_URI = URI.create("http://open.api.ebay.com/Shopping");
	private static final String CALL_NAME_QUERY_PARAMETER = "callname";
	private static final String APP_ID_QUERY_PARAMETER = "appid";
	private static final String VERSION_QUERY_PARAMETER = "version";
	private static final String CATEGORY_ID_QUERY_PARAMETER = "categoryid";
	private static final String SITE_ID_QUERY_PARAMETER = "siteid";
	private static final String GET_CATEGORY_INFO = "GetCategoryInfo";
	private static final String SHOPPING_API_VERSION = "981";
	private static final String ACK_SUCCESS = "success";

	private final String clientId;
	private final URI uri;

	public CategoryClientImpl(final String clientId, final boolean sandbox) {
		this.clientId = clientId;
		this.uri = sandbox ? SANDBOX_URI : PRODUCTION_URI;
	}

	public CategoryClientImpl(final String clientId, final URI uri) {
		this.clientId = clientId;
		this.uri = uri;
	}

	@Override
	public Category get(final String categoryId) {
		final Response response = CLIENT.target(uri).queryParam(CALL_NAME_QUERY_PARAMETER, GET_CATEGORY_INFO)
				.queryParam(APP_ID_QUERY_PARAMETER, clientId).queryParam(VERSION_QUERY_PARAMETER, SHOPPING_API_VERSION)
				.queryParam(SITE_ID_QUERY_PARAMETER, 0).queryParam(CATEGORY_ID_QUERY_PARAMETER, categoryId).request()
				.get();
		final GetCategoryInfoResponse getCategoryInfoResponse = response.readEntity(GetCategoryInfoResponse.class);
		if (ACK_SUCCESS.equalsIgnoreCase(getCategoryInfoResponse.getAck())) {
			final List<Category> categories = getCategoryInfoResponse.getCategories();
			return categories.isEmpty() ? null : categories.stream().findFirst().get();
		}
		throw new EbayErrorException(response);
	}

}
