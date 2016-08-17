package com.ai.slp.order.api.demo.param;

import java.io.Serializable;

public class HelloParam implements Serializable {
	private String name;

	public HelloParam() {
		super();
	}

	public HelloParam(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
