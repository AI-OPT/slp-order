package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class OrdProductInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单品Id
     */
    @NotBlank(message = "单品Id不能为空")
    private String skuId;
    
    /**
     * 销售商id
     */
    @NotBlank(message="销售商id不能为空")
    private Long supplierId;

    /**
     * 购买数量
     */
    @NotBlank(message = "购买数量不能为空")
    private int buySum;
    
    /**
     * 赠送积分
     */
    private long giveJF;

    /**
     * 运营商
     */
    private String basicOrgId;

    /**
     * 省份
     */
    private String provinceCode;

    /**
     * 充值面额
     */
    private String chargeFee;

    /**
     * 优惠费用
     */
    private long discountFee;

    /**
     * 减免金额
     */
    private long operDiscountFee;

    /**
     * 减免原因
     */
    private String operDiscountDesc;

    public long getDiscountFee() {
        return discountFee;
    }

    public long getOperDiscountFee() {
        return operDiscountFee;
    }

    public String getOperDiscountDesc() {
        return operDiscountDesc;
    }

    public void setDiscountFee(long discountFee) {
        this.discountFee = discountFee;
    }

    public void setOperDiscountFee(long operDiscountFee) {
        this.operDiscountFee = operDiscountFee;
    }

    public void setOperDiscountDesc(String operDiscountDesc) {
        this.operDiscountDesc = operDiscountDesc;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getBuySum() {
        return buySum;
    }

    public void setBuySum(int buySum) {
        this.buySum = buySum;
    }

    public String getBasicOrgId() {
        return basicOrgId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public String getChargeFee() {
        return chargeFee;
    }

    public void setBasicOrgId(String basicOrgId) {
        this.basicOrgId = basicOrgId;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public void setChargeFee(String chargeFee) {
        this.chargeFee = chargeFee;
    }

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public long getGiveJF() {
		return giveJF;
	}

	public void setGiveJF(long giveJF) {
		this.giveJF = giveJF;
	}
}
