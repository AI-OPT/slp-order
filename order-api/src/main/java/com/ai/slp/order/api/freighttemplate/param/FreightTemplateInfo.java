package com.ai.slp.order.api.freighttemplate.param;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class FreightTemplateInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 模版名称
	 */
	@NotBlank(message = "模版名称不能为空")
	private String templateName;
	
	/**
	 * 模版类型
	 */
	private String templateType;
	
	/**
	 * 销售商id
	 */
	@NotBlank(message="销售品id不能为空")
	private String supplierId;
	
	/**
	 * 物流公司id
	 */
	private String logisticsCompanyId;
	
	/**
	 * 是否包邮
	 */
	@NotBlank(message = "是否包邮不能为空")
	private String isFree;
	
	/**
	 * 是否有条件包邮
	 */
	private String isTermFree;
	
	/**
	 * 计价方式
	 */
	private String valuationType;

	public String getTemplateName() {
		return templateName;
	}
	
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getLogisticsCompanyId() {
		return logisticsCompanyId;
	}

	public void setLogisticsCompanyId(String logisticsCompanyId) {
		this.logisticsCompanyId = logisticsCompanyId;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	public String getIsTermFree() {
		return isTermFree;
	}

	public void setIsTermFree(String isTermFree) {
		this.isTermFree = isTermFree;
	}

	public String getValuationType() {
		return valuationType;
	}

	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}
}
