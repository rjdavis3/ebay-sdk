package com.ebay.sell.inventory.inventoryitemgroups.models;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof VariesBy)) {
			return false;
		}
		final VariesBy variesBy = (VariesBy) object;
		return new EqualsBuilder().append(getSpecifications(), variesBy.getSpecifications())
				.append(getAspectsImageVariesBy(), variesBy.getAspectsImageVariesBy()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getSpecifications()).append(getAspectsImageVariesBy()).toHashCode();
	}

}
