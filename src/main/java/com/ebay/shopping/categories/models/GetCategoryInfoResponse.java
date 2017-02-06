package com.ebay.shopping.categories.models;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GetCategoryInfoResponse {

	@XmlElement(name = "Timestamp")
	private String timestamp;
	@XmlElement(name = "Ack")
	private String ack;
	@XmlElement(name = "Build")
	private String build;
	@XmlElement(name = "Version")
	private String version;
	@XmlElement(name = "CategoryArray")
	private List<Category> categories = new LinkedList<>();
	@XmlElement(name = "CategoryCount")
	private int categoryCount;
	@XmlElement(name = "UpdateTime")
	private String updateTime;
	@XmlElement(name = "CategoryVersion")
	private String categoryVersion;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getAck() {
		return ack;
	}

	public void setAck(String ack) {
		this.ack = ack;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public int getCategoryCount() {
		return categoryCount;
	}

	public void setCategoryCount(int categoryCount) {
		this.categoryCount = categoryCount;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCategoryVersion() {
		return categoryVersion;
	}

	public void setCategoryVersion(String categoryVersion) {
		this.categoryVersion = categoryVersion;
	}

}
