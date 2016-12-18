package com.ebay.sell.inventoryitemgroups.clients.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ebay.exceptions.EbayErrorException;
import com.ebay.sell.inventoryitemgroups.clients.InventoryItemGroupClient;
import com.ebay.sell.inventoryitemgroups.models.InventoryItemGroup;

public class InventoryItemGroupClientImplTest {

	private static final String SOME_OAUTH_USER_TOKEN = "v1-ebay-oauth-token";
	private static final String SOME_INVENTORY_ITEM_GROUP_KEY = "1444";
	private static final String SOME_TITLE = "Clif Bar Energy Bar";
	private static final String SOME_EBAY_ERROR_MESSAGE = "{\r\n  \"errors\": [\r\n    {\r\n      \"errorId\": 25710,\r\n      \"domain\": \"API_INVENTORY\",\r\n      \"subdomain\": \"Selling\",\r\n      \"category\": \"REQUEST\",\r\n      \"message\": \"We didn't find the entity you are requesting. Please verify the request\"\r\n    }\r\n  ]\r\n}";

	private InventoryItemGroupClient inventoryItemGroupClient;

	@Mock
	private Client client;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		inventoryItemGroupClient = new InventoryItemGroupClientImpl(client,
				SOME_OAUTH_USER_TOKEN);
	}

	@Test
	public void givenSomeValidInventoryItemGroupKeyWhenRetrievingInventoryItemGroupThenReturnInventoryItemGroup() {
		final Status expectedStatus = Status.OK;
		final InventoryItemGroup expectedInventoryItemGroup = new InventoryItemGroup();
		expectedInventoryItemGroup.setTitle(SOME_TITLE);

		final WebTarget webTarget = mock(WebTarget.class);
		when(
				client.target(InventoryItemGroupClientImpl.INVENTORY_ITEM_GROUP_RESOURCE))
				.thenReturn(webTarget);
		when(webTarget.path(SOME_INVENTORY_ITEM_GROUP_KEY)).thenReturn(
				webTarget);
		final Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
		when(webTarget.request()).thenReturn(invocationBuilder);
		when(
				invocationBuilder.header(
						eq(InventoryItemGroupClientImpl.AUTHORIZATION_HEADER),
						anyString())).thenReturn(invocationBuilder);
		final Response response = mock(Response.class);
		final int statusCode = expectedStatus.getStatusCode();
		when(response.getStatus()).thenReturn(statusCode);
		when(response.readEntity(InventoryItemGroup.class)).thenReturn(
				expectedInventoryItemGroup);
		when(invocationBuilder.get()).thenReturn(response);

		final InventoryItemGroup actualInventoryItemGroup = inventoryItemGroupClient
				.getInventoryItemGroup(SOME_INVENTORY_ITEM_GROUP_KEY);

		assertEquals(SOME_INVENTORY_ITEM_GROUP_KEY,
				actualInventoryItemGroup.getInventoryItemGroupKey());
		assertEquals(SOME_TITLE, actualInventoryItemGroup.getTitle());
	}

	@Test(expected = EbayErrorException.class)
	public void givenSomeInvalidInventoryItemGroupKeyWhenRetrievingInventoryItemGroupThenThrowNewEbayErrorExceptionWith404StatusCodeAndSomeEbayErrorMessage() {
		final Status expectedStatus = Status.NOT_FOUND;

		final WebTarget webTarget = mock(WebTarget.class);
		when(
				client.target(InventoryItemGroupClientImpl.INVENTORY_ITEM_GROUP_RESOURCE))
				.thenReturn(webTarget);
		when(webTarget.path(SOME_INVENTORY_ITEM_GROUP_KEY)).thenReturn(
				webTarget);
		final Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
		when(webTarget.request()).thenReturn(invocationBuilder);
		when(
				invocationBuilder.header(
						eq(InventoryItemGroupClientImpl.AUTHORIZATION_HEADER),
						anyString())).thenReturn(invocationBuilder);
		final Response response = mock(Response.class);
		final int statusCode = expectedStatus.getStatusCode();
		when(response.getStatus()).thenReturn(statusCode);
		when(response.readEntity(String.class)).thenReturn(
				SOME_EBAY_ERROR_MESSAGE);
		when(invocationBuilder.get()).thenReturn(response);

		inventoryItemGroupClient
				.getInventoryItemGroup(SOME_INVENTORY_ITEM_GROUP_KEY);
	}

}
