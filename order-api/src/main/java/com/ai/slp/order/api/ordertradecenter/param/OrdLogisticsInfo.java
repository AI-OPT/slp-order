package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true) 
public class OrdLogisticsInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 配送方式
     */
    private String logisticsType;
    
    /**
     * 到件方公司
     */
    private String contactCompany;
    
    /**
     * 收件人姓名
     */
    private String contactName;
    
    /**
     * 收件人邮箱
     */
    private String contactEmail;
    
    /**
     * 收件人电话
     */
    private String contactTel;
    
    /**
     * 收件人省份
     */
    private String provinceCode;
    
    /**
     * 收件人地市
     */
    private String cityCode;
    
    /**
     * 收件人区县
     */
    private String countyCode;
    
    /**
     * 收件人邮编
     */
    private String postCode;
    
    /**
     * 收件人末级区域
     */
    private String areaCode;
    
    /**
     * 详细地址(自提地址)
     */
    private String address;
    
    /**
     * 物流公司ID
     */
    private String expressId;
    
    /**
     * 快递自提点编码
     */
    private String expressSelfId;
    
    /**
     * 配送时间段
     */
    private String logisticsTime;
    
    /**
     * 附加信息
     */
    private String remark;

	public String getLogisticsType() {
		return logisticsType;
	}

	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}

	public String getContactCompany() {
		return contactCompany;
	}

	public void setContactCompany(String contactCompany) {
		this.contactCompany = contactCompany;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getExpressId() {
		return expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

	public String getExpressSelfId() {
		return expressSelfId;
	}

	public void setExpressSelfId(String expressSelfId) {
		this.expressSelfId = expressSelfId;
	}

	public String getLogisticsTime() {
		return logisticsTime;
	}

	public void setLogisticsTime(String logisticsTime) {
		this.logisticsTime = logisticsTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
