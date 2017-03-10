package com.ai.slp.order.search.bo;

import java.util.List;

public class OrdProdExtend {
	private long  orderid;
	private long  parentorderid;
	private String  state;
	private String  statename;
	private String  busicode;
	private long  prodsize;
	private String routeid; 
	private List<ProdInfo> prodinfos;
	public long getOrderid() {
		return orderid;
	}
	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}
	public long getParentorderid() {
		return parentorderid;
	}
	public void setParentorderid(long parentorderid) {
		this.parentorderid = parentorderid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStatename() {
		return statename;
	}
	public void setStatename(String statename) {
		this.statename = statename;
	}
	public String getBusicode() {
		return busicode;
	}
	public void setBusicode(String busicode) {
		this.busicode = busicode;
	}
	public long getProdsize() {
		return prodsize;
	}
	public void setProdsize(long prodsize) {
		this.prodsize = prodsize;
	}
	public List<ProdInfo> getProdinfos() {
		return prodinfos;
	}
	public void setProdinfos(List<ProdInfo> prodinfos) {
		this.prodinfos = prodinfos;
	}
	public String getRouteid() {
		return routeid;
	}
	public void setRouteid(String routeid) {
		this.routeid = routeid;
	}
}
