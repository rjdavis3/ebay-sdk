package com.ebay.sell.inventory.inventoryitems.models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@XmlRootElement
public class Product {

	private String brand;
	private String description;
	private List<String> ean = new LinkedList<>();
	private List<String> imageUrls = new LinkedList<>();
	private List<String> isbn = new LinkedList<>();
	private String mpn;
	private String subtitle;
	private String title;
	private List<String> upc = new LinkedList<>();
	private Map<String, List<String>> aspects = new HashMap<>();

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getEan() {
		return ean;
	}

	public void setEan(List<String> ean) {
		this.ean = ean;
	}

	public List<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

	public List<String> getIsbn() {
		return isbn;
	}

	public void setIsbn(List<String> isbn) {
		this.isbn = isbn;
	}

	public String getMpn() {
		return mpn;
	}

	public void setMpn(String mpn) {
		this.mpn = mpn;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getUpc() {
		return upc;
	}

	public void setUpc(List<String> upc) {
		this.upc = upc;
	}

	public Map<String, List<String>> getAspects() {
		return aspects;
	}

	public void setAspects(Map<String, List<String>> aspects) {
		this.aspects = aspects;
	}

	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Product)) {
			return false;
		}
		final Product product = (Product) object;
		return new EqualsBuilder().append(getBrand(), product.getBrand())
				.append(getDescription(), product.getDescription()).append(getEan(), product.getEan())
				.append(getImageUrls(), product.getImageUrls()).append(getIsbn(), product.getIsbn())
				.append(getMpn(), product.getMpn()).append(getSubtitle(), product.getSubtitle())
				.append(getTitle(), product.getTitle()).append(getUpc(), product.getUpc())
				.append(getAspects(), product.getAspects()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getBrand()).append(getDescription()).append(getEan()).append(getImageUrls())
				.append(getIsbn()).append(getMpn()).append(getSubtitle()).append(getTitle()).append(getUpc())
				.append(getAspects()).toHashCode();
	}

}
