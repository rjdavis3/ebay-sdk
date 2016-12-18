package com.ebay.sell.inventoryitemgroups.models;

import java.util.LinkedList;
import java.util.List;

public class Specification {

	private String name;
	private List<String> values = new LinkedList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

}
