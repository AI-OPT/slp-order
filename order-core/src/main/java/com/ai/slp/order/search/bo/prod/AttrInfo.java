package com.ai.slp.order.search.bo.prod;

import com.google.gson.annotations.Expose;

/**
 * Created by xin on 16-5-13.
 */
public class AttrInfo {
	/**
	 * 属性值
	 */
	@Expose
	private String attrvalue;
	/**
	 * 属性标识
	 */
	@Expose
	private String attrid;
	/**
	 * 属性值ID
	 */
	@Expose
	private String attrvaluedefid;
	/**
	 * 属性名称
	 */
	@Expose
	private String attrname;

	/**
	 * 属性類型
	 */
	@Expose
	private String attrtype;

	public String getAttrname() {
		return attrname;
	}

	public String getAttrtype() {
		return attrtype;
	}

	public void setAttrtype(String attrtype) {
		this.attrtype = attrtype;
	}

	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}

	public AttrInfo() {
		super();
	}

	public AttrInfo(String attrID) {
		this.attrid = attrID;
	}

	public void setAttrvalue(String attrvalue) {
		this.attrvalue = attrvalue;
	}

	public String getAttrid() {
		return attrid;
	}

	public void setAttrid(String attrid) {
		this.attrid = attrid;
	}

	public String getAttrvaluedefid() {
		return attrvaluedefid;
	}

	public void setAttrvaluedefid(String attrvaluedefid) {
		this.attrvaluedefid = attrvaluedefid;
	}

	public String getAttrvalue() {
		return attrvalue;
	}

}
