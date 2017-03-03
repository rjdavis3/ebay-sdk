package com.ebay.sell.inventory.inventoryitemgroups.models;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Specification)) {
			return false;
		}
		final Specification specification = (Specification) object;
		return new EqualsBuilder().append(getName(), specification.getName())
				.append(getValues(), specification.getValues()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getName()).append(getValues()).toHashCode();
	}

}
