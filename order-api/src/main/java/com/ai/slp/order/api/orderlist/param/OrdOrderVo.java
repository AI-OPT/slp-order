package com.ai.slp.order.api.orderlist.param;

import java.sql.Timestamp;
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
     * 订单来源  受理渠道
     */
    private String chlId;
    
    /**
     * 仓库ID
     */
    private String routeId;
    
    /**
     * 仓库信息 ????
     */
    
    /**
     * 父订单id
     */
    private Long parentOrderId;
    
    /**
     * 支付流水号
     */
    private Long balacneIfId;
    
    /**
     * 买家帐号      userid
     */
    private String userId;
    
    /**
     * 买家留言  订单备注
     */
    private String remark;
    
    /**
     * 业务订单ID
     */
    private Long orderId;
    

    /**
     * 业务类型
     */
    private String busiCode;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订单状态(后厂)
     */
    private String state;

    /**
     * 订单状态展示
     */
    private String stateName;

    /**
     * 支付方式
     */
    private String payStyle;

    /**
     * 支付方式显示值
     */
    private String payStyleName;

    /**
     * 支付时间
     */
    private Timestamp payTime;

    /**
     * 下单时间
     */
    private Timestamp orderTime;

    /**
     * 手机个数
     */
    private Integer phoneCount;

    /**
     * 总费用
     */
    private Long totalFee;

    /**
     * 总优惠金额
     */
    private Long discountFee;

    /**
     * 减免金额
     */
    private long operDiscountFee;

    /**
     * 总应收费用
     */
    private Long adjustFee;

    /**
     * 总实收费用
     */
    private Long paidFee;

    /**
     * 总待收费用
     */
    private Long payFee;
    
    /**
     * 发票类型
     */
    private String invoiceType;
    
    /**
     * 发票抬头
     */
    private String invoiceTitle;
    
    /**
     * 登记打印内容
     */
    private String invoiceContent;
    
    /**
     * 物流单号
     */
    private String expressOddNumber;
    
    /**
     * 到件方公司
     */
    private String contactCompany;
    
    /**
     * 收件人姓名
     */
    private String contactName;
    
    /**
     * 收件人电话
     */
    private String contactTel;
    
    /**
     * 收件人省份
     */
    private String provinceCode;
    
    /**
     * 收件人地市
     */
    private String cityCode;
    
    /**
     * 收件人区县
     */
    private String countyCode;
    
    /**
     * 收件人邮编
     */
    private String postCode;
    
    /**
     * 收件人末级区域
     */
    private String areaCode;
    
    /**
     * 详细地址(自提地址)
     */
    private String address;
    
    /**
     * 物流公司ID
     */
    private String expressId;

    /**
     * 支付信息
     */
    private List<OrderPayVo> payDataList;

    /**
     * 商品集合
     */
    private List<OrdProductVo> productList;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
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

    public Long getAdjustFee() {
        return adjustFee;
    }

    public void setAdjustFee(Long adjustFee) {
        this.adjustFee = adjustFee;
    }

    public List<OrdProductVo> getProductList() {
        return productList;
    }

    public void setProductList(List<OrdProductVo> productList) {
        this.productList = productList;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getPayStyle() {
        return payStyle;
    }

    public void setPayStyle(String payStyle) {
        this.payStyle = payStyle;
    }

    public String getPayStyleName() {
        return payStyleName;
    }

    public void setPayStyleName(String payStyleName) {
        this.payStyleName = payStyleName;
    }

    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    public Long getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(Long paidFee) {
        this.paidFee = paidFee;
    }

    public Long getPayFee() {
        return payFee;
    }

    public void setPayFee(Long payFee) {
        this.payFee = payFee;
    }

    public List<OrderPayVo> getPayDataList() {
        return payDataList;
    }

    public void setPayDataList(List<OrderPayVo> payDataList) {
        this.payDataList = payDataList;
    }

    public Integer getPhoneCount() {
        return phoneCount;
    }

    public void setPhoneCount(Integer phoneCount) {
        this.phoneCount = phoneCount;
    }

    public long getOperDiscountFee() {
        return operDiscountFee;
    }

    public void setOperDiscountFee(long operDiscountFee) {
        this.operDiscountFee = operDiscountFee;
    }

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public String getExpressOddNumber() {
		return expressOddNumber;
	}

	public void setExpressOddNumber(String expressOddNumber) {
		this.expressOddNumber = expressOddNumber;
	}

	public String getContactCompany() {
		return contactCompany;
	}

	public void setContactCompany(String contactCompany) {
		this.contactCompany = contactCompany;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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

	public String getChlId() {
		return chlId;
	}

	public void setChlId(String chlId) {
		this.chlId = chlId;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public Long getParentOrderId() {
		return parentOrderId;
	}

	public void setParentOrderId(Long parentOrderId) {
		this.parentOrderId = parentOrderId;
	}

	public Long getBalacneIfId() {
		return balacneIfId;
	}

	public void setBalacneIfId(Long balacneIfId) {
		this.balacneIfId = balacneIfId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
