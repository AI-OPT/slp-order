package com.ai.slp.order.util;

public enum KeyType {

	PRIVATE_KEY("私钥", "private"), PUBLIC_KEY("公钥", "public");

	private String name;
	private String value;

	private KeyType(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
