package com.ebay.exceptions;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.Response;

import com.ebay.clients.models.EbayError;
import com.ebay.clients.models.ErrorResponse;

public class EbayErrorResponseException extends EbayException {

	static final String MESSAGE = "Received unexpected Response Status Code of %d and Body of:\n%s";

	private static final long serialVersionUID = 4291284903448380314L;

	private final int statusCode;
	private final List<EbayError> errors;

	public EbayErrorResponseException(final Response response) {
		super(buildMessage(response));
		this.statusCode = response.getStatus();
		this.errors = buildErrors(response);

	}

	public int getStatusCode() {
		return statusCode;
	}

	public List<EbayError> getErrors() {
		return errors;
	}

	private static String buildMessage(final Response response) {
		response.bufferEntity();
		return String.format(MESSAGE, response.getStatus(), response.readEntity(String.class));
	}

	private List<EbayError> buildErrors(final Response response) {
		try {
			final ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
			return errorResponse.getErrors();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
}
