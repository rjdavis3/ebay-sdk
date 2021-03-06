package com.ebay.shopping.categories.clients.impl;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;

import com.ebay.exceptions.EbayErrorResponseException;
import com.ebay.models.Marketplace;
import com.ebay.shopping.categories.clients.CategoryClient;
import com.ebay.shopping.categories.models.AckCodeType;
import com.ebay.shopping.categories.models.CategoryType;
import com.ebay.shopping.categories.models.ErrorType;
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

	static final String CALL_NAME_QUERY_PARAMETER = "callname";
	static final String APP_ID_QUERY_PARAMETER = "appid";
	static final String VERSION_QUERY_PARAMETER = "version";
	static final String CATEGORY_ID_QUERY_PARAMETER = "categoryid";
	static final String SITE_ID_QUERY_PARAMETER = "siteid";
	static final String INCLUDE_SELECTOR_QUERY_PARAMETER = "includeselector";
	static final String GET_CATEGORY_INFO = "GetCategoryInfo";
	static final String SHOPPING_API_VERSION = "963";
	static final String CHILD_CATEGORIES_SELECTOR = "childcategories";

	private static final String INVALID_CATEGORY_ID_ERROR_CODE = "10.36";
	private static final String CATEGORY_INVALID_ON_CURRENT_SITE_ERROR_CODE = "10.54";

	private static final Client CLIENT = ClientBuilder.newClient().property(ClientProperties.CONNECT_TIMEOUT, 60000)
			.property(ClientProperties.READ_TIMEOUT, 600000);

	private final String clientId;
	private final URI uri;

	public CategoryClientImpl(final String clientId, final URI uri) {
		this.clientId = clientId;
		this.uri = uri;
	}

	@Override
	public CategoryType getCategory(final Marketplace marketplace, final String categoryId) {
		final Response response = CLIENT.target(uri).queryParam(CALL_NAME_QUERY_PARAMETER, GET_CATEGORY_INFO)
				.queryParam(APP_ID_QUERY_PARAMETER, clientId).queryParam(VERSION_QUERY_PARAMETER, SHOPPING_API_VERSION)
				.queryParam(SITE_ID_QUERY_PARAMETER, marketplace.getShoppingSiteId())
				.queryParam(CATEGORY_ID_QUERY_PARAMETER, categoryId).request().get();
		response.bufferEntity();
		final GetCategoryInfoResponseType getCategoryInfoResponseType = response
				.readEntity(GetCategoryInfoResponseType.class);
		if (isSuccess(getCategoryInfoResponseType)) {
			final List<CategoryType> categories = getCategoryInfoResponseType.getCategoryArray().getCategory();
			return categories.stream().findFirst().get();
		} else if (isInvalidCategory(getCategoryInfoResponseType)) {
			return null;
		}
		throw new EbayErrorResponseException(response);
	}

	@Override
	public List<CategoryType> getCategoryWithChildren(final Marketplace marketplace, final String categoryId) {
		final Response response = CLIENT.target(uri).queryParam(CALL_NAME_QUERY_PARAMETER, GET_CATEGORY_INFO)
				.queryParam(INCLUDE_SELECTOR_QUERY_PARAMETER, CHILD_CATEGORIES_SELECTOR)
				.queryParam(APP_ID_QUERY_PARAMETER, clientId).queryParam(VERSION_QUERY_PARAMETER, SHOPPING_API_VERSION)
				.queryParam(SITE_ID_QUERY_PARAMETER, marketplace.getShoppingSiteId())
				.queryParam(CATEGORY_ID_QUERY_PARAMETER, categoryId).request().get();
		response.bufferEntity();
		final GetCategoryInfoResponseType getCategoryInfoResponseType = response
				.readEntity(GetCategoryInfoResponseType.class);
		if (isSuccess(getCategoryInfoResponseType)) {
			return getCategoryInfoResponseType.getCategoryArray().getCategory();
		} else if (isInvalidCategory(getCategoryInfoResponseType)) {
			return Collections.emptyList();
		}
		throw new EbayErrorResponseException(response);
	}

	private boolean isSuccess(final GetCategoryInfoResponseType getCategoryInfoResponseType) {
		return (AckCodeType.SUCCESS == getCategoryInfoResponseType.getAck())
				|| (AckCodeType.WARNING == getCategoryInfoResponseType.getAck());
	}

	private boolean isInvalidCategory(final GetCategoryInfoResponseType getCategoryInfoResponse) {
		return AckCodeType.FAILURE == getCategoryInfoResponse.getAck() && getCategoryInfoResponse.getErrors().stream()
				.map(ErrorType::getErrorCode).anyMatch(errorCode -> INVALID_CATEGORY_ID_ERROR_CODE.equals(errorCode)
						|| CATEGORY_INVALID_ON_CURRENT_SITE_ERROR_CODE.equals(errorCode));
	}

}
