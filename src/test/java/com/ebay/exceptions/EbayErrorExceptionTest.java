package com.ebay.exceptions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

public class EbayErrorExceptionTest {

	private static final int SOME_NOT_FOUND_STATUS_CODE = Status.NOT_FOUND
			.getStatusCode();
	private static final String SOME_NOT_FOUND_INVENTORY_ITEM_EBAY_ERROR = "{\r\n  \"errors\": [\r\n    {\r\n      \"errorId\": 25710,\r\n      \"domain\": \"API_INVENTORY\",\r\n      \"subdomain\": \"Selling\",\r\n      \"category\": \"REQUEST\",\r\n      \"message\": \"We didn't find the entity you are requesting. Please verify the request\"\r\n    }\r\n  ]\r\n}";

	@Test
	public void givenSomeResponseWithNotFoundStatusCodeAnd25710ErrorIdWhenCreatingExceptionThenCreateExceptionWithCorrectMessage()
			throws Exception {
		final Response response = mock(Response.class);
		when(response.getStatus()).thenReturn(SOME_NOT_FOUND_STATUS_CODE);
		when(response.readEntity(String.class)).thenReturn(
				SOME_NOT_FOUND_INVENTORY_ITEM_EBAY_ERROR);

		final EbayErrorException actualEbayErrorException = new EbayErrorException(
				response);

		assertEquals(String.format(EbayErrorException.MESSAGE,
				SOME_NOT_FOUND_STATUS_CODE,
				SOME_NOT_FOUND_INVENTORY_ITEM_EBAY_ERROR),
				actualEbayErrorException.getMessage());
	}

}
