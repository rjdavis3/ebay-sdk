package com.ebay.clients.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ebay.exceptions.EbayErrorException;

public class EbayClientImpl {

	protected <T> T handleResponse(final Response response, final Class<T> entityType, final Status... expectedStatus) {
		final List<Integer> expectedStatusCodes = Arrays.asList(expectedStatus).stream().map(Status::getStatusCode)
				.collect(Collectors.toList());
		if (expectedStatusCodes.contains(response.getStatus())) {
			return response.readEntity(entityType);
		}
		throw new EbayErrorException(response);
	}

	protected void handleResponse(final Response response, final Status... expectedStatus) {
		final List<Integer> expectedStatusCodes = Arrays.asList(expectedStatus).stream().map(Status::getStatusCode)
				.collect(Collectors.toList());
		if (!expectedStatusCodes.contains(response.getStatus())) {
			throw new EbayErrorException(response);
		}
	}

}
