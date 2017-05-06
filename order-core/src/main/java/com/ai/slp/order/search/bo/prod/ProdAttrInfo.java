package com.ai.slp.order.search.bo.prod;



import com.google.gson.annotations.Expose;

/**
 * 商品属性 Date: 2017年3月26日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public class ProdAttrInfo {

	/**
	 * 商品属性id
	 */
	private long prodattrid;

	/**
	 * prodid
	 */
	private String prodid;

	/**
	 * attrid
	 */
	private String attrid;

	/**
	 * 属性标识
	 */
	private Long attrvaluedefid;

	/**
	 * 属性名
	 */
	private String attrvaluename;

	/**
	 * 属性名2
	 */
	private String attrvaluename2;

	/**
	 * 属性类型
	 */
	@Expose
	private String attrtype;

	public long getProdattrid() {
		return prodattrid;
	}

	public String getAttrtype() {
		return attrtype;
	}

	public void setAttrtype(String attrtype) {
		this.attrtype = attrtype;
	}

	public void setProdattrid(long prodattrid) {
		this.prodattrid = prodattrid;
	}

	public String getProdid() {
		return prodid;
	}

	public void setProdid(String prodid) {
		this.prodid = prodid;
	}

	public String getAttrid() {
		return attrid;
	}

	public void setAttrid(String attrid) {
		this.attrid = attrid;
	}

	public Long getAttrvaluedefid() {
		return attrvaluedefid;
	}

	public void setAttrvaluedefid(Long attrvaluedefid) {
		this.attrvaluedefid = attrvaluedefid;
	}

	public String getAttrvaluename() {
		return attrvaluename;
	}

	public void setAttrvaluename(String attrvaluename) {
		this.attrvaluename = attrvaluename;
	}

	public String getAttrvaluename2() {
		return attrvaluename2;
	}

	public void setAttrvaluename2(String attrvaluename2) {
		this.attrvaluename2 = attrvaluename2;
	}

}
