package com.ai.slp.order.api.freighttemplate.param;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class FreightTemplateUpdateRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 模版id
	 */
	@NotBlank(message="模版id不能为空")
	private String templateId;
	
	/**
	 * 运费模版信息
	 */
	@NotNull(message="运费模版信息不能为空")
	private FreightTemplateInfo freightTemplateInfo;
	
	/**
	 * 运费模版明细信息
	 */
	private List<FreightTemplateProdInfo> freightTemplateProdInfos;
	
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public FreightTemplateInfo getFreightTemplateInfo() {
		return freightTemplateInfo;
	}

	public void setFreightTemplateInfo(FreightTemplateInfo freightTemplateInfo) {
		this.freightTemplateInfo = freightTemplateInfo;
	}

	public List<FreightTemplateProdInfo> getFreightTemplateProdInfos() {
		return freightTemplateProdInfos;
	}

	public void setFreightTemplateProdInfos(List<FreightTemplateProdInfo> freightTemplateProdInfos) {
		this.freightTemplateProdInfos = freightTemplateProdInfos;
	}
	
}
