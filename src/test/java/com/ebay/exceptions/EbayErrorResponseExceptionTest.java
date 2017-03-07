package com.ebay.exceptions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Test;

import com.ebay.clients.models.EbayError;
import com.ebay.clients.models.EbayParameter;
import com.ebay.clients.models.ErrorResponse;

public class EbayErrorResponseExceptionTest {

	private static final int SOME_NOT_FOUND_STATUS_CODE = Status.NOT_FOUND.getStatusCode();

	@Test
	public void givenSomeResponseWithNotFoundStatusCodeAnd25710ErrorIdWhenCreatingExceptionThenCreateExceptionWithCorrectMessageAndStatusCodeAndEbayErrors()
			throws Exception {
		final ErrorResponse errorResponse = new ErrorResponse();
		final EbayError ebayError = new EbayError();
		ebayError.setErrorId(25710);
		ebayError.setDomain("API_INVENTORY");
		ebayError.setSubdomain("Selling");
		ebayError.setMessage("We didn't find the entity you are requesting. Please verify the request");
		final EbayParameter ebayParameter = new EbayParameter();
		ebayParameter.setName("sku");
		ebayParameter.setValue("440220");
		ebayError.setParameters(Arrays.asList(ebayParameter));
		errorResponse.setErrors(Arrays.asList(ebayError));

		final Response response = mock(Response.class);
		when(response.getStatus()).thenReturn(SOME_NOT_FOUND_STATUS_CODE);
		final String expectedErrorString = new JSONObject(errorResponse).toString();
		when(response.readEntity(String.class)).thenReturn(expectedErrorString);

		when(response.readEntity(ErrorResponse.class)).thenReturn(errorResponse);

		final EbayErrorResponseException actualEbayErrorResponseException = new EbayErrorResponseException(response);

		assertEquals(String.format(EbayErrorResponseException.MESSAGE, SOME_NOT_FOUND_STATUS_CODE, expectedErrorString),
				actualEbayErrorResponseException.getMessage());
		assertEquals(SOME_NOT_FOUND_STATUS_CODE, actualEbayErrorResponseException.getStatusCode());
		assertSame(errorResponse.getErrors(), actualEbayErrorResponseException.getErrors());
	}

}
