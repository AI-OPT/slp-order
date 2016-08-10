package com.ai.slp.order.api.orderlist.param;


import com.ai.opt.base.vo.BaseInfo;

/**
 * api查询返回的信息
 * @author caofz
 *
 */
public class OrdOrderApiVo extends BaseInfo{

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private String userId;
	
	/**
	 * 帐号ID
	 */
	private long acctId;
	
	/**
	 * 订购ID
	 */
	private long subsId;

	/**
     * 业务类型
     */
    private String busiCode;
    
    /**
     * 订单类型
     */
    private String orderType;
    
    /**
     * 是否需要物流
     */
    private String deliveryFlag;

    /**
     * 订单简要信息
     */
    private String orderDesc;
    
    /**
     * 订单关键词
     */
    private String keywords;

    /**
     * 订单备注
     */
    private String remark;
    
    /**
     * 订单状态
     */
    private String state;
    
    /**
     * 默认支付方式
     */
    private String payStyle;
    
    /**
     * 总费用
     */
    private Long totalFee;
    
    /**
     * 总优惠金额
     */
    private Long discountFee;
    
    /**
     * 总减免费用
     */
    private Long operDiscountFee;
    
    /**
     * 减免原因
     */
    private String operDiscountDesc;
    
    /**
     * 总应收费用
     */
    private Long adjustFee;
    
    /**
     * 总已收费用
     */
    private Long paidFee;
    
	/**
	 * 附加信息
	 */
	private String infoJson;
	
	private OrdProductApiVo ordProductApiVo;
	
	public OrdProductApiVo getOrdProductApiVo() {
		return ordProductApiVo;
	}

	public void setOrdProductApiVo(OrdProductApiVo ordProductApiVo) {
		this.ordProductApiVo = ordProductApiVo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getAcctId() {
		return acctId;
	}

	public void setAcctId(long acctId) {
		this.acctId = acctId;
	}

	public long getSubsId() {
		return subsId;
	}

	public void setSubsId(long subsId) {
		this.subsId = subsId;
	}

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getDeliveryFlag() {
		return deliveryFlag;
	}

	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPayStyle() {
		return payStyle;
	}

	public void setPayStyle(String payStyle) {
		this.payStyle = payStyle;
	}

	public Long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}

	public Long getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(Long discountFee) {
		this.discountFee = discountFee;
	}

	public Long getOperDiscountFee() {
		return operDiscountFee;
	}

	public void setOperDiscountFee(Long operDiscountFee) {
		this.operDiscountFee = operDiscountFee;
	}

	public String getOperDiscountDesc() {
		return operDiscountDesc;
	}

	public void setOperDiscountDesc(String operDiscountDesc) {
		this.operDiscountDesc = operDiscountDesc;
	}

	public Long getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(Long adjustFee) {
		this.adjustFee = adjustFee;
	}

	public Long getPaidFee() {
		return paidFee;
	}

	public void setPaidFee(Long paidFee) {
		this.paidFee = paidFee;
	}

	public String getInfoJson() {
		return infoJson;
	}

	public void setInfoJson(String infoJson) {
		this.infoJson = infoJson;
	}
	
	

}
