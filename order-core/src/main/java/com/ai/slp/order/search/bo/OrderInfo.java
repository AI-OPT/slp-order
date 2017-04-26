package com.ai.slp.order.search.bo;
import java.util.Date;
import java.util.List;

public class OrderInfo {
//	private String tenantid;
	private long porderid;
	private String username;
	private String usertel;
	private String flag;
	private String deliveryflag;
	private String deliveryflagname;
	private long discountfee;
	private long adjustfee;
	private String contacttel;
	private long points;
	private long totalprodsize;
	private Date ordertime;
	private Date paytime;
	private String parentorderstate;
	private String supplierid;
	private String ifwarning;
	private String warningtype;
	private List<OrdProdExtend> ordextendes;
	
	
	private String chlid;
	private String accountid;
//	private String userid;
//	private long acctid;
	private String token;
	private String downstreamorderid;      //TODO
	private String ordertype;  
	private String ordertypename;
	private String paystyle; 
	private String invoicetype; 
	private String invoicetitle;
	private String invoicecontent;
	private String invoicestatus; //TODO
	private String expressoddnumber;
	private String contactcompany;
	private String contactname;
	private String logisticstype;
	private String provincecode;
	private String citycode;
	private String countycode;
	private String postcode;
	private String areacode;
	private String address;
	private String expressId; //TODO
//	private String operid; 
	private String buyertaxpayernumber;
	private String buyerbankname;
	private String buyerbankaccount;
	private long balacneifid; //TODO
	private String externalid; //TODO
	private String remark;

	
	public long getPorderid() {
		return porderid;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public void setPorderid(long porderid) {
		this.porderid = porderid;
	}
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
	public long getTotalprodsize() {
		return totalprodsize;
	}
	public void setTotalprodsize(long totalprodsize) {
		this.totalprodsize = totalprodsize;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public String getParentorderstate() {
		return parentorderstate;
	}
	public void setParentorderstate(String parentorderstate) {
		this.parentorderstate = parentorderstate;
	}
	public String getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}
	public String getIfwarning() {
		return ifwarning;
	}
	public void setIfwarning(String ifwarning) {
		this.ifwarning = ifwarning;
	}
	public String getWarningtype() {
		return warningtype;
	}
	public void setWarningtype(String warningtype) {
		this.warningtype = warningtype;
	}
	public List<OrdProdExtend> getOrdextendes() {
		return ordextendes;
	}
	public void setOrdextendes(List<OrdProdExtend> ordextendes) {
		this.ordextendes = ordextendes;
	}
	public String getAccountid() {
		return accountid;
	}
	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getDownstreamorderid() {
		return downstreamorderid;
	}
	public void setDownstreamorderid(String downstreamorderid) {
		this.downstreamorderid = downstreamorderid;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public String getOrdertypename() {
		return ordertypename;
	}
	public void setOrdertypename(String ordertypename) {
		this.ordertypename = ordertypename;
	}
	public String getInvoicetitle() {
		return invoicetitle;
	}
	public void setInvoicetitle(String invoicetitle) {
		this.invoicetitle = invoicetitle;
	}
	public String getInvoicecontent() {
		return invoicecontent;
	}
	public void setInvoicecontent(String invoicecontent) {
		this.invoicecontent = invoicecontent;
	}
	public String getInvoicestatus() {
		return invoicestatus;
	}
	public void setInvoicestatus(String invoicestatus) {
		this.invoicestatus = invoicestatus;
	}
	public String getExpressoddnumber() {
		return expressoddnumber;
	}
	public void setExpressoddnumber(String expressoddnumber) {
		this.expressoddnumber = expressoddnumber;
	}
	public String getContactcompany() {
		return contactcompany;
	}
	public void setContactcompany(String contactcompany) {
		this.contactcompany = contactcompany;
	}
	public String getContactname() {
		return contactname;
	}
	public void setContactname(String contactname) {
		this.contactname = contactname;
	}
	public String getLogisticstype() {
		return logisticstype;
	}
	public void setLogisticstype(String logisticstype) {
		this.logisticstype = logisticstype;
	}
	public String getProvincecode() {
		return provincecode;
	}
	public void setProvincecode(String provincecode) {
		this.provincecode = provincecode;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getCountycode() {
		return countycode;
	}
	public void setCountycode(String countycode) {
		this.countycode = countycode;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
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
	public String getBuyertaxpayernumber() {
		return buyertaxpayernumber;
	}
	public void setBuyertaxpayernumber(String buyertaxpayernumber) {
		this.buyertaxpayernumber = buyertaxpayernumber;
	}
	public String getBuyerbankname() {
		return buyerbankname;
	}
	public void setBuyerbankname(String buyerbankname) {
		this.buyerbankname = buyerbankname;
	}
	public String getBuyerbankaccount() {
		return buyerbankaccount;
	}
	public void setBuyerbankaccount(String buyerbankaccount) {
		this.buyerbankaccount = buyerbankaccount;
	}
	public long getBalacneifid() {
		return balacneifid;
	}
	public void setBalacneifid(long balacneifid) {
		this.balacneifid = balacneifid;
	}
	public String getExternalid() {
		return externalid;
	}
	public void setExternalid(String externalid) {
		this.externalid = externalid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getPaytime() {
		return paytime;
	}
	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}
	public String getChlid() {
		return chlid;
	}
	public void setChlid(String chlid) {
		this.chlid = chlid;
	}
	public String getPaystyle() {
		return paystyle;
	}
	public void setPaystyle(String paystyle) {
		this.paystyle = paystyle;
	}
	public String getInvoicetype() {
		return invoicetype;
	}
	public void setInvoicetype(String invoicetype) {
		this.invoicetype = invoicetype;
	}
}
