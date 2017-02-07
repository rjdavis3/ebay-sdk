package com.ebay.shopping.categories.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Category {

	@XmlElement(name = "CategoryId")
	private String categoryId;
	@XmlElement(name = "CategoryLevel")
	private int categoryLevel;
	@XmlElement(name = "CategoryName")
	private String categoryName;
	@XmlElement(name = "CategoryParentId")
	private String categoryParentId;
	@XmlElement(name = "CategoryNamePath")
	private String categoryNamePath;
	@XmlElement(name = "CategoryIdPath")
	private String categoryIdPath;
	@XmlElement(name = "LeafCategory")
	private boolean leafCategory;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public int getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(int categoryLevel) {
		this.categoryLevel = categoryLevel;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryParentId() {
		return categoryParentId;
	}

	public void setCategoryParentId(String categoryParentId) {
		this.categoryParentId = categoryParentId;
	}

	public String getCategoryNamePath() {
		return categoryNamePath;
	}

	public void setCategoryNamePath(String categoryNamePath) {
		this.categoryNamePath = categoryNamePath;
	}

	public String getCategoryIdPath() {
		return categoryIdPath;
	}

	public void setCategoryIdPath(String categoryIdPath) {
		this.categoryIdPath = categoryIdPath;
	}

	public boolean isLeafCategory() {
		return leafCategory;
	}

	public void setLeafCategory(boolean leafCategory) {
		this.leafCategory = leafCategory;
	}

}
