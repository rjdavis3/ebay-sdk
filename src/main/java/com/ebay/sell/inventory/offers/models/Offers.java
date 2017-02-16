package com.ebay.sell.inventory.offers.models;

import java.util.LinkedList;
import java.util.List;

import com.ebay.clients.models.EbayResponse;

public class Offers extends EbayResponse {

	private int total;
	private int size;
	private String href;
	private String next;
	private int limit;
	private List<Offer> offers = new LinkedList<>();

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

}
