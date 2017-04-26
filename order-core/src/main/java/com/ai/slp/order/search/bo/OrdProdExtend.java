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
	
	
	
	
	

	private long origorderid;
	private String busicodename;
	private long totalfee; //TODO
	private long discountfee;
	private long adjustfee;
	private long paidfee; //TODO
	private long payfee;
	private long freight;
	private long operdiscountfee;//TODO 没赋值
	private String operdiscountdesc;//TODO
	private String aftercontactTel;
	private String aftercontactinfo;
	
	
	
	
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
	
	public long getOrigorderid() {
		return origorderid;
	}
	public void setOrigorderid(long origorderid) {
		this.origorderid = origorderid;
	}
	
	public long getTotalfee() {
		return totalfee;
	}
	public void setTotalfee(long totalfee) {
		this.totalfee = totalfee;
	}
	public long getDiscountfee() {
		return discountfee;
	}
	public void setDiscountfee(long discountfee) {
		this.discountfee = discountfee;
	}
	public long getAdjustfee() {
		return adjustfee;
	}
	public void setAdjustfee(long adjustfee) {
		this.adjustfee = adjustfee;
	}
	public long getPaidfee() {
		return paidfee;
	}
	public void setPaidfee(long paidfee) {
		this.paidfee = paidfee;
	}
	public long getPayfee() {
		return payfee;
	}
	public void setPayfee(long payfee) {
		this.payfee = payfee;
	}
	public long getFreight() {
		return freight;
	}
	public void setFreight(long freight) {
		this.freight = freight;
	}
	
	public String getAftercontactTel() {
		return aftercontactTel;
	}
	public void setAftercontactTel(String aftercontactTel) {
		this.aftercontactTel = aftercontactTel;
	}
	public String getAftercontactinfo() {
		return aftercontactinfo;
	}
	public void setAftercontactinfo(String aftercontactinfo) {
		this.aftercontactinfo = aftercontactinfo;
	}
	public String getBusicodename() {
		return busicodename;
	}
	public void setBusicodename(String busicodename) {
		this.busicodename = busicodename;
	}
	public long getOperdiscountfee() {
		return operdiscountfee;
	}
	public void setOperdiscountfee(long operdiscountfee) {
		this.operdiscountfee = operdiscountfee;
	}
	public String getOperdiscountdesc() {
		return operdiscountdesc;
	}
	public void setOperdiscountdesc(String operdiscountdesc) {
		this.operdiscountdesc = operdiscountdesc;
	}
}
