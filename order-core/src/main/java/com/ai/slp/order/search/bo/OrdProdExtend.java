package com.ai.slp.order.search.bo;

import java.util.List;

public class OrdProdExtend {
	private long  orderid; //订单id
	private long  parentorderid; //父订单id
	private String  state;//状态
	private String  statename;//状态名称
	private String  busicode;//业务类型
	private long  prodsize;//商品数量
	private String routeid; //仓库id
	private String operid; //操作员id
	private long origorderid; //原始订单id
	private long totalfee; //总费用
	private long discountfee; //优惠额
	private long adjustfee;//实支付
	private long paidfee; //退款金额
	private long payfee;//实支付
	private long freight;//运费
	private String afterexpressoddnumber;  //售后填写物流单号
	private String afterexpressid; //售后物流公司id
	private String remark;  //子订单为留言信息,售后订单为退换货,退款理由
	
	private List<ProdInfo> prodinfos;
	public String getOperid() {
		return operid;
	}
	public void setOperid(String operid) {
		this.operid = operid;
	}
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
	public String getAfterexpressoddnumber() {
		return afterexpressoddnumber;
	}
	public void setAfterexpressoddnumber(String afterexpressoddnumber) {
		this.afterexpressoddnumber = afterexpressoddnumber;
	}
	public String getAfterexpressid() {
		return afterexpressid;
	}
	public void setAfterexpressid(String afterexpressid) {
		this.afterexpressid = afterexpressid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
