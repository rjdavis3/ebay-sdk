package com.ebay.sell.inventory.inventoryitems.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Dimension {

	private static final int SCALE = 10;
	private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

	private BigDecimal height;
	private BigDecimal length;
	private BigDecimal width;
	private String unit;

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = (height == null) ? null : height.setScale(SCALE, ROUNDING_MODE).stripTrailingZeros();
	}

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = (length == null) ? null : length.setScale(SCALE, ROUNDING_MODE).stripTrailingZeros();
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = (width == null) ? null : width.setScale(SCALE, ROUNDING_MODE).stripTrailingZeros();
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Dimension)) {
			return false;
		}
		final Dimension dimension = (Dimension) object;
		return new EqualsBuilder().append(getHeight(), dimension.getHeight()).append(getLength(), dimension.getLength())
				.append(getWidth(), dimension.getWidth()).append(getUnit(), dimension.getUnit()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getHeight()).append(getLength()).append(getWidth()).append(getUnit())
				.toHashCode();
	}

}
