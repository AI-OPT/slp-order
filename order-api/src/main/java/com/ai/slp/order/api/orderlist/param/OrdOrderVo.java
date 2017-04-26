package com.ai.slp.order.api.orderlist.param;

import java.util.Date;
import java.util.List;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 订单表 Date: 2016年5月3日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangqiang7
 */
public class OrdOrderVo extends BaseInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单来源 受理渠道
	 */
	private String chlid;
	
	private String chlidname;

	/**
	 * 仓库ID
	 */
	private String routeid;//--
	/**
	 * 仓库信息
	 */
	private String routename;//--
	/**
	 * 父订单id
	 */
	private Long parentorderid;
	/**
	 * 支付流水号
	 */
	private Long balacneifid;//--
	/**
	 * 外部流水号
	 */
	private String externalid;//--

	/**
	 * 买家帐号 userid
	 */
	private String userid;//--

	/**
	 * 用户姓名
	 */
	private String username;

	/**
	 * 支付帐号
	 */
	private Long acctid;//--

	/**
	 * 买家留言 订单备注
	 */
	private String remark;

	/**
	 * 业务订单ID
	 */
	private Long orderid;

	/**
	 * 原始订单号
	 */
	private Long origorderid;

	/**
	 * 积分账户id
	 */
	private String accountid;
	
	/**
	 * 积分令牌
	 */
	private String token;

	/**
	 * 积分中心返回的id
	 */
	private String downstreamorderid;//--

	/**
	 * 业务类型
	 */
	private String busicode;//--

	/**
	 * 业务类型展示名称
	 */
	private String busicodename;

	/**
	 * 订单类型
	 */
	private String ordertype;//--

	/**
	 * 订单类型展示名称
	 */
	private String ordertypename;
	/**
	 * 订单状态(后厂)
	 */
	private String state;
	/**
	 * 订单状态展示
	 */
	private String statename;
	/**
	 * 支付方式
	 */
	private String paystyle;//--
	/**
	 * 支付方式显示值
	 */
	private String paystylename;//--
	/**
	 * 支付时间
	 */
	private Date paytime;//--
	/**
	 * 下单时间
	 */
	private Date ordertime;
	/**
	 * 总费用
	 */
	private Long totalfee;
	/**
	 * 总优惠金额
	 */
	private Long discountfee;
	/**
	 * 减免金额
	 */
	private long operdiscountfee;//--
	/**
	 * 减免原因
	 */
	private String operdiscountdesc;//--
	/**
	 * 总应收费用
	 */
	private Long adjustfee;
	/**
	 * 总实收费用
	 */
	private Long paidfee;
	/**
	 * 总待收费用
	 */
	private Long payfee;

	/**
	 * 运费
	 */
	private Long freight;

	/**
	 * 发票类型
	 */
	private String invoicetype;//--

	/**
	 * 发票类型展示名称
	 */
	private String invoicetypename;

	/**
	 * 发票抬头
	 */
	private String invoicetitle;

	/**
	 * 登记打印内容
	 */
	private String invoicecontent;

	/**
	 * 发票打印状态
	 */
	private String invoicestatus;//--
	/**
	 * 物流单号
	 */
	private String expressoddnumber;//--
	/**
	 * 到件方公司
	 */
	private String contactcompany;
	/**
	 * 收件人姓名
	 */
	private String contactname;
	/**
	 * 售后页面客户手机
	 */
	private String aftercontacttel;//--
	/**
	 * 售后页面收货信息
	 */
	private String aftercontactinfo;//--

	/**
	 * 收件人电话
	 */
	private String contacttel;

	/**
	 * 配送方式
	 */
	private String logisticstype;

	/**
	 * 收件人省份
	 */
	private String provincecode;

	/**
	 * 收件人地市
	 */
	private String citycode;

	/**
	 * 收件人区县
	 */
	private String countycode;

	/**
	 * 收件人邮编
	 */
	private String postcode;

	/**
	 * 收件人末级区域
	 */
	private String areacode;//--

	/**
	 * 详细地址(自提地址)
	 */
	private String address;

	/**
	 * 物流公司ID
	 */
	private String expressid;//--

	/**
	 * 受理工号
	 */
	private String operid;//--

	/**
	 * 纳税人识别号
	 */
	private String buyertaxpayernumber;

	/**
	 * 购货方开户行名称
	 */
	private String buyerbankname;

	/**
	 * 购货方开户行帐号
	 */
	private String buyerbankaccount;
	/**
	 * 商品集合
	 */
	private List<OrdProductVo> productList;
	public String getRouteid() {
		return routeid;
	}
	public void setRouteid(String routeid) {
		this.routeid = routeid;
	}
	public String getRoutename() {
		return routename;
	}
	public void setRoutename(String routename) {
		this.routename = routename;
	}
	public Long getParentorderid() {
		return parentorderid;
	}
	public void setParentorderid(Long parentorderid) {
		this.parentorderid = parentorderid;
	}
	public Long getBalacneifid() {
		return balacneifid;
	}
	public void setBalacneifid(Long balacneifid) {
		this.balacneifid = balacneifid;
	}
	public String getExternalid() {
		return externalid;
	}
	public void setExternalid(String externalid) {
		this.externalid = externalid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getAcctid() {
		return acctid;
	}
	public void setAcctid(Long acctid) {
		this.acctid = acctid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getOrigorderid() {
		return origorderid;
	}
	public void setOrigorderid(Long origorderid) {
		this.origorderid = origorderid;
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
	public String getBusicode() {
		return busicode;
	}
	public void setBusicode(String busicode) {
		this.busicode = busicode;
	}
	public String getBusicodename() {
		return busicodename;
	}
	public void setBusicodename(String busicodename) {
		this.busicodename = busicodename;
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
	public String getPaystyle() {
		return paystyle;
	}
	public void setPaystyle(String paystyle) {
		this.paystyle = paystyle;
	}
	public String getPaystylename() {
		return paystylename;
	}
	public void setPaystylename(String paystylename) {
		this.paystylename = paystylename;
	}
	public Date getPaytime() {
		return paytime;
	}
	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public Long getTotalfee() {
		return totalfee;
	}
	public void setTotalfee(Long totalfee) {
		this.totalfee = totalfee;
	}
	public Long getDiscountfee() {
		return discountfee;
	}
	public void setDiscountfee(Long discountfee) {
		this.discountfee = discountfee;
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
	public Long getAdjustfee() {
		return adjustfee;
	}
	public void setAdjustfee(Long adjustfee) {
		this.adjustfee = adjustfee;
	}
	public Long getPaidfee() {
		return paidfee;
	}
	public void setPaidfee(Long paidfee) {
		this.paidfee = paidfee;
	}
	public Long getPayfee() {
		return payfee;
	}
	public void setPayfee(Long payfee) {
		this.payfee = payfee;
	}
	public Long getFreight() {
		return freight;
	}
	public void setFreight(Long freight) {
		this.freight = freight;
	}
	public String getInvoicetype() {
		return invoicetype;
	}
	public void setInvoicetype(String invoicetype) {
		this.invoicetype = invoicetype;
	}
	public String getInvoicetypename() {
		return invoicetypename;
	}
	public void setInvoicetypename(String invoicetypename) {
		this.invoicetypename = invoicetypename;
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
	public String getAftercontacttel() {
		return aftercontacttel;
	}
	public void setAftercontacttel(String aftercontacttel) {
		this.aftercontacttel = aftercontacttel;
	}
	public String getAftercontactinfo() {
		return aftercontactinfo;
	}
	public void setAftercontactinfo(String aftercontactinfo) {
		this.aftercontactinfo = aftercontactinfo;
	}
	public String getContacttel() {
		return contacttel;
	}
	public void setContacttel(String contacttel) {
		this.contacttel = contacttel;
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
	public String getOperid() {
		return operid;
	}
	public void setOperid(String operid) {
		this.operid = operid;
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
	public List<OrdProductVo> getProductList() {
		return productList;
	}
	public void setProductList(List<OrdProductVo> productList) {
		this.productList = productList;
	}
	public String getChlidname() {
		return chlidname;
	}
	public void setChlidname(String chlidname) {
		this.chlidname = chlidname;
	}
	public String getChlid() {
		return chlid;
	}
	public void setChlid(String chlid) {
		this.chlid = chlid;
	}
}
