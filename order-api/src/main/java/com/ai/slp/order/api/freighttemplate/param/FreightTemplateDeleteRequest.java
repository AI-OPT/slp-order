package com.ai.slp.order.api.freighttemplate.param;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 运费模版删除请求参数
 * @date 2016年8月5日 
 * @author caofz
 */
public class FreightTemplateDeleteRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message="运费模版id不能为空")
	private String templateId;

	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
}
