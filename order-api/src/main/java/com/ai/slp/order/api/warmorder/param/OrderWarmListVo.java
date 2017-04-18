package com.ai.slp.order.api.warmorder.param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.ai.opt.base.vo.BaseInfo;

public class OrderWarmListVo extends BaseInfo {

	private static final long serialVersionUID = 1L;
	/**
	 * 订单来源
	 */
	private String chlid;
	 /**
	  * 订单来源展示名称
	  */
    private String chlidname;
	/**
	 * 父订单号
	 */
	private Long porderid;
	/**
	 * 用户名称
	 */
	private String username;
	/**
	 * 用户手机（长虹提供）
	 */
	private String usertel;
	/**
	 * 收货人手机
	 */
	private String contacttel;

	/**
	 * 是否预警订单
	 */
	private String ifWarning;
	/**
	 * 预警类型
	 */
	private String warningType;
	/**
	 * 是否需要物流
	 */
	private String deliveryflag;
	 //是否需要物流展示名称
    private String deliveryflagname;
	/**
	 * 商品信息
	 */
	private List<ProductListInfo> ordextendes;
	/**
	 * 订单状态
	 */
	private String state;
	/**
	 * 总优惠金额
	 */
	private Long discountfee;
	/**
	 * 总实收费用
	 */
	private Long adjustfee;
	/**
	 * 下单时间
	 */
	private Date ordertime;
	public String getChlid() {
		return chlid;
	}
	public void setChlid(String chlid) {
		this.chlid = chlid;
	}
	public String getChlidname() {
		return chlidname;
	}
	public void setChlidname(String chlidname) {
		this.chlidname = chlidname;
	}
	public Long getPorderid() {
		return porderid;
	}
	public void setPorderid(Long porderid) {
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
	public String getContacttel() {
		return contacttel;
	}
	public void setContacttel(String contacttel) {
		this.contacttel = contacttel;
	}
	public String getIfWarning() {
		return ifWarning;
	}
	public void setIfWarning(String ifWarning) {
		this.ifWarning = ifWarning;
	}
	public String getWarningType() {
		return warningType;
	}
	public void setWarningType(String warningType) {
		this.warningType = warningType;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getDiscountfee() {
		return discountfee;
	}
	public void setDiscountfee(Long discountfee) {
		this.discountfee = discountfee;
	}
	public Long getAdjustfee() {
		return adjustfee;
	}
	public void setAdjustfee(Long adjustfee) {
		this.adjustfee = adjustfee;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public List<ProductListInfo> getOrdextendes() {
		return ordextendes;
	}
	public void setOrdextendes(List<ProductListInfo> ordextendes) {
		this.ordextendes = ordextendes;
	}
}
