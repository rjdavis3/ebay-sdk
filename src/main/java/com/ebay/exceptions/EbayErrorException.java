package com.ebay.exceptions;

import javax.ws.rs.core.Response;

public class EbayErrorException extends RuntimeException {

	static final String MESSAGE = "Received unexpected Response Status Code of %s and Body of:\n%s";

	private static final long serialVersionUID = 4291284903448380314L;

	public EbayErrorException(final Response response) {
		super(buildMessage(response));
	}

	private static String buildMessage(final Response response) {
		response.bufferEntity();
		return String.format(MESSAGE, response.getStatus(),
				response.readEntity(String.class));
	}
}
