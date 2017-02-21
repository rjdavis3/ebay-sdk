package com.ebay.sell.account.policies.models;

public class PolicyCategoryType {

	public enum Name {
		ALL_EXCLUDING_MOTORS_VEHICLES, MOTORS_VEHICLES
	}

	private String name;
	private boolean isDefault;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

}
