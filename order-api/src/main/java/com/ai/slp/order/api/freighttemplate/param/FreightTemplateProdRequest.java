package com.ai.slp.order.api.freighttemplate.param;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class FreightTemplateProdRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message="对应区域id不能为空")
	private String regionId;

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
}
