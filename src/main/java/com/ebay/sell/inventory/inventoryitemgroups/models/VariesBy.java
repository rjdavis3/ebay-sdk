package com.ebay.sell.inventory.inventoryitemgroups.models;

import java.util.LinkedList;
import java.util.List;

public class VariesBy {

	private List<Specification> specifications = new LinkedList<>();
	private List<String> aspectsImageVariesBy = new LinkedList<>();

	public List<Specification> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(List<Specification> specifications) {
		this.specifications = specifications;
	}

	public List<String> getAspectsImageVariesBy() {
		return aspectsImageVariesBy;
	}

	public void setAspectsImageVariesBy(List<String> aspectsImageVariesBy) {
		this.aspectsImageVariesBy = aspectsImageVariesBy;
	}

}
