package com.ebay;

import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;
import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;

import com.ebay.clients.models.RequestRetryConfiguration;
import com.ebay.identity.ouath2.token.clients.impl.TokenClientImpl;
import com.ebay.sell.inventory.inventoryitems.clients.impl.InventoryItemClientImpl;
import com.ebay.sell.inventory.inventoryitems.models.InventoryItem;
import com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import com.github.restdriver.clientdriver.ClientDriverRule;

public class EbaySdkTest {

	private static final char EQUAL = '=';
	private static final char AMPERSAND = '&';
	private static final char FORWARD_SLASH = '/';
	private static final String SOME_CLIENT_ID = "some-client-id";
	private static final String SOME_CLIENT_SECRET = "some-client-secret";
	private static final String SOME_REFRESH_TOKEN = "some-refresh-token";
	private static final String SOME_RU_NAME = "some-ru-name";
	private static final String SOME_AUTHORIZATION_CODE = "some-authorization-code";
	private static final String SOME_ACCESS_TOKEN = "some-access-token";
	private static final String SOME_SKU = "540008";
	private static final String SOME_CONDITION = "NEW";

	@Rule
	public ClientDriverRule driver = new ClientDriverRule();

	@Test
	public void givenRefreshTokenAndSkuWhenRetrievingInventoryItemThenReturnInventoryItem() {
		final String expectedRequestBody = new StringBuilder().append(TokenClientImpl.GRANT_TYPE).append(EQUAL)
				.append(TokenClientImpl.REFRESH_TOKEN).append(AMPERSAND).append(TokenClientImpl.REFRESH_TOKEN)
				.append(EQUAL).append(SOME_REFRESH_TOKEN).toString();
		mockTokenRequest(expectedRequestBody, Status.OK);

		final URI baseUri = URI.create(driver.getBaseUrl());

		final RequestRetryConfiguration requestRetryConfiguration = RequestRetryConfiguration.newBuilder()
				.withMininumWait(100, TimeUnit.MILLISECONDS).withTimeout(300, TimeUnit.MILLISECONDS).build();
		final EbaySdk ebaySdk = EbaySdk.newBuilder().withClientId(SOME_CLIENT_ID).withClientSecret(SOME_CLIENT_SECRET)
				.withRefreshToken(SOME_REFRESH_TOKEN).withRequestRetryConfiguration(requestRetryConfiguration)
				.withBaseUri(baseUri).withShoppingUri(baseUri).build();

		assertEquals(SOME_REFRESH_TOKEN, ebaySdk.getRefreshToken());

		mockGetInventoryItem();

		final InventoryItem actualInventoryItem = ebaySdk.getInventoryItem(SOME_SKU);

		assertEquals(SOME_SKU, actualInventoryItem.getSku());
		assertEquals(SOME_CONDITION, actualInventoryItem.getCondition());
	}

	@Test
	public void givenRuNameAndAuthorizationCodeAndSkuWhenRetrievingInventoryItemThenReturnInventoryItem() {

		final String expectedRequestBody = new StringBuilder().append(TokenClientImpl.GRANT_TYPE).append(EQUAL)
				.append(TokenClientImpl.AUTHORIZATION_CODE).append(AMPERSAND).append(TokenClientImpl.CODE).append(EQUAL)
				.append(SOME_AUTHORIZATION_CODE).append(AMPERSAND).append(TokenClientImpl.REDIRECT_URI).append(EQUAL)
				.append(SOME_RU_NAME).toString();
		mockTokenRequest(expectedRequestBody, Status.OK);

		final String expectedRefreshTokenRequestBody = new StringBuilder().append(TokenClientImpl.GRANT_TYPE)
				.append(EQUAL).append(TokenClientImpl.REFRESH_TOKEN).append(AMPERSAND)
				.append(TokenClientImpl.REFRESH_TOKEN).append(EQUAL).append(SOME_REFRESH_TOKEN).toString();
		mockTokenRequest(expectedRefreshTokenRequestBody, Status.OK);

		final URI baseUri = URI.create(driver.getBaseUrl());

		final RequestRetryConfiguration requestRetryConfiguration = RequestRetryConfiguration.newBuilder()
				.withMininumWait(100, TimeUnit.MILLISECONDS).withTimeout(300, TimeUnit.MILLISECONDS).build();
		final EbaySdk ebaySdk = EbaySdk.newBuilder().withClientId(SOME_CLIENT_ID).withClientSecret(SOME_CLIENT_SECRET)
				.withRuName(SOME_RU_NAME).withCode(SOME_AUTHORIZATION_CODE)
				.withRequestRetryConfiguration(requestRetryConfiguration).withBaseUri(baseUri).withShoppingUri(baseUri)
				.build();

		assertEquals(SOME_REFRESH_TOKEN, ebaySdk.getRefreshToken());

		mockGetInventoryItem();

		final InventoryItem actualInventoryItem = ebaySdk.getInventoryItem(SOME_SKU);

		assertEquals(SOME_SKU, actualInventoryItem.getSku());
		assertEquals(SOME_CONDITION, actualInventoryItem.getCondition());
	}

	private void mockTokenRequest(final String expectedRequestBody, final Status expectedResponseStatus) {
		final String expectedResponseBody = buildTokenResponseBody();
		driver.addExpectation(
				onRequestTo(TokenClientImpl.TOKEN_RESOURCE).withBasicAuth(SOME_CLIENT_ID, SOME_CLIENT_SECRET)
						.withMethod(Method.POST).withBody(expectedRequestBody, MediaType.APPLICATION_FORM_URLENCODED),
				giveResponse(expectedResponseBody, MediaType.APPLICATION_JSON)
						.withStatus(expectedResponseStatus.getStatusCode()));
	}

	private String buildTokenResponseBody() {
		final JSONObject jsonObject = new JSONObject();
		jsonObject.put("access_token", SOME_ACCESS_TOKEN);
		jsonObject.put("expires_in", 7200);
		jsonObject.put("refresh_token", SOME_REFRESH_TOKEN);
		jsonObject.put("refresh_token_expires_in", 7200);
		jsonObject.put("token_type", "User Token");
		return jsonObject.toString();
	}

	private void mockGetInventoryItem() {
		final InventoryItem expectedInventoryItem = new InventoryItem();
		expectedInventoryItem.setSku(SOME_SKU);
		expectedInventoryItem.setCondition(SOME_CONDITION);
		final String expectedResponseBody = new JSONObject(expectedInventoryItem).toString();
		driver.addExpectation(
				onRequestTo(new StringBuilder().append(InventoryItemClientImpl.INVENTORY_ITEM_RESOURCE)
						.append(FORWARD_SLASH).append(SOME_SKU).toString())
								.withHeader(InventoryItemClientImpl.AUTHORIZATION_HEADER,
										new StringBuilder().append(InventoryItemClientImpl.OAUTH_USER_TOKEN_PREFIX)
												.append(SOME_ACCESS_TOKEN).toString())
								.withMethod(Method.GET),
				giveResponse(expectedResponseBody, MediaType.APPLICATION_JSON).withStatus(Status.OK.getStatusCode()));
	}

}
