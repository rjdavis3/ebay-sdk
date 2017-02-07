package com.ebay.shopping.categories.clients.impl;

import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;

import com.ebay.exceptions.EbayErrorException;
import com.ebay.shopping.categories.clients.CategoryClient;
import com.ebay.shopping.categories.models.AckCodeType;
import com.ebay.shopping.categories.models.CategoryType;
import com.ebay.shopping.categories.models.GetCategoryInfoResponseType;

/**
 * Uses eBay Shopping API version 981 with generated classes using XSD at below
 * link.
 * 
 * @see http://developer.ebay.com/webservices/981/ShoppingService.xsd
 * 
 * @author rjdavis
 *
 */
public class CategoryClientImpl implements CategoryClient {

	private static final Client CLIENT = ClientBuilder.newClient().property(ClientProperties.CONNECT_TIMEOUT, 60000)
			.property(ClientProperties.READ_TIMEOUT, 600000);
	private static final String CALL_NAME_QUERY_PARAMETER = "callname";
	private static final String APP_ID_QUERY_PARAMETER = "appid";
	private static final String VERSION_QUERY_PARAMETER = "version";
	private static final String CATEGORY_ID_QUERY_PARAMETER = "categoryid";
	private static final String SITE_ID_QUERY_PARAMETER = "siteid";
	private static final String INCLUDE_SELECTOR_QUERY_PARAMETER = "includeselector";
	private static final String GET_CATEGORY_INFO = "GetCategoryInfo";
	private static final String SHOPPING_API_VERSION = "981";
	private static final String CHILD_CATEGORIES_SELECTOR = "childcategories";

	private final String clientId;
	private final URI uri;

	public CategoryClientImpl(final String clientId, final URI uri) {
		this.clientId = clientId;
		this.uri = uri;
	}

	@Override
	public CategoryType get(final String categoryId) {
		final Response response = CLIENT.target(uri).queryParam(CALL_NAME_QUERY_PARAMETER, GET_CATEGORY_INFO)
				.queryParam(APP_ID_QUERY_PARAMETER, clientId).queryParam(VERSION_QUERY_PARAMETER, SHOPPING_API_VERSION)
				.queryParam(SITE_ID_QUERY_PARAMETER, 0).queryParam(CATEGORY_ID_QUERY_PARAMETER, categoryId).request()
				.get();
		final GetCategoryInfoResponseType getCategoryInfoResponse = response
				.readEntity(GetCategoryInfoResponseType.class);
		final AckCodeType ackCodeType = getCategoryInfoResponse.getAck();
		if (AckCodeType.SUCCESS == ackCodeType) {
			final List<CategoryType> categories = getCategoryInfoResponse.getCategoryArray().getCategory();
			return categories.isEmpty() ? null : categories.stream().findFirst().get();
		}
		throw new EbayErrorException(response);
	}

	@Override
	public List<CategoryType> getChildren(final String categoryId) {
		final Response response = CLIENT.target(uri).queryParam(CALL_NAME_QUERY_PARAMETER, GET_CATEGORY_INFO)
				.queryParam(INCLUDE_SELECTOR_QUERY_PARAMETER, CHILD_CATEGORIES_SELECTOR)
				.queryParam(APP_ID_QUERY_PARAMETER, clientId).queryParam(VERSION_QUERY_PARAMETER, SHOPPING_API_VERSION)
				.queryParam(SITE_ID_QUERY_PARAMETER, 0).queryParam(CATEGORY_ID_QUERY_PARAMETER, categoryId).request()
				.get();
		final GetCategoryInfoResponseType getCategoryInfoResponse = response
				.readEntity(GetCategoryInfoResponseType.class);
		final AckCodeType ackCodeType = getCategoryInfoResponse.getAck();
		if (AckCodeType.SUCCESS == ackCodeType) {
			return getCategoryInfoResponse.getCategoryArray().getCategory();
		}
		throw new EbayErrorException(response);
	}

}
