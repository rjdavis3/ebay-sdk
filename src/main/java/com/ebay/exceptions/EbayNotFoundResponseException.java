package com.ebay.exceptions;

import javax.ws.rs.core.Response;

public class EbayNotFoundResponseException extends EbayErrorResponseException {

	private static final long serialVersionUID = 3691861894843730351L;

	public EbayNotFoundResponseException(final Response response) {
		super(response);
	}

}
