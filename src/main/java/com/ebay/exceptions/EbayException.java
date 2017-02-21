package com.ebay.exceptions;

public class EbayException extends RuntimeException {

	private static final long serialVersionUID = -7290571418598712878L;

	public EbayException(final String message, final Exception exception) {
		super(message, exception);
	}

}
