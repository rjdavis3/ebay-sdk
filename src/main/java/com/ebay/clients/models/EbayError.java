package com.ebay.clients.models;

import java.util.LinkedList;
import java.util.List;

public class EbayError {

	private int errorId;
	private String domain;
	private String subdomain;
	private String category;
	private String message;
	private List<EbayParameter> parameters = new LinkedList<>();

	public int getErrorId() {
		return errorId;
	}

	public void setErrorId(int errorId) {
		this.errorId = errorId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSubdomain() {
		return subdomain;
	}

	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<EbayParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<EbayParameter> parameters) {
		this.parameters = parameters;
	}

}
