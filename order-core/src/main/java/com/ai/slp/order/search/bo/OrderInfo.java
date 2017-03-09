package com.ai.slp.order.search.bo;
import java.util.Date;
import java.util.List;

public class OrderInfo {
	private String tenantid;
	private String chlid;
	private long porderid;
//	private String userid;
	private String username;
	private String usertel;
	private String deliveryflag;
	private String deliveryflagname;
	private long discountfee;
	private long adjustfee;
	private String contacttel;
	private long points;
//	private long totalcouponfee;
	private long totalprodsize;
//	private String routeid;
	private Date ordertime;
	private List<OrdProdExtend> ordextendes;
	
	public String getTenantid() {
		return tenantid;
	}
	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}
	public String getChlid() {
		return chlid;
	}
	public void setChlid(String chlid) {
		this.chlid = chlid;
	}
	public long getPorderid() {
		return porderid;
	}
	public void setPorderid(long porderid) {
		this.porderid = porderid;
	}
/*	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}*/
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsertel() {
		return usertel;
	}
	public void setUsertel(String usertel) {
		this.usertel = usertel;
	}
	public String getDeliveryflag() {
		return deliveryflag;
	}
	public void setDeliveryflag(String deliveryflag) {
		this.deliveryflag = deliveryflag;
	}
	public String getDeliveryflagname() {
		return deliveryflagname;
	}
	public void setDeliveryflagname(String deliveryflagname) {
		this.deliveryflagname = deliveryflagname;
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
	public String getContacttel() {
		return contacttel;
	}
	public void setContacttel(String contacttel) {
		this.contacttel = contacttel;
	}
	public long getPoints() {
		return points;
	}
	public void setPoints(long points) {
		this.points = points;
	}
/*	public long getTotalcouponfee() {
		return totalcouponfee;
	}
	public void setTotalcouponfee(long totalcouponfee) {
		this.totalcouponfee = totalcouponfee;
	}*/
	public long getTotalprodsize() {
		return totalprodsize;
	}
	public void setTotalprodsize(long totalprodsize) {
		this.totalprodsize = totalprodsize;
	}
/*	public String getRouteid() {
		return routeid;
	}
	public void setRouteid(String routeid) {
		this.routeid = routeid;
	}*/
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public List<OrdProdExtend> getOrdextendes() {
		return ordextendes;
	}
	public void setOrdextendes(List<OrdProdExtend> ordextendes) {
		this.ordextendes = ordextendes;
	}
}
