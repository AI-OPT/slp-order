package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class OrdProductInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单品Id
     */
    private String skuId;
    
    /**
     * 购买数量
     */
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

	public long getGiveJF() {
		return giveJF;
	}

	public void setGiveJF(long giveJF) {
		this.giveJF = giveJF;
	}
}
