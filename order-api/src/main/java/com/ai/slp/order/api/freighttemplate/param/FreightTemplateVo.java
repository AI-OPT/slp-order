package com.ai.slp.order.api.freighttemplate.param;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 返回的运费模版信息
 * @date 2016年8月4日 
 * @author caofz
 */
public class FreightTemplateVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 运费模版名称
	 */
	private String templateName;
	
	/**
	 * 模版id
	 */
	private String templateId;
	
	/**
	 * 计价方式
	 */
	private String valuationType;
	
	/**
	 * 编辑时间
	 */
	private Timestamp time;
	
	/**
	 * 运费模版明细集合
	 */
	private List<FreightTemplateProdVo> templateProdList;

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getValuationType() {
		return valuationType;
	}

	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}
	
	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public List<FreightTemplateProdVo> getTemplateProdList() {
		return templateProdList;
	}

	public void setTemplateProdList(List<FreightTemplateProdVo> templateProdList) {
		this.templateProdList = templateProdList;
	}
}
