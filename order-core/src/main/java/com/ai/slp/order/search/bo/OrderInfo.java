package com.ai.slp.order.search.bo;
import java.util.Date;
import java.util.List;

public class OrderInfo {
//	private String tenantid;
//	private long acctid;
	private long porderid; //父订单
	private String userid;//用户id
	private String username; //用户名称
	private String usertel; //用户手机号
	private String flag;//业务标识
	private String deliveryflag;//是否需要物流
	private String deliveryflagname; //翻译是否需要物流
	private long discountfee;//优惠额
	private long adjustfee;//实支付
	private String contacttel;//收货人联系手机号
	private long points;//积分
	private long totalprodsize; //商品总个数
	private Date ordertime;//订单下单时间
	private String parentorderstate;//父订单状态
	private String supplierid;//供应商id
	private String ifwarning;//是否预警
	private String warningtype;//预警类型
	private String chlid;//订单来源
	private String accountid;//积分账户id
	private String token;//积分令牌id
	private String downstreamorderid; //用户消费积分oid
	private String ordertype;  //订单类型
	private String paystyle; //支付方式
	private String invoicetype; //发票类型
	private String invoicetitle;//发票抬头
	private String invoicecontent;//发票登记打印内容
	private String invoicestatus; //发票状态
	private String expressoddnumber;//物流单号
	private String contactcompany; //到件方公司
	private String contactname;//收件人姓名
	private String logisticstype;//配送方式
	private String provincecode;//收件人省份
	private String citycode;//地市
	private String countycode;//区县
	private String postcode;//邮编
	private String areacode;//收件人末级区域
	private String address;//详细地址
	private String expressid; //物流公司id
	private String buyertaxpayernumber;//纳税人识别号
	private String buyerbankname;//购货方开户行名称
	private String buyerbankaccount;//购货方开户行账号
	private long balacneifid; //支付机构id
	private String externalid; //外部流水号
	
	private List<OrdProdExtend> ordextendes;
	
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
	public String getExpressid() {
		return expressid;
	}
	public void setExpressid(String expressid) {
		this.expressid = expressid;
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
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
}
