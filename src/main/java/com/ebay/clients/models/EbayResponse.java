package com.ebay.clients.models;

import java.util.LinkedList;
import java.util.List;

public abstract class EbayResponse {

	private List<EbayError> errors = new LinkedList<>();

	public List<EbayError> getErrors() {
		return errors;
	}

	public void setErrors(List<EbayError> errors) {
		this.errors = errors;
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}
}
